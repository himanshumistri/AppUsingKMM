import androidx.compose.ui.window.ComposeUIViewController
import platform.Foundation.NSLog
import screen.ShowHomeScreen


actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { App() }

actual fun printLog(msg:String){
   NSLog("Ios:: $msg")
}