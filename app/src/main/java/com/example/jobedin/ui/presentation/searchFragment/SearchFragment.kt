package com.example.jobedin.ui.presentation.searchFragment

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.navigation.fragment.findNavController
import com.example.jobedin.R
import com.example.jobedin.ui.presentation.parcelables.UserDetailParcel
import com.example.jobedin.ui.theme.PostDesColorGrey
import com.example.jobedin.ui.theme.RobotoFontFamily
import com.example.jobedin.util.loadPicture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {


                Scaffold(topBar = {
                    TopActionSearchBar(currentText = viewModel.currentText.value, onValueChanged = {
                        viewModel.currentText.value = it
                    }) {
                        viewModel.searchUser()
                    }
                }) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(Color(0xFFE9E6DF)),
                        contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
                    ) {
                        items(viewModel.size.value) { index ->
                            if (index < viewModel.searchResults.value.size) {
                                val data = viewModel.searchResults.value[index]
                                Spacer(modifier = Modifier.size(3.dp))
                                SearchResult(
                                    imageUrl = data.image ?: "nan",
                                    userName = data.name ?: "nan",
                                    description = data.description ?: "nan",
                                    modifier = Modifier.clickable {


                                        val action =
                                            SearchFragmentDirections.actionSearchFragmentToChatFragmentNew(
                                                UserDetailParcel(
                                                    name = data?.name ?: "nan",
                                                    image = data.image ?: "nan",
                                                    uid = data.uid ?: "nan"
                                                )
                                            )
                                        findNavController().navigate(action)
                                    }
                                )
                                Spacer(modifier = Modifier.size(3.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopActionSearchBar(
    currentText: String,
    onValueChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(42.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "back button",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(18.dp))
        Box() {
            BasicTextField(
                value = currentText,
                onValueChange = {
                    onValueChanged(it)
                },
                textStyle = TextStyle(
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearch()
                })
            )
            if (currentText.isEmpty()) {
                Text(
                    text = "Search", color = PostDesColorGrey, fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                )
            }
        }

    }
}


@Composable
fun SearchResult(
    imageUrl: String,
    userName: String,
    description: String,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .clip(
                RoundedCornerShape(7.dp)
            )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 15.dp, end = 15.dp, bottom = 19.dp)

        ) {
            Row {
                loadPicture(
                    url = imageUrl,
                    defaultImage = R.drawable.ic_comment
                ).value?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "profile pic",
                        modifier = Modifier
                            .size(61.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.size(12.dp))
                Column {
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = userName,
                        fontFamily = RobotoFontFamily,
                        fontSize = 19.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = description,
                        fontFamily = RobotoFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }

}