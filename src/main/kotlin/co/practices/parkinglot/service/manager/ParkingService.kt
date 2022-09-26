package co.practices.parkinglot.service.manager

import co.practices.parkinglot.constant.FareConstants
import co.practices.parkinglot.constant.MessageConstants
import co.practices.parkinglot.domain.Car
import co.practices.parkinglot.internal.exception.ParkingException
import co.practices.parkinglot.repository.IParkingRepository
import co.practices.parkinglot.repository.manager.ParkingRepository
import co.practices.parkinglot.service.IParkingService
import java.util.*

object ParkingService : IParkingService {
    private lateinit var parkingRepository: IParkingRepository

    @Throws(ParkingException::class)
    override fun create(parkingLotsCapacity: Int): String {
        if (this::parkingRepository.isInitialized) {
            throw ParkingException(MessageConstants.EXCEPTION_CREATE_NEW_PARKING_LOTS_CAPACITY.message)
        }

        this.parkingRepository = ParkingRepository(parkingLotsCapacity)
        return String.format(MessageConstants.SUCCESSFUL_CREATE_NEW_PARKING_LOTS_CAPACITY.message, parkingLotsCapacity)
    }

    @Throws(ParkingException::class)
    override fun park(registrationNumber: String): String {
        try {
            this.hasInitializedParkingLots()
            val car = Car(registrationNumber = registrationNumber)
            val freeLot = this.parkingRepository.add(car)
            return String.format(MessageConstants.SUCCESSFUL_ALLOCATED_PARKING_LOT.message, freeLot)
        } catch (e: ParkingException) {
            throw e
        }
    }

    @Throws(ParkingException::class)
    override fun leave(registrationNumber: String, parkingDuration: Int): String {
        try {
            this.hasInitializedParkingLots()
            val freeLot = this.parkingRepository.remove(registrationNumber)
            val parkingCost = this.calculateParkingCost(parkingDuration)
            return String.format(MessageConstants.SUCCESSFUL_LEAVE_PARKING_LOT.message, registrationNumber, freeLot, parkingCost)
        } catch (e: ParkingException) {
            throw e
        }
    }

    override fun status(): String {
        val stringBuilder = mutableListOf<String>()
        this.parkingRepository.getAll().forEach{with(it) {stringBuilder.add("${it.first}\t\t\t${it.second.registrationNumber}")} }
        return "Slot No.\tRegistration No.\n" + stringBuilder.joinToString("\n")
    }

    private fun hasInitializedParkingLots() {
        if (!this::parkingRepository.isInitialized) {
            throw ParkingException(MessageConstants.EXCEPTION_PARKING_SERVICE_NULL.message)
        }
    }

    private fun calculateParkingCost(parkingDuration: Int): Int {
        if (parkingDuration <= FareConstants.BASE_FARE_DURATION) {
            return FareConstants.BASE_FARE_PRICE
        }
         return FareConstants.BASE_FARE_PRICE + ((parkingDuration - FareConstants.BASE_FARE_DURATION) * FareConstants.EXTRA_HOURLY_FARE_RATE_PRICE)
    }
}