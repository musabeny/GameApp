package uk.co.ivano.gameapp.domain.game.usecase

import uk.co.ivano.gameapp.domain.game.usecase.useCase.CountTime
import uk.co.ivano.gameapp.domain.game.usecase.useCase.GameSpeed
import uk.co.ivano.gameapp.domain.game.usecase.useCase.GenerateTwoGameStates
import uk.co.ivano.gameapp.domain.game.usecase.useCase.RandomNumbersForDelay

data class GameUseCase(
   val speed: GameSpeed,
   val gameChance: GenerateTwoGameStates,
   val delays:RandomNumbersForDelay,
   val time: CountTime
)