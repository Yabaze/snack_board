package com.exact.snackboard

interface GameAction {
    fun makeOutPlayer(playerDetails: PlayerDetails, playerId: Int?)//player id is who send player out
}