package uk.co.ivano.gameapp.core.navigation

sealed class Screens(val route:String) {
     data object Home: Screens(Route.Home)
     data object Game:Screens(Route.Game)
}