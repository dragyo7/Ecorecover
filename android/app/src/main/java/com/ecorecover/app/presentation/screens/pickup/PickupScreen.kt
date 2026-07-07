package com.ecorecover.app.presentation.screens.pickup

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ecorecover.app.presentation.common.ErrorScreen
import com.ecorecover.app.presentation.common.LoadingScreen
import com.ecorecover.app.util.SessionManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickupScreen(
    product: String,
    viewModel: PickupViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    
    LaunchedEffect(product) {
        viewModel.loadEstimate(product)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.bookedAppointment != null) "Booking Confirmation" else "Schedule Pickup") },
                navigationIcon = {
                    if (uiState.bookedAppointment == null) {
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
            when {
                uiState.isLoading -> {
                    LoadingScreen()
                }
                uiState.error != null -> {
                    ErrorScreen(
                        message = uiState.error!!,
                        onRetry = { viewModel.loadEstimate(product) }
                    )
                }
                uiState.bookedAppointment != null -> {
                    // Confirmation Screen
                    ConfirmationContent(
                        appointment = uiState.bookedAppointment!!,
                        onGoHome = onNavigateToHome,
                        onGoToHistory = onNavigateToHistory
                    )
                }
                uiState.estimate != null -> {
                    // Booking Form Screen
                    BookingFormContent(
                        uiState = uiState,
                        viewModel = viewModel,
                        sessionManager = sessionManager
                    )
                }
            }
        }
    }
}

