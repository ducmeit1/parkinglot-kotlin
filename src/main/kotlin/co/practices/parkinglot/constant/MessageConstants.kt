package co.practices.parkinglot.constant

enum class MessageConstants(var message: String) {
    //successful messages
    SUCCESSFUL_CREATE_NEW_PARKING_LOTS_CAPACITY("Created parking slot with %d slots"),
    SUCCESSFUL_LEAVE_("Registration Number %s from Lot %d has left with Charge %d"),
    SUCCESSFUL_ALLOCATED__LOT("Allocated lot number: %d"),

    //Exception messages
    EXCEPTION_CREATE_NEW_PARKING_LOTS_CAPACITY("The capacity of parking lots has been created. You not able to change it!"),
    EXCEPTION_REGISTRATION_NUMBER_NOT_FOUND("Registration Number %s not found"),
    EXCEPTION_PARKING_LOTS_IS_FULL("Sorry, parking lot is full"),
    EXCEPTION_PARKING_SERVICE_NULL("The parking slots capacity wasn't created yet"),
    EXCEPTION_ENTER_INVALID_COMMAND("No command exist"),
    EXCEPTION_INVALID_INTEGER_INPUT("The input: %s must be integer, and greater than: %d"),
    EXCEPTION_INVALID_STRING_INPUT("The input: %s must not empty")
}