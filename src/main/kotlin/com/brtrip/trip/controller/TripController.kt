package com.brtrip.trip.controller

import com.brtrip.auth.domain.UserPrincipal
import com.brtrip.common.response.ApiResponse
import com.brtrip.path.controller.request.PathRequest
import com.brtrip.trip.controller.request.TripRequest
import com.brtrip.trip.domain.TripService
import com.brtrip.user.domain.LoginUser
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/trips")
class TripController(
    private val tripService: TripService
) {
    @PostMapping
    fun create(
        @LoginUser userPrincipal: UserPrincipal,
        @Valid @RequestBody request: TripRequest
        ): ResponseEntity<Void> {
        val tripId = tripService.create(userPrincipal.getId(), request)

        val location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/api/v1/trips/$tripId")
            .buildAndExpand(tripId).toUri()

        return ResponseEntity.created(location).build()
    }

    @GetMapping("/my")
    fun findMyTrips(
        @LoginUser userPrincipal: UserPrincipal
    ): ResponseEntity<ApiResponse> {
        val trips = tripService.findMyTrips(userPrincipal.getId())
        return ResponseEntity
            .ok(
                ApiResponse(
                    data = trips
                )
            )
    }

    @GetMapping("/recent")
    fun findRecentTrip(
        @LoginUser userPrincipal: UserPrincipal
    ): ResponseEntity<ApiResponse> {
        val trip = tripService.findRecentTrip(userPrincipal.getId())
        return ResponseEntity
            .ok(
                ApiResponse(
                    data = trip
                )
            )
    }

    @PatchMapping("/{id}")
    fun update(
        @LoginUser userPrincipal: UserPrincipal,
        @PathVariable id: Long,
        @Valid @RequestBody request: TripRequest
    ): ResponseEntity<Void> {
        tripService.update(userPrincipal.getId(), id, request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun delete(
        @LoginUser userPrincipal: UserPrincipal,
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        tripService.delete(userPrincipal.getId(), id)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}/paths")
    fun deletePathInTrip(
        @LoginUser userPrincipal: UserPrincipal,
        @PathVariable id: Long,
        @Valid @RequestBody pathRequest: PathRequest
    ): ResponseEntity<Void> {
        tripService.deletePathInTrip(userPrincipal.getId(), id, pathRequest)
        return ResponseEntity.noContent().build()
    }
}
