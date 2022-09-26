package co.practices.parkinglot.domain

data class Car(val registrationNumber: String, val parkingLot: Int) {
    override fun toString(): String {
        return "${parkingLot}\t\t\t${registrationNumber}"
    }
}