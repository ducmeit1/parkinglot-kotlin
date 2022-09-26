package co.practices.parkinglot.constant

enum class CommandConstants(var command: String) {
    CREATE("create"),
    PARK("park"),
    LEAVE("leave"),
    STATUS("status"),
    EXIT("exit"),
    HELP("help")
}