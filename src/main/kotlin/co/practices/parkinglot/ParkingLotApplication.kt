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
    private val parkingService: IParkingService = ParkingService

    fun bootstrap() {
        with(scanner) {
            CommandLineHelper.showAvailableCommands()
            CommandLineHelper.showInstructions()
            while (true) {
                try {
                    when (next()) {
                        CommandConstants.EXIT.command -> return

                        CommandConstants.HELP.command -> CommandLineHelper.showAvailableCommands()

                        CommandConstants.CREATE.command -> {
                            val parkingLotsCapacity = CommandLineHelper.getInteger(scanner, "size", 1)
                            println(parkingService.create(parkingLotsCapacity))
                        }

                        CommandConstants.PARK.command -> {
                            val carNumber = CommandLineHelper.getString(scanner, "car-number")
                            println(parkingService.park(carNumber))
                        }

                        CommandConstants.LEAVE.command -> {
                            val carNumber = CommandLineHelper.getString(scanner, "car-number")
                            val hours = CommandLineHelper.getInteger(scanner, "hours", 1)
                            println(parkingService.leave(carNumber, hours))
                        }

                        CommandConstants.STATUS.command -> {
                            println(parkingService.status())
                        }

                        else -> throw InputException(MessageConstants.EXCEPTION_ENTER_INVALID_COMMAND.message)
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