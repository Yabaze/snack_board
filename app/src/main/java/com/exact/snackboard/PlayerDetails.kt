package com.exact.snackboard

class PlayerDetails {

    var value:Int? = 0
    var started : Boolean? = false
    var pos : Int? = 90

    constructor()

    constructor(value: Int?, started: Boolean) {
        this.value = value
        this.started = started
    }

}