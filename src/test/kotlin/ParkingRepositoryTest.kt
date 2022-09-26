import co.practices.parkinglot.domain.Car
import co.practices.parkinglot.repository.IParkingRepository
import co.practices.parkinglot.repository.manager.ParkingRepository
import org.junit.Before
import org.junit.Test
import kotlin.test.DefaultAsserter.assertEquals

class ParkingRepositoryTest {
    private lateinit var parkingRepository: IParkingRepository

    @Before
    fun before() {
        parkingRepository = ParkingRepository(10)
    }

    @Test
    fun addSuccess() {
        for (index in 1..10){
            val registrationNumber = String.format("%d", index)
            val car = Car(registrationNumber)
            assertEquals("Registration number of car isn't correct",registrationNumber, car.registrationNumber)
            val actual = this.parkingRepository.add(car)
            assertEquals("Return wrong free slot", index, actual)
        }
    }
}