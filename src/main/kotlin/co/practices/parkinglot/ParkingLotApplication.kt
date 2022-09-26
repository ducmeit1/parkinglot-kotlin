package co.practices.parkinglot

import co.practices.parkinglot.constant.CommandConstants
import co.practices.parkinglot.constant.MessageConstants
import co.practices.parkinglot.internal.exception.InputException
import co.practices.parkinglot.internal.ultility.CommandLineHelper
import co.practices.parkinglot.service.manager.ParkingService
import java.util.*

object ParkingLotApplication {
    private val scanner = Scanner(System.`in`)
    private lateinit var parkingService: ParkingService

    fun bootstrap() {
        with(scanner) {
            CommandLineHelper.showAvailableCommands()
            CommandLineHelper.showInstructions()
            while (true) {
                try {
                    when (val command = next()) {
                        CommandConstants.EXIT.command -> return
                        CommandConstants.HELP.command -> CommandLineHelper.showAvailableCommands()
                        CommandConstants.CREATE.command -> {
                            if (ParkingLotApplication::parkingService.isInitialized) {
                                throw InputException(MessageConstants.EXCEPTION_CREATE_NEW_PARKING_LOTS_CAPACITY.message)
                            } else {
                                val parkingLotsCapacity = CommandLineHelper.getInteger(scanner, "size", 1)
                                parkingService = ParkingService(parkingLotsCapacity)
                            }
                        }

                        else ->
                            if (ParkingLotApplication::parkingService.isInitialized) with(parkingService) {
                                when (command) {
                                    co.practices.parkinglot.constant.CommandConstants.PARK.command -> park(
                                        co.practices.parkinglot.internal.ultility.CommandLineHelper.getString(
                                            co.practices.parkinglot.ParkingLotApplication.scanner,
                                            "car-number"
                                        )
                                    )

                                    co.practices.parkinglot.constant.CommandConstants.LEAVE.command -> leave(
                                        co.practices.parkinglot.internal.ultility.CommandLineHelper.getString(
                                            co.practices.parkinglot.ParkingLotApplication.scanner,
                                            "car-number"
                                        ), co.practices.parkinglot.internal.ultility.CommandLineHelper.getInteger(co.practices.parkinglot.ParkingLotApplication.scanner, "hours", 1)
                                    )

                                    co.practices.parkinglot.constant.CommandConstants.STATUS.command -> status()
                                    else -> {
                                        throw InputException(co.practices.parkinglot.constant.MessageConstants.EXCEPTION_ENTER_INVALID_COMMAND.message)
                                    }
                                }
                            }
                            else {
                                throw InputException(MessageConstants.EXCEPTION_PARKING_SERVICE_NULL.message)
                            }
                    }
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }
    }
}

fun main() {
    ParkingLotApplication.bootstrap()
}