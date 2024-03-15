package uk.co.ivano.gameapp.domain.game.usecase.useCase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GameSpeed {
    suspend operator fun invoke() : Flow<Int> = flow {
        var time = 10000
        while (time > 500){
//            60000 5000
            delay(60000)
            time -= 500
            emit(time)

        }

    }
}