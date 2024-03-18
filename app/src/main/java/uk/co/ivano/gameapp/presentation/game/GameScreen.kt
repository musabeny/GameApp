package uk.co.ivano.gameapp.presentation.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uk.co.ivano.gameapp.core.util.GameChance
import uk.co.ivano.gameapp.core.util.GameMode
import uk.co.ivano.gameapp.core.util.GameObject
import uk.co.ivano.gameapp.presentation.game.component.LifeIcon
import uk.co.ivano.gameapp.presentation.game.component.Score
import uk.co.ivano.gameapp.ui.theme.WindowType
import uk.co.ivano.gameapp.ui.theme.background

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun GameScreen(
    state: GameState,
    event: (GameEvent) -> Unit,
    gameMode: GameMode?,
    windowType: WindowType,
    navController: NavHostController
){


    val config = LocalConfiguration.current

    LaunchedEffect(key1 = true){
        gameMode?.let {
            event(GameEvent.SelectedMode(it))
        }
        event(GameEvent.CountTime(navController = navController))

    }

    val animatableY = remember { Animatable(0f) }
    val animatableY2 = remember { Animatable(0f) }
    val animatableY3 = remember { Animatable(0f) }
    val animatableY4 = remember { Animatable(0f) }
    val animatableY5 = remember { Animatable(0f) }


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
                        if(this.value > 0 && !state.fallingObject3.shouldShow){
                            event(GameEvent.WaitingObject(GameObject.Object3))
                        }

                    }
                }

                launch {
                    animatableY4.animateTo(
                        targetValue = config.screenHeightDp.toFloat()/2, // end position
                        animationSpec = tween(
                            durationMillis = state.duration/2,
                            easing = LinearEasing,
                            delayMillis = state.fallingObject4.delay.toInt()
                        )
                    ){
                        if(this.value > 0 && !state.fallingObject4.shouldShow){
                            event(GameEvent.WaitingObject(GameObject.Object4))
                        }

                    }
                }
                launch {
                    animatableY5.animateTo(
                        targetValue = config.screenHeightDp.toFloat()/2, // end position
                        animationSpec = tween(
                            durationMillis = state.duration/2,
                            easing = LinearEasing,
                            delayMillis = state.fallingObject5.delay.toInt()
                        )
                    ){
                        if(this.value > 0 && !state.fallingObject5.shouldShow){
                            event(GameEvent.WaitingObject(GameObject.Object5))
                        }

                    }
                }

            }
            coroutineScope {
                event(GameEvent.RotateGameChance)
                launch {
                    animatableY.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 0) )
              }
                launch {
                    animatableY2.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 0) )
                }

                launch {
                    animatableY3.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 0) )
                }

                launch {
                    animatableY4.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 0) )
                }

                launch {
                    animatableY5.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 0) )
                }

            }
        }
    }

    when(windowType){
        WindowType.Compact ->{
            GameCompact(state = state, event = event, transition =animatableY.value, transition2 = animatableY2.value , transition3 = animatableY3.value, transition4 = animatableY4.value, transition5 = animatableY5.value)
        }
        else ->{
            GameLarge(state = state, event = event, transition = animatableY.value, transition2 = animatableY2.value, transition3 = animatableY3.value, transition4 = animatableY4.value, transition5 = animatableY5.value)
        }
    }


}

@Composable
fun GameCompact(
    state: GameState,
    event: (GameEvent) -> Unit,
    transition: Float,
    transition2: Float,
    transition3: Float,
    transition4: Float,
    transition5: Float
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

                LifeAndTime(state)


            }


            FallingObject(
                state = state ,
                event = event,
                transition = transition,
                modifier = Modifier.fillMaxSize(),
                transition2 = transition2,
                transition3 = transition3,
                transition4 = transition4,
                transition5 = transition5
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
    transition4: Float,
    transition5: Float
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
                    LifeAndTime(state)


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
               transition3 = transition3,
               transition4 = transition4,
               transition5 = transition5
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
    transition4: Float,
    transition5: Float,
    modifier: Modifier
){

    Box(modifier = modifier) {

        VisibilityObject(visible = state.fallingObject2.shouldShow) {

            CatchObject(
               transition =transition2 ,
               icon = state.fallingObject2.icon,
               gameObject =GameObject.Object2 ,
               chance =state.fallingObject2.chance ,
               shouldShow = state.fallingObject2.shouldShow,
              modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart,
               event = event
           )
        }
        VisibilityObject(visible = state.fallingObject1.shouldShow) {

            CatchObject(
                transition =transition ,
                icon = state.fallingObject1.icon,
                gameObject =GameObject.Object1 ,
                chance =state.fallingObject1.chance ,
                shouldShow = state.fallingObject1.shouldShow,
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
                event = event
            )
        }

        VisibilityObject(visible = state.fallingObject3.shouldShow) {

            CatchObject(
                transition =transition3 ,
                icon = state.fallingObject3.icon,
                gameObject =GameObject.Object3 ,
                chance =state.fallingObject3.chance ,
                shouldShow = state.fallingObject3.shouldShow,
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd,
                event = event
            )
        }

        VisibilityObject(visible = state.fallingObject4.shouldShow) {

            CatchObject(
                transition =transition4 ,
                icon = state.fallingObject4.icon,
                gameObject =GameObject.Object4 ,
                chance =state.fallingObject4.chance ,
                shouldShow = state.fallingObject4.shouldShow,
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart,
                event = event
            )


        }

        VisibilityObject(visible = state.fallingObject5.shouldShow) {
            CatchObject(
                transition =transition5 ,
                icon = state.fallingObject5.icon,
                gameObject =GameObject.Object5 ,
                chance =state.fallingObject5.chance ,
                shouldShow = state.fallingObject5.shouldShow,
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd,
                event = event
            )
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

@Composable
fun LifeAndTime(
    state: GameState
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier) {
            for(i in 1..state.life){
                LifeIcon(modifier = Modifier.size(30.dp))
            }
        }
        Text(
            text = "Time elapse: ${state.gameTime + 1}/10 ",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CatchObject(
   transition:Float,
   icon:Int,
   gameObject: GameObject,
   chance: GameChance,
   shouldShow:Boolean,
   modifier: Modifier,
   contentAlignment: Alignment,
   event: (GameEvent) -> Unit

){
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ){

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .offset(y = transition.dp)
                .clickable {
                    if (shouldShow) {
                        event(
                            GameEvent.GetPoints(
                                gameObject,
                                chance
                            )
                        )
                    }

                }
        )


    }
}