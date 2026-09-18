package com.visacoach.profile

import com.visacoach.domain.entity.UserProfile
import com.visacoach.domain.repository.UserProfileRepository
import com.visacoach.domain.repository.UserRepository
import com.visacoach.domain.repository.VisaTypeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileService(
    private val userProfileRepository: UserProfileRepository,
    private val userRepository: UserRepository,
    private val visaTypeRepository: VisaTypeRepository
) {

    @Transactional(readOnly = true)
    fun getProfile(userId: String): ProfileDto {
        val profile = userProfileRepository.findByUserId(userId)
            .orElseGet {
                val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
                val defaultVisa = visaTypeRepository.findByCode("B1_B2").orElse(null)
                val newProf = UserProfile(
                    user = user,
                    visaType = defaultVisa,
                    fullName = user.fullName ?: "Applicant",
                    purposeOfTravel = "Temporary visit / tourism"
                )
                userProfileRepository.save(newProf)
            }

        return ProfileDto(
            id = profile.id,
            userId = profile.user?.id,
            visaTypeCode = profile.visaType?.code ?: "B1_B2",
            visaTypeName = profile.visaType?.name ?: "B1/B2 Visitor Visa",
            fullName = profile.fullName,
            purposeOfTravel = profile.purposeOfTravel,
            intendedTravelDate = profile.intendedTravelDate,
            intendedDuration = profile.intendedDuration,
            occupation = profile.occupation,
            employer = profile.employer,
            employmentDuration = profile.employmentDuration,
            incomeRange = profile.incomeRange,
            sponsorType = profile.sponsorType,
            internationalTravelHistory = profile.internationalTravelHistory,
            usVisaHistory = profile.usVisaHistory,
            usFamilyInfo = profile.usFamilyInfo,
            accommodationDetails = profile.accommodationDetails
        )
    }

    @Transactional
    fun updateProfile(userId: String, dto: ProfileDto): ProfileDto {
        val profile = userProfileRepository.findByUserId(userId)
            .orElseGet {
                val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
                UserProfile(user = user, fullName = dto.fullName, purposeOfTravel = dto.purposeOfTravel)
            }

        val visaType = visaTypeRepository.findByCode(dto.visaTypeCode)
            .orElseGet { visaTypeRepository.findByCode("B1_B2").orElse(null) }

        profile.visaType = visaType
        profile.fullName = dto.fullName
        profile.purposeOfTravel = dto.purposeOfTravel
        profile.intendedTravelDate = dto.intendedTravelDate
        profile.intendedDuration = dto.intendedDuration
        profile.occupation = dto.occupation
        profile.employer = dto.employer
        profile.employmentDuration = dto.employmentDuration
        profile.incomeRange = dto.incomeRange
        profile.sponsorType = dto.sponsorType
        profile.internationalTravelHistory = dto.internationalTravelHistory
        profile.usVisaHistory = dto.usVisaHistory
        profile.usFamilyInfo = dto.usFamilyInfo
        profile.accommodationDetails = dto.accommodationDetails

        val saved = userProfileRepository.save(profile)

        // Also update user's name if modified
        saved.user?.let { u ->
            u.fullName = dto.fullName
            userRepository.save(u)
        }

        return getProfile(userId)
    }
}
