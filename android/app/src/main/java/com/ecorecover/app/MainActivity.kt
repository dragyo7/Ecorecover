package com.ecorecover.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ecorecover.app.presentation.navigation.EcoRecoverApp
import com.ecorecover.app.presentation.theme.EcoRecoverTheme
import com.ecorecover.app.util.SessionManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val sessionManager = remember { SessionManager(context) }
            val isDarkMode by sessionManager.isDarkMode.collectAsState()

            EcoRecoverTheme(darkTheme = isDarkMode) {
                EcoRecoverApp(providedSessionManager = sessionManager)
            }
        }
    }
}