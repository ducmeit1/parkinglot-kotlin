package co.practices.parkinglot
import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import java.util.*

data class ParkingLotApplicationTestCase(val name: String, val input: List<String>, val expected: List<String>){}

class ParkingLotApplicationTest : FunSpec({
    context("Test parking lot application") {
        withData(
            nameFn = { it.name },
            ParkingLotApplicationTestCase(
                name = "create parking lot and do every action successful",
                input = listOf("create 2", "park a", "leave a 20", "exit"),
                expected = listOf(
                    "Created parking slot with 2 slots",
                    "Allocated lot number: 1",
                    "Registration Number a from Lot 1 has left with Charge 200",
                )
            ),
            ParkingLotApplicationTestCase(
                name = "create parking lot with error",
                input = listOf("create 2", "park a", "park a", "leave b 20", "exit"),
                expected = listOf(
                    "Created parking slot with 2 slots",
                    "Allocated lot number: 1",
                    "Sorry, Registration Number a already parked",
                    "Registration Number b not found",
                )
            ),
        ){(name, input, expected) ->
            val stdin = System.`in`
            val data = input.joinToString("\n").trim()
            System.setIn(data.byteInputStream())
            val scanner = Scanner(System.`in`)
            System.setIn(stdin)

            val parkingLotApplication = ParkingLotApplication(scanner, true)
            val actual = tapSystemOut{
                parkingLotApplication.bootstrap()
            }.trim().split("\n")

            for (index in expected.indices) {
                actual[index].trim().shouldBeEqualComparingTo(expected[index].trim())
            }
        }
    }
})
