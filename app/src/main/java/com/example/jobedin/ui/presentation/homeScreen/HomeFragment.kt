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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jobedin.R
import com.example.jobedin.ui.presentation.components.Post
import com.example.jobedin.ui.theme.RobotoFontFamily
import com.example.jobedin.util.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                    val posts by viewModel.posts.observeAsState()

                    val size by remember {
                        viewModel.number
                    }

                    LazyColumn(contentPadding = PaddingValues(top = toolbarHeight)) {
                        item {
                            StoriesRow()
                        }
                        items(size) { post ->
                            Spacer(modifier = Modifier.size(3.dp))
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
                        })
                }
            }
        }
    }


    private fun navigateToSearch() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        findNavController().navigate(action)
    }

}


val userImage =
    "https://yt3.ggpht.com/ytc/AAUvwnix1W5yfYHFVUru51TRhdeSyFkMhglTrBp_IYP1qA=s900-c-k-c0x00ffffff-no-rj"



@Composable
fun TopBar(
    currentText: String,
    onTextChanged: (String) -> Unit,
    onSearchExecute: (String) -> Unit,
    modifier: Modifier
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
            url = userImage,
            defaultImage = R.drawable.ic_comment
        ).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "user Profile",
                modifier = Modifier
                    .clip(
                        CircleShape
                    )
                    .size(29.dp)
            )

        }
        SearchBar(
            currentText = currentText,
            onTextChanged = onTextChanged,
            onSearchExecute = onSearchExecute
        )

        Image(
            painter = painterResource(id = R.drawable.ic_messagefill),
            contentDescription = "Message icon"
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
                painter = painterResource(id = R.drawable.ic_search),
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
fun StoriesRow() {

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
    TopBar("", {}, {}, Modifier)
}
