package com.geotrainer.android.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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

@Composable
fun SettingsContent(
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
        text = "Settings",
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
fun SettingsSectionContainer(content: @Composable ColumnScope.() -> Unit) = Column(
    modifier = Modifier
        .padding(top = 8.dp, bottom = 16.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(GeoTrainerTheme.colors.White, shape = RoundedCornerShape(8.dp)),
    content = content
)

@Composable
fun SettingsItem(
    text: String,
    onClick: () -> Unit,
    trailing: @Composable (() -> Unit)? = null,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .sizeIn(minHeight = 48.dp)
        .padding(horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
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
fun QuizSection(
    onQuizSettings: () -> Unit,
    onQuizStatistics: () -> Unit,
) = Column {
    Text(
        text = "Quiz",
        style = MaterialTheme.typography.headlineSmall,
    )
    SettingsSectionContainer {
        SettingsItem(text = "Quiz settings", onQuizSettings)
        HorizontalDivider()
        SettingsItem(text = "Statistics", onQuizStatistics)
    }
}

@Composable
fun CommunicationSection(
    onContact: () -> Unit,
    onReportIssue: () -> Unit,
) = Column {
    var isPushNotificationToggled by remember { mutableStateOf(false) }
    Text(
        text = "Communication",
        style = MaterialTheme.typography.headlineSmall,
    )
    SettingsSectionContainer {
        SettingsItem(
            text = "Push notifications",
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
        SettingsItem(text = "Contact", onContact)
        HorizontalDivider()
        SettingsItem(text = "Report an issue", onReportIssue)
    }
}

@Composable
fun LegalSection(
    onPrivacyPolicy: () -> Unit,
    onLicensingInformation: () -> Unit,
    onSources: () -> Unit,
) = Column {
    Text(
        text = "Legal",
        style = MaterialTheme.typography.headlineSmall,
    )
    SettingsSectionContainer {
        SettingsItem(text = "Privacy policy", onPrivacyPolicy)
        HorizontalDivider()
        SettingsItem(text = "Licensing information", onLicensingInformation)
        HorizontalDivider()
        SettingsItem(text = "Sources", onSources)
    }
}

@Composable
fun AboutSection(onAbout: () -> Unit) = Column {
    Text(
        text = "About",
        style = MaterialTheme.typography.headlineSmall,
    )
    SettingsSectionContainer {
        SettingsItem(text = "About authors", onAbout)
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
