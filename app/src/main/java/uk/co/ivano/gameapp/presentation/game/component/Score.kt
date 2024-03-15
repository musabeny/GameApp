package uk.co.ivano.gameapp.presentation.game.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Score(
    modifier: Modifier,
    icon:Int,
    score:Int
){
   Row(
       modifier = modifier,
       verticalAlignment = Alignment.CenterVertically
   ) {
       Image(
           painter = painterResource(id = icon),
           contentDescription = null,
           modifier = Modifier.size(50.dp)
       )
       Column {
           Text(
               text = "Score",
               fontWeight = FontWeight.ExtraBold,
               color = Color.Yellow,
               fontSize = 30.sp
           )
           Text(
               text = "$score",
               fontWeight = FontWeight.ExtraBold,
               fontSize = 30.sp,
               color = Color.White,
           )
       }
   }
}