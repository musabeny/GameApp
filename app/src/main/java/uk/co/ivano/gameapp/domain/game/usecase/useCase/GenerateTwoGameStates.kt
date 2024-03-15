package uk.co.ivano.gameapp.domain.game.usecase.useCase

class GenerateTwoGameStates {
    operator fun invoke(highProbState: Boolean, highProb: Double): Pair<Boolean, Boolean> {
        val randomValue = Math.random() // Generate random double between 0.0 and 1.0
        val state1 = if (randomValue < highProb) highProbState else !highProbState
        val state2 = !state1
        return Pair(state1, state2)
    }
}