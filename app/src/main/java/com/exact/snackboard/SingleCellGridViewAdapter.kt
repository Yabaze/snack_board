package com.exact.snackboard

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.grid_adapter_view.view.*

class SingleCellGridViewAdapter(
    private val context: Context,
    private val abc: MutableList<CellData>
) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val grid = inflator.inflate(R.layout.grid_adapter_view, null)
        grid.textView.text = "" + abc[p0].value;

        if(abc[p0].isActive!!){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                grid.textView.setBackgroundColor(context.getColor(R.color.colorAccent))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                grid.textView.setBackgroundColor(context.getColor(R.color.white))
            }

        }
        //switch()

        return grid
    }

    override fun getItem(p0: Int): Any {
        return abc[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return abc.count()
    }
}