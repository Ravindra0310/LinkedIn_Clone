package com.example.jobedin.ui.presentation.chatScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.jobedin.MainActivity
import com.example.jobedin.R
import com.example.jobedin.ui.presentation.modelsForDetachingListeners.DatabaseRefAndChildEventListener
import com.example.jobedin.ui.presentation.parcelables.UserDetailParcel
import com.example.jobedin.ui.theme.PostDesColorGrey
import com.example.jobedin.ui.theme.RobotoFontFamily
import com.example.jobedin.util.loadPicture
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragmentNew : Fragment() {

    private val args by navArgs<ChatFragmentNewArgs>()

    private val viewModel by viewModels<ChatViewModel>()
    private lateinit var friendData: UserDetailParcel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        friendData = args.userDetailParcel


        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "nan"
        val currentUserName = FirebaseAuth.getInstance().currentUser?.displayName ?: "nan"
        val currentUserImage = FirebaseAuth.getInstance().currentUser?.photoUrl.toString() ?: "nan"


        return ComposeView(requireContext()).apply {
            setContent {

                ProvideWindowInsets {
                    Box(modifier = Modifier.navigationBarsWithImePadding()) {
                        Scaffold(topBar = { ChatFragmentTopBar(friendData.name) },
                            bottomBar = {

                            }
                        ) {

                            Box(
                                modifier = Modifier.padding(it),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Column() {
                                    LazyColumn(
                                        reverseLayout = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 70.dp)
                                            .fillMaxSize()
                                    ) {
                                        items(viewModel.allMessagesList.size) { index ->
                                            val data = viewModel.allMessagesList[index]
                                            if (data != null) {
                                                if (data.senderUid == currentUserUid) {
                                                    ChatBox(
                                                        name = currentUserName,
                                                        imageUrl = currentUserImage,
                                                        message = data.messages ?: "nan"
                                                    )
                                                } else {
                                                    ChatBox(
                                                        name = friendData.name,
                                                        imageUrl = friendData.image,
                                                        message = data.messages ?: "nan"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                ChatTextBox(
                                    currentText = viewModel.currentMessage.value,
                                    onTextChange = {
                                        viewModel.currentMessage.value = it
                                    },
                                    onSend = {
                                        viewModel.addMessage(
                                            currentUserUid = currentUserUid,
                                            friendUid = friendData.uid
                                        )
                                    }
                                )
                            }

                        }
                    }

                }

            }
        }
    }


    lateinit var databaseRef: DatabaseRefAndChildEventListener
    override fun onResume() {
        super.onResume()
        val activity = activity as MainActivity
        activity.hideBottomNavi()
        databaseRef = viewModel.startConversation(
            friendUid = friendData.uid,
            friendImageUrl = friendData.image,
            friendName = friendData.name
        )
    }

    override fun onPause() {
        super.onPause()
        val activity = activity as MainActivity
        activity.showBottomNavi()
        databaseRef.databaseRef.removeEventListener(databaseRef.childEventListener)
    }
}

@Composable
fun ChatTextBox(
    currentText: String,
    onSend: () -> Unit,
    onTextChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(Color(0xFFF9FAFC)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .border(width = 0.5.dp, color = PostDesColorGrey, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = "add",
                modifier = Modifier.size(18.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_baseline_video_call_24),
            contentDescription = "video",
            modifier = Modifier.size(24.dp)
        )

        Box(
            modifier = Modifier
                .width(238.dp)
                .height(45.dp)
                .background(Color.White)
                .clip(RoundedCornerShape(7.dp))
                .border(width = 0.5.dp, color = PostDesColorGrey, shape = RoundedCornerShape(7.dp))
                .padding(start = 15.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = currentText,
                onValueChange = {
                    onTextChange(it)
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    onSend()
                })
            )
            if (currentText.isEmpty()) {
                Text(
                    text = "Write a message...", color = PostDesColorGrey,
                    fontSize = 15.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_baseline_mic_none_24),
            contentDescription = "mic",
            modifier = Modifier.size(24.dp)
        )

    }

}


@Composable
fun ChatBox(
    name: String,
    imageUrl: String,
    message: String,
) {

    Row(
        modifier = Modifier
            .padding(top = 13.dp, bottom = 13.dp, start = 11.dp, end = 11.dp)
            .fillMaxWidth()
    ) {
        loadPicture(url = imageUrl, defaultImage = R.drawable.ic_comment).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "image of $name",
                modifier = Modifier
                    .size(31.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.size(9.dp))
        Column() {
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = name,
                fontSize = 15.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.size(9.dp))
            Text(
                text = message,
                fontSize = 15.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }

}

@Composable
fun ChatFragmentTopBar(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
    ) {

        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.size(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "back button",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(30.dp))
            Text(
                text = name,
                fontFamily = RobotoFontFamily,
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium
            )

        }
    }
}