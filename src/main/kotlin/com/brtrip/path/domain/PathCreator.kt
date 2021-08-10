package com.brtrip.path.domain

import com.brtrip.path.Path
import com.brtrip.path.controller.request.PathRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class PathCreator(
    private val pathRepository: PathRepository,
    private val pathPlaceRepository: PathPlaceRepository
) {
    fun create(request: PathRequest): Path {
        val path = pathRepository.save(request.toEntity())

        request.places.forEachIndexed { index, place ->
            pathPlaceRepository.save(PathPlace(
                path = path,
                place = place,
                sequence = index.toLong()
            ))
        }
        return path
    }

    fun createPathWithinTrip(request: List<PathRequest>, idx: Int): Path {
        val pathRequest = request[idx]
        val path = pathRepository.findById(pathRequest.id!!)

        pathRequest.places.forEachIndexed { index, place ->
            pathPlaceRepository.save(PathPlace(
                path = path.get(),
                place = place,
                sequence = index.toLong()
            ))
        }

        return path.get()
    }
}
