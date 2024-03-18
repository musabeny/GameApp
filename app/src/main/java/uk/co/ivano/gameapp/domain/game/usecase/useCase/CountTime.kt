package uk.co.ivano.gameapp.domain.game.usecase.useCase

import android.os.SystemClock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountTime {

   suspend  operator fun invoke():Flow<Int> = flow{
        val startTime = SystemClock.uptimeMillis()
        while (true) {
            delay(60000)
            val elapsedTime = SystemClock.uptimeMillis() - startTime
            if (elapsedTime <= MINUTE_IN_MILLIS) {
                val time =  elapsedTime / 60000  // Convert to seconds (optional)
                emit(time.toInt())
            }else{
                emit(-1)
            }
        }
    }
}

val MINUTE_IN_MILLIS = 600000

//600000 ten minute