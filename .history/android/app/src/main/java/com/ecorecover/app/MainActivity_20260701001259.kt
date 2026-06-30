package com.ecorecover.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ecorecover.app.presentation.navigation.EcoRecoverApp
import com.ecorecover.app.presentation.theme.EcoRecoverTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EcoRecoverTheme {
                EcoRecoverApp()
            }
        }
    }
}