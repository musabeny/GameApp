package uk.co.ivano.gameapp.presentation.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.InfiniteTransition
import uk.co.ivano.gameapp.core.util.GameMode

sealed interface GameEvent {
    data object ObjectFalling:GameEvent
    data class StartAnim(val animatableY: Animatable<Float, AnimationVector1D>):GameEvent
    data  class SelectedMode(val gameMode: GameMode):GameEvent
    data object RotateGameChance:GameEvent
    data object GetPoints:GameEvent

}