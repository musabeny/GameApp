package uk.co.ivano.gameapp.core.navigation

import uk.co.ivano.gameapp.core.util.userMode

object Route {
    const val Home = "Home"
    const val Game = "Game/{$userMode}"
}