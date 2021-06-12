package com.example.jobedin.ui.presentation.homeScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jobedin.ui.presentation.components.Post
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeScreenViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("dasda", "sdfs")
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

                        items(size) { post ->
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
                        }
                    }
                }
            }
        }
    }
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

