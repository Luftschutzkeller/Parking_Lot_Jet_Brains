package parking


fun main() {
    data class Car(val regNumber: String, val color: String)

    val parking = mutableListOf<Car?>(Car("reserved", "reserved"))

    fun park(regNumber: String, color: String): String {
        parking.add(Car(regNumber, color))
        val spotNumber = parking.indexOf(Car(regNumber, color)) + 1
        return "$color car parked in spot ${spotNumber}."
    }

    fun leave(spot: Int): String {
        return if (spot <= parking.size && parking[spot - 1] != null) {
            parking[spot - 1] = null
            "Spot $spot is free."
        } else "There is no car in spot ${spot}."
    }

    val input = readLine()!!.split(' ')
    when {
        input[0] == "park" -> println(park(input[1], input[2]))
        input[0] == "leave" -> println(leave(input[1].toInt()))
    }
}