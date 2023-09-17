import android.util.Log
import androidx.compose.runtime.Composable
import screen.ShowHomeScreen

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = ShowHomeScreen()

actual fun printLog(msg:String){
    Log.i("TAG","Android:: $msg")
}
