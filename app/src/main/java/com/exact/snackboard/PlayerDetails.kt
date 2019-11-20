package com.exact.snackboard

class PlayerDetails {

    var id:Int? = 0
    var value: Int? = 0
    var started: Boolean? = false
    var pos: Int? = 90
    var colorCode: Int? = R.color.colorAccent
    var playerName: String? = null

    constructor()

    constructor(id:Int?,value: Int?, started: Boolean?, colorCode: Int?, playerName: String?) {
        this.id = id
        this.value = value
        this.started = started
        this.colorCode = colorCode
        this.playerName = playerName

    }

}