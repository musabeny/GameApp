package uk.co.ivano.gameapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uk.co.ivano.gameapp.core.navigation.Route
import uk.co.ivano.gameapp.core.navigation.Screens
import uk.co.ivano.gameapp.core.util.GameMode
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor():ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onEvent(event: HomeEvent){
        when(event){
           is HomeEvent.SelectMode ->{
               val gameMode = Json.encodeToString(event.mode)
                event.navController.navigate("Game/$gameMode")
            }
        }
    }
}