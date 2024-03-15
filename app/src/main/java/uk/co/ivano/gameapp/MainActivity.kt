package uk.co.ivano.gameapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.co.ivano.gameapp.core.navigation.GameNavHost
import uk.co.ivano.gameapp.core.navigation.Screens
import uk.co.ivano.gameapp.ui.theme.GameAppTheme
import uk.co.ivano.gameapp.ui.theme.rememberWindowSize

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val route = navController.currentBackStackEntryAsState().value?.destination?.route
                    val windowSize = rememberWindowSize()
                    Scaffold(
                        topBar = {
                            if(route in listOf(
                               Screens.Game.route
                            )){
                                TopAppBar(
                                    title = {
                                       Text(text = navController.currentBackStackEntry?.destination?.route?.replaceAfter("/","")?.replace("/","") ?: "Title")
                                    },
                                    navigationIcon = {
                                        if(route in listOf( Screens.Game.route)){
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Back Arrow" ,
                                                modifier = Modifier.clickable {
                                                    navController.popBackStack()
                                                }
                                            )
                                        }

                                    }
                                )

                            }

                        }
                    ) {
                        Box(
                            modifier = Modifier.padding(0.dp,it.calculateTopPadding(),0.dp,0.dp)
                        ) {
                            GameNavHost(navController = navController, windowType = windowSize.width)
                        }

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GameAppTheme {
        Greeting("Android")
    }
}