package com.visacoach.profile

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val profileService: ProfileService
) {

    @GetMapping
    fun getProfile(@AuthenticationPrincipal userId: String): ResponseEntity<ProfileDto> {
        val profile = profileService.getProfile(userId)
        return ResponseEntity.ok(profile)
    }

    @PutMapping
    fun updateProfile(
        @AuthenticationPrincipal userId: String,
        @Valid @RequestBody dto: ProfileDto
    ): ResponseEntity<ProfileDto> {
        val updated = profileService.updateProfile(userId, dto)
        return ResponseEntity.ok(updated)
    }
}
