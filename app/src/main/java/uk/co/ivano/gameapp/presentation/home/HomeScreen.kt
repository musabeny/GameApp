package uk.co.ivano.gameapp.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.co.ivano.gameapp.R
import uk.co.ivano.gameapp.core.util.GameMode
import uk.co.ivano.gameapp.presentation.home.component.UserMode
import uk.co.ivano.gameapp.ui.theme.WindowSize
import uk.co.ivano.gameapp.ui.theme.WindowType
import uk.co.ivano.gameapp.ui.theme.background
import uk.co.ivano.gameapp.ui.theme.casinoFont
import uk.co.ivano.gameapp.ui.theme.darkRed
import uk.co.ivano.gameapp.ui.theme.darkYellow
import uk.co.ivano.gameapp.ui.theme.green20
import uk.co.ivano.gameapp.ui.theme.red80
import uk.co.ivano.gameapp.ui.theme.rememberWindowSize
import javax.annotation.meta.When

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    navController: NavHostController,
    windowType: WindowType
){

     when(windowType){
        WindowType.Compact -> {
            HomeCompact(state = state, onEvent = onEvent, navController = navController )
        }
        else ->{
            HomeLarge(state = state, onEvent = onEvent, navController = navController)
        }
    }


}

@Composable
fun HomeCompact(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    navController: NavHostController
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            ,
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                HeadingText(
                    name =  "CASINO",
                    color = darkYellow,
                    size = 90
                )
                HeadingText(
                    name =  "CUT",
                    color = red80,
                    size = 170
                )
                Text(
                    text = "SELECT YOUR MODE:",
                    color = darkRed,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }


            GameModes(onEvent = onEvent, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
                navController = navController
            )
        }



    }
}

@Composable
fun HomeLarge(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    navController: NavHostController
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        contentAlignment = Alignment.Center
    ){
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeadingText(
                    name =  "CASINO",
                    color = darkYellow,
                    size = 90
                )
                HeadingText(
                    name =  "CUT",
                    color = red80,
                    size = 170
                )
                Text(
                    text = "SELECT YOUR MODE:",
                    color = darkRed,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )


            }
            GameModes(onEvent = onEvent, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
                navController = navController
            )

        }




    }
}

@Composable
fun GameModes(
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier,
    navController: NavHostController
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserMode(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .clickable {
                    onEvent(
                        HomeEvent.SelectMode(
                            navController = navController,
                            GameMode.Flowers
                        )
                    )
                }
            ,
            color = green20,
            title = stringResource(id = R.string.flowers),
            icon = R.drawable.flowers
        )
        UserMode(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .clickable {
                    onEvent(
                        HomeEvent.SelectMode(
                            navController = navController,
                            GameMode.Fruits
                        )
                    )
                },
            color = green20,
            title = stringResource(id = R.string.fruits),
            icon = R.drawable.fruits
        )
        UserMode(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .clickable {
                    onEvent(
                        HomeEvent.SelectMode(
                            navController = navController,
                            GameMode.Vegetables
                        )
                    )
                },
            color = green20,
            title = stringResource(id = R.string.vegetables),
            icon = R.drawable.vegetables
        )
    }
}

@Composable
fun HeadingText(
    name:String,
    color: Color,
    size:Int
){
    Text(
        text = name,
        fontFamily = casinoFont,
        fontWeight = FontWeight.ExtraBold,
        color = color,
        fontSize = size.sp,
    )
}

