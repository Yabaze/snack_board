package com.exact.snackboard

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, GameAction {

    override fun makeOutPlayer(
        playerDetails: PlayerDetails,
        playerId: Int?
    ) {
        Log.e("Snack Board", "" + playerDetails.playerName + " Out")
        Toast.makeText(
            this@MainActivity,
            "" + playerDetails.playerName + " Out",
            Toast.LENGTH_LONG
        ).show()

        val l = player.indexOf(playerDetails)

        player[l].started = false
        player[l].value = 0
        player[l].pos = 90

        disableAll()

        when (playerId) {
            0 -> {
                findViewById<MaterialButton>(R.id.one).setOnClickListener(this@MainActivity)
                radioGroup.check(R.id.r1)
            }
            1 -> {
                findViewById<MaterialButton>(R.id.two).setOnClickListener(this@MainActivity)
                radioGroup.check(R.id.r2)
            }
            2 -> {
                findViewById<MaterialButton>(R.id.three).setOnClickListener(this@MainActivity)
                radioGroup.check(R.id.r3)
            }
            3 -> {
                findViewById<MaterialButton>(R.id.four).setOnClickListener(this@MainActivity)
                radioGroup.check(R.id.r4)
            }
        }

    }

    private var a: MutableList<CellData> = mutableListOf()
    private var adapter: SingleCellGridViewAdapter? = null

    var player: MutableList<PlayerDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup.isClickable = false

        resetGameData()

        radioGroup.check(R.id.r1)
        adapter = SingleCellGridViewAdapter(this@MainActivity, a, player, this@MainActivity)
        cellsGridView.adapter = adapter

    }

    private fun resetGameData() {
        player = mutableListOf()
        player.add(PlayerDetails(0, 0, false, R.color.red, "Mirakle"))
        player.add(PlayerDetails(1, 0, false, R.color.green, "Yabaze"))
        player.add(PlayerDetails(2, 0, false, R.color.blue, "Jegathesan"))
        player.add(PlayerDetails(3, 0, false, R.color.violot, "cool"))

        a = mutableListOf()
        for (i in 10 downTo 1) {
            when (i % 2) {
                0 -> {
                    for (j in 10 downTo 1) {
                        a.add(CellData(false, (i - 1) * 10 + j))
                    }
                }
                1 -> {
                    for (j in 9 downTo 0) {
                        a.add(CellData(false, (i * 10 - j)))
                    }
                }
            }
        }
    }

    override fun onClick(view: View) {

        var started = false
        var value = 0
        var pos: Int? = 90
        var playerName: String? = null
        var playerId: Int? = null

        when (view.id) {
            R.id.one -> {

                started = player[0].started!!
                value = player[0].value!!
                pos = player[0].pos!!
                playerName = player[0].playerName
                playerId = player[0].id

            }
            R.id.two -> {
                started = player[1].started!!
                value = player[1].value!!
                pos = player[1].pos!!
                playerName = player[1].playerName
                playerId = player[1].id
            }
            R.id.three -> {
                started = player[2].started!!
                value = player[2].value!!
                pos = player[2].pos!!
                playerName = player[2].playerName
                playerId = player[2].id
            }
            R.id.four -> {
                started = player[3].started!!
                value = player[3].value!!
                pos = player[3].pos!!
                playerName = player[3].playerName
                playerId = player[3].id

            }
        }

        val randomNumber = (1..6).random()

        dice.text = "" + randomNumber

        if (started) {

            if (pos != null) {
                a[pos].isActive = false
                a[pos].playerId = null
            }

            val temp = a[pos!!].value!! + randomNumber

            if (temp <= 100) {
                value = temp
                if (value == 100) {
                    dice.text = "$playerName Won"
                    view.setOnClickListener(null)

                    findViewById<MaterialButton>(view.id).text =
                        " ${findViewById<MaterialButton>(view.id).text}  *"
                    //a[pos].playerId = playerId
                }
            }

            for (i in a) {

                if (i.value == value) {

                    pos = a.indexOf(i)

                }
            }

            if (pos != null) {
                a[pos].isActive = true
                a[pos].playerId = playerId
            }


            if (temp > 100) {
                Toast.makeText(this@MainActivity, "Move Not Available!", Toast.LENGTH_LONG).show()
                when (view.id) {
                    R.id.one -> {
                        disableAll()
                        findViewById<MaterialButton>(R.id.two).setOnClickListener(this@MainActivity)
                        radioGroup.check(R.id.r2)
                    }
                    R.id.two -> {
                        disableAll()
                        findViewById<MaterialButton>(R.id.three).setOnClickListener(this@MainActivity)
                        radioGroup.check(R.id.r3)
                    }
                    R.id.three -> {
                        disableAll()
                        findViewById<MaterialButton>(R.id.four).setOnClickListener(this@MainActivity)
                        radioGroup.check(R.id.r4)

                    }
                    R.id.four -> {
                        disableAll()
                        findViewById<MaterialButton>(R.id.one).setOnClickListener(this@MainActivity)
                        radioGroup.check(R.id.r1)
                    }
                }
                return
            }

        }

        if (randomNumber == 6 && !started) {
            started = true
            value = 1
            a[90].isActive = true
            a[90].playerId = playerId
        }

        when (view.id) {
            R.id.one -> {
                player[0].started = started
                player[0].value = value
                player[0].pos = pos
                player[0].id = playerId

                if (player[0].value != 100) {
                    if (randomNumber != 6) {

                        radioGroup.check(R.id.r2)

                        disableAll()

                        findViewById<MaterialButton>(R.id.two).setOnClickListener(this@MainActivity)

                    } else {
                        radioGroup.check(R.id.r1)
                    }
                }
            }
            R.id.two -> {
                player[1].started = started
                player[1].value = value
                player[1].pos = pos
                player[1].id = playerId

                if (player[1].value != 100) {
                    if (randomNumber != 6) {
                        radioGroup.check(R.id.r3)

                        disableAll()
                        findViewById<MaterialButton>(R.id.three).setOnClickListener(this@MainActivity)
                    } else {
                        radioGroup.check(R.id.r2)
                    }
                }
            }
            R.id.three -> {
                player[2].id = playerId
                player[2].started = started
                player[2].value = value
                player[2].pos = pos
                if (player[2].value != 100) {
                    if (randomNumber != 6) {
                        radioGroup.check(R.id.r4)
                        disableAll()
                        findViewById<MaterialButton>(R.id.four).setOnClickListener(this@MainActivity)
                    } else {
                        radioGroup.check(R.id.r3)
                    }
                }
            }
            R.id.four -> {
                player[3].started = started
                player[3].value = value
                player[3].pos = pos
                player[3].id = playerId

                if (player[3].value != 100) {
                    if (randomNumber != 6) {

                        radioGroup.check(R.id.r1)

                        disableAll()
                        findViewById<MaterialButton>(R.id.one).setOnClickListener(this@MainActivity)
                    } else {
                        radioGroup.check(R.id.r4)
                    }
                }
            }
        }

        cellsGridView.adapter =
            SingleCellGridViewAdapter(this@MainActivity, a, player, this@MainActivity)

        //ladder And Snake Movement

        when (value) {
            //Ladder
            9 -> {

                player[playerId!!].value = 31

            }
            16 -> {

                player[playerId!!].value = 45

            }
            18 -> {

                player[playerId!!].value = 69

            }
            48 -> {

                player[playerId!!].value = 66

            }
            50 -> {
                player[playerId!!].value = 93

            }
            63 -> {
                player[playerId!!].value = 81

            }
            //Snake
            99 -> {
                player[playerId!!].value = 39

            }
            86 -> {
                player[playerId!!].value = 51

            }
            74 -> {

                player[playerId!!].value = 22

            }
            32 -> {

                player[playerId!!].value = 6

            }

        }

        if (player[playerId!!].value != value) {

            if (pos != null) {
                a[pos].isActive = false
                a[pos].playerId = null
            }

            for (i in a) {

                if (i.value == player[playerId].value) {

                    player[playerId].pos = a.indexOf(i)

                }
            }

            if (player[playerId].pos != null) {
                a[player[playerId].pos!!].isActive = true
                a[player[playerId].pos!!].playerId = playerId
            }


            Handler().postDelayed({
                cellsGridView.adapter =
                    SingleCellGridViewAdapter(this@MainActivity, a, player, this@MainActivity)

            }, 2000)
        }


    }


    private fun disableAll() {
        findViewById<MaterialButton>(R.id.one).setOnClickListener(null)
        findViewById<MaterialButton>(R.id.two).setOnClickListener(null)
        findViewById<MaterialButton>(R.id.three).setOnClickListener(null)
        findViewById<MaterialButton>(R.id.four).setOnClickListener(null)
    }

    fun restartGame(view: View) {
        resetGameData()

        radioGroup.check(R.id.r1)
        disableAll()
        findViewById<MaterialButton>(R.id.one).setOnClickListener(this@MainActivity)
        adapter = SingleCellGridViewAdapter(this@MainActivity, a, player, this@MainActivity)
        cellsGridView.adapter = adapter
    }

}
