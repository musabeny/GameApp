package uk.co.ivano.gameapp.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.ivano.gameapp.domain.game.usecase.GameUseCase
import uk.co.ivano.gameapp.domain.game.usecase.useCase.GameSpeed
import uk.co.ivano.gameapp.domain.game.usecase.useCase.GenerateTwoGameStates
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun  provideGameUseCase():GameUseCase{
        return GameUseCase(
            speed = GameSpeed(),
            gameChance = GenerateTwoGameStates()
        )
    }

}