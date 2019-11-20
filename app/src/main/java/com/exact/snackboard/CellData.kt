package com.exact.snackboard

class CellData {

    var playerId: Int? = null
    var isActive: Boolean? = null
    var value: Int? = null

    constructor()

    constructor(isActive: Boolean?, value: Int?) {//playerId: Int?,
        //this.playerId = playerId
        this.isActive = isActive
        this.value = value
    }


}