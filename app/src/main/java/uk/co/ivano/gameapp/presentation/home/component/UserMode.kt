package uk.co.ivano.gameapp.presentation.home.component

import android.graphics.Rect
import android.graphics.Typeface
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.ivano.gameapp.R
import uk.co.ivano.gameapp.ui.theme.GameAppTheme
import uk.co.ivano.gameapp.ui.theme.blue20
import uk.co.ivano.gameapp.ui.theme.brown20
import uk.co.ivano.gameapp.ui.theme.green20
import kotlin.math.min



@Composable
fun UserMode(
    modifier: Modifier ,
    color: Color,
    title: String,
    @DrawableRes icon:Int
){

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
       CircleMode(
           color = color,
           title = title
       )
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )

    }

}

@Composable
fun CircleMode(
    color: Color,
     title: String
){
    Canvas(modifier = Modifier.fillMaxSize() ){

        val radius = min(size.width,size.height)/2
        drawCircle(
            color = color,
            radius = radius,
            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
        )
        drawCircle(
            color = Color.White,
            radius = radius,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
       val c1 = Offset(
           x = radius/3 ,
           y = center.y - radius
       )
        val c2 = Offset(
            x = size.width - radius/2,
            y = center.y - radius
        )



        val path = Path().apply {
            moveTo(size.width * 0.09f,center.y )
            cubicTo(
                x1 = c1.x, y1 = c1.y,
                x2 = c2.x, y2 = c2.y,
                x3 = size.width * 0.95f,center.y
            )
        }



        this.drawContext.canvas.nativeCanvas.apply {
            drawTextOnPath(
                title,
                path.asAndroidPath(),
                0f,
                0f,
                android.graphics.Paint().apply {
                    this.color = android.graphics.Color.WHITE
                    this.textSize = 14.sp.toPx()
                    this.textAlign = android.graphics.Paint.Align.LEFT
                    this.setTypeface(Typeface.DEFAULT_BOLD)

                }
            )
        }

         rotate(180f){
             this.drawContext.canvas.nativeCanvas.apply {
                 drawTextOnPath(
                     title,
                     path.asAndroidPath(),
                     0f,
                     0f,
                     android.graphics.Paint().apply {
                         this.color = android.graphics.Color.WHITE
                         this.textSize = 14.sp.toPx()
                         this.textAlign = android.graphics.Paint.Align.LEFT
                         this.setTypeface(Typeface.DEFAULT_BOLD)
                     }
                 )
             }
         }



        drawCircle(
            color = color,
            radius = radius * .7f,
            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
        )

        drawCircle(
            color = Color.White,
            radius = radius * .7f,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
    }


}



@Preview(
    showBackground = false,
    apiLevel = 33,
)
@Composable
fun UserModePreview(){
    GameAppTheme {
        UserMode(
            modifier = Modifier.fillMaxWidth(),
            color = blue20,
            title = "arcade",
            icon = R.drawable.vegetables
        )
    }
}