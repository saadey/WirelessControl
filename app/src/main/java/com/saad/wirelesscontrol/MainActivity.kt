package com.saad.wirelesscontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var command: EditText
    lateinit var write: Button
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
    lateinit var back: Button
    lateinit var disconnect: Button
    lateinit var delete: Button
    var toggle = true
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initViews()
        database = FirebaseDatabase.getInstance().reference.child("KeyEvent")
            .child("Key0")

        submit.setOnClickListener {
            val cmd = command.text.toString()
            database.setValue(cmd)
                .addOnSuccessListener {
                    Toast.makeText(baseContext, "$cmd sent...", Toast.LENGTH_SHORT).show()
                }
        }
        write.setOnClickListener {
            val cmd = command.text.toString()
            database.setValue(cmd)
                .addOnSuccessListener {
                    Toast.makeText(baseContext, "$cmd sent...", Toast.LENGTH_SHORT).show()
                }
        }
        disconnect.setOnClickListener(){submitKey("exit")}
        up.setOnClickListener(){submitKey("up")}
        down.setOnClickListener(){submitKey("down")}
        left.setOnClickListener(){submitKey("left")}
        right.setOnClickListener(){submitKey("right")}
        capslock.setOnClickListener(){submitKey("capslock")}
        back.setOnClickListener(){submitKey("back")}
        enter.setOnClickListener(){submitKey("enter")}
        delete.setOnClickListener(){submitKey("delete")}

    }

    private fun submitKey(cmd: String) {
        database.setValue(toggleCommand(cmd))
            .addOnSuccessListener {
                Toast.makeText(baseContext, "${cmd.uppercase()} sent...", Toast.LENGTH_SHORT).show()
            }
    }

    private fun toggleCommand(cmd: String): String {
        toggle = !toggle
        return if(toggle)
            cmd.uppercase()
        else
            cmd.lowercase()
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
        back = findViewById(R.id.backspace)
        disconnect= findViewById(R.id.disconnect)
        delete = findViewById(R.id.delete)
        write = findViewById(R.id.write)

    }
}