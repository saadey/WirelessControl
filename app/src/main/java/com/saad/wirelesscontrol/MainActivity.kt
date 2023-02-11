package com.saad.wirelesscontrol

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var command: EditText
    lateinit var write: Button
    lateinit var submit: Button
    lateinit var up: TextView
    lateinit var down: TextView
    lateinit var left: TextView
    lateinit var right: TextView
    lateinit var alt: TextView
    lateinit var clt: TextView
    lateinit var shift: TextView
    lateinit var enter: TextView
    lateinit var capslock: TextView
    lateinit var back: TextView
    lateinit var disconnect: TextView
    lateinit var f5: TextView
    lateinit var esc: TextView
    lateinit var fn: TextView
    lateinit var tab: TextView
    lateinit var windows: TextView
    lateinit var space: TextView
    lateinit var volUp: TextView
    lateinit var volDown: TextView
    lateinit var menu: TextView
    lateinit var next: TextView
    lateinit var prev: TextView
    lateinit var mute: TextView
    lateinit var close: TextView
    lateinit var deviceName: TextView
    lateinit var database: DatabaseReference
    lateinit var writeLineDb: DatabaseReference
    lateinit var onlineDb: DatabaseReference
    lateinit var shiftDb: DatabaseReference
    lateinit var altDb: DatabaseReference
    lateinit var ctrlDb: DatabaseReference
    var laptopOnline = false
    var toggle = false
    var cltToggle = false
    var altToggle = false
    var shiftToggle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initViews()
        database = FirebaseDatabase.getInstance().reference.child("KeyEvent")
            .child("key0")
        writeLineDb = FirebaseDatabase.getInstance().reference.child("KeyEvent")
            .child("text")
        altDb = FirebaseDatabase.getInstance().reference.child("KeyEvent")
            .child("alt")
        ctrlDb = FirebaseDatabase.getInstance().reference.child("KeyEvent")
            .child("ctrl")
        shiftDb = FirebaseDatabase.getInstance().reference.child("KeyEvent")
            .child("shift")
        onlineDb = FirebaseDatabase.getInstance().reference.child("available")

        onlineDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                laptopOnline = snapshot.getValue(Boolean::class.java) as Boolean
                if (!laptopOnline) {
                    Toast.makeText(baseContext, "Disconnected", Toast.LENGTH_LONG).show()
                    deviceName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_cell_tower_24_offline,
                        0,
                        0,
                        0
                    )
                } else {
                    Toast.makeText(baseContext, "Connected", Toast.LENGTH_LONG).show()
                    deviceName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_cell_tower_24,
                        0,
                        0,
                        0
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        submit.setOnClickListener {
//            var toggles = getToggles()
//            val cmd = toggles + command.text.toString()
            val cmd = command.text.toString()
            database.setValue(cmd)
                .addOnSuccessListener {
                    Toast.makeText(baseContext, "$cmd sent...", Toast.LENGTH_SHORT).show()
                }
        }
        write.setOnClickListener {
            val text = command.text.toString()
            writeLineDb.setValue(text)
                .addOnSuccessListener {
                    Toast.makeText(baseContext, "Text sent...", Toast.LENGTH_SHORT).show()
                }
        }

        disconnect.setOnClickListener { submitKey("exit") }
        up.setOnClickListener { submitKey("up") }
        down.setOnClickListener { submitKey("down") }
        left.setOnClickListener { submitKey("left") }
        right.setOnClickListener { submitKey("right") }
        capslock.setOnClickListener { submitKey("capslock") }
        back.setOnClickListener { submitKey("backspace") }
        enter.setOnClickListener { submitKey("enter") }
        f5.setOnClickListener { submitKey("f5") }

        esc.setOnClickListener { submitKey("esc") }

//        fn.setOnClickListener { submitKey("up") }

        tab.setOnClickListener { submitKey("tab") }
        windows.setOnClickListener { submitKey("windows") }
        space.setOnClickListener { submitKey("space") }
        volUp.setOnClickListener { submitKey("vol+") }
        volDown.setOnClickListener { submitKey("vol-") }
//        menu.setOnClickListener { submitKey("enter") }
        next.setOnClickListener { submitKey("nextTrack") }
        prev.setOnClickListener { submitKey("prevTrack") }
        mute.setOnClickListener { submitKey("mute") }
        close.setOnClickListener { submitKey("alt+f4") }
    }

    private fun getToggles(): String {
        var toggles = ""
        if (cltToggle)
            toggles += "ctrl+"
        if (altToggle)
            toggles += "alt+"
        if (shiftToggle)
            toggles += "shift+"
        return toggles
    }

    private fun submitKey(cmd: String) {
//        var newCommand = getToggles() + cmd
        val newCommand =  cmd
        database.setValue(toggleCommand(newCommand))
            .addOnSuccessListener {
                Toast.makeText(baseContext, "${newCommand.uppercase()} sent...", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun toggleCommand(cmd: String): String {
        toggle = !toggle
        return if (toggle)
            cmd.uppercase()
        else
            cmd.lowercase()
    }

    private fun initViews() {
        command = findViewById(R.id.cmdInput)
        write = findViewById(R.id.write)
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
        disconnect = findViewById(R.id.disconnect)
        f5 = findViewById(R.id.f5)
        esc = findViewById(R.id.esc)
        fn = findViewById(R.id.fn)
        tab = findViewById(R.id.tab)
        windows = findViewById(R.id.windows)
        space = findViewById(R.id.space)
        volUp = findViewById(R.id.volUp)
        volDown = findViewById(R.id.volDown)
//        menu = findViewById(R.id.menu)
        next = findViewById(R.id.trackRight)
        prev = findViewById(R.id.trackLeft)
        mute = findViewById(R.id.mute)
        close = findViewById(R.id.exit)
        deviceName = findViewById(R.id.deviceName)
        // control keys
        clt.setOnClickListener {
            if (cltToggle) {
                it.setBackgroundColor(resources.getColor(R.color.not_pressed))
                ctrlDb.setValue(false)
            } else{
                ctrlDb.setValue(true)
                it.setBackgroundColor(resources.getColor(R.color.pressed))
            }
            cltToggle = !cltToggle
        }
        alt.setOnClickListener {
            if (altToggle) {
                it.setBackgroundColor(resources.getColor(R.color.not_pressed))
                altDb.setValue(false)
            } else{
                altDb.setValue(true)
                it.setBackgroundColor(resources.getColor(R.color.pressed))
            }
            altToggle = !altToggle
        }
        shift.setOnClickListener {
            if (shiftToggle) {
                it.setBackgroundColor(resources.getColor(R.color.not_pressed))
                shiftDb.setValue(false)
            } else{
                shiftDb.setValue(true)
                it.setBackgroundColor(resources.getColor(R.color.pressed))
            }
            shiftToggle = !shiftToggle
        }
    }

    override fun onStop() {
        database.setValue("exit")
        super.onStop()
    }
}