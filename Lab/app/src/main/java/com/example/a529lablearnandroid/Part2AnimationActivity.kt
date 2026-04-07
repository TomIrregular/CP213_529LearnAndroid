package com.example.a529lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class Part2AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactListScreen()
        }
    }
}

class ContactViewModel : ViewModel() {
    private val initialNames = listOf(
        "Adam", "Alice", "Bob", "Brian", "Charlie", "Chloe", "David", "Diana",
        "Edward", "Emma", "Frank", "Fiona", "George", "Grace", "Harry", "Hannah"
    )
    
    private val _contacts = MutableStateFlow(initialNames)
    val contacts: StateFlow<List<String>> = _contacts.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private var pageCount = 1

    fun loadMoreContacts() {
        if (_isLoading.value) return
        _isLoading.value = true
        
        // Simulating network delay
        viewModelScope.launch {
            delay(2000L)
            val moreNames = listOf(
                "Ian ($pageCount)", "Ivy ($pageCount)", "Jack ($pageCount)", "Julia ($pageCount)",
                "Kevin ($pageCount)", "Karen ($pageCount)", "Liam ($pageCount)", "Lily ($pageCount)"
            )
            _contacts.value = _contacts.value + moreNames
            pageCount++
            _isLoading.value = false
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen(viewModel: ContactViewModel = viewModel()) {
    val contacts by viewModel.contacts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    val listState = rememberLazyListState()
    
    // Group contacts by their first character
    val groupedContacts = contacts.groupBy { it.first().uppercaseChar() }

    // Trigger loadMore automatically when scrolling to the end
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .filter { it != null && it >= sumIndices(groupedContacts) - 1 }
            .collect {
                viewModel.loadMoreContacts()
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            groupedContacts.forEach { (initial, contactsForInitial) ->
                stickyHeader {
                    Text(
                        text = initial.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(8.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                
                items(contactsForInitial) { contact ->
                    Text(
                        text = contact,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
            
            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

// Since sticky header introduces extra items, the index of the last item isn't just contacts.size.
// Total items = total headers + total items.
private fun sumIndices(groups: Map<Char, List<String>>): Int {
    return groups.size + groups.values.sumOf { it.size }
}
