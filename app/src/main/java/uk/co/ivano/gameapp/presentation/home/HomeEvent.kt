package uk.co.ivano.gameapp.presentation.home

import androidx.navigation.NavHostController
import uk.co.ivano.gameapp.core.util.GameMode

sealed interface  HomeEvent {

    data class SelectMode(val navController: NavHostController,val mode:GameMode):HomeEvent
}