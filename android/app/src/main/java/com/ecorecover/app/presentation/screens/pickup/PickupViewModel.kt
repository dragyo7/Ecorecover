package com.ecorecover.app.presentation.screens.pickup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.AppointmentRequest
import com.ecorecover.app.data.repository.AppointmentRepository
import com.ecorecover.app.data.repository.EstimateRepository
import com.ecorecover.app.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PickupViewModel : ViewModel() {

    private val estimateRepository = EstimateRepository()
    private val appointmentRepository = AppointmentRepository()

    private val _uiState = MutableStateFlow(PickupUiState())
    val uiState: StateFlow<PickupUiState> = _uiState.asStateFlow()

    fun loadEstimate(product: String) {
        println("[DIAGNOSTIC] PickupViewModel.loadEstimate received product: '$product' (length: ${product.length})")
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = estimateRepository.getEstimate(product)
                if (response.success && response.data != null) {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            estimate = response.data
                        )
                    }
                } else {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = response.message?.ifBlank { "Failed to load device valuation summary" } ?: "Failed to load device valuation summary"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Failed to connect to estimation service"
                    )
                }
            }
        }
    }

    fun onAddressChanged(address: String) {
        _uiState.update { it.copy(address = address) }
    }

    fun onCityChanged(city: String) {
        _uiState.update { it.copy(city = city) }
    }

    fun onContactNumberChanged(contactNumber: String) {
        _uiState.update { it.copy(contactNumber = contactNumber) }
    }

    fun onDateChanged(date: String) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun onTimeSlotChanged(timeSlot: String) {
        _uiState.update { it.copy(selectedTimeSlot = timeSlot) }
    }

    fun onInstructionsChanged(instructions: String) {
        _uiState.update { it.copy(pickupInstructions = instructions) }
    }

    fun onNotesChanged(notes: String) {
        _uiState.update { it.copy(recyclerNotes = notes) }
    }

    fun confirmPickup(sessionManager: SessionManager) {
        val state = _uiState.value
        val estimate = state.estimate

        if (estimate == null) {
            _uiState.update { it.copy(formError = "Estimate data is missing.") }
            return
        }

        if (state.address.isBlank()) {
            _uiState.update { it.copy(formError = "Pickup address is required.") }
            return
        }

        if (state.city.isBlank()) {
            _uiState.update { it.copy(formError = "City is required.") }
            return
        }

        if (state.contactNumber.isBlank()) {
            _uiState.update { it.copy(formError = "Contact number is required.") }
            return
        }

        if (state.selectedDate.isBlank()) {
            _uiState.update { it.copy(formError = "Please select a pickup date.") }
            return
        }

        if (state.selectedTimeSlot.isBlank()) {
            _uiState.update { it.copy(formError = "Please select a preferred time slot.") }
            return
        }

        _uiState.update { it.copy(isBooking = true, bookingError = null, formError = null) }

        viewModelScope.launch {
            try {
                val userId = sessionManager.getUserId()
                val request = AppointmentRequest(
                    userId = userId,
                    productName = estimate.product,
                    estimatedPrice = estimate.estimated_total_value_inr,
                    serviceType = "Pickup",
                    appointmentDate = state.selectedDate,
                    appointmentTime = state.selectedTimeSlot,
                    address = state.address,
                    city = state.city,
                    notes = if (state.pickupInstructions.isNotBlank()) {
                        "${state.pickupInstructions} | ${state.recyclerNotes}".trim()
                    } else {
                        state.recyclerNotes
                    }
                )

                val response = appointmentRepository.createAppointment(request)
                if (response.success && !response.data.isNullOrEmpty()) {
                    _uiState.update { 
                        it.copy(
                            isBooking = false,
                            bookedAppointment = response.data[0]
                        )
                    }
                } else {
                    _uiState.update { 
                        it.copy(
                            isBooking = false,
                            bookingError = response.message ?: "Failed to book appointment"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isBooking = false,
                        bookingError = e.localizedMessage ?: "Network error booking appointment"
                    )
                }
            }
        }
    }
}
