package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class IplTeam( @SerialName("name") var teamName : String){

    var teamFlag :String = ""

    @SerialName("win")
    var win = 0

    @SerialName("loss")
    var loss = 0

    @SerialName("url")
    var url : String =""

    @SerialName("played")
    var played : Int = 0
}
