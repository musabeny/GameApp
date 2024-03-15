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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.co.ivano.gameapp.core.util.GameMode
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

    LaunchedEffect(key1 = state.duration) {
        while (true) {
         animatableY.animateTo(
                targetValue = config.screenHeightDp.toFloat(), // end position
                animationSpec = tween(
                    durationMillis = state.duration,
                    easing = LinearEasing
                )
            )
            animatableY.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 0) )

        }
    }





    when(windowType){
        WindowType.Compact ->{
            GameCompact(state = state, event = event, transition =animatableY.value )
        }
        else ->{
            GameLarge(state = state, event = event, transition = animatableY.value)
        }
    }


}

@Composable
fun GameCompact(
    state: GameState,
    event: (GameEvent) -> Unit,
    transition: Float,
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
                modifier = Modifier.fillMaxSize()
            )

        }

    }
}

@Composable
fun GameLarge(
    state: GameState,
    event: (GameEvent) -> Unit,
    transition: Float
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
               modifier = Modifier.weight(1f)
           )

        }

    }
}

@Composable
fun FallingObject(
    state: GameState,
    event: (GameEvent) -> Unit,
    transition: Float,
    modifier: Modifier
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ){

            Image(
                painter = painterResource(id = state.fallingIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .offset(y = transition.dp)
                    .clickable {
                        if (state.shouldCount) {
                            event(GameEvent.GetPoints)
                        }

                    }
            )


    }
}