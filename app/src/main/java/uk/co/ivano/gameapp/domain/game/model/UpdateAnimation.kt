package uk.co.ivano.gameapp.domain.game.model

import java.time.Duration

data class UpdateAnimation (
    val duration: Int,
    val objectDelays: List<Int>
)