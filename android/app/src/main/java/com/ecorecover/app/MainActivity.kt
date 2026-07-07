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
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener

class MainActivity : ComponentActivity(), PaymentResultListener {

    companion object {
        var paymentCallback: ((Boolean, String?) -> Unit)? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(applicationContext)

        setContent {
            val context = LocalContext.current
            val sessionManager = remember { SessionManager(context) }
            val isDarkMode by sessionManager.isDarkMode.collectAsState()

            EcoRecoverTheme(darkTheme = isDarkMode) {
                EcoRecoverApp(providedSessionManager = sessionManager)
            }
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        paymentCallback?.invoke(true, razorpayPaymentId)
    }

    override fun onPaymentError(code: Int, response: String?) {
        paymentCallback?.invoke(false, response ?: "Payment cancelled or failed")
    }
}