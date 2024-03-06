package com.habibfr.myviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.habibfr.myviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
//    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        displayResult()

        binding.btnCalculate.setOnClickListener {
            val width = binding.edtWidth.text.toString()
            val heigth = binding.edtHeight.text.toString()
            val length = binding.edtLength.text.toString()

            when {
                width.isEmpty() -> {
                    binding.edtWidth.error = "Masih Kosong"
                }

                heigth.isEmpty() -> {
                    binding.edtHeight.error = "Masih Kosong"
                }

                length.isEmpty() -> {
                    binding.edtLength.error = "Masih Kosong"
                }

                else -> {
                    viewModel.calculate(width, heigth, length)
                    displayResult()
                }
            }
        }
    }

    private fun displayResult() {
        binding.tvResult.text = viewModel.result.toString()
    }
}

class MainViewModel : ViewModel() {
    var result = 0

    fun calculate(width: String, heigth: String, length: String) {
        result = width.toInt() * heigth.toInt() * length.toInt()
    }
}