package com.example.a529lablearnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.core.app.ActivityOptionsCompat

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val view = LocalView.current
            Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part1AnimationActivity::class.java))
                }) {
                    Text("Part 1: Animation (Default)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out)
                    startActivity(Intent(this@MenuActivity, Part2AnimationActivity::class.java), options.toBundle())
                }) {
                    Text("Part 2: Animation (Fade)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(this@MenuActivity, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    startActivity(Intent(this@MenuActivity, Part3AnimationActivity::class.java), options.toBundle())
                }) {
                    Text("Part 3: Animation (Left slide)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.width / 2, view.height / 2, 0, 0)
                    startActivity(Intent(this@MenuActivity, Part4AnimationActivity::class.java), options.toBundle())
                }) {
                    Text("Part 4: Animation (Scale Up)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeClipRevealAnimation(view, view.width / 2, view.height / 2, 0, 0)
                    startActivity(Intent(this@MenuActivity, Part5AnimationActivity::class.java), options.toBundle())
                }) {
                    Text("Part 5: Animation (Clip Reveal)")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, Part6AnimationActivity::class.java))
                    overridePendingTransition(0, 0)
                }) {
                    Text("Part 6: Animation (No Animation)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(this@MenuActivity, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    startActivity(Intent(this@MenuActivity, Part8AnimationActivity::class.java), options.toBundle())
                }) {
                    Text("Part 8: Responsive Profile (Right slide / Slide In Left)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out)
                    startActivity(Intent(this@MenuActivity, Part9Activity::class.java), options.toBundle())
                }) {
                    Text("Part 9: Collapsing TopBar (Fade)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.width / 2, view.height / 2, 0, 0)
                    startActivity(Intent(this@MenuActivity, Part10Activity::class.java), options.toBundle())
                }) {
                    Text("Part 10: App Widget (Scale Up)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeClipRevealAnimation(view, view.width / 2, view.height / 2, 0, 0)
                    startActivity(Intent(this@MenuActivity, Part11Activity::class.java), options.toBundle())
                }) {
                    Text("Part 11: Skeleton Loading (Clip Reveal)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(this@MenuActivity, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    startActivity(Intent(this@MenuActivity, Part12Activity::class.java), options.toBundle())
                }) {
                    Text("Part 12: Bottom Sheet & Dialog (Slide)")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, CameraActivity::class.java))
                }) {
                    Text("Task 1: Camera (Default)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out)
                    startActivity(Intent(this@MenuActivity, GalleryActivity::class.java), options.toBundle())
                }) {
                    Text("Task 2: Gallery Viewer (Fade)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(this@MenuActivity, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    startActivity(Intent(this@MenuActivity, SensorActivity::class.java), options.toBundle())
                }) {
                    Text("Task 2&3: Sensor MVVM (Left slide)")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, RPGCardActivity::class.java))
                }) {
                    Text("RPG Status (Default)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out)
                    startActivity(Intent(this@MenuActivity, PokedexActivity::class.java), options.toBundle())
                }) {
                    Text("Pokedex (Fade)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(this@MenuActivity, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    startActivity(Intent(this@MenuActivity, MainWeasel::class.java), options.toBundle())
                }) {
                    Text("LifeCycleComposeActivity (Left slide)")
                }
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.width / 2, view.height / 2, 0, 0)
                    startActivity(Intent(this@MenuActivity, SharedPreferencesActivity::class.java), options.toBundle())
                }) {
                    Text("SharedPreferencesActivity (Scale Up)")
                }
            }
        }
    }
}