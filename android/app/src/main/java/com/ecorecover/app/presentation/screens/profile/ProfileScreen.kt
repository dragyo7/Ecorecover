package com.ecorecover.app.presentation.screens.profile

import android.widget.Toast
import androidx.compose.foundation.background
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecorecover.app.data.model.ProfileData
import com.ecorecover.app.presentation.common.LoadingScreen
import com.ecorecover.app.util.SessionManager

import androidx.compose.material3.pulltorefresh.PullToRefreshBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    sessionManager: SessionManager,
    onLogout: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isUpdating by viewModel.isUpdating.collectAsState()
    val updateMessage by viewModel.updateMessage.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val context = LocalContext.current

    var showEditDialog by remember { mutableStateOf(false) }
    var newNameInput by remember { mutableStateOf("") }
    
    // Privacy and About Dialog states
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    // Switched states
    val darkModeChecked by sessionManager.isDarkMode.collectAsState()
    var notificationsChecked by remember { mutableStateOf(true) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(updateMessage) {
        updateMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
            viewModel.clearUpdateMessage()
            showEditDialog = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Profile Settings", fontWeight = FontWeight.Bold) },
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
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.loadProfile(isRefresh = true) },
                modifier = Modifier.fillMaxSize()
            ) {
                when (val state = uiState) {
                    is ProfileUiState.Loading -> LoadingScreen()
                    is ProfileUiState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        }
                    }
                    is ProfileUiState.Success -> {
                        ProfileContent(
                            profile = state.profile,
                            darkModeChecked = darkModeChecked,
                            onDarkModeChange = { sessionManager.setDarkMode(it) },
                            notificationsChecked = notificationsChecked,
                            onNotificationsChange = { notificationsChecked = it },
                            onEditNameClick = {
                                newNameInput = state.profile.fullName
                                showEditDialog = true
                            },
                            onPrivacyClick = { showPrivacyDialog = true },
                            onAboutClick = { showAboutDialog = true },
                            onLogout = onLogout,
                            onNavigateToTransactions = onNavigateToTransactions
                        )
                    }
            }
        }
        if (showEditDialog) {
                AlertDialog(
                    onDismissRequest = { if (!isUpdating) showEditDialog = false },
                    title = { Text("Edit Profile Name") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = newNameInput,
                                onValueChange = { newNameInput = it },
                                label = { Text("Full Name") },
                                singleLine = true,
                                enabled = !isUpdating,
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (isUpdating) {
                                Spacer(modifier = Modifier.height(8.dp))
                                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (newNameInput.isNotBlank()) {
                                    viewModel.updateProfileName(newNameInput, sessionManager)
                                }
                            },
                            enabled = !isUpdating && newNameInput.isNotBlank()
                        ) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showEditDialog = false },
                            enabled = !isUpdating
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }

            if (showPrivacyDialog) {
                AlertDialog(
                    onDismissRequest = { showPrivacyDialog = false },
                    title = { Text("Privacy Policy") },
                    text = {
                        Text("EcoRecover complies with data protection acts. We securely encrypt all user credentials, tokens, and pickup addresses. Your device diagnostic data is never shared with unauthorized third parties.")
                    },
                    confirmButton = {
                        Button(onClick = { showPrivacyDialog = false }) {
                            Text("Close")
                        }
                    }
                )
            }

            if (showAboutDialog) {
                AlertDialog(
                    onDismissRequest = { showAboutDialog = false },
                    title = { Text("About EcoRecover") },
                    text = {
                        Text("EcoRecover is a community e-waste recycling framework that leverages AI-driven live pricing and verified green rewards. Join us in diverting electronic waste from landfills to reclaim precious earth metals.")
                    },
                    confirmButton = {
                        Button(onClick = { showAboutDialog = false }) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ProfileContent(
    profile: ProfileData,
    darkModeChecked: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    notificationsChecked: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    onEditNameClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onAboutClick: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToTransactions: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Avatar
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = profile.fullName.firstOrNull()?.uppercase() ?: "U",
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // User Info Header
        Text(
            text = profile.fullName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = profile.email,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        // Trust & Verification Card (Feature 2)
        Text(
            text = "Trust & Verification",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = Color(0xFF22C55E),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Verified Seller Account",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFDCFCE7))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "100% Verified",
                            color = Color(0xFF15803D),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                // Verification parameters progress
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Email Verified", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "✓ ${profile.email}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = Color(0xFF22C55E))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Phone Verified", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "✓ +91 ******4321", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = Color(0xFF22C55E))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "KYC Verification", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "COMPLETED", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Settings Category List
        Text(
            text = "App Settings",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
        ) {
            Column {
                SettingsItem(
                    icon = Icons.Outlined.Edit,
                    title = "Edit Profile Name",
                    subtitle = "Update your full name",
                    onClick = onEditNameClick
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                SettingsItem(
                    icon = Icons.Default.Payment,
                    title = "Payout Transactions",
                    subtitle = "History of released payments",
                    onClick = onNavigateToTransactions
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                
                SettingsSwitchItem(
                    icon = Icons.Default.Brightness4,
                    title = "Dark Mode",
                    subtitle = "System default",
                    checked = darkModeChecked,
                    onCheckedChange = onDarkModeChange
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                SettingsSwitchItem(
                    icon = Icons.Outlined.Notifications,
                    title = "Push Notifications",
                    subtitle = "Alerts on pickup assignments",
                    checked = notificationsChecked,
                    onCheckedChange = onNotificationsChange
                )
            }
        }

        Text(
            text = "Support & Legal",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
        ) {
            Column {
                SettingsItem(
                    icon = Icons.Outlined.Security,
                    title = "Privacy Policy",
                    subtitle = "How we protect your e-waste records",
                    onClick = onPrivacyClick
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                
                SettingsItem(
                    icon = Icons.Outlined.Info,
                    title = "About EcoRecover",
                    subtitle = "Recycling objectives and framework",
                    onClick = onAboutClick
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Log out CTA
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Log Out", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Text(
            text = "App Version 1.0.0",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun SettingsSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}