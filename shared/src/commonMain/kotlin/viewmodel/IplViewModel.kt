package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import data.IplReadData
import data.IplTeam
import data.SortType
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import printLog


/**
 *
 * @author Himanshu.Mistri
 * Model class to perform the filter operation
 */
class IplViewModel  {
    private val  mTag="IplViewModel"
    private var mMutableIplList = mutableStateListOf<IplTeam>()
    private var mImgBoarderState = mutableStateOf(false)
    private var mActiveSortType = mutableStateOf(SortType.NAME)
    private var mIsAscSort = mutableStateOf(true)

    init {
        //Default Sort Type would be Team Name sorting
        mActiveSortType.value = SortType.NAME
        printLog("Init Default Sort type when app lunch ${mActiveSortType.value }")
        //LogUtils.i(mTag,"Init Default Sort type when app lunch ${mActiveSortType.value }")
    }

    fun setSortType(sortType: SortType){
        mActiveSortType.value = sortType
    }

    private fun setIsAscSort(ascSort: Boolean){
        mIsAscSort.value = ascSort
    }

    fun getAscSort(): Boolean{
        return mIsAscSort.value
    }

    fun getSortType(): SortType{
        return mActiveSortType.value
    }

    fun addTeam(iplTeam : IplTeam){
        mMutableIplList.add(iplTeam)
    }

    fun performSorting(sortType: SortType, isAsc: Boolean){
        setIsAscSort(isAsc)

        when(sortType){

            SortType.NAME->{
                val list =mMutableIplList.sortedWith(Comparator { t1, t2 ->
                    if(isAsc){
                        t1.teamName.compareTo(t2.teamName)
                    }else{
                        t2.teamName.compareTo(t1.teamName)
                    }
                })
                addAll(list.toMutableList())

            }
            SortType.WON->{
                val list =mMutableIplList.sortedWith(Comparator { t1, t2 ->
                    if(isAsc){
                        t1.win.compareTo(t2.win)
                    }else{
                        t2.win.compareTo(t1.win)
                    }
                })
                addAll(list.toMutableList())
            }
            SortType.LOST->{
                val list =mMutableIplList.sortedWith(Comparator { t1, t2 ->
                    if(isAsc){
                        t1.loss.compareTo(t2.loss)
                    }else{
                        t2.loss.compareTo(t1.loss)
                    }
                })
                addAll(list.toMutableList())

            }
            else -> {

            }
        }

    }

    fun addAll(listItem: ArrayList<IplTeam>){
        mMutableIplList.clear()
        mMutableIplList.addAll(listItem)
    }

    private fun addAll(listItem: MutableList<IplTeam>){
        mMutableIplList.clear()
        mMutableIplList.addAll(listItem)
    }

    fun isEmpty() : Boolean{
       return mMutableIplList.isEmpty()
    }


    fun getList() : List<IplTeam>{
        return mMutableIplList.toList()
    }

    fun getIplList():MutableListIterator<IplTeam>{
        return  mMutableIplList.listIterator()
    }

    fun getImageBoarder(): Boolean{
        return mImgBoarderState.value
    }

    fun setImageBoarder(isBoarder: Boolean){
        mImgBoarderState.value = isBoarder
    }


    @OptIn(ExperimentalResourceApi::class)
    suspend fun readJson(): IplReadData?{
        try {
            val configString = resource("teamlist.json").readBytes().decodeToString()
            printLog(configString)
            val json = Json {ignoreUnknownKeys = true }
            val teamList =json.decodeFromString(IplReadData.serializer(),configString)
            printLog("${teamList.mArrayList!!.size}")
            return teamList
        } catch (e: Exception) {
            e.printStackTrace()
            printLog("Error:: ${e.message}")
        }
        return null
    }

}