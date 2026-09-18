package com.visacoach.profile

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ProfileDto(
    val id: String? = null,
    val userId: String? = null,
    val visaTypeCode: String = "B1_B2",
    val visaTypeName: String? = null,

    @field:NotBlank(message = "Full legal name as in passport is required")
    @field:Size(max = 150)
    val fullName: String,

    @field:NotBlank(message = "Purpose of travel is required")
    val purposeOfTravel: String,

    val intendedTravelDate: String? = null,
    val intendedDuration: String? = null,
    val occupation: String? = null,
    val employer: String? = null,
    val employmentDuration: String? = null,
    val incomeRange: String? = null,
    val sponsorType: String? = null,
    val internationalTravelHistory: String? = null,
    val usVisaHistory: String? = null,
    val usFamilyInfo: String? = null,
    val accommodationDetails: String? = null
)
