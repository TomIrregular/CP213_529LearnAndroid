package com.example.a529lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

class Part9Activity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    // Removed containerColor from here so the background is default white
                    topBar = {
                        LargeTopAppBar(
                            title = {
                                // Setting color here ensures it is white when EXPANDED
                                Text(
                                    "Collapsing Top Bar (Part 9)",
                                    color = Color.White
                                )
                            },
                            scrollBehavior = scrollBehavior,
                            colors = TopAppBarDefaults.largeTopAppBarColors(
                                // Use ONLY the parameters from your function signature:
                                containerColor = Color(0xFF00ACE6),
                                scrolledContainerColor = Color(0xFF00ACE6),
                                navigationIconContentColor = Color.White,
                                titleContentColor = Color.White, // This handles the COLLAPSED state
                                actionIconContentColor = Color.White
                            )
                        )
                    }
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            Text(
                                text = "Concept of Collapsing Toolbar:\n\n" +
                                        "Collapsing Toolbar คือรูปแบบ UI ที่ส่วนหัว (Top App Bar) จะสามารถหดตัวหรือขยายตัวได้...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black // Changed back to Black for white background
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                        items(50) { index ->
                            Text(
                                text = "Item $index",
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color.Black // Changed back to Black
                            )
                        }
                    }
                }
            }
        }
    }
}