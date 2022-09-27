package co.practices.parkinglot.repository

import co.practices.parkinglot.constant.MessageConstants
import co.practices.parkinglot.domain.Car
import co.practices.parkinglot.internal.exception.ParkingException
import co.practices.parkinglot.repository.manager.ParkingRepository
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.WithDataTestName
import io.kotest.datatest.withData
import io.kotest.matchers.comparables.shouldBeEqualComparingTo

data class ParkingRepositoryTestCase(
    val name: String,
    val parkingLotCapacity: Int,
    val input: List<String>,
    val want: List<Int>?,
    val throwException: Boolean,
    val exception: ParkingException?
) : WithDataTestName {
    override fun dataTestName(): String {
        return name
    }
}

class ParkingRepositoryTest : FunSpec({
    context("Test add car to parking") {
        withData(
            ParkingRepositoryTestCase(
                name = "Add to parking successful",
                parkingLotCapacity = 2,
                input = listOf("11", "22"),
                want = listOf(1, 2),
                throwException = false,
                exception = null
            ),
            ParkingRepositoryTestCase(
                name = "Throw exception due the parking has full lot",
                parkingLotCapacity = 1,
                input = listOf("11", "22"),
                want = null,
                throwException = true,
                exception = ParkingException(MessageConstants.EXCEPTION_PARKING_LOTS_IS_FULL.message)
            ),
            ParkingRepositoryTestCase(
                name = "Throw exception due the car has parked",
                parkingLotCapacity = 2,
                input = listOf("11", "11"),
                want = null,
                throwException = true,
                exception = ParkingException(String.format(MessageConstants.EXCEPTION_ALREADY_PARKED.message, "11"))
            )
        ) { (name, parkingLotCapacity, input, want, throwException, exception) ->
            val parkingRepository: IParkingRepository = ParkingRepository(parkingLotCapacity)
            if (throwException) {
                val actualException = shouldThrowExactly<ParkingException> {
                    input.forEach { r ->
                        parkingRepository.add(Car(registrationNumber = r))
                    }
                }
                actualException.message!!.shouldBeEqualComparingTo(exception!!.message!!)
            } else {
                for (index in want!!.indices) {
                    val actual = parkingRepository.add(Car(registrationNumber = input[index]))
                    actual.shouldBeEqualComparingTo(want[index])
                }
            }
        }
    }

    context("Test remove car from parking") {
        withData(
            ParkingRepositoryTestCase(
                name = "Remove car from parking successful",
                parkingLotCapacity = 2,
                input = listOf("11", "22"),
                want = listOf(1, 2),
                throwException = false,
                exception = null
            ),
            ParkingRepositoryTestCase(
                name = "Throw exception due the registration number not found",
                parkingLotCapacity = 1,
                input = listOf("11"),
                want = null,
                throwException = true,
                exception = ParkingException(
                    String.format(
                        MessageConstants.EXCEPTION_REGISTRATION_NUMBER_NOT_FOUND.message,
                        "11"
                    )
                )
            ),
        ) { (name, parkingLotCapacity, input, want, throwException, exception) ->
            val parkingRepository: IParkingRepository = ParkingRepository(parkingLotCapacity)
            if (throwException) {
                val actualException = shouldThrowExactly<ParkingException> {
                    input.forEach { r ->
                        parkingRepository.remove(r)
                    }
                }
                actualException.message!!.shouldBeEqualComparingTo(exception!!.message!!)
            } else {
                for (index in want!!.indices) {
                    val addActual = parkingRepository.add(Car(registrationNumber = input[index]))
                    addActual.shouldBeEqualComparingTo(want[index])
                }
                for (index in want.indices) {
                    val removeActual = parkingRepository.remove(registrationNumber = input[index])
                    removeActual.shouldBeEqualComparingTo(want[index])
                }
            }
        }
    }
})