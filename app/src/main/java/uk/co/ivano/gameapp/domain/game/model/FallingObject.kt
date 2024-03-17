package uk.co.ivano.gameapp.domain.game.model

import androidx.annotation.DrawableRes
import uk.co.ivano.gameapp.core.util.GameChance

data class FallingObject(
    val delay: Float,
    val shouldShow: Boolean,
    @DrawableRes val icon:Int,
    val chance:GameChance
)

