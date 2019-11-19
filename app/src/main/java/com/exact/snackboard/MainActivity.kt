package com.exact.snackboard

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var a: MutableList<CellData> = mutableListOf()
    private var adapter: SingleCellGridViewAdapter? = null

//    var pos : Int? = 90

    var player: MutableList<PlayerDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player.add(PlayerDetails(0, false))
        player.add(PlayerDetails(0, false))
        player.add(PlayerDetails(0, false))
        player.add(PlayerDetails(0, false))

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

        adapter = SingleCellGridViewAdapter(this@MainActivity, a)
        cellsGridView.adapter = adapter

    }



    override fun onClick(view: View) {

        var started = false
        var value = 0
        var pos: Int? = 90

        when (view.id) {
            R.id.one -> {

                started = player[0].started!!
                value = player[0].value!!
                pos = player[0].pos!!

            }
            R.id.two -> {
                started = player[1].started!!
                value = player[1].value!!
                pos = player[1].pos!!


            }
            R.id.three -> {
                started = player[2].started!!
                value = player[2].value!!
                pos = player[2].pos!!


            }
            R.id.four -> {
                started = player[3].started!!
                value = player[3].value!!
                pos = player[3].pos!!

            }
        }

        val randomNumer = (1..6).random()

        dice.text = "" + randomNumer

        if (started) {

            if (pos != null) {
                a[pos!!].isActive = false
            }

            var temp = a[pos!!].value!! + randomNumer

            if (temp <= 100) {
                value = temp
                if (value == 100) {
                    dice.text = "You Won"
                    view.setOnClickListener(null)
                }
            }

            for (i in a) {

                if (i.value == value) {

                    pos = a.indexOf(i)

                }
            }

            if (pos != null) {
                a[pos!!].isActive = true
            }

        }

        if (randomNumer == 6 && !started) {
            started = true
            value = 1
            a[90].isActive = true
        }

        when (view.id) {
            R.id.one -> {
                player[0].started = started
                player[0].value = value
                player[0].pos = pos
                if (player[0].value != 100) {

                    disableAll()
                    findViewById<Button>(R.id.two).setOnClickListener(this@MainActivity)
                }
            }
            R.id.two -> {
                player[1].started = started
                player[1].value = value
                player[1].pos = pos
                if (player[1].value != 100) {

                    disableAll()
                    findViewById<Button>(R.id.three).setOnClickListener(this@MainActivity)
                }
            }
            R.id.three -> {
                player[2].started = started
                player[2].value = value
                player[2].pos = pos
                if (player[2].value != 100) {
                    disableAll()
                    findViewById<Button>(R.id.four).setOnClickListener(this@MainActivity)
                }
            }
            R.id.four -> {
                player[3].started = started
                player[3].value = value
                player[3].pos = pos
                if (player[3].value != 100) {

                    disableAll()
                    findViewById<Button>(R.id.one).setOnClickListener(this@MainActivity)
                }
            }
        }

        cellsGridView.adapter = SingleCellGridViewAdapter(this@MainActivity, a)

    }

    private fun disableAll() {
        findViewById<Button>(R.id.one).setOnClickListener(null)
        findViewById<Button>(R.id.two).setOnClickListener(null)
        findViewById<Button>(R.id.three).setOnClickListener(null)
        findViewById<Button>(R.id.four).setOnClickListener(null)
    }

}
