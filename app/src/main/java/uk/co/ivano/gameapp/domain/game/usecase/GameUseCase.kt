package uk.co.ivano.gameapp.domain.game.usecase

import uk.co.ivano.gameapp.domain.game.usecase.useCase.GameSpeed
import uk.co.ivano.gameapp.domain.game.usecase.useCase.GenerateTwoGameStates

data class GameUseCase(
   val speed: GameSpeed,
   val gameChance: GenerateTwoGameStates,
)