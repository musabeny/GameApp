package uk.co.ivano.gameapp.presentation.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.InfiniteTransition
import uk.co.ivano.gameapp.core.util.GameChance
import uk.co.ivano.gameapp.core.util.GameMode
import uk.co.ivano.gameapp.core.util.GameObject

sealed interface GameEvent {
    data object ObjectFalling:GameEvent
    data object StartAnim:GameEvent
    data  class SelectedMode(val gameMode: GameMode):GameEvent
    data object RotateGameChance:GameEvent
    data class GetPoints(val gameObject: GameObject,val gameChance: GameChance):GameEvent
    data class WaitingObject(val gameObject: GameObject):GameEvent

}