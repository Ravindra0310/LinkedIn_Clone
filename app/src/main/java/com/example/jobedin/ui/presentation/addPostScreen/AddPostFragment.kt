package com.example.jobedin.ui.presentation.addPostScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.jobedin.MainActivity
import com.example.jobedin.R
import com.example.jobedin.ui.theme.PostDesColorGrey
import com.example.jobedin.ui.theme.RobotoFontFamily
import com.example.jobedin.util.loadPicture
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddPostFragment : Fragment() {

    private val viewModel by viewModels<AddPostVIewModel>()

    companion object {
        public lateinit var navController: NavController
    }

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = findNavController()

        val userImage = FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
        val userName = FirebaseAuth.getInstance().currentUser?.displayName ?: "nan"
        val userUid = FirebaseAuth.getInstance().currentUser?.uid ?: "nan"

        return ComposeView(requireContext()).apply {
            setContent {
                ProvideWindowInsets {
                    val insets = LocalWindowInsets.current

                    val imeBottom = with(LocalDensity.current) { insets.ime.bottom.toDp() }
                    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = rememberBottomSheetState(
                            initialValue = BottomSheetValue.Expanded
                        )
                    )
                    val coroutineScope = rememberCoroutineScope()

                    BottomSheetScaffold(
                        sheetContent = {
                            BottomSheet()
                        },
                        topBar = {
                            AddPostTopBar(
                                isPostEnabled = viewModel.currentText.value.isNotEmpty(),
                                onClick = {
                                    if (viewModel.currentText.value.isNotEmpty()) {
                                        viewModel.addPost(
                                            name = userName,
                                            image = userImage,
                                            currentUserUid = userUid
                                        )
                                        val action =
                                            AddPostFragmentDirections.actionAddPostFragmentToHomeFragment()
                                        findNavController().navigate(action)
                                    }
                                }
                            )
                        },
                        scaffoldState = bottomSheetScaffoldState,
                        sheetPeekHeight = 0.dp
                    ) {
                        Scaffold(
                            bottomBar = {


                            }
                        ) {
                            AddPostTextArea(
                                currentText = viewModel.currentText.value,
                                onTextChanged = {
                                    viewModel.changeCurrentText(it)
                                },
                                onFocusChanged = {
                                    if (it) {
                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                            viewModel.showBottomBar.value = true
                                        }
                                    } else {
                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                            viewModel.showBottomBar.value = false
                                        }
                                    }
                                },
                                userName = userName,
                                userImage = userImage
                            )


                        }
                    }

                    if (viewModel.showBottomBar.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = imeBottom),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            AddPostBottomBar()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as MainActivity
        activity.hideBottomNavi()
    }

    override fun onPause() {
        super.onPause()
        val activity = activity as MainActivity
        activity.showBottomNavi()
    }

}

@Composable
fun AddPostTopBar(
    isPostEnabled: Boolean,
    onClick: () -> Unit
) {
    Card(

    ) {
        Row(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "close",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Share Post",
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            }
            Text(
                text = "Post",
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = if (isPostEnabled) Color.Black else PostDesColorGrey,
                modifier = Modifier.clickable {
                    onClick()
                }
            )
        }
    }
}

@Composable
fun AddPostBottomBar() {
    Row(
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .width(160.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_hiring),
                contentDescription = "hiring",
                modifier = Modifier.size(24.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_video),
                contentDescription = "video",
                modifier = Modifier.size(24.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_image),
                contentDescription = "image",
                modifier = Modifier.size(24.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_dot),
                contentDescription = "dot",
                modifier = Modifier.size(24.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_comment),
                contentDescription = "Anyone"
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = "Anyone",
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = PostDesColorGrey
            )
        }
    }
}


@Composable
fun BottomSheet(
) {
    Card(
        modifier = Modifier
            .height(360.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 17.dp, topEnd = 17.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(start = 13.dp, end = 13.dp, top = 20.dp)
                .clickable {

                },
            verticalArrangement = Arrangement.SpaceEvenly,

            ) {
            (BottomSheetItems).forEach { item ->
                BottomSheetItem(item.icon, item.name)
            }
        }

    }
}


val BottomSheetItems =
    listOf<BottomSheetItemModel>(
        BottomSheetItemModel(R.drawable.ic_image, "Add a photo."),
        BottomSheetItemModel(R.drawable.ic_video, "Take a video."),
        BottomSheetItemModel(R.drawable.ic_celebrate, "Celebrate an occasion."),
        BottomSheetItemModel(R.drawable.ic_document, "Add document"),
        BottomSheetItemModel(R.drawable.ic_hiring, "Share that you're hiring"),
        BottomSheetItemModel(R.drawable.ic_expert, "Find an expert"),
        BottomSheetItemModel(R.drawable.ic_poll, "Create a poll"),
    )


data class BottomSheetItemModel(
    val icon: Int,
    val name: String
)

@Composable
fun BottomSheetItem(
    icon: Int,
    name: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon), contentDescription = "image of $name",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    if (name == "Add a photo.") {
                        //open the camera fragment
                        navigateToCameraFragment()
                    }
                }
        )
        Spacer(modifier = Modifier.size(15.dp))

        Text(
            text = name,
            fontFamily = RobotoFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )

    }
}

fun navigateToCameraFragment() {
    val action = AddPostFragmentDirections.actionAddPostFragmentToCameraFragment()
    AddPostFragment.navController.navigate(action)
}

@Composable
fun AddPostTextArea(
    currentText: String,
    onTextChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    userName: String,
    userImage: String
) {

    Column(modifier = Modifier.padding(start = 17.dp, top = 20.dp)) {
        AddPostHeader(name = userName, userImage = userImage)
        Spacer(modifier = Modifier.size(20.dp))
        Box() {
            BasicTextField(
                value = currentText,
                onValueChange = { data ->
                    onTextChanged(data)
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                ),
                modifier = Modifier.onFocusChanged {
                    onFocusChanged(it.isFocused)
                }
            )
            if (currentText.isEmpty()) {
                Text(
                    text = "What do you want to talk about?",
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = PostDesColorGrey
                )
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        loadPicture(
            MainActivity.tempPicPath,
            R.drawable.place_holder
        ).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "header of use $userName",
                modifier = Modifier
                    //.clip(CircleShape)
                    .size(150.dp)
                    .fillMaxHeight(200f)
                    .fillMaxWidth()
            )
        }
    }

}


@Composable
fun AddPostHeader(
    name: String,
    userImage: String
) {
    Row {
        loadPicture(url = userImage, defaultImage = R.drawable.place_holder).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Profile pic of $name",
                modifier = Modifier
                    .size(43.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.size(10.dp))

        Column {
            //  Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = name,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.size(5.dp))
            Card(
                modifier = Modifier
                    .width(105.dp)
                    .height(23.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        color = PostDesColorGrey,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.size(7.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_earth),
                        contentDescription = "globule",
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Anyone",
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = PostDesColorGrey
                    )
                }
            }
        }
    }
}