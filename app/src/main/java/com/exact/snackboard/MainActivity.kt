package com.exact.snackboard

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener, GameAction {

    private var diceImages = intArrayOf(
        R.drawable.d1,
        R.drawable.d2,
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d5,
        R.drawable.d6
    )

    private var rollAnimations = 50
    private var delayTime = 15
    private var roll = 5

    private var handler: Handler? = Handler {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById<ImageView>(imageView!!.id).setImageDrawable(getDrawable(diceImages[roll]))
        }
        true
    }

    lateinit var imageView: View
    private var paused = false

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
                one.setOnClickListener(this@MainActivity)
                firstIV.visibility = View.VISIBLE
            }
            1 -> {
                two.setOnClickListener(this@MainActivity)
                secondIV.visibility = View.VISIBLE

            }
            2 -> {
                three.setOnClickListener(this@MainActivity)
                thirdIV.visibility = View.VISIBLE

            }
            3 -> {
                four.setOnClickListener(this@MainActivity)
                forthIV.visibility = View.VISIBLE

            }
        }

    }

    private var a: MutableList<CellData> = mutableListOf()
    private lateinit var adapter: SingleCellGridViewAdapter

    private var player: MutableList<PlayerDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetGameData()

        Glide.with(this@MainActivity).load(R.drawable.right)
            .transform(RotateTransformation(this@MainActivity, 180f)).into(firstIV)
        Glide.with(this@MainActivity).load(R.drawable.right).into(secondIV)
        Glide.with(this@MainActivity).load(R.drawable.right)
            .transform(RotateTransformation(this@MainActivity, 180f)).into(thirdIV)
        Glide.with(this@MainActivity).load(R.drawable.right).into(forthIV)

        adapter = SingleCellGridViewAdapter(this@MainActivity, a, player, this@MainActivity)
        cellsGridView.adapter = adapter

    }

    private fun afterDiceRoll(view: View) {

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

        val randomNumber = roll + 1//(1..6).random()

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

                    /*findViewById<ImageView>(view.id).text =
                        " ${findViewById<ImageView>(view.id).text}  *"*/

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
                        two.setOnClickListener(this@MainActivity)
                        secondIV.visibility = View.VISIBLE
                    }
                    R.id.two -> {
                        disableAll()
                        three.setOnClickListener(this@MainActivity)
                        thirdIV.visibility = View.VISIBLE
                    }
                    R.id.three -> {
                        disableAll()
                        four.setOnClickListener(this@MainActivity)
                        forthIV.visibility = View.VISIBLE

                    }
                    R.id.four -> {
                        disableAll()
                        one.setOnClickListener(this@MainActivity)
                        firstIV.visibility = View.VISIBLE
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

                        disableAll()
                        secondIV.visibility = View.VISIBLE
                        two.setOnClickListener(this@MainActivity)

                    } else {
                        hideAnimationArrow()
                        firstIV.visibility = View.VISIBLE
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
                        disableAll()
                        thirdIV.visibility = View.VISIBLE
                        three.setOnClickListener(this@MainActivity)
                    } else {
                        hideAnimationArrow()
                        secondIV.visibility = View.VISIBLE
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
                        disableAll()
                        forthIV.visibility = View.VISIBLE
                        four.setOnClickListener(this@MainActivity)
                    } else {
                        hideAnimationArrow()
                        thirdIV.visibility = View.VISIBLE
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
                        disableAll()
                        firstIV.visibility = View.VISIBLE
                        one.setOnClickListener(this@MainActivity)
                    } else {
                        hideAnimationArrow()
                        forthIV.visibility = View.VISIBLE
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

    private fun doRoll() {
        roll = (0..5).random()
        synchronized(layoutInflater) {
            handler!!.sendEmptyMessage(0)
        }
        try {
            Thread.sleep(delayTime.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun resetGameData() {
        player = mutableListOf()
        player.add(PlayerDetails(0, 0, false, R.color.red, "Mirakle", R.drawable.player_icon_red))
        player.add(
            PlayerDetails(
                1,
                0,
                false,
                R.color.green,
                "Yabaze",
                R.drawable.player_icon_green
            )
        )
        player.add(
            PlayerDetails(
                2,
                0,
                false,
                R.color.blue,
                "Jegathesan",
                R.drawable.player_icon_blue
            )
        )
        player.add(
            PlayerDetails(
                3,
                0,
                false,
                R.color.violot,
                "cool",
                R.drawable.player_icon_violot
            )
        )

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

        disableAll()
        firstIV.visibility = View.VISIBLE
        one.setOnClickListener(this@MainActivity)
    }

    override fun onClick(view: View) {
        imageView = view
        if (paused) return
        Thread(Runnable {
            for (i in 0 until rollAnimations) {
                doRoll()
            }

            runOnUiThread {
                afterDiceRoll(view)
            }

        }).start()

        val mp = MediaPlayer.create(this, R.raw.roll)
        try {
            mp.prepare()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mp.start()
    }

    private fun disableAll() {
        one.setOnClickListener(null)
        two.setOnClickListener(null)
        three.setOnClickListener(null)
        four.setOnClickListener(null)
        hideAnimationArrow()
    }

    private fun hideAnimationArrow() {
        firstIV.visibility = View.INVISIBLE
        secondIV.visibility = View.INVISIBLE
        thirdIV.visibility = View.INVISIBLE
        forthIV.visibility = View.INVISIBLE
    }

    fun restartGame(view: View) {
        resetGameData()


        adapter = SingleCellGridViewAdapter(this@MainActivity, a, player, this@MainActivity)
        cellsGridView.adapter = adapter
    }

}