@Composable
fun BookingFormContent(
    uiState: PickupUiState,
    viewModel: PickupViewModel,
    sessionManager: SessionManager
) {
    val scrollState = rememberScrollState()
    val estimate = uiState.estimate!!

    // Generate upcoming 7 days starting tomorrow
    val dates = remember {
        val list = mutableListOf<Pair<String, String>>()
        val cal = Calendar.getInstance()
        val uiFormat = SimpleDateFormat("EEE, d MMM", Locale.getDefault())
        val dbFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        for (i in 1..7) {
            cal.add(Calendar.DAY_OF_YEAR, 1)
            list.add(Pair(uiFormat.format(cal.time), dbFormat.format(cal.time)))
        }
        list
    }

    val timeSlots = remember {
        listOf(
            Pair("Morning (9 AM - 12 PM)", "09:00:00"),
            Pair("Afternoon (12 PM - 3 PM)", "12:00:00"),
            Pair("Evening (3 PM - 6 PM)", "15:00:00"),
            Pair("Night (6 PM - 9 PM)", "18:00:00")
        )
    }

    val presetInstructions = listOf(
        "Call me before arriving",
        "Leave with security guard",
        "Ring doorbell when arrived",
        "Keep change ready"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        // Device Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = estimate.product,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Estimated Recycle Payout",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                    Text(
                        text = "₹ ${String.format(Locale.US, "%.2f", estimate.estimated_total_value_inr)}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f)
                )

                // Metals Summary
                Text(
                    text = "Recovered Materials",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val items = estimate.metal_content_used.toList()
                    for (i in items.indices step 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // First item
                            val (metal1, weight1) = items[i]
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.05f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = metal1,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = String.format(Locale.US, "%.3fg", weight1),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }

                            // Second item
                            if (i + 1 < items.size) {
                                val (metal2, weight2) = items[i + 1]
                                Surface(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.05f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = metal2,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = String.format(Locale.US, "%.3fg", weight2),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Address & Contact Information Section
        Text(
            text = "Pickup Location & Contact",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = uiState.address,
            onValueChange = { viewModel.onAddressChanged(it) },
            label = { Text("Pickup Address") },
            leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = uiState.city,
                onValueChange = { viewModel.onCityChanged(it) },
                label = { Text("City") },
                leadingIcon = { Icon(Icons.Default.LocationCity, contentDescription = null) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            OutlinedTextField(
                value = uiState.contactNumber,
                onValueChange = { viewModel.onContactNumberChanged(it) },
                label = { Text("Contact Number") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.weight(1.2f),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Date Picker (Premium horizontal list of chips)
        Text(
            text = "Select Pickup Date",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            dates.take(3).forEach { (uiDate, dbDate) ->
                val isSelected = uiState.selectedDate == dbDate
                ElevatedFilterChip(
                    selected = isSelected,
                    onClick = { viewModel.onDateChanged(dbDate) },
                    label = { Text(uiDate) },
                    modifier = Modifier.weight(1f),
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            dates.drop(3).take(4).forEach { (uiDate, dbDate) ->
                val isSelected = uiState.selectedDate == dbDate
                ElevatedFilterChip(
                    selected = isSelected,
                    onClick = { viewModel.onDateChanged(dbDate) },
                    label = { Text(uiDate) },
                    modifier = Modifier.weight(1f),
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Time Slot Selector
        Text(
            text = "Select Time Slot",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            timeSlots.chunked(2).forEach { rowSlots ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowSlots.forEach { (uiTime, dbTime) ->
                        val isSelected = uiState.selectedTimeSlot == dbTime
                        ElevatedFilterChip(
                            selected = isSelected,
                            onClick = { viewModel.onTimeSlotChanged(dbTime) },
                            label = { Text(uiTime, fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.elevatedFilterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Pickup Instructions
        Text(
            text = "Pickup Instructions (Optional)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            presetInstructions.take(2).forEach { instruction ->
                val isSelected = uiState.pickupInstructions.contains(instruction)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        val current = uiState.pickupInstructions
                        if (isSelected) {
                            viewModel.onInstructionsChanged(current.replace(instruction, "").trim())
                        } else {
                            viewModel.onInstructionsChanged(if (current.isBlank()) instruction else "$current, $instruction")
                        }
                    },
                    label = { Text(instruction, fontSize = 11.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            presetInstructions.drop(2).forEach { instruction ->
                val isSelected = uiState.pickupInstructions.contains(instruction)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        val current = uiState.pickupInstructions
                        if (isSelected) {
                            viewModel.onInstructionsChanged(current.replace(instruction, "").trim())
                        } else {
                            viewModel.onInstructionsChanged(if (current.isBlank()) instruction else "$current, $instruction")
                        }
                    },
                    label = { Text(instruction, fontSize = 11.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.pickupInstructions,
            onValueChange = { viewModel.onInstructionsChanged(it) },
            placeholder = { Text("Enter custom delivery/pickup instructions...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Recycler Notes
        Text(
            text = "Notes for the Recycler (Optional)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = uiState.recyclerNotes,
            onValueChange = { viewModel.onNotesChanged(it) },
            placeholder = { Text("E.g., Device has minor scratches on the back screen, battery health 75%...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            maxLines = 3
        )

        // Error Banner
        if (uiState.formError != null || uiState.bookingError != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = uiState.formError ?: uiState.bookingError!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Confirm Button
        Button(
            onClick = { viewModel.confirmPickup(sessionManager) },
            enabled = !uiState.isBooking,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            if (uiState.isBooking) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Confirm Pickup Booking", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun ConfirmationContent(
    appointment: com.ecorecover.app.data.model.AppointmentData,
    onGoHome: () -> Unit,
    onGoToHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success Animation Icon
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "Success",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Pickup Booked Successfully!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Your recycle agent will contact you shortly to verify the device condition.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Payout Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                DetailRow(label = "Booking ID", value = appointment.id.take(8).uppercase())
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailRow(label = "Recycled Device", value = appointment.productName)
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailRow(
                    label = "Est. Payout",
                    value = "₹ ${String.format(Locale.US, "%.2f", appointment.estimatedPrice)}",
                    valueColor = MaterialTheme.colorScheme.primary,
                    valueBold = true
                )
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailRow(label = "Scheduled Date", value = appointment.appointmentDate)
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailRow(
                    label = "Time Slot",
                    value = when(appointment.appointmentTime) {
                        "09:00:00" -> "Morning (9 AM - 12 PM)"
                        "12:00:00" -> "Afternoon (12 PM - 3 PM)"
                        "15:00:00" -> "Evening (3 PM - 6 PM)"
                        "18:00:00" -> "Night (6 PM - 9 PM)"
                        else -> appointment.appointmentTime
                    }
                )
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailRow(
                    label = "Status",
                    value = "Scheduled", // Override backend status to be explicitly Scheduled as requested
                    valueColor = MaterialTheme.colorScheme.secondary,
                    valueBold = true
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Actions
        Button(
            onClick = onGoHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Icon(Icons.Default.Home, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Back to Home", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onGoToHistory,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Icon(Icons.Default.History, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("View Booking History", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
    valueBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor,
            fontWeight = if (valueBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}
