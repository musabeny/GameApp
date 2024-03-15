package uk.co.ivano.gameapp.presentation.game

import androidx.annotation.DrawableRes
import uk.co.ivano.gameapp.R
import uk.co.ivano.gameapp.core.util.GameChance
import uk.co.ivano.gameapp.core.util.GameMode
import uk.co.ivano.gameapp.domain.game.model.FallingObject

data class GameState(
    val duration: Int = 10000,
    val animateState:Boolean = false,
    val gameMode: GameMode = GameMode.Vegetables,
     @DrawableRes val modeIcon:Int = R.drawable.vegetables,
    val life :Int = 3,
    val chance: GameChance = GameChance.Life,
    @DrawableRes val fallingIcon:Int = R.drawable.vegetables,
    val  score:Int = 0,
    val shouldCount:Boolean = true,
    val fallingCount:Int = 0,
    val animationProgress:Float = 0f,
    val fallingObject: FallingObject = FallingObject(0f,0f), //to be deleted
    val lastUpdateTime:Long = 0
)
