package com.geotrainer.shared.type

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * The disjoint union of two types
 */

sealed class DataResult<out A, out E> {
    fun isSuccess() = this is Success
    fun isFailure() = this is Failure
    data class Success<A>(val a: A) : DataResult<A, Nothing>()
    data class Failure<E>(val e: E) : DataResult<Nothing, E>()

    companion object {
        fun <A, E, T> fold(
            result: DataResult<A, E>,
            fe: (E) -> T,
            fa: (A) -> T
        ): T = when (result) {
            is Failure -> fe(result.e)
            is Success -> fa(result.a)
        }

        suspend fun <A, E, T> suspendFold(
            result: DataResult<A, E>,
            fa: suspend (A) -> T,
            fe: suspend (E) -> T
        ): T = when (result) {
            is Failure -> fe(result.e)
            is Success -> fa(result.a)
        }

        fun <A, E1, E2> mapFailure(
            result: DataResult<A, E1>,
            fe: (E1) -> E2
        ): DataResult<A, E2> = fold(result, { Failure(fe(it)) }, { Success(it) })

        suspend fun <A, E1, E2> suspendMapFailure(
            result: DataResult<A, E1>,
            fe: suspend (E1) -> E2
        ): DataResult<A, E2> = suspendFold(result, { Success(it) }, { Failure(fe(it)) })

        fun <E, A1, A2> mapSuccess(
            result: DataResult<A1, E>,
            fa: (A1) -> A2
        ): DataResult<A2, E> = fold(result, { Failure(it) }, { Success(fa(it)) })

        suspend fun <E, A1, A2> suspendMapSuccess(
            result: DataResult<A1, E>,
            fa: suspend (A1) -> A2
        ): DataResult<A2, E> = suspendFold(result, { Success(fa(it)) }, { Failure(it) })

        fun <E, A1, A2> flatMapSuccess(
            result: DataResult<A1, E>,
            fa: (A1) -> DataResult<A2, E>
        ): DataResult<A2, E> = when (result) {
            is Failure -> result
            is Success -> fa(result.a)
        }

        fun <A, E1, E2> flatMapFailure(
            result: DataResult<A, E1>,
            fe: (E1) -> DataResult<A, E2>
        ): DataResult<A, E2> = when (result) {
            is Failure -> fe(result.e)
            is Success -> result
        }

        suspend fun <A1, A2, E> suspendFlatMapSuccess(
            result: DataResult<A1, E>,
            fa: suspend (A1) -> DataResult<A2, E>
        ): DataResult<A2, E> = when (result) {
            is Failure -> result
            is Success -> fa(result.a)
        }

        suspend fun <A, E1, E2> suspendFlatMapFailure(
            result: DataResult<A, E1>,
            fe: suspend (E1) -> DataResult<A, E2>
        ): DataResult<A, E2> = when (result) {
            is Failure -> fe(result.e)
            is Success -> result
        }

        fun <A, E> getOrElse(a: A?, orElse: () -> E): DataResult<A, E> = a?.let {
            Success(it)
        } ?: Failure(orElse())
    }
}

fun <A, E, T> DataResult<A, E>.fold(
    fe: (E) -> T, fa: (A) -> T
): T = DataResult.fold(this, fe, fa)
fun <A, E> DataResult<A, E>.dataOrNull() = (this as? DataResult.Success)?.a
fun <A, E> DataResult<A, E>.errorOrNull() = (this as? DataResult.Failure)?.e
fun <A, E> DataResult<A, E>.dataOrThrow() = when (this) {
    is DataResult.Success -> this.a
    is DataResult.Failure -> throw when (e) {
        is Throwable -> e
        else -> Exception(e.toString())
    }
}

suspend fun <A, E, T> DataResult<A, E>.suspendFold(
    fe: suspend (E) -> T,
    fa: suspend (A) -> T
): T = DataResult.suspendFold(this, fa, fe)

fun <A, E1, E2> DataResult<A, E1>.mapFailure(
    fe: (E1) -> E2
): DataResult<A, E2> = DataResult.mapFailure(this, fe)

suspend fun <A, E1, E2> DataResult<A, E1>.suspendMapFailure(
    fe: suspend (E1) -> E2
): DataResult<A, E2> = DataResult.suspendMapFailure(this, fe)

fun <E, A1, A2> DataResult<A1, E>.mapSuccess(
    fa: (A1) -> A2
): DataResult<A2, E> = DataResult.mapSuccess(this, fa)

suspend fun <E, A1, A2> DataResult<A1, E>.suspendMapSuccess(
    fa: suspend (A1) -> A2
): DataResult<A2, E> = DataResult.suspendMapSuccess(this, fa)

fun <A1, A2, E> DataResult<A1, E>.flatMapSuccess(
    fa: (A1) -> DataResult<A2, E>
): DataResult<A2, E> = DataResult.flatMapSuccess(this, fa)

fun <A, E1, E2> DataResult<A, E1>.flatMapFailure(
    fe: (E1) -> DataResult<A, E2>
): DataResult<A, E2> = DataResult.flatMapFailure(this, fe)

suspend fun <A1, A2, E> DataResult<A1, E>.suspendFlatMapSuccess(
    fa: suspend (A1) -> DataResult<A2, E>
): DataResult<A2, E> = DataResult.suspendFlatMapSuccess(this, fa)

suspend fun <A, E1, E2> DataResult<A, E1>.suspendFlatMapFailure(
    fe: suspend (E1) -> DataResult<A, E2>
): DataResult<A, E2> = DataResult.suspendFlatMapFailure(this, fe)

fun <A, E> DataResult<A, E>.tapSuccess(block: (A) -> Unit) = mapSuccess { it.also(block) }
fun <A, E> DataResult<A, E>.tapFailure(block: (E) -> Unit) = mapFailure { it.also(block) }
suspend fun <A, E> DataResult<A, E>.suspendTapFailure(block: suspend (E) -> Unit) = suspendMapFailure {
    block(it)
    it
}

suspend fun <E, A> DataResult<A, E>.suspendTapSuccess(block: suspend (A) -> Unit) =
    suspendMapSuccess {
        block(it)
        it
    }

fun <A> A.success() = DataResult.Success(this)
fun <E> E.failure() = DataResult.Failure(this)

suspend fun <A1, E, A2, Result> combine(
    op1: suspend () -> DataResult<A1, E>,
    op2: suspend () -> DataResult<A2, E>,
    combiner: suspend (A1, A2) -> DataResult<Result, E>
): DataResult<Result, E> = coroutineScope {
    val result1 = async { op1() }
    val result2 = async { op2() }

    val data1 = result1.await().let {
        when (it) {
            is DataResult.Failure -> return@coroutineScope it
            is DataResult.Success -> it.a
        }
    }
    val data2 = result2.await().let {
        when (it) {
            is DataResult.Failure -> return@coroutineScope it
            is DataResult.Success -> it.a
        }
    }

    combiner(data1, data2)
}
