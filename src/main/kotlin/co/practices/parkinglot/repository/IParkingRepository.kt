package co.practices.parkinglot.repository

import co.practices.parkinglot.domain.Car
import co.practices.parkinglot.internal.exception.ParkingException

interface IParkingRepository {
    fun getAll(): List<Pair<Int, Car>>

    @Throws(ParkingException::class)
    fun add(car: Car): Int

    @Throws(ParkingException::class)
    fun remove(registrationNumber: String): Int
}