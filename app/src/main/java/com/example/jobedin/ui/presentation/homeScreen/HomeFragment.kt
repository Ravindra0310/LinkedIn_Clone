package com.example.jobedin.ui.presentation.homeScreen

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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jobedin.R
import com.example.jobedin.ui.presentation.components.Post
import com.example.jobedin.ui.theme.RobotoFontFamily
import com.example.jobedin.util.loadPicture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeScreenViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(
                    modifier = Modifier.background(Color(0xFFE9E6DF))
                ) {
                    val posts by viewModel.posts.observeAsState()

                    val size by remember {
                        viewModel.number
                    }
                    Button(onClick = { viewModel.addPost() }) {
                        Text(text = "$size")
                    }

                    LazyColumn {
                        item {
                            StoriesRow()
                        }
                        items(size) { post ->
                            Spacer(modifier = Modifier.size(8.dp))
                            Post(
                                profilePic = posts!![post]?.profilePic ?: "nan",
                                userName = posts!![post]?.userName ?: "nan",
                                des = posts!![post]?.subDis1 ?: "nan",
                                time = posts!![post]?.time ?: "nan",
                                postText = posts!![post]?.postText,
                                tags = posts!![post]?.tags,
                                postImage = posts!![post]?.postImage ?: "nan",
                                postVideo = posts!![post]?.postVideo,
                                likes = "${posts!![post]?.likes}"
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                }
            }
        }
    }
}


val userImage =
    "https://yt3.ggpht.com/ytc/AAUvwnix1W5yfYHFVUru51TRhdeSyFkMhglTrBp_IYP1qA=s900-c-k-c0x00ffffff-no-rj"

@Preview
@Composable
fun StoriesRow() {
    Spacer(modifier = Modifier.size(8.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White
            )
    ) {
        Column(
            modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 15.dp, end = 12.dp)
        ) {
            Text(
                text = "Stories",
                fontFamily = RobotoFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.size(12.dp))
            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.size(57.dp)) {
                        loadPicture(
                            url = userImage,
                            defaultImage = R.drawable.ic_comment
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
                    Box(modifier = Modifier.size(120.dp)) {
                        loadPicture(
                            url = userImage,
                            defaultImage = R.drawable.ic_comment
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

@Preview
@Composable
fun ComposablePreview() {
    Post(
        profilePic = "https://media-exp1.licdn.com/dms/image/C560BAQG-DVu64TnfaQ/company-logo_200_200/0/1620381956947?e=1631750400&v=beta&t=_jYOhjy6_Xff9Gkl7IUUDDn0lIroIudbjpNv1SuQjKg",
        userName = "Masai School",
        des = "8,867 followers",
        time = "20h",
        postText = "After experiencing his Engineering college's teaching methods and curriculum, \n \n Nikhil Gudur lost the motivation to study and decided to drop out. Exploring his options, he enrolled at Masai, simply because it allowed him to experiment and understand \"why\" something was made and not just \"how\" it was made.  This fueled a fire in him and the mentorship at Masai coupled with his own motivation took him to smallcase, a fast-growing Fintech startup where he's working as a Frontend Engineer today. Just like Nikhil, if you too have a passion to learn coding, sign up with Masai at https://lnkd.in/dahqxvg",
        "werw",
        "nan",
        "nan",
        "550"
    )
}

