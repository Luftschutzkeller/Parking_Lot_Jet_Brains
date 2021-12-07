package parking

fun main() {
    data class Car(val regNumber: String, val color: String)

    val parking = mutableListOf<Car?>()

    fun park(regNumber: String, color: String): String {
        val nullSpot = parking.indexOf(null)
        if (parking.size == 20 && nullSpot == -1) return "Sorry, the parking lot is full."
        return if (nullSpot != -1) {
            parking[nullSpot] = Car(regNumber, color)
            "$color car parked in spot ${nullSpot + 1}."
        } else {
            parking.add(Car(regNumber, color))
            val spotNumber = parking.indices.last + 1
            "$color car parked in spot ${spotNumber}."
        }
    }

    fun leave(spot: Int): String {
        return if (spot <= parking.size && parking[spot - 1] != null) {
            parking[spot - 1] = null
            "Spot $spot is free."
        } else "There is no car in spot ${spot}."
    }
    while (true) {
        val input = readLine()!!.split(' ')
        when (input[0]) {
            "park" -> println(park(input[1], input[2]))
            "leave" -> println(leave(input[1].toInt()))
            "exit" -> return
        }
    }
}
