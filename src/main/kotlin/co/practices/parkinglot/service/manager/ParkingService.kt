package co.practices.parkinglot.service.manager

import co.practices.parkinglot.constant.FareConstants
import co.practices.parkinglot.constant.MessageConstants
import co.practices.parkinglot.domain.Car
import co.practices.parkinglot.internal.exception.ParkingException
import co.practices.parkinglot.service.IParkingService
import java.util.*

class ParkingService(private val parkingLotCapacity: Int) : IParkingService {
    private val freeParkingLots = TreeSet<Int>()
    private val parkingLotsMap = HashMap<String, Car>()

    @Throws(ParkingException::class)
    override fun park(registrationNumber: String) {
        try {
            val freeLotIndex = freeParkingLots.first()
            val car = Car(registrationNumber = registrationNumber, parkingLot = freeLotIndex)
            parkingLotsMap[registrationNumber] = car
            freeParkingLots.remove(freeLotIndex)
            println(String.format(MessageConstants.SUCCESSFUL_ALLOCATED__LOT.message, freeLotIndex))
        } catch (e: NoSuchElementException) {
            throw ParkingException(MessageConstants.EXCEPTION_PARKING_LOTS_IS_FULL.message)
        }
    }

    @Throws(ParkingException::class)
    override fun leave(registrationNumber: String, parkingDuration: Int) {
        val car = parkingLotsMap[registrationNumber]
            ?: throw ParkingException(
                String.format(
                    MessageConstants.EXCEPTION_REGISTRATION_NUMBER_NOT_FOUND.message,
                    registrationNumber
                )
            )

        val lotIndex = car.parkingLot
        parkingLotsMap.remove(registrationNumber)
        freeParkingLots.add(lotIndex)
        val parkingCost = this.calculateParkingCost(parkingDuration)
        println(
            String.format(
                MessageConstants.SUCCESSFUL_LEAVE_.message,
                registrationNumber,
                lotIndex,
                parkingCost
            )
        )
    }

    override fun status() {
        println("Slot No.\tRegistration No.")
        parkingLotsMap.keys.forEach { registrationNumber ->
            val car = this.parkingLotsMap[registrationNumber]
            println(car.toString())
        }
    }

    private fun calculateParkingCost(parkingDuration: Int): Int {
        var parkingCost: Int
        if (parkingDuration <= FareConstants.BASE_FARE_DURATION) {
            parkingCost = FareConstants.BASE_FARE_PRICE
        } else {
            parkingCost =
                FareConstants.BASE_FARE_PRICE + ((parkingDuration - FareConstants.BASE_FARE_DURATION) * FareConstants.EXTRA_HOURLY_FARE_RATE_PRICE)
        }

        return parkingCost
    }

    init {
        for (index in 1..parkingLotCapacity) {
            freeParkingLots.add(index)
        }
        println(String.format(MessageConstants.SUCCESSFUL_CREATE_NEW_PARKING_LOTS_CAPACITY.message, parkingLotCapacity))
    }
}