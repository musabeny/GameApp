package uk.co.ivano.gameapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.json.Json
import uk.co.ivano.gameapp.core.util.GameMode
import uk.co.ivano.gameapp.core.util.userMode
import uk.co.ivano.gameapp.presentation.game.GameScreen
import uk.co.ivano.gameapp.presentation.game.GameViewModel
import uk.co.ivano.gameapp.presentation.home.HomeScreen
import uk.co.ivano.gameapp.presentation.home.HomeViewModel
import uk.co.ivano.gameapp.ui.theme.WindowType

@Composable
fun GameNavHost(
    navController: NavHostController,
    windowType: WindowType
){
   NavHost(navController = navController, startDestination = Screens.Home.route){
       composable(route = Screens.Home.route){
           val viewModel = hiltViewModel<HomeViewModel>()
           val state by viewModel.state.collectAsState()
           HomeScreen(
               state = state,
               onEvent = viewModel::onEvent,
               navController = navController,
               windowType = windowType
           )
       }

       composable(
           route = Screens.Game.route,
           arguments = listOf(navArgument(userMode) { type = NavType.StringType })
       ){ it ->
           val argument = it.arguments?.getString(userMode)
           val gameMode = argument?.let {arg ->
               Json.decodeFromString<GameMode>(arg)
           }
           val viewModel = hiltViewModel<GameViewModel>()
           val state by viewModel.state.collectAsState()
           GameScreen(
               state = state,
               event = viewModel::onEvent,
               gameMode = gameMode,
               windowType = windowType,
               navController = navController
           )
       }
   }
}