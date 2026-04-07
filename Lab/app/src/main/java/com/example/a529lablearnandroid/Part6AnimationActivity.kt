package com.example.a529lablearnandroid

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import com.example.a529lablearnandroid.ui.theme._529LabLearnAndroidTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// 1. สร้าง ViewModel ที่มี State เก็บค่า URL String (ค่าเริ่มต้นคือ Google)
class WebViewModel : ViewModel() {
    private val _url = MutableStateFlow("https://www.google.com")
    val url: StateFlow<String> = _url.asStateFlow()

    fun updateUrl(newUrl: String) {
        // เพิ่ม http:// อัตโนมัติหากผู้ใช้พิมพ์มาแบบลืมใส่
        var finalUrl = newUrl
        if (!finalUrl.startsWith("http://") && !finalUrl.startsWith("https://")) {
            finalUrl = "https://$finalUrl"
        }
        _url.value = finalUrl
    }
}

class Part6AnimationActivity : ComponentActivity() {
    private val viewModel by viewModels<WebViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _529LabLearnAndroidTheme {
                Scaffold { innerPadding ->
                    WebViewScreen(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(viewModel: WebViewModel, modifier: Modifier = Modifier) {
    val currentUrl by viewModel.url.collectAsState()
    
    // 4. สร้าง TextField ด้านบนหน้าจอสำหรับพิมพ์ URL
    var inputText by remember { mutableStateOf(currentUrl) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                label = { Text("Enter URL") }
            )
            // ปุ่ม 'Go' เมื่อกดให้ส่ง URL ใหม่ไปอัปเดตที่ ViewModel
            Button(
                onClick = { viewModel.updateUrl(inputText) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Go")
            }
        }

        // 2. ใช้ Composable AndroidView เพื่อสร้าง android.webkit.WebView
        AndroidView(
            factory = { context ->
                // รันเฉพาะตอนเริ่มต้นเพื่อจำลอง View เท่านั้น
                WebView(context).apply {
                    // 3. ตั้งค่า webViewClient ให้โหลดเว็บภายในแอป (ไม่เด้งออกไปเบราว์เซอร์ข้างนอก)
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                }
            },
            update = { webView ->
                // Constraints: update block นี้จะถูกเรียกซ้ำเมื่อตัวแปร State (currentUrl) มีการเปลี่ยนแปลง
                // จึงใช้วิธีเรียกโหลดหน้าเว็บใหม่เมื่อ currentUrl ถูกเปลี่ยนจากปุ่ม Go
                webView.loadUrl(currentUrl)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
