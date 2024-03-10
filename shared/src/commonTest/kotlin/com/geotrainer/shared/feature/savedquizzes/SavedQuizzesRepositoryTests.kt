package com.geotrainer.shared.feature.savedquizzes

import androidx.datastore.preferences.core.intPreferencesKey

import com.geotrainer.shared.utils.BaseTest
import com.geotrainer.shared.utils.PreferencesDataStore

import io.mockative.Mock
import io.mockative.classOf
import io.mockative.coEvery
import io.mockative.coVerify
import io.mockative.every
import io.mockative.mock
import io.mockative.verify

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf

internal class SavedQuizzesRepositoryTests : BaseTest<SavedQuizzesRepository>() {
    @Mock
    private val dataStore = mock(classOf<PreferencesDataStore>())
    private val testPreferenceKey = intPreferencesKey("test_key")

    override fun createSut(testScope: CoroutineScope) = SavedQuizzesRepositoryImpl(
        dataStore = dataStore,
        storedCountKey = testPreferenceKey
    )

    @Test
    fun getStoredCountFlowReturnsCurrentStoredFlow() = runBlockingTest {
        // Given
        // Stored count is 2
        val expectedFlow = flowOf(2)
        every {
            dataStore.getPreference(testPreferenceKey, 0)
        }.returns(expectedFlow)

        // When
        // Get stored count flow
        val actualFlow = sut.getStoredCountFlow()

        // Then
        // The flow is the expected flow
        assertEquals(expectedFlow, actualFlow)
        verify {
            dataStore.getPreference(testPreferenceKey, 0)
        }.wasInvoked(1)
    }

    @Test
    fun incrementCountCallsWriteWithIncrementedValue() = runBlockingTest {
        // Given
        // dataStore.write runs
        // Stored count is 0
        val storedCount = 0
        coEvery {
            dataStore.write(
                testPreferenceKey,
                storedCount + 1
            )
        }.returns(Unit)

        every { dataStore.getPreference(testPreferenceKey, 0) }
            .returns(flowOf(storedCount))

        // When
        // Increment stored count
        sut.incrementStoredCount()

        // Then
        // dataStore.write is called with the incremented value
        coVerify {
            dataStore.write(testPreferenceKey, storedCount + 1)
        }.wasInvoked(exactly = 1)
    }
}
