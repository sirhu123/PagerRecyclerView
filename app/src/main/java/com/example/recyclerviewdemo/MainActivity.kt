package com.example.recyclerviewdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.recyclerview.GridItemDecoration
import com.example.recyclerviewdemo.recyclerview.PagerSnapHelper
import com.example.recyclerviewdemo.recyclerview.RvAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rv)
        val adapter = RvAdapter(this, 3)
        PagerSnapHelper(6).attachToRecyclerView(rv)
        rv.run {
            layoutManager = GridLayoutManager(this@MainActivity, 2, RecyclerView.HORIZONTAL, false)
            this.adapter = adapter
            addItemDecoration(GridItemDecoration(this@MainActivity))
        }
    }
}
