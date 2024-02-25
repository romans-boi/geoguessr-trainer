package com.geotrainer.android.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.geotrainer.android.R
import com.geotrainer.android.ui.components.NavigationBarIconsColor
import com.geotrainer.android.ui.components.PrimarySwitch
import com.geotrainer.android.ui.components.Screen
import com.geotrainer.android.ui.components.ScreenSlotBasicScrollable
import com.geotrainer.android.ui.components.StatusBarIconsColor
import com.geotrainer.android.ui.components.SystemBarIconsColor
import com.geotrainer.android.ui.components.navigation.FadeTransitions
import com.geotrainer.android.ui.components.navigation.navgraphs.SettingsNavGraph
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.theme.GeoTrainerTheme
import com.geotrainer.android.utils.clickableSingle
import com.geotrainer.android.utils.resource

import GeoTrainer.shared.MR
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

private const val previewGroup = "Settings"

@SettingsNavGraph(start = true)
@Destination(style = FadeTransitions::class)
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
) {
    Screen(
        systemBarIconsColor = SystemBarIconsColor(
            statusBar = StatusBarIconsColor.Dark,
            navigationBar = NavigationBarIconsColor.Light
        )
    ) {
        SettingsContent(
            onQuizSettings = { /* TODO */ },
            onQuizStatistics = { /* TODO */ },
            onContact = { /* TODO */ },
            onReportIssue = { /* TODO */ },
            onPrivacyPolicy = { /* TODO */ },
            onLicensingInformation = { /* TODO */ },
            onSources = { /* TODO */ },
            onAbout = { /* TODO */ },
        )
    }
}

@Preview(name = "Main Content", group = previewGroup)
@Composable
fun SettingsScreenContentPreview() = PreviewSurface {
    SettingsContent(
        onQuizSettings = {},
        onQuizStatistics = {},
        onContact = {},
        onReportIssue = {},
        onPrivacyPolicy = {},
        onLicensingInformation = {},
        onSources = {},
        onAbout = {},
    )
}

@Composable
private fun SettingsContent(
    onQuizSettings: () -> Unit,
    onQuizStatistics: () -> Unit,
    onContact: () -> Unit,
    onReportIssue: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onLicensingInformation: () -> Unit,
    onSources: () -> Unit,
    onAbout: () -> Unit,
) = ScreenSlotBasicScrollable(contentModifier = Modifier.padding(16.dp)) {
    Text(
        text = MR.strings.settings_screen_title.resource(),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(32.dp))

    QuizSection(onQuizSettings, onQuizStatistics)
    CommunicationSection(onContact, onReportIssue)
    LegalSection(onPrivacyPolicy, onLicensingInformation, onSources)
    AboutSection(onAbout)
}

@Composable
private fun SettingsSectionContainer(content: @Composable ColumnScope.() -> Unit) = Column(
    modifier = Modifier
        .padding(top = 8.dp, bottom = 16.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(GeoTrainerTheme.colors.White),
    content = content
)

@Composable
private fun SettingsItem(
    text: String,
    onClick: () -> Unit,
    trailing: @Composable (() -> Unit)? = null,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clickableSingle(onClick = onClick)
        .sizeIn(minHeight = 48.dp)
        .padding(horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.weight(1f)
    )
    when {
        trailing != null -> trailing()
        else -> Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(24.dp),
            tint = GeoTrainerTheme.colors.DarkBlue,
        )
    }
}

@Composable
private fun QuizSection(
    onQuizSettings: () -> Unit,
    onQuizStatistics: () -> Unit,
) = Column {
    Text(
        text = MR.strings.settings_screen_quiz.resource(),
        style = MaterialTheme.typography.headlineSmall,
    )
    SettingsSectionContainer {
        SettingsItem(text = MR.strings.settings_screen_quiz_settings.resource(), onQuizSettings)
        HorizontalDivider()
        SettingsItem(text = MR.strings.settings_screen_stats.resource(), onQuizStatistics)
    }
}

@Composable
private fun CommunicationSection(
    onContact: () -> Unit,
    onReportIssue: () -> Unit,
) = Column {
    var isPushNotificationToggled by remember { mutableStateOf(false) }
    Text(
        text = MR.strings.settings_screen_communication.resource(),
        style = MaterialTheme.typography.headlineSmall,
    )
    SettingsSectionContainer {
        SettingsItem(
            text = MR.strings.settings_screen_notifications.resource(),
            onClick = {
                isPushNotificationToggled = !isPushNotificationToggled
            },
            trailing = {
                PrimarySwitch(
                    checked = isPushNotificationToggled,
                    onCheckedChange = { isPushNotificationToggled = it }
                )
            }
        )
        HorizontalDivider()
        SettingsItem(text = MR.strings.settings_screen_contact.resource(), onContact)
        HorizontalDivider()
        SettingsItem(text = MR.strings.settings_screen_report.resource(), onReportIssue)
    }
}

@Composable
private fun LegalSection(
    onPrivacyPolicy: () -> Unit,
    onLicensingInformation: () -> Unit,
    onSources: () -> Unit,
) = Column {
    Text(
        text = MR.strings.settings_screen_legal.resource(),
        style = MaterialTheme.typography.headlineSmall,
    )
    SettingsSectionContainer {
        SettingsItem(text = MR.strings.settings_screen_privacy.resource(), onPrivacyPolicy)
        HorizontalDivider()
        SettingsItem(text = MR.strings.settings_screen_licensing.resource(), onLicensingInformation)
        HorizontalDivider()
        SettingsItem(text = MR.strings.settings_screen_sources.resource(), onSources)
    }
}

@Composable
private fun AboutSection(onAbout: () -> Unit) = Column {
    Text(
        text = MR.strings.settings_screen_about.resource(),
        style = MaterialTheme.typography.headlineSmall,
    )
    SettingsSectionContainer {
        SettingsItem(text = MR.strings.settings_screen_authors.resource(), onAbout)
    }
}
