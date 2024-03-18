package uk.co.ivano.gameapp.domain.game.usecase.useCase

import java.util.Random

class RandomNumbersForDelay {

    operator fun invoke(count: Int, rangeStart: Int = 0, rangeEnd: Int = 200):List<Int>{
        require(count >= 1) { "Number of random numbers must be at least 1." }
        require(rangeEnd >= rangeStart) { "Range end must be greater than or equal to range start." }

        val randomNumbers = mutableListOf<Int>()
        val randomGenerator = Random()

        // Ensure zero is included
        randomNumbers.add(0)

        while (randomNumbers.size < count) {
            val randomNumber = randomGenerator.nextInt(rangeEnd - rangeStart + 1) + rangeStart
            randomNumbers.add(randomNumber)
        }

        return randomNumbers.toList()
    }
}