package com.example.jobedin.ui.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jobedin.R
import com.example.jobedin.ui.theme.PostDesColorGrey
import com.example.jobedin.ui.theme.RobotoFontFamily
import com.example.jobedin.util.loadPicture


@Composable
fun Post(
    profilePic: String,
    userName: String,
    des: String,
    time: String,
    postText: String?,
    tags: String?,
    postImage: String?,
    postVideo: String?,
    likes: String
) {

    var showFull by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        ) {
            Spacer(modifier = Modifier.size(12.dp))
            PostHeader(
                imageUrl = profilePic,
                userName = userName,
                des = des,
                time = time
            )
            Spacer(modifier = Modifier.size(15.dp))
            if (showFull && postText != null && postText != "nan") {
                Text(
                    text = postText,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.size(25.dp))

                if (tags != null && tags != "nan") {
                    Text(
                        text = tags,
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = Color(0xFF0D66C0)
                    )
                }
            } else if (!showFull && postText != null && postText != "nan") {
                Text(
                    text = postText,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 15.sp
                )
                Row {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "See more",
                            modifier = Modifier.clickable {
                                showFull = true
                            },
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        if (postImage != null && postImage != "nan") {
            loadPicture(postImage, R.drawable.ic_comment).value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "image of the post done by $userName",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else if (postVideo != null && postVideo != "nan") {
        //    VideoPlayer(postVideo)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_like_fill),
                contentDescription = "Like",
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.size(2.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_heart_fill),
                contentDescription = "heart",
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.size(2.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_clap_fill),
                contentDescription = "clap",
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = likes, fontFamily = RobotoFontFamily, fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 11.sp,
                color = PostDesColorGrey
            )

        }
        Spacer(modifier = Modifier.size(10.dp))
        Divider(
            thickness = 0.5.dp,
            color = PostDesColorGrey,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
        postButtons()

    }

}

@Composable
fun postButtons() {
    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
        PostButtonItem(R.drawable.ic_like, name = "Like")
        PostButtonItem(R.drawable.ic_comment, name = "Comment")
        PostButtonItem(R.drawable.ic_share, name = "share")
        PostButtonItem(R.drawable.ic_send, name = "send")
    }

}

@Composable
fun PostButtonItem(
    src: Int,
    name: String
) {
    Column(
        modifier = Modifier.height(42.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = src),
            contentDescription = "$name button",
            modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = name, fontFamily = RobotoFontFamily, fontWeight = FontWeight.Normal,


            fontSize = 11.sp,
            color = PostDesColorGrey
        )
    }
}


@Composable
fun PostHeader(
    imageUrl: String,
    userName: String,
    des: String,
    time: String
) {

    Row(modifier = Modifier.fillMaxWidth()) {

        loadPicture(
            imageUrl,
            R.drawable.ic_share
        ).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "header of use $userName",
                modifier = Modifier
                    //.clip(CircleShape)
                    .size(45.dp)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Column(
            modifier = Modifier
                .height(45.dp)
                .width(200.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = userName,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                fontSize = 13.sp,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = des, fontFamily = RobotoFontFamily, fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 11.sp,
                color = PostDesColorGrey
            )
            Text(
                text = time, fontFamily = RobotoFontFamily, fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 11.sp,
                color = PostDesColorGrey
            )
        }
    }
}