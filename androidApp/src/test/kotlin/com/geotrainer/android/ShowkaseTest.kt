package com.geotrainer.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode

import com.geotrainer.android.ui.theme.GeoTrainerTheme

import app.cash.paparazzi.DeviceConfig.Companion.PIXEL_5
import app.cash.paparazzi.Paparazzi
import com.airbnb.android.showkase.models.Showkase
import com.airbnb.android.showkase.models.ShowkaseBrowserComponent
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class ComponentPreview(
    private val showkaseBrowserComponent: ShowkaseBrowserComponent
) {
    val content: @Composable () -> Unit = showkaseBrowserComponent.component
    override fun toString(): String =
        showkaseBrowserComponent.group + ":" + showkaseBrowserComponent.componentName
}

@RunWith(TestParameterInjector::class)
class ComposePaparazziTests {
    @get:Rule
    val paparazzi = Paparazzi(
        maxPercentDifference = 0.0,
        deviceConfig = PIXEL_5.copy(softButtons = false),
    )

    object PreviewProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues(): List<ComponentPreview> =
            Showkase.getMetadata().componentList.map(::ComponentPreview)
    }

    @Test
    fun previewTests(
        @TestParameter(valuesProvider = PreviewProvider::class) componentPreview: ComponentPreview,
    ) {
        paparazzi.snapshot {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                GeoTrainerTheme {
                    componentPreview.content()
                }
            }
        }
    }
}
