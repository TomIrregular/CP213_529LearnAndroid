package com.example.a529lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.a529lablearnandroid.ui.theme._529LabLearnAndroidTheme

// 1. สร้าง ViewModel ที่มี mutableStateListOf เก็บรายการข้อความ 5 รายการ
class TodoViewModel : ViewModel() {
    private val _todoItems = mutableStateListOf(
        "Shopping for groceries",
        "Finish Android Lab tasks",
        "Clean the house",
        "Pay electricity bill",
        "Call family"
    )
    val todoItems: List<String> get() = _todoItems

    fun removeItem(item: String) {
        _todoItems.remove(item)
    }
}

class Part4AnimationActivity : ComponentActivity() {
    // กำหนด ViewModel ผูกกับ Activity
    private val viewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _529LabLearnAndroidTheme {
                Scaffold { innerPadding ->
                    TodoListScreen(
                        items = viewModel.todoItems,
                        onRemoveItem = { item -> viewModel.removeItem(item) },
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    items: List<String>,
    onRemoveItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = items,
            key = { it } // ใช้ key ให้แม่นยำสำหรับการแก้ state และ animation
        ) { item ->
            
            // 4. เมื่อปัดจนสุด ให้ลบข้อมูลนั้นออกจาก State
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.EndToStart) {
                        onRemoveItem(item)
                        true
                    } else {
                        false
                    }
                }
            )

            // 2. ใช้ SwipeToDismissBox สร้าง Item
            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    // 3. ปัดไปทางซ้าย พื้นหลังเปลี่ยนเป็นสีแดง
                    val color by animateColorAsState(
                        targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                            Color.Red
                        } else {
                            Color.Transparent
                        },
                        label = "background_color"
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                            // มี Icon รูปถังขยะ
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Task",
                                tint = Color.White
                            )
                        }
                    }
                },
                enableDismissFromStartToEnd = false // อนุญาตให้ปัด End-to-Start เพื่อลบเท่านั้น
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
