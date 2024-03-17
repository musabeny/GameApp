package uk.co.ivano.gameapp.presentation.game

import android.os.SystemClock
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.co.ivano.gameapp.core.util.GameMode
import uk.co.ivano.gameapp.core.util.GameObject
import uk.co.ivano.gameapp.presentation.game.component.LifeIcon
import uk.co.ivano.gameapp.presentation.game.component.Score
import uk.co.ivano.gameapp.presentation.home.HomeEvent
import uk.co.ivano.gameapp.ui.theme.WindowType
import uk.co.ivano.gameapp.ui.theme.background
import uk.co.ivano.gameapp.ui.theme.rememberWindowSize

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun GameScreen(
    state: GameState,
    event: (GameEvent) -> Unit,
    gameMode: GameMode?,
    windowType: WindowType
){


    val config = LocalConfiguration.current

    LaunchedEffect(key1 = true){
        gameMode?.let {
            event(GameEvent.SelectedMode(it))
        }
    }



    val animatableY = remember { Animatable(0f) }
    val animatableY2 = remember { Animatable(0f) }
    val animatableY3 = remember { Animatable(0f) }


    LaunchedEffect(key1 = state.animationState) {
        while (true) {
            coroutineScope {
               launch {
                   animatableY.animateTo(
                       targetValue = config.screenHeightDp.toFloat(), // end position
                       animationSpec = tween(
                           durationMillis = state.duration,
                           easing = LinearEasing,
                           delayMillis = state.fallingObject1.delay.toInt()
                       )
                   ){
                       if(this.value > 0 && !state.fallingObject1.shouldShow){
                           event(GameEvent.WaitingObject(GameObject.Object1))
                       }
                   }
               }
                launch {
                   animatableY2.animateTo(
                        targetValue = config.screenHeightDp.toFloat(), // end position
                        animationSpec = tween(
                            durationMillis = state.duration,
                            easing = LinearEasing,
                            delayMillis = state.fallingObject2.delay.toInt()
                        )
                    ){
                       if(this.value > 0 && !state.fallingObject2.shouldShow ){
                           event(GameEvent.WaitingObject(GameObject.Object2))
                       }

                   }
                }
                launch {
                    animatableY3.animateTo(
                        targetValue = config.screenHeightDp.toFloat(), // end position
                        animationSpec = tween(
                            durationMillis = state.duration,
                            easing = LinearEasing,
                            delayMillis = state.fallingObject3.delay.toInt()
                        )
                    ){
//                       Log.d("namana","animation status ${this.value} ")
                        if(this.value > 0 && !state.fallingObject3.shouldShow){
                            event(GameEvent.WaitingObject(GameObject.Object3))
                        }

                    }
                }

            }
            coroutineScope {
                launch {
                    event(GameEvent.RotateGameChance)
                    animatableY.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 0) )
              }
                launch {
                    animatableY2.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 0) )
                }

                launch {
                    animatableY3.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 0) )
                }

            }


        }

    }





    when(windowType){
        WindowType.Compact ->{
            GameCompact(state = state, event = event, transition =animatableY.value, transition2 = animatableY2.value , transition3 = animatableY3.value)
        }
        else ->{
            GameLarge(state = state, event = event, transition = animatableY.value, transition2 = animatableY2.value, transition3 = animatableY3.value)
        }
    }


}

@Composable
fun GameCompact(
    state: GameState,
    event: (GameEvent) -> Unit,
    transition: Float,
    transition2: Float,
    transition3: Float
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp)
            ,
        contentAlignment = Alignment.TopCenter
    ){
        Text(
            text = "${state.fallingCount}",
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            fontSize = 35.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center

        )
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Score(modifier = Modifier,
                    icon = state.modeIcon,
                    score = state.score
                )

                Row(modifier = Modifier) {
                    for(i in 1..state.life){
                        LifeIcon(modifier = Modifier.size(30.dp))
                    }
                }

            }


            FallingObject(
                state = state ,
                event = event,
                transition = transition,
                modifier = Modifier.fillMaxSize(),
                transition2 = transition2,
                transition3 = transition3
            )

        }

    }
}

@Composable
fun GameLarge(
    state: GameState,
    event: (GameEvent) -> Unit,
    transition: Float,
    transition2: Float,
    transition3: Float,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 8.dp)
            .background(background),
        contentAlignment = Alignment.TopCenter
    ){

        Row {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Score(modifier = Modifier,
                        icon = state.modeIcon,
                        score = state.score
                    )

                    Row(modifier = Modifier) {
                        for(i in 1..state.life){
                            LifeIcon(modifier = Modifier.size(30.dp))
                        }
                    }

                }

                Text(
                    text = "${state.fallingCount}",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 35.sp,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,


                )
            }

           FallingObject(
               state = state ,
               event = event,
               transition = transition,
               modifier = Modifier.weight(1f),
               transition2 = transition2,
               transition3 = transition3
           )

        }

    }
}

@Composable
fun FallingObject(
    state: GameState,
    event: (GameEvent) -> Unit,
    transition: Float,
    transition2: Float,
    transition3: Float,
    modifier: Modifier
){

    Box(modifier = modifier) {

        VisibilityObject(visible = state.fallingObject2.shouldShow) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ){

                Image(
                    painter = painterResource(id = state.fallingObject2.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .offset(y = transition2.dp)
                        .clickable {
                            if (state.fallingObject2.shouldShow) {
                                event(GameEvent.GetPoints(GameObject.Object2,state.fallingObject2.chance))
                            }

                        }
                )


            }
        }
        VisibilityObject(visible = state.fallingObject1.shouldShow) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ){

                Image(
                    painter = painterResource(id = state.fallingObject1.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .offset(y = transition.dp)
                        .clickable {
                            if (state.fallingObject1.shouldShow) {
                                event(GameEvent.GetPoints(GameObject.Object1,state.fallingObject1.chance))
                            }

                        }
                )


            }
        }

        VisibilityObject(visible = state.fallingObject3.shouldShow) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ){

                Image(
                    painter = painterResource(id = state.fallingObject3.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .offset(y = transition3.dp)
                        .clickable {
                            if (state.fallingObject3.shouldShow) {
                                event(GameEvent.GetPoints(GameObject.Object3,state.fallingObject3.chance))
                            }

                        }
                )


            }
        }

    }


}

@Composable
fun VisibilityObject(
    visible:Boolean,
     content:@Composable ()-> Unit

){
   AnimatedVisibility(
       visible = visible,
       enter = fadeIn(),
       exit = fadeOut()
   ) {
       content()
   }
}