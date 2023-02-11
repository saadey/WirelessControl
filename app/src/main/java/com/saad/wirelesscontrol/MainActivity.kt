package com.saad.wirelesscontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var command: EditText
    lateinit var submit: Button
    lateinit var up: Button
    lateinit var down: Button
    lateinit var left: Button
    lateinit var right: Button
    lateinit var alt: Button
    lateinit var clt: Button
    lateinit var shift: Button
    lateinit var enter: Button
    lateinit var capslock: Button
    lateinit var numlock: Button
    lateinit var scllock: Button
    lateinit var delete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initViews()
        val database = FirebaseDatabase.getInstance().reference

        submit.setOnClickListener(View.OnClickListener {
            val cmd = command.text.toString()
            database.child("KeyEvent")
                .child("Key0").setValue(cmd)
                .addOnSuccessListener {
                    Toast.makeText(baseContext, "$cmd sent...", Toast.LENGTH_SHORT).show()
                }
        })

    }

    private fun initViews() {
        command = findViewById(R.id.cmdInput)
        submit = findViewById(R.id.submit)
        up = findViewById(R.id.arrow_up)
        down = findViewById(R.id.arrow_down)
        left = findViewById(R.id.arrow_left)
        right = findViewById(R.id.arrow_right)
        alt = findViewById(R.id.alt)
        clt = findViewById(R.id.ctrl)
        shift = findViewById(R.id.shift)
        enter = findViewById(R.id.enter)
        capslock = findViewById(R.id.capslock)
        numlock = findViewById(R.id.numLock)
        scllock = findViewById(R.id.scroll)
        delete = findViewById(R.id.delete)


    }
}