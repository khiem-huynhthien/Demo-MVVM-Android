package com.example.demo.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.demo.domain.model.UserDetail
import com.example.demo.presentation.ui.theme.LynxWhite
import com.example.demo.presentation.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(username: String, navController: NavController, viewModel: UserViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchUserDetails(username)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("User Details", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier.background(Color.White),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
        ) {
            val user = state.user ?: return@Column

            UserItem(user)
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .background(color = Color.LightGray, shape = CircleShape)
                            .padding(16.dp)
                    )
                    Text(text = "${user.followers}+", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Followers", style = MaterialTheme.typography.bodySmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .background(color = Color.LightGray, shape = CircleShape)
                            .padding(16.dp)
                    )
                    Text(text = "${user.following}+", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Following", style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Blog", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
            Text(
                text = user.htmlUrl,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun UserItem(user: UserDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)  // Outer padding for spacing between items
            .shadow(8.dp, RoundedCornerShape(16.dp))  // Shadow for elevation
            .clip(RoundedCornerShape(16.dp))  // Rounded corners
            .background(Color.White)  // Background color
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = user.location.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
