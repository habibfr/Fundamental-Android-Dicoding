package com.habibfr.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.btn_start)
        val txtStatus: TextView = findViewById(R.id.tv_status)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        btnStart.setOnClickListener {
            executor.execute {
                try {
                    for (i in 1..10) {
                        Thread.sleep(500)
                        val percentage = i * 10
                        handler.post {
                            if (percentage == 100) {
                                txtStatus.setText(R.string.task_completed)
                            } else {
                                txtStatus.text =
                                    String.format(getString(R.string.compressing), percentage)
                            }
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}