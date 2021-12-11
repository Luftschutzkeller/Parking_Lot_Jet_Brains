package parking

data class Car(val regPlate: String, val color: String, val spotNum: Int)

object Parking {
    private val spots = mutableListOf<Car?>()

    fun createSpots(spotsCount: Int): String {
        spots.clear()
        repeat(spotsCount) { spots.add(null) }
        return "Created a parking lot with $spotsCount spots."
    }

    fun checkSpotsCreated() = spots.isNotEmpty()

    fun parkCar(regPlate: String, color: String): String {
        val nullSpot = spots.indexOf(null)
        return when {
            spots.isNotEmpty() && nullSpot == -1 -> "Sorry, the parking lot is full."
            else -> {
                spots[nullSpot] = Car(regPlate, color, nullSpot + 1)
                "$color car parked in spot ${nullSpot + 1}."
            }
        }
    }

    fun leaveSpot(spotNum: Int): String = when {
        spotNum <= spots.size && spots[spotNum - 1] != null -> {
            spots[spotNum - 1] = null
            "Spot $spotNum is free."
        }
        else -> "There is no car in spot ${spotNum}."
    }

    private enum class FilterCarsBy(val strValue: String) {
        COLOR("color"),
        REG_PLATE("registration number"),
        SPOT("spot"),
    }

    private fun filterCars(filterValue: String, base1: FilterCarsBy, filter: FilterCarsBy): String {
        val spotsNotNull = spots.filterNotNull()
        val carsByBase = when (filter) {
            FilterCarsBy.COLOR -> spotsNotNull.filter { it.color.lowercase() == filterValue.lowercase() }
            FilterCarsBy.REG_PLATE -> spotsNotNull.filter { it.regPlate == filterValue }
            FilterCarsBy.SPOT -> spotsNotNull.filter { it.spotNum.toString() == filterValue }
        }
        return if (carsByBase.isEmpty()) "No cars with ${filter.strValue} $filterValue were found."
        else when (base1) {
            FilterCarsBy.REG_PLATE -> carsByBase.joinToString { it.regPlate }
            FilterCarsBy.SPOT -> carsByBase.joinToString { it.spotNum.toString() }
            FilterCarsBy.COLOR -> carsByBase.joinToString { it.color }
        }
    }

    fun regByColor(color: String) = filterCars(color, FilterCarsBy.REG_PLATE, FilterCarsBy.COLOR)

    fun spotByColor(color: String) = filterCars(color, FilterCarsBy.SPOT, FilterCarsBy.COLOR)

    fun spotByReg(regPlate: String) = filterCars(regPlate, FilterCarsBy.SPOT, FilterCarsBy.REG_PLATE)

    override fun toString(): String {
        val spotsNotNull = spots.filterNotNull()
        return if (spotsNotNull.isEmpty()) "Parking lot is empty."
        else spotsNotNull.joinToString(separator = "\n") { "${it.spotNum} ${it.regPlate} ${it.color}" }
    }
}

fun main() {
    val parking = Parking

    while (true) {
        val input = readLine()!!.split(' ')
        if (input[0] != "exit"
            && input[0] != "create"
            && !parking.checkSpotsCreated()
        ) println("Sorry, a parking lot has not been created.")
        else when (input[0]) {
            "create" -> println(parking.createSpots(input[1].toInt()))
            "status" -> println(parking)
            "park" -> println(parking.parkCar(input[1], input[2]))
            "leave" -> println(parking.leaveSpot(input[1].toInt()))
            "reg_by_color" -> println(parking.regByColor(input[1]))
            "spot_by_color" -> println(parking.spotByColor(input[1]))
            "spot_by_reg" -> println(parking.spotByReg(input[1]))
            "exit" -> return
        }
    }
}