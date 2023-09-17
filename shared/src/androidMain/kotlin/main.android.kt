import android.util.Log
import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App()

actual fun printLog(msg:String){
    Log.i("TAG","Android:: $msg")
}
