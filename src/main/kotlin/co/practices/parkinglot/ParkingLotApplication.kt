package co.practices.parkinglot

import co.practices.parkinglot.constant.CommandConstants
import co.practices.parkinglot.constant.MessageConstants
import co.practices.parkinglot.internal.exception.InputException
import co.practices.parkinglot.internal.ultility.CommandLineHelper
import co.practices.parkinglot.service.IParkingService
import co.practices.parkinglot.service.manager.ParkingService
import java.util.*

object ParkingLotApplication {
    private val scanner = Scanner(System.`in`)
    private lateinit var parkingService: IParkingService

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
                                    CommandConstants.PARK.command -> park(
                                        CommandLineHelper.getString(
                                            scanner,
                                            "car-number"
                                        )
                                    )

                                    CommandConstants.LEAVE.command -> leave(
                                        CommandLineHelper.getString(
                                            scanner,
                                            "car-number"
                                        ), CommandLineHelper.getInteger(scanner, "hours", 1)
                                    )

                                    CommandConstants.STATUS.command -> status()
                                    else -> {
                                        throw InputException(MessageConstants.EXCEPTION_ENTER_INVALID_COMMAND.message)
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