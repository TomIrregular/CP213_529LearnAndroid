package com.tomweasley.overgrilled

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.tomweasley.overgrilled.navigation.NavGraph
import com.tomweasley.overgrilled.ui.theme.DarkBrown
import com.tomweasley.overgrilled.ui.theme.OvergrilledTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OvergrilledTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkBrown
                ) {
                    NavGraph()
                }
            }
        }
    }
}
