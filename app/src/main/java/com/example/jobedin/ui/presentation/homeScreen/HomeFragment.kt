package com.example.jobedin.ui.presentation.homeScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jobedin.ProfileActivity
import com.example.jobedin.R
import com.example.jobedin.ui.presentation.components.Post
import com.example.jobedin.ui.presentation.parcelables.PostParcel
import com.example.jobedin.ui.theme.RobotoFontFamily
import com.example.jobedin.util.loadPicture
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class HomeFragment : Fragment() {
    var userimages = FirebaseAuth.getInstance().currentUser!!.photoUrl.toString()
    private val viewModel by viewModels<HomeScreenViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "nan"
        return ComposeView(requireContext()).apply {
            setContent {

                val toolbarHeight = 42.dp
                val toolbarHeightPx =
                    with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
                val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
                val nestedScrollConnection = remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            val delta = available.y
                            val newOffset = toolbarOffsetHeightPx.value + delta
                            toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                            return Offset.Zero
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .background(Color(0xFFE9E6DF))
                        .fillMaxSize()
                        .nestedScroll(nestedScrollConnection)

                ) {


                    val size by remember {
                        viewModel.number
                    }

                    LazyColumn(contentPadding = PaddingValues(top = toolbarHeight)) {
                        item {
                            StoriesRow(userImage = userimages)
                        }
                        items(viewModel.posts.size) { post ->
                            Spacer(modifier = Modifier.size(3.dp))
                            val likeData = viewModel.posts[post]?.listOfAllLiked
                            var isLiked by remember {
                                mutableStateOf(likeData?.containsKey(currentUserUid) == true)
                            }
                            Post(
                                profilePic = viewModel.posts!![post]?.profilePic ?: "nan",
                                userName = viewModel.posts!![post]?.userName ?: "nan",
                                des = viewModel.posts!![post]?.subDis1 ?: "nan",
                                time = viewModel.posts!![post]?.time ?: "nan",
                                postText = viewModel.posts!![post]?.postText,
                                tags = viewModel.posts!![post]?.tags,
                                postImage = viewModel.posts!![post]?.postImage ?: "nan",
                                postVideo = viewModel.posts!![post]?.postVideo,
                                likes = "${viewModel.posts[post]?.likes}",
                                sharepost = {
                                    sharePost(viewModel.posts[post]?.postText!!)
                                },
                                onLikePressed = {
                                    isLiked = if (likeData?.containsKey(currentUserUid) == true) {
                                        likeData.remove(currentUserUid)
                                        viewModel.posts[post]?.likes =
                                            viewModel.posts[post]?.likes?.minus(
                                                1
                                            )
                                        viewModel.updateLikeList(
                                            postId = viewModel.posts[post]!!.uniqueKey!!,
                                            addLike = false,
                                            numberOfLikes = viewModel.posts[post]?.likes ?: 1,
                                            uidOfPostOwner = viewModel.posts[post]?.userUid ?: "nan"
                                        )
                                        false

                                    } else {
                                        likeData?.put(currentUserUid, true)
                                        viewModel.posts[post]?.likes =
                                            viewModel.posts[post]?.likes?.plus(
                                                1
                                            )
                                        viewModel.updateLikeList(
                                            postId = viewModel.posts[post]!!.uniqueKey!!,
                                            addLike = true,
                                            numberOfLikes = viewModel.posts[post]?.likes ?: 0,
                                            uidOfPostOwner = viewModel.posts[post]?.userUid ?: "nan"
                                        )
                                        true
                                    }

                                },
                                isLiked = isLiked,
                                onComment = {
                                    val action =
                                        HomeFragmentDirections.actionHomeFragmentToCommentFragment(
                                            PostParcel(
                                                profilePic = viewModel.posts!![post]?.profilePic
                                                    ?: "nan",
                                                userName = viewModel.posts!![post]?.userName
                                                    ?: "nan",
                                                subDis1 = viewModel.posts!![post]?.subDis1 ?: "nan",
                                                time = viewModel.posts!![post]?.time ?: "nan",
                                                postText = viewModel.posts!![post]?.postText,
                                                tags = viewModel.posts!![post]?.tags,
                                                postImage = viewModel.posts!![post]?.postImage
                                                    ?: "nan",
                                                postVideo = viewModel.posts!![post]?.postVideo,
                                                likes = viewModel.posts[post]?.likes,
                                                isLiked = isLiked,
                                                uniqueKey = viewModel.posts[post]?.uniqueKey,
                                                postOwnerUid = viewModel.posts[post]?.userUid
                                                    ?: "nan"
                                            )
                                        )
                                    findNavController().navigate(action)
                                }
                            )
                            Spacer(modifier = Modifier.size(3.dp))
                        }
                    }
                    TopBar(currentText = viewModel.currentQueryText.value,
                        onTextChanged = { viewModel.currentQueryText.value = it },
                        modifier = Modifier
                            .height(toolbarHeight)
                            .offset {
                                IntOffset(
                                    x = 0,
                                    y = toolbarOffsetHeightPx.value.roundToInt()
                                )
                            }, onSearchExecute = {
                            viewModel.doSearch(it)
                            navigateToSearch()
                        }, goToChat = {
                            // startActivity(Intent(activity, ChatActivity::class.java))
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToAllConversationFragment()
                            findNavController().navigate(action)

                        }, profile = {
                            startActivity(Intent(activity, ProfileActivity::class.java))
                        }, userimage = userimages
                    )
                }
            }
        }
    }

    private fun sharePost(postImage: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, postImage)
        startActivity(intent)
    }


    private fun navigateToSearch() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        findNavController().navigate(action)
    }

}


