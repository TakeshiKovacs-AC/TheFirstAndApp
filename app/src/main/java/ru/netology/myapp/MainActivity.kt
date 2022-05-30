package ru.netology.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val likesButton = findViewById<Button>(R.id.likes)
        val likesFigures = findViewById<TextView>(R.id.likesCount)
        var likesQuantity = 0
        likesButton.setOnClickListener {
            likesQuantity++
            likesFigures.text = likesQuantity.toString()
        }
    }
}