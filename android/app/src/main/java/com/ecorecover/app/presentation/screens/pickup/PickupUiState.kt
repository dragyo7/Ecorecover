package com.ecorecover.app.presentation.screens.pickup

import com.ecorecover.app.data.model.AppointmentData
import com.ecorecover.app.data.model.EstimateData

data class PickupUiState(
    val isLoading: Boolean = false,
    val estimate: EstimateData? = null,
    val error: String? = null,

    // Booking Form States
    val address: String = "",
    val city: String = "",
    val contactNumber: String = "",
    val selectedDate: String = "", // YYYY-MM-DD
    val selectedTimeSlot: String = "", // HH:MM:SS
    val pickupInstructions: String = "",
    val recyclerNotes: String = "",

    // Validation/Form Error
    val formError: String? = null,

    // Booking Call State
    val isBooking: Boolean = false,
    val bookingError: String? = null,
    val bookedAppointment: AppointmentData? = null
)
