package com.example.jobedin.ui.presentation.commentScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.jobedin.MainActivity
import com.example.jobedin.R
import com.example.jobedin.ui.presentation.components.Post
import com.example.jobedin.ui.presentation.modelsForDetachingListeners.DatabaseRefAndChildEventListener
import com.example.jobedin.ui.presentation.parcelables.PostParcel
import com.example.jobedin.ui.theme.PostDesColorGrey
import com.example.jobedin.ui.theme.RobotoFontFamily
import com.example.jobedin.util.loadPicture
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : Fragment() {

    private val args by navArgs<CommentFragmentArgs>()
    private val viewModel by viewModels<CommentsScreenViewModel>()
    private lateinit var postData: PostParcel

    private lateinit var databaseRef: DatabaseRefAndChildEventListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postData = args.postParcel
        val currentUserImage = FirebaseAuth.getInstance().currentUser?.photoUrl.toString() ?: "nan"

        return ComposeView(requireContext()).apply {
            setContent {

                Box(contentAlignment = Alignment.BottomCenter) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        contentPadding = PaddingValues(bottom = 50.dp)
                    ) {
                        item {
                            Column {
                                Post(
                                    profilePic = postData.profilePic ?: "nan",
                                    userName = postData.userName ?: "nan",
                                    des = postData.subDis1 ?: "nan",
                                    time = postData.time ?: "nan",
                                    postText = postData.postText,
                                    tags = postData.tags,
                                    postImage = postData.postImage ?: "nan",
                                    postVideo = postData.postVideo,
                                    likes = "${postData.likes}",
                                    sharepost = { /*TODO*/ },
                                    onLikePressed = { /*TODO*/ },
                                    isLiked = postData.isLiked,
                                    onComment = {}
                                )
                                Spacer(modifier = Modifier.size(9.dp))
                            }
                        }
                        item {
                            Column {
                                Spacer(modifier = Modifier.size(9.dp))
                                Row {
                                    Spacer(modifier = Modifier.size(12.dp))
                                    Text(
                                        text = "Comments",
                                        fontFamily = RobotoFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 15.sp
                                    )

                                }
                                Spacer(modifier = Modifier.size(9.dp))
                            }
                        }

                        items(viewModel.comments.size) { index ->
                            Column {
                                Spacer(modifier = Modifier.size(9.dp))
                                val comment = viewModel.comments[index]
                                CommentBox(
                                    name = comment?.name ?: "nan",
                                    des = comment?.des ?: "nan",
                                    image = comment?.image ?: "nan",
                                    comment = comment?.comment ?: "nan"
                                )
                                Spacer(modifier = Modifier.size(9.dp))
                            }
                        }


                    }
                    CommentSectionTextBox(
                        currentText = viewModel.currentText.value,
                        currentUserImage = currentUserImage,
                        onTextChange = {
                            viewModel.currentText.value = it
                        },
                        onPost = {
                            viewModel.postComment(
                                postData.uniqueKey ?: "nan",
                                postOwnerUid = postData.postOwnerUid
                            )
                        }
                    )
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        databaseRef = viewModel.getComments(postId = postData.uniqueKey ?: "nan")
        val activity = activity as MainActivity
        activity.hideBottomNavi()
    }

    override fun onPause() {
        super.onPause()
        databaseRef.databaseRef.removeEventListener(databaseRef.childEventListener)
        val activity = activity as MainActivity
        activity.showBottomNavi()
    }

}

@Composable
fun CommentSectionTextBox(
    currentUserImage: String,
    currentText: String,
    onPost: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Column {
        Divider(modifier = Modifier.fillMaxWidth(), color = PostDesColorGrey, thickness = 0.5.dp)
        Row(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            loadPicture(
                url = currentUserImage,
                defaultImage = R.drawable.place_holder
            ).value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(31.dp)
                )
            }

            Box {
                BasicTextField(
                    value = currentText,
                    onValueChange = {
                        onTextChange(it)
                    },
                    modifier = Modifier.width(240.dp),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = RobotoFontFamily
                    )
                )

                if (currentText.isEmpty()) {
                    Text(
                        text = "Leave your thoughts here..",
                        color = PostDesColorGrey,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = RobotoFontFamily
                    )
                }

            }

            Text(
                text = "Post",
                color = if (currentText.isEmpty()) PostDesColorGrey else Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = RobotoFontFamily,
                modifier = Modifier.clickable {
                    if (currentText.isNotEmpty()) {
                        onPost()
                    }
                }
            )
        }
    }
}


@Composable
fun CommentBox(
    name: String,
    des: String,
    image: String,
    comment: String
) {

    Row(
        modifier = Modifier.padding(end = 12.dp)
    ) {
        Spacer(modifier = Modifier.size(11.dp))
        Column {
            Spacer(modifier = Modifier.size(18.dp))
            loadPicture(
                url = image,
                defaultImage = R.drawable.place_holder,
            ).value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "profile pic of $name",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(31.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
        Row(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topEnd = 7.dp,
                        bottomEnd = 7.dp,
                        bottomStart = 7.dp
                    )
                )
                .background(color = Color(0xFFF2F2F2))
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = name,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                )
                Spacer(modifier = Modifier.size(3.dp))
                Text(
                    text = des,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = PostDesColorGrey,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                )
                Spacer(modifier = Modifier.size(7.dp))
                Text(
                    text = comment,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

}