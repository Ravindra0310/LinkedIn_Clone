package com.example.jobedin.ui.presentation.allConversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jobedin.R
import com.example.jobedin.ui.presentation.parcelables.UserDetailParcel
import com.example.jobedin.ui.theme.PostDesColorGrey
import com.example.jobedin.ui.theme.RobotoFontFamily
import com.example.jobedin.util.loadPicture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllConversationFragment : Fragment() {
    private val viewModel by viewModels<AllConversationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = {
                        AllConversationFragmentTopBar()
                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            item {
                                SearchBar()
                            }

                            items(viewModel.callConversations.size) { index ->
                                val data = viewModel.callConversations[index]

                                DisplayMessage(
                                    iamgeUrl = data?.image ?: "nan",
                                    username = data?.userName ?: "nan",
                                    message = data?.lastMessage ?: "nan",
                                    onClick = {
                                        val action =
                                            AllConversationFragmentDirections.actionAllConversationFragmentToChatFragmentNew(
                                                UserDetailParcel(
                                                    name = data?.userName ?: "nan",
                                                    image = data?.image ?: "nan",
                                                    uid = data?.friendUid ?: "nan"
                                                )
                                            )
                                        findNavController().navigate(action)
                                    }
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayMessage(
    iamgeUrl: String,
    username: String,
    message: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .height(77.dp)
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Spacer(modifier = Modifier.size(15.dp))
            loadPicture(
                url = iamgeUrl,
                defaultImage = R.drawable.ic_comment
            ).value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "profile pic",
                    modifier = Modifier.size(45.dp)
                )
            }
            Spacer(modifier = Modifier.size(9.dp))
            Column {
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = username,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.size(9.dp))
                Text(
                    text = message,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }
        Divider(color = PostDesColorGrey, thickness = 0.5.dp, modifier = Modifier.fillMaxWidth())
    }


}


@Composable
fun SearchBar() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(14.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_search_mag),
                contentDescription = "Search"
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Search messages")
        }
        Divider(color = PostDesColorGrey, thickness = 0.5.dp, modifier = Modifier.fillMaxWidth())
    }


}

@Composable
fun AllConversationFragmentTopBar() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(32.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                    contentDescription = "three dot",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.size(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "back button",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(30.dp))
            Text(
                text = "Messaging",
                fontFamily = RobotoFontFamily,
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium
            )

        }
    }
}