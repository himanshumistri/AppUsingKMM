package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class IplReadData {

    @SerialName("list")
    var mArrayList : ArrayList<IplTeam> ?= null
}