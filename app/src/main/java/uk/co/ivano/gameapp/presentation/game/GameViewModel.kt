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
import uk.co.ivano.gameapp.core.util.GameObject
import uk.co.ivano.gameapp.domain.game.usecase.GameUseCase
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class GameViewModel @Inject constructor(
    private val useCase: GameUseCase
):ViewModel()  {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private var updateVisibility1 = true
    private var updateVisibility2 = true
    private var updateVisibility3 = true
    private var updateVisibility4 = true
    private var updateVisibility5 = true

    init {
        onEvent(GameEvent.ObjectFalling)
    }

    fun onEvent(event: GameEvent){
        when(event){
            is GameEvent.ObjectFalling ->{
                viewModelScope.launch {
                   useCase.speed().onEach {duration ->

                       _state.update {
                           it.copy(duration = duration)
                       }

                   }.launchIn(viewModelScope)
                }


            }
            is GameEvent.StartAnim ->{
                _state.update {
                    it.copy(fallingObject1 = _state.value.fallingObject1.copy(icon = _state.value.modeIcon ))
                }
                _state.update {
                    it.copy(fallingObject2 = _state.value.fallingObject1.copy(icon = _state.value.modeIcon ))
                }
                _state.update {
                    it.copy(fallingObject3 = _state.value.fallingObject1.copy(icon = _state.value.modeIcon ))
                }

                _state.update {
                    it.copy(fallingObject4 = _state.value.fallingObject4.copy(icon = _state.value.modeIcon ))
                }
                _state.update {
                    it.copy(fallingObject5 = _state.value.fallingObject5.copy(icon = _state.value.modeIcon ))
                }
            }
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

                onEvent(GameEvent.StartAnim)


            }
            is GameEvent.RotateGameChance ->{
                viewModelScope.launch {
                    updateVisibility1 = true
                    updateVisibility2 = true
                    updateVisibility3 = true
                    updateVisibility4 = true
                    updateVisibility5 = true
                    val objectDelays = useCase.delays(count = 3)
                    _state.update {
                        it.copy(animationState = it.animationState.copy(objectDelays = objectDelays))
                    }
//                    Log.d("namana","objectDelays $objectDelays")
                    _state.update {
                        it.copy(fallingObject1 = _state.value.fallingObject1.copy(delay = objectDelays[0].toFloat(),shouldShow = false ))
                    }
                    _state.update {
                        it.copy(fallingObject2 = _state.value.fallingObject2.copy(delay = objectDelays[1].toFloat(),shouldShow = false ))
                    }
                    _state.update {
                        it.copy(fallingObject3 = _state.value.fallingObject3.copy(delay = objectDelays[2].toFloat(),shouldShow = false ))
                    }

                    _state.update {
                        it.copy(fallingObject4 = _state.value.fallingObject4.copy(delay =0f,shouldShow = false ))
                    }
                    _state.update {
                        it.copy(fallingObject5 = _state.value.fallingObject5.copy(delay =0f,shouldShow = false ))
                    }
                    _state.update {
                            it.copy(shouldCount = true)
                        }
                       val (chance1,chance2) = useCase.gameChance(true,0.8)
                       val (chance3,chance4) = useCase.gameChance(true,0.8)
                       val (chance5,chance6) = useCase.gameChance(true,0.8)

                    val (chance7,chance8) = useCase.gameChance(true,0.8)
                    val (chance9,chance10) = useCase.gameChance(true,0.8)
                        _state.update {
                            it.copy(fallingCount = _state.value.fallingCount + 1)
                        }
                        if(chance1){
                            _state.update {
                                it.copy(fallingObject1 = _state.value.fallingObject1.copy(chance =  GameChance.Life, icon = _state.value.modeIcon))
                            }

                        } else{
                            _state.update {
                                it.copy(fallingObject1 = _state.value.fallingObject1.copy(chance = GameChance.Danger, icon = R.drawable.poison))
                            }

                      }

                    if(chance3){

                        _state.update {
                            it.copy(fallingObject2 = _state.value.fallingObject2.copy(chance = GameChance.Life ,icon = _state.value.modeIcon))
                        }

                    } else{

                        _state.update {
                            it.copy(fallingObject2 = _state.value.fallingObject2.copy(chance = GameChance.Danger,icon = R.drawable.poison ))
                        }
                    }

                    if(chance5){

                        _state.update {
                            it.copy(fallingObject3 = _state.value.fallingObject3.copy(chance = GameChance.Life, icon = _state.value.modeIcon ))
                        }

                    } else{

                        _state.update {
                            it.copy(fallingObject3 = _state.value.fallingObject3.copy(chance = GameChance.Danger , icon = R.drawable.poison))
                        }
                    }

                    if(chance7){
                        _state.update {
                            it.copy(fallingObject4 = _state.value.fallingObject5.copy(chance = GameChance.Life, icon = _state.value.modeIcon ))
                        }
                    }else{
                        _state.update {
                            it.copy(fallingObject4 = _state.value.fallingObject5.copy(chance = GameChance.Danger , icon = R.drawable.poison))
                        }
                    }

                    if(chance9){
                        _state.update {
                            it.copy(fallingObject5 = _state.value.fallingObject5.copy(chance = GameChance.Life, icon = _state.value.modeIcon ))
                        }
                    }else{
                        _state.update {
                            it.copy(fallingObject5 = _state.value.fallingObject5.copy(chance = GameChance.Danger , icon = R.drawable.poison))
                        }
                    }



//                    }
                }

            }
            is GameEvent.GetPoints ->{
                if(event.gameChance == GameChance.Danger){
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

                when(event.gameObject){
                    GameObject.Object1 ->{
                        _state.update {
                            it.copy(fallingObject1 = _state.value.fallingObject1.copy(shouldShow = false))
                        }

                    }
                    GameObject.Object2 ->{
                        _state.update {
                            it.copy(fallingObject2 = _state.value.fallingObject2.copy(shouldShow = false))
                        }
                    }
                    GameObject.Object3 ->{
                        _state.update {
                            it.copy(fallingObject3 = _state.value.fallingObject3.copy(shouldShow = false))
                        }
                    }
                    GameObject.Object4 ->{
                        _state.update {
                            it.copy(fallingObject4 = _state.value.fallingObject4.copy(shouldShow = false))
                        }
                    }
                    GameObject.Object5 ->{
                        _state.update {
                            it.copy(fallingObject5 = _state.value.fallingObject5.copy(shouldShow = false))
                        }
                    }
                }

                _state.update {
                    it.copy(shouldCount = false)
                }
            }
            is GameEvent.WaitingObject ->{
                when(event.gameObject){
                    GameObject.Object1 ->{
                        if(updateVisibility1){
                            updateVisibility1 = false
                            _state.update {
                                it.copy(fallingObject1 = _state.value.fallingObject1.copy(shouldShow = true))
                            }
                        }

                    }
                    GameObject.Object2 ->{
                        if(updateVisibility2){
                            updateVisibility2 = false
                            _state.update {
                                it.copy(fallingObject2 = _state.value.fallingObject2.copy(shouldShow = true))
                            }
                        }

                    }
                    GameObject.Object3 ->{
                         if(updateVisibility3){
                             updateVisibility3 = false
                             _state.update {
                                 it.copy(fallingObject3 = _state.value.fallingObject3.copy(shouldShow = true))
                             }
                         }


                    }
                    GameObject.Object4 ->{
                        if(updateVisibility4){
                            updateVisibility4 = false
                            _state.update {
                                it.copy(fallingObject4 = _state.value.fallingObject4.copy(shouldShow = true))
                            }
                        }


                    }
                    GameObject.Object5 ->{
                        if(updateVisibility5){
                            updateVisibility5 = false
                            _state.update {
                                it.copy(fallingObject5 = _state.value.fallingObject5.copy(shouldShow = true))
                            }
                        }


                    }
                }
            }
            is GameEvent.CountTime ->{
                viewModelScope.launch {
                    useCase.time().onEach {time ->
                        if(time == -1){
                           event.navController.popBackStack()
                        }else{
                            _state.update {
                                it.copy(gameTime = time)
                            }
                        }

                    }.launchIn(viewModelScope)
                }

            }
        }
    }
}