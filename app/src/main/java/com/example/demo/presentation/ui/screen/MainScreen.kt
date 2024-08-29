package com.example.demo.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.demo.domain.model.User
import com.example.demo.presentation.navigation.Screen
import com.example.demo.presentation.ui.theme.LynxWhite
import com.example.demo.presentation.viewmodel.MainEvent
import com.example.demo.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()  // LazyListState to track scroll position

    // Coroutine scope to trigger load more when needed
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Github Users", fontWeight = FontWeight.Bold)
                },
                modifier = Modifier.background(Color.White)
            )
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(state.users) { user ->
                UserItem(user, onClick = {
                    navController.navigate(Screen.UserDetails.createRoute(user.login))
                })
            }

            if (state.isLoading) {
                item {
                    CircularProgressIndicator()
                }
            }
        }
    }

    // Trigger load more when the user scrolls within the last 5 items
    LaunchedEffect(listState) {
        coroutineScope.launch {
            snapshotFlow { listState.firstVisibleItemIndex }
                .collect { firstVisibleItemIndex ->
                    val lastVisibleItemIndex = firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size
                    if (lastVisibleItemIndex >= state.users.size - 5 && !state.isLoading && state.hasMore) {
                        viewModel.onEvent(MainEvent.LoadMore)
                    }
                }
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)  // Outer padding for spacing between items
            .shadow(8.dp, RoundedCornerShape(16.dp))  // Shadow for elevation
            .clip(RoundedCornerShape(16.dp))  // Rounded corners
            .background(Color.White)  // Background color
            .clickable { onClick() }
            .padding(8.dp),  // Inner padding for content
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(96.dp)
                .background(color = LynxWhite, shape = RoundedCornerShape(8.dp))
                .padding(4.dp)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = user.login,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 6.dp))
            Text(
                text = user.htmlUrl,
                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline),
                color = Color.Blue
            )
        }
    }
}
