package com.visacoach.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visacoach.data.remote.VisaCoachApi
import com.visacoach.domain.models.ProfileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profile: ProfileModel, val isSaving: Boolean = false, val saveMessage: String? = null) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val api: VisaCoachApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val res = api.getProfile()
                if (res.isSuccessful && res.body() != null) {
                    _uiState.value = ProfileUiState.Success(res.body()!!)
                } else {
                    _uiState.value = ProfileUiState.Error("Failed to fetch applicant profile")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.localizedMessage ?: "Network error")
            }
        }
    }

    fun updateProfile(updated: ProfileModel) {
        val current = (_uiState.value as? ProfileUiState.Success)?.profile ?: updated
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Success(current, isSaving = true)
            try {
                val res = api.updateProfile(updated)
                if (res.isSuccessful && res.body() != null) {
                    _uiState.value = ProfileUiState.Success(res.body()!!, isSaving = false, saveMessage = "Profile updated successfully")
                } else {
                    _uiState.value = ProfileUiState.Error("Failed to save profile changes")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.localizedMessage ?: "Failed to update profile")
            }
        }
    }
}