@Composable
fun TopBar(
    currentText: String,
    onTextChanged: (String) -> Unit,
    onSearchExecute: (String) -> Unit,
    modifier: Modifier,
    goToChat: () -> Unit,
    profile: () -> Unit,
    userimage: String

) {
    Row(
        modifier = modifier
            .height(42.dp)
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        loadPicture(
            url = userimage,
            defaultImage = R.drawable.place_holder
        ).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "user Profile",
                modifier = Modifier
                    .clip(
                        CircleShape
                    )
                    .size(29.dp)
                    .clickable {
                        profile()
                    }
            )

        }
        SearchBar(
            currentText = currentText,
            onTextChanged = onTextChanged,
            onSearchExecute = onSearchExecute
        )

        Image(
            painter = painterResource(id = R.drawable.ic_messagefill),
            contentDescription = "Message icon",
            modifier = Modifier.clickable {
                goToChat()
            }
        )

    }

}


@Composable
fun SearchBar(
    currentText: String,
    onTextChanged: (String) -> Unit,
    onSearchExecute: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFFEEF3F7))
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.size(9.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_search_mag),
                contentDescription = "Search Icon",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Box() {

                BasicTextField(
                    value = currentText, onValueChange = { onTextChanged(it) },
                    textStyle = TextStyle(
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (currentText.isNotEmpty()) {
                            onSearchExecute(currentText)
                        }
                    })
                )
                if (currentText.isEmpty()) {
                    Text(
                        text = "Search",
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp
                    )
                }
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.qr_scan),
                        contentDescription = "qr scan",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun StoriesRow(userImage: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White
            )
    ) {
        Column(
            modifier = Modifier.padding(start = 12.dp, bottom = 15.dp, end = 12.dp)
        ) {

            Spacer(modifier = Modifier.size(12.dp))
            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.size(57.dp)) {
                        loadPicture(
                            url = userImage,
                            defaultImage = R.drawable.place_holder
                        ).value?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "user Stories",
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                            )
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                Card(
                                    modifier = Modifier
                                        .size(23.dp)
                                        .clip(CircleShape)
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xffE4E8EB),
                                            shape = CircleShape
                                        )
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_add),
                                            contentDescription = "add story",
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(
                        text = "Your Story",
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.size(57.dp)) {
                        loadPicture(
                            url = userImage,
                            defaultImage = R.drawable.place_holder
                        ).value?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "user Stories",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                            )

                        }

                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(
                        text = "Android",
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp
                    )
                }
            }
        }

    }

    Spacer(modifier = Modifier.size(8.dp))
}

//@Preview
//@Composable
//fun ComposablePreview() {
//    TopBar("", {}, {}, Modifier)
//}
