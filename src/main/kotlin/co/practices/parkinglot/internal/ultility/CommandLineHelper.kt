package co.practices.parkinglot.internal.ultility

import co.practices.parkinglot.constant.MessageConstants
import co.practices.parkinglot.internal.exception.InputException
import java.util.*

object CommandLineHelper {
    fun showAvailableCommands() {
        println(
            """
                [Parking Lot]
                =============
                - create [size] - Create parking lot of size N
                - park [car-number] - Parks a car
                - leave [car-number] [hours] - Removes (unpack) a car
                - status - Print status of the parking lot
                - exit - Exit the program
                - help - Show help
            """.trimIndent().trimMargin()
        )
    }

    fun showInstructions() {
        println(
            """
               =============
               Please enter a valid command, use exit to exit the program or help to show available commands
            """.trimIndent().trimMargin()
        )
    }

    fun getInteger(scanner: Scanner, name: String, min: Int): Int {
        try {
            val value = Integer.parseInt(scanner.next())
            if (value < min) {
                throw InputException(String.format(MessageConstants.EXCEPTION_INVALID_INTEGER_INPUT.message, name, min))
            }
            return value
        } catch (e: Exception) {
            throw InputException(String.format(MessageConstants.EXCEPTION_INVALID_INTEGER_INPUT.message, name, min))
        }
    }

    fun getString(scanner: Scanner, name: String): String {
        val value = scanner.next()
        if (value.isEmpty()) {
            throw InputException(String.format(MessageConstants.EXCEPTION_INVALID_STRING_INPUT.message, name))
        }
        return value
    }
}