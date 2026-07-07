package com.ecorecover.app.presentation.screens.profile

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KycScreen(
    onNavigateBack: () -> Unit,
    userEmail: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seller KYC & Trust Center", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Circular Trust Score Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(120.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = { 0.95f },
                            modifier = Modifier.fillMaxSize(),
                            strokeWidth = 10.dp,
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "95%",
                                fontWeight = FontWeight.Black,
                                fontSize = 28.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Trust Score",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Verified Elite Seller",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Last verified on July 01, 2026",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // KYC Status Checklist title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Security, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Verification Breakdown",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Expanded states for individual sections
            var expandedAadhaar by remember { mutableStateOf(false) }
            var expandedPan by remember { mutableStateOf(false) }
            var expandedMobile by remember { mutableStateOf(false) }
            var expandedEmail by remember { mutableStateOf(false) }
            var expandedFace by remember { mutableStateOf(false) }
            var expandedAddress by remember { mutableStateOf(false) }
            var expandedBank by remember { mutableStateOf(false) }
            var expandedUpi by remember { mutableStateOf(false) }

            KycCard(
                title = "Aadhaar Card Verification",
                subtitle = "Verified via UIDAI biometric database",
                isVerified = true,
                isExpanded = expandedAadhaar,
                onToggleExpand = { expandedAadhaar = !expandedAadhaar }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Document Type: Aadhaar Card (UIDAI)", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("Verification Number: XXXX XXXX 5821", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Status: Authenticated & Digitally Signed", fontSize = 12.sp, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)
                    Text("Verification Date: July 01, 2026", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                }
            }

            KycCard(
                title = "PAN Card Verification",
                subtitle = "Verified via NSDL database lookup",
                isVerified = true,
                isExpanded = expandedPan,
                onToggleExpand = { expandedPan = !expandedPan }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Document Type: Permanent Account Number (PAN)", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("Verification Number: XXXXX 1827G", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Status: Valid & Mapped", fontSize = 12.sp, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)
                    Text("Verification Date: July 01, 2026", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                }
            }

            KycCard(
                title = "Mobile Number OTP Verified",
                subtitle = "Secure OTP validation completed",
                isVerified = true,
                isExpanded = expandedMobile,
                onToggleExpand = { expandedMobile = !expandedMobile }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Verified Number: +91 ******4321", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("Carrier Registration: Verified matches profile name", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Status: OTP Handshake Confirmed", fontSize = 12.sp, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)
                }
            }

            KycCard(
                title = "Email Address Verified",
                subtitle = "Active communication channel verified",
                isVerified = true,
                isExpanded = expandedEmail,
                onToggleExpand = { expandedEmail = !expandedEmail }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Verified Email: $userEmail", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("Status: Verified via Activation Link", fontSize = 12.sp, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)
                }
            }

            KycCard(
                title = "Face Biometrics Verification",
                subtitle = "Liveness and identification audit",
                isVerified = false,
                statusText = "Under Review",
                isExpanded = expandedFace,
                onToggleExpand = { expandedFace = !expandedFace }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Biometric Check: 3D Liveness Selfie Scan", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("Analysis Confidence: 95.8% match against UIDAI/PAN photos", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Status: Pending manual operations approval", fontSize = 12.sp, color = Color(0xFFE59866), fontWeight = FontWeight.Bold)
                }
            }

            KycCard(
                title = "GPS Address Verification",
                subtitle = "Physical geofencing checks complete",
                isVerified = true,
                isExpanded = expandedAddress,
                onToggleExpand = { expandedAddress = !expandedAddress }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Verified Address: Nagpur Green Park Hub", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("Geofence Radius: Under 25m coordinates variance", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Status: Location Matches Bank Records", fontSize = 12.sp, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)
                }
            }

            KycCard(
                title = "Bank Account Verification",
                subtitle = "IMPS Penny drop verification successful",
                isVerified = true,
                isExpanded = expandedBank,
                onToggleExpand = { expandedBank = !expandedBank }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Bank Name: HDFC Bank Limited", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("Account Number: *********9901", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Account Holder: Matches Registered Profile Name", fontSize = 12.sp, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)
                }
            }

            KycCard(
                title = "UPI VPA ID Verified",
                subtitle = "Virtual Payment Address mapped",
                isVerified = true,
                isExpanded = expandedUpi,
                onToggleExpand = { expandedUpi = !expandedUpi }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("UPI Address: abhyudaya@okaxis", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("Provider: Axis Bank UPI Gateway", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Status: Active & Verified", fontSize = 12.sp, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KycCard(
    title: String,
    subtitle: String,
    isVerified: Boolean,
    statusText: String = "Verified",
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
        onClick = onToggleExpand
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isVerified) Icons.Default.Verified else Icons.Default.HourglassBottom,
                    contentDescription = null,
                    tint = if (isVerified) Color(0xFF22C55E) else Color(0xFFE59866),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background((if (isVerified) Color(0xFFDCFCE7) else Color(0xFFFEF9E7)))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (isVerified) statusText else "Review",
                        color = if (isVerified) Color(0xFF15803D) else Color(0xFFB7950B),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    content()
                }
            }
        }
    }
}
