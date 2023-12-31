import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import utils.ImageItem
import data.IplReadData
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.resource
import screen.ShowHomeScreen
@Composable
fun App() {

    LaunchedEffect("Home"){
        readJson()
    }
    MaterialTheme {
        ShowHomeScreen()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShowMainView(){
    var greetingText by remember { mutableStateOf("Hello, World!") }
    var showImage by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            greetingText = "Hello, ${getPlatformName()}"
            showImage = !showImage
        }) {
            Text(greetingText)
        }
        AnimatedVisibility(showImage) {
            Column {
                Image(
                    painterResource("compose-multiplatform.xml"), null
                )
                ImageItem(modifier = Modifier.aspectRatio(1.0F),data = "https://www.countryflags.com/wp-content/uploads/india-flag-png-large.png",
                    blurRadius =  0)
            }

        }
    }
}

@OptIn(ExperimentalResourceApi::class)
suspend fun readJson():IplReadData?{
    try {
        val configString = resource("teamlist.json").readBytes().decodeToString()
        printLog(configString)
        val json = Json {ignoreUnknownKeys = true }
        val teamList =json.decodeFromString(IplReadData.serializer(),configString)
        printLog("${teamList.mArrayList!!.size}")
        return teamList
       // _masterModelState.update { s -> s.copy(config = format.decodeFromString(configString)) }
    } catch (e: Exception) {
        e.printStackTrace()
       // log.error(e) { "Could not read configuration resource file." }
    }
    return null
}

expect fun getPlatformName(): String

//expect suspend fun getJsondata ():String
expect fun printLog(msg: String)