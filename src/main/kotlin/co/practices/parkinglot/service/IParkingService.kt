package co.practices.parkinglot.service

import co.practices.parkinglot.internal.exception.ParkingException

interface IParkingService {
    @Throws(ParkingException::class)
    fun create(parkingLotsCapacity: Int): String

    @Throws(ParkingException::class)
    fun park(registrationNumber: String): String

    @Throws(ParkingException::class)
    fun leave(registrationNumber: String, parkingDuration: Int): String
    fun status(): String
}