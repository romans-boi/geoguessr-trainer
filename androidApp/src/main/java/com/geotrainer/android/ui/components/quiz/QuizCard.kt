package com.geotrainer.android.ui.components.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.android.R
import com.geotrainer.android.ui.components.GeoTrainerCard
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.theme.GeoTrainerTheme
import com.geotrainer.android.utils.clickableSingle
import com.geotrainer.android.utils.colorIndicator
import com.geotrainer.android.utils.localizedString
import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.model.quiz.Quiz

private const val previewGroup = "Quiz card"

@Composable
fun QuizCard(
    quiz: Quiz,
    onAccessQuiz: () -> Unit,
    isSaved: Boolean,
    onSaveToggled: () -> Unit
) {
    GeoTrainerCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onAccessQuiz
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .heightIn(40.dp)
                    .align(Alignment.CenterVertically)
                    .background(
                        quiz.continent.colorIndicator(),
                        shape = AbsoluteCutCornerShape(topRight = 8.dp, bottomRight = 8.dp)
                    )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = quiz.continent.localizedString(),
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            SaveQuizToggleButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                isSaved = isSaved,
                onSaveToggled = onSaveToggled
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(28.dp),
                tint = GeoTrainerTheme.colors.DarkBlue,
            )

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun SaveQuizToggleButton(
    modifier: Modifier,
    isSaved: Boolean,
    onSaveToggled: () -> Unit
) = Box(
    modifier = modifier
        .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
        .clickableSingle(onClick = onSaveToggled)
) {
    val iconPainter = when (isSaved) {
        true -> painterResource(R.drawable.ic_favourite_filled)
        false -> painterResource(R.drawable.ic_favourite)
    }

    Icon(
        painter = iconPainter,
        contentDescription = null,
        modifier = Modifier
            .size(28.dp)
            .align(Alignment.Center),
        tint = GeoTrainerTheme.colors.DarkBlue,
    )
}

@Composable
@Preview(name = "Quiz", group = previewGroup)
fun QuizCardPreview() = PreviewSurface {
    val savedQuizIds = listOf("1", "3")
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        listOf(
            Quiz(
                quizId = "1",
                title = "First Quiz",
                description = "",
                continent = null
            ),
            Quiz(
                quizId = "2",
                title = "Quiz 2",
                description = "",
                continent = Continent.Africa
            ),
            Quiz(
                quizId = "3",
                title = "3rd quiz with quite a long name if you ask me",
                description = "",
                continent = Continent.Asia
            )
        ).map { quiz ->
            QuizCard(
                quiz = quiz,
                onAccessQuiz = {},
                isSaved = quiz.quizId in savedQuizIds,
                onSaveToggled = {}
            )
        }
    }
}
