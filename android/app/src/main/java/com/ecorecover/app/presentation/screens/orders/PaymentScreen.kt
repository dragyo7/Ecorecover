package com.ecorecover.app.presentation.screens.orders

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecorecover.app.MainActivity
import com.ecorecover.app.presentation.common.LoadingScreen
import org.json.JSONObject
import java.util.UUID

private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    orderId: String,
    amount: Double,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: PaymentViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }

    // Selected Payment Method State
    var selectedMethod by remember { mutableStateOf("UPI") }

    // Register Razorpay Callbacks
    DisposableEffect(orderId, amount) {
        MainActivity.paymentCallback = { success, result ->
            if (success) {
                // Success: verify on backend
                val orderIdToUse = when (val state = viewModel.uiState.value) {
                    is PaymentUiState.OrderInitialized -> state.orderId
                    else -> "order_rp_" + UUID.randomUUID().toString().take(12)
                }
                viewModel.verifyPayment(orderId, orderIdToUse, result ?: "", amount)
            } else {
                // Cancelled or Failed
                viewModel.setPaymentError(result ?: "Payment cancelled or failed by user")
            }
        }
        onDispose {
            MainActivity.paymentCallback = null
        }
    }

    // Launch Razorpay when order is successfully initialized
    LaunchedEffect(uiState) {
        if (uiState is PaymentUiState.OrderInitialized) {
            val init = uiState as PaymentUiState.OrderInitialized
            if (activity != null) {
                try {
                    val checkout = com.razorpay.Checkout()
                    // Set a valid test key (using standard sandbox key syntax)
                    checkout.setKeyID("rzp_test_57s8o7u1s89e81")
                    
                    val options = JSONObject()
                    options.put("name", "EcoRecover")
                    options.put("description", "Secure Recycling Payout Release")
                    options.put("order_id", init.orderId)
                    options.put("currency", "INR")
                    options.put("amount", (init.amount * 100).toInt()) // paise
                    
                    val prefill = JSONObject()
                    prefill.put("email", "payout@ecorecover.com")
                    prefill.put("contact", "9876543210")
                    options.put("prefill", prefill)
                    
                    val theme = JSONObject()
                    theme.put("color", "#2E7D32") // Primary Green
                    options.put("theme", theme)

                    checkout.open(activity, options)
                } catch (e: Exception) {
                    viewModel.setPaymentError("Failed to open Razorpay SDK: ${e.localizedMessage}")
                }
            } else {
                viewModel.setPaymentError("Could not retrieve Activity context for payment gateway.")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState is PaymentUiState.Success) "Payout Success" else "Secure Payout Release", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    if (uiState !is PaymentUiState.Success && uiState !is PaymentUiState.Loading && uiState !is PaymentUiState.OrderInitialized) {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is PaymentUiState.Idle -> {
                    PaymentCheckoutContent(
                        orderId = orderId,
                        amount = amount,
                        selectedMethod = selectedMethod,
                        onMethodSelect = { selectedMethod = it },
                        onReleaseClick = {
                            if (selectedMethod in listOf("UPI", "Credit/Debit Card", "Net Banking", "Wallets")) {
                                viewModel.createPaymentOrder(orderId, amount)
                            } else if (selectedMethod == "EMI") {
                                Toast.makeText(context, "EMI options are enabled for payments above ₹3,000.", Toast.LENGTH_SHORT).show()
                            } else {
                                // Direct/offline options: Cash on Pickup, Bank Transfer, Eco Wallet
                                val mockPaymentId = "pay_offline_" + UUID.randomUUID().toString().replace("-", "").take(12)
                                val mockOrderId = "order_offline_" + UUID.randomUUID().toString().replace("-", "").take(12)
                                viewModel.verifyPayment(orderId, mockOrderId, mockPaymentId, amount)
                            }
                        }
                    )
                }
                is PaymentUiState.Loading, is PaymentUiState.OrderInitialized -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(56.dp))
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Initiating Secure Payout Transfer...",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Verifying details with Razorpay Sandbox",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
                is PaymentUiState.Success -> {
                    PaymentSuccessContent(
                        transaction = state.transaction,
                        selectedMethod = selectedMethod,
                        onHomeClick = {
                            viewModel.resetState()
                            onNavigateToHome()
                        },
                        onInvoiceClick = {
                            Toast.makeText(context, "Invoice download started (REC-${state.transaction.receiptId})", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
                is PaymentUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Payout Failed",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { viewModel.resetState() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Retry Payout")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentCheckoutContent(
    orderId: String,
    amount: Double,
    selectedMethod: String,
    onMethodSelect: (String) -> Unit,
    onReleaseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Transaction Summary",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Booking Reference", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = "#${orderId.take(8).uppercase()}", fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Transfer Method", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = "Instant Secured Gateway", fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Status", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = "Awaiting Release", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Payout Amount",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₹${String.format("%,.2f", amount)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Guaranteed secure by EcoRecover",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
            }
        }

        // Payout Options
        Text(
            text = "Select Payout Release Option",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        val optionsList = listOf(
            PaymentMethod("UPI", "Instant release to Virtual Payment Address", Icons.Default.QrCode),
            PaymentMethod("Credit/Debit Card", "Visa, Mastercard, RuPay Cards", Icons.Default.CreditCard),
            PaymentMethod("Net Banking", "Release via standard Net Banking portals", Icons.Default.AccountBalance),
            PaymentMethod("Wallets", "Paytm, PhonePe, Amazon Pay", Icons.Default.Wallet),
            PaymentMethod("EMI", "Convert transaction into installment credits", Icons.Default.Timeline),
            PaymentMethod("Cash on Pickup", "Receive direct hand-in-hand cash from agent", Icons.Default.Payments),
            PaymentMethod("Bank Transfer", "Direct IMPS/NEFT Account Credit", Icons.Default.CurrencyExchange),
            PaymentMethod("Eco Wallet", "Reclaim credits to EcoRecover wallet", Icons.Default.Eco)
        )

        optionsList.forEach { method ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMethodSelect(method.title) },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = if (selectedMethod == method.title) 2.dp else 1.dp,
                    color = if (selectedMethod == method.title) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = method.icon,
                        contentDescription = null,
                        tint = if (selectedMethod == method.title) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = method.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = method.subtitle,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                    RadioButton(
                        selected = (selectedMethod == method.title),
                        onClick = { onMethodSelect(method.title) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action release button
        Button(
            onClick = onReleaseClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(imageVector = Icons.Default.LockOpen, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Release Payout via $selectedMethod", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Text(
            text = "By releasing payment, you confirm that your e-waste items have been handed over to the recycling partner.",
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PaymentSuccessContent(
    transaction: com.ecorecover.app.data.model.TransactionData,
    selectedMethod: String,
    onHomeClick: () -> Unit,
    onInvoiceClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFDCFCE7)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color(0xFF15803D),
                modifier = Modifier.size(40.dp)
            )
        }

        Text(
            text = "Payout Released Successfully!",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Text(
            text = "₹${String.format("%,.2f", transaction.amount)} has been credited via $selectedMethod.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )

        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f), modifier = Modifier.padding(vertical = 8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ReceiptRow(label = "Payment ID", value = transaction.paymentId)
            ReceiptRow(label = "Order ID", value = transaction.orderId)
            ReceiptRow(label = "Receipt ID", value = transaction.receiptId)
            ReceiptRow(label = "Payout Channel", value = selectedMethod)
            ReceiptRow(label = "Status", value = "PAID (Success)")
            ReceiptRow(label = "Timestamp", value = transaction.createdAt.replace("T", " ").take(19))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onInvoiceClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(imageVector = Icons.Default.Receipt, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Download PDF Receipt", color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = onHomeClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Dashboard", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

private data class PaymentMethod(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
