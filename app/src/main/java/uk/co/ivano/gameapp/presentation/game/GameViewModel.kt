package uk.co.ivano.gameapp.presentation.game

import android.os.SystemClock
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.ivano.gameapp.R
import uk.co.ivano.gameapp.core.util.GameChance
import uk.co.ivano.gameapp.core.util.GameMode
import uk.co.ivano.gameapp.domain.game.usecase.GameUseCase
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class GameViewModel @Inject constructor(
    private val useCase: GameUseCase
):ViewModel()  {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    init {
        onEvent(GameEvent.ObjectFalling)
        onEvent(GameEvent.RotateGameChance)
    }

    fun onEvent(event: GameEvent){
        when(event){
            is GameEvent.ObjectFalling ->{
                val startTime = SystemClock.uptimeMillis()
                viewModelScope.launch {
                   useCase.speed().onEach {duration ->

                       _state.update {
                           it.copy(duration = duration)
                       }

                   }.launchIn(viewModelScope)
                }


            }
            is GameEvent.StartAnim ->{}
            is GameEvent.SelectedMode ->{
                _state.update {
                    it.copy(gameMode = event.gameMode)
                }
                _state.update {
                    it.copy(modeIcon = when(event.gameMode){
                        GameMode.Vegetables -> R.drawable.vegetables
                        GameMode.Fruits -> R.drawable.fruits
                        GameMode.Flowers -> R.drawable.flowers
                    })
                }

                _state.update {
                    it.copy(fallingIcon = _state.value.modeIcon)
                }

            }
            is GameEvent.RotateGameChance ->{
                viewModelScope.launch {
                    _state.update {
                        it.copy(fallingIcon = _state.value.modeIcon)
                    }

                    while (true){
                        delay(Duration.parse("${_state.value.duration }ms"))
                        _state.update {
                            it.copy(shouldCount = true)
                        }
                       val (chance1,chance2) = useCase.gameChance(true,0.8)
                        _state.update {
                            it.copy(fallingCount = _state.value.fallingCount + 1)
                        }
                        if(chance1){
                          _state.update {
                              it.copy(chance = GameChance.Life)
                          }

                      } else{
                          _state.update {
                              it.copy(chance = GameChance.Danger)
                          }
                      }
                      if(_state.value.chance == GameChance.Life){
                          _state.update {
                              it.copy(fallingIcon = _state.value.modeIcon)
                          }
                      } else{
                          _state.update {
                              it.copy(fallingIcon = R.drawable.poison)
                          }
                      }
                    }
                }

            }
            is GameEvent.GetPoints ->{
                if(_state.value.chance == GameChance.Danger){
                    if(_state.value.life > 0){
                        _state.update {
                            it.copy(life = _state.value.life-1)
                        }
                    }

                    if(_state.value.life == 0){
                        _state.update {
                            it.copy(life =3)
                        }
                        _state.update {
                            it.copy(score =0)
                        }
                        _state.update {
                            it.copy(fallingCount = 0)
                        }
                    }

                }else{
                    _state.update {
                        it.copy(score =_state.value.score+1)
                    }
                }

                _state.update {
                    it.copy(shouldCount = false)
                }
            }
        }
    }
}