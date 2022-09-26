package co.practices.parkinglot.repository.manager

import co.practices.parkinglot.constant.MessageConstants
import co.practices.parkinglot.domain.Car
import co.practices.parkinglot.internal.exception.ParkingException
import co.practices.parkinglot.repository.IParkingRepository

class ParkingRepository(private val parkingLotsCapacity: Int) : IParkingRepository {
    private val parkingLotsMap = mutableMapOf<Int, Car>()

    override fun getAll(): List<Pair<Int, Car>> {
        return parkingLotsMap.toList()
    }

    @Throws(ParkingException::class)
    override fun add(car: Car): Int {
        if (parkingLotsMap.size == parkingLotsCapacity) {
            throw ParkingException(MessageConstants.EXCEPTION_PARKING_LOTS_IS_FULL.message)
        }

        if (lookup(car.registrationNumber) != null) {
            throw ParkingException(String.format(MessageConstants.EXCEPTION_ALREADY_PARKED.message, car.registrationNumber))
        }

        val freeLot = (1..parkingLotsCapacity).firstOrNull { it !in parkingLotsMap } ?: 1
        parkingLotsMap[freeLot] = car

        return freeLot
    }

    @Throws(ParkingException::class)
    override fun remove(registrationNumber: String): Int {
        val lot = this.lookup(registrationNumber) ?: throw ParkingException(
            String.format(
                MessageConstants.EXCEPTION_REGISTRATION_NUMBER_NOT_FOUND.message,
                registrationNumber
            )
        )

        parkingLotsMap.remove(lot)
        return lot
    }

    private fun lookup(registrationNumber: String): Int? {
        return parkingLotsMap.filter { it.value.registrationNumber.equals(registrationNumber, true) }.map { it.key }
            .singleOrNull()
    }
}