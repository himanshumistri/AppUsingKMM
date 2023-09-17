package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utils.ImageItem
import data.IplTeam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import printLog
import utils.AppConstants
import viewmodel.IplViewModel
import data.SortType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private var isMyData = false
private var mHeaderHeight=58.dp
private var mSpaceTxtImg = 20.dp
private val HeaderColor = Color(0xFFAD1457)
private val DarkGreen = Color(0xFF2E7D32) //
private val TextColor = Color.White

/**
 * @author Himanshu.Mistri
 */
@Composable
fun ShowHomeScreen(){

    val iplViewModel = remember {
        printLog("IplViewModel object is created ")
        IplViewModel()
    }

    LaunchedEffect("HomeScreen"){
        withContext(Dispatchers.IO){
            val iplReadData =iplViewModel.readJson()
            if(iplReadData?.mArrayList != null){
                iplViewModel.addAll(iplReadData.mArrayList!!)
            }
        }
    }
    MaterialTheme {
        Column (modifier = Modifier.fillMaxSize()){
            IplList(iplViewModel)
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun IplList(iplViewModel: IplViewModel){
    val mSortAscDscNameIs  = remember {
        mutableStateOf(AppConstants.SORT_ASC)
    }
    val mSortAscDscWonIs  = remember {
        mutableStateOf(AppConstants.SORT_ASC)
    }
    val mSortAscDscLostIs  = remember {
        mutableStateOf(AppConstants.SORT_ASC)
    }

    val isBoarder = iplViewModel.getImageBoarder()

    var imgModifier = Modifier
        .size(80.dp, 90.dp)

    if(isBoarder){
        imgModifier = imgModifier.border(
            width = 1.dp, color = Color.Black
        )
    }


    //Check if IPLList is Empty
    if(iplViewModel.isEmpty()){
        printLog("List is Empty")
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            //Put your Compose View here to make it center in this Box
            Text(text = "No WorldCup T20 Team available to show",
                color = Color.Magenta, fontFamily = FontFamily.Default, fontSize = 20.sp)
        }
    }else{
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(mHeaderHeight)
                .background(HeaderColor), verticalAlignment = Alignment.CenterVertically) {
                Row(modifier = Modifier
                    .weight(0.35F)
                    .clickable(enabled = true) {
                        if (mSortAscDscNameIs.value == AppConstants.SORT_ASC) {
                            mSortAscDscNameIs.value = AppConstants.SORT_DESC
                            iplViewModel.performSorting(SortType.NAME, false)
                        } else {
                            mSortAscDscNameIs.value = AppConstants.SORT_ASC
                            iplViewModel.performSorting(SortType.NAME, true)
                        }
                        iplViewModel.setSortType(SortType.NAME)
                    }, verticalAlignment = Alignment.CenterVertically) {
                    Row(modifier = Modifier.fillMaxSize(),verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Team", style = MaterialTheme.typography.h5, color = TextColor, modifier = Modifier.padding(start = 10.dp), fontWeight =
                        if(iplViewModel.getSortType() == SortType.NAME){
                            FontWeight.Bold
                        }else {
                            FontWeight.Light
                        })
                        Spacer(modifier = Modifier.width(mSpaceTxtImg))
                        Image(painter = if(mSortAscDscNameIs.value == AppConstants.SORT_ASC) {
                            painterResource("sort-by-alpha.xml")
                        }else{
                            painterResource("sort-by-alpha.xml")
                        }, contentDescription = mSortAscDscNameIs.value )
                    }
                }
                Row(modifier = Modifier.weight(0.20F)) {
                    //modifier = Modifier.background(color = Color.Magenta)
                    Text(text = "Played", style = MaterialTheme.typography.h5,  color = TextColor,textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light)
                }
                Row(modifier = Modifier
                    .weight(0.25F)
                    .clickable(enabled = true) {
                        if (mSortAscDscWonIs.value == AppConstants.SORT_ASC) {
                            mSortAscDscWonIs.value = AppConstants.SORT_DESC
                            iplViewModel.performSorting(SortType.WON, false)
                        } else {
                            mSortAscDscWonIs.value = AppConstants.SORT_ASC
                            iplViewModel.performSorting(SortType.WON, true)
                        }
                        iplViewModel.setSortType(SortType.WON)
                    },verticalAlignment = Alignment.CenterVertically) {
                    Row(modifier = Modifier.fillMaxSize(),verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Won", style = MaterialTheme.typography.h5, color = TextColor,
                            fontWeight = if(iplViewModel.getSortType() == SortType.WON){
                                FontWeight.Bold
                            }else {
                                FontWeight.Light
                            })
                        Spacer(modifier = androidx.compose.ui.Modifier.width(mSpaceTxtImg))
                        Image(painter = if(mSortAscDscWonIs.value == AppConstants.SORT_ASC) {
                            painterResource("north.xml")
                        }else{
                            painterResource("south.xml")
                        }, contentDescription = mSortAscDscWonIs.value )
                    }
                }
                Row(modifier = Modifier
                    .weight(0.25F)
                    .clickable(enabled = true) {
                        if (mSortAscDscLostIs.value == AppConstants.SORT_ASC) {
                            mSortAscDscLostIs.value = AppConstants.SORT_DESC
                            iplViewModel.performSorting(SortType.LOST, false)
                        } else {
                            mSortAscDscLostIs.value = AppConstants.SORT_ASC
                            iplViewModel.performSorting(SortType.LOST, true)
                        }
                        iplViewModel.setSortType(SortType.LOST)

                    },verticalAlignment = Alignment.CenterVertically) {
                    //.background(color = Color.Magenta)
                    Row(modifier = Modifier.fillMaxSize(),verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Lost", style = MaterialTheme.typography.h5, color = TextColor
                            , fontWeight = if(iplViewModel.getSortType() == SortType.LOST){
                                FontWeight.Bold
                            }else {
                                FontWeight.Light
                            })
                        Spacer(modifier = androidx.compose.ui.Modifier.width(mSpaceTxtImg))
                        Image(painter = if(mSortAscDscLostIs.value == AppConstants.SORT_ASC) {
                            painterResource("north.xml")
                        }else{
                            painterResource("south.xml")
                        }, contentDescription = mSortAscDscLostIs.value )
                    }

                }
            }

            LazyColumn(contentPadding = PaddingValues(start = 5.dp, end = 5.dp, bottom = 5.dp)){
                items(iplViewModel.getList()) {  item: IplTeam ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        //CardDefaults.cardElevation(5.dp)
                        .background(color = Color.White), elevation = 5.dp) {
                        //modifier = Modifier.background(color = Color.LightGray) Check Row Color
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                            //Column 1
                            Row(modifier = Modifier.weight(0.50F),verticalAlignment = Alignment.CenterVertically) {
                                ImageItem(modifier = imgModifier.weight(0.40F) ,data = item.url,
                                    blurRadius =  0)
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(text = item.teamName,modifier = Modifier.weight(0.60F), color = Color.Black, fontFamily = FontFamily.Monospace, fontSize = 16.sp)
                            }
                            //Column 2
                            Text(text = item.played.toString(),modifier = Modifier.weight(0.10F), color = Color.Black,
                                fontFamily = FontFamily.Serif, fontSize = 18.sp)
                            //Column 3
                            Text(text = item.win.toString(), modifier = Modifier.weight(0.25F),color = DarkGreen,
                                fontFamily = FontFamily.Serif, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            //Column 4
                            Text(text = item.loss.toString(), modifier = Modifier.weight(0.25F),color = Color.Red,
                                fontFamily = FontFamily.Serif, fontSize = 18.sp,fontWeight = FontWeight.Bold)

                        }
                    }

                }
                item {
                    ShowLastSpecialItem()
                }
            }
        }
    }
}

@Composable
fun ShowLastSpecialItem(){
    Column(modifier = Modifier.fillMaxWidth().padding(10.dp).
    height(200.dp).
    background(color = Color.Gray, shape = RoundedCornerShape(20.dp))) {
    }
    Column(modifier = Modifier.fillMaxWidth().padding(10.dp).
    height(10.dp).
    background(color = Color.Gray, shape = RoundedCornerShape(20.dp))) {
    }
}