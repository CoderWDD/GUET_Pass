package com.example.guet_pass.ui.page

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.guet_pass.R
import com.example.guet_pass.ui.theme.*
import com.example.guet_pass.ui.widget.BackgroundLines
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GUETGatePage(viewModel: GateViewModel = viewModel()){
    val gateViewState = viewModel.gateViewState
    var showDialog by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss:SS"))) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val albumPermissionsState = rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
    PermissionsRequired(
        multiplePermissionsState = albumPermissionsState,
        permissionsNotGrantedContent = { /*TODO*/ },
        permissionsNotAvailableContent = { /*TODO*/ }) {
    }

    // 打开相册的处理
    val openAlbumLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = {
        imageUri = it
    })

    LaunchedEffect(key1 = null){
        gateViewState.timeFlow.collect{
            time = it
        }
    }

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()) {
        val (roundButton, passText, locationText, centerBox, blueBox, agreeBox, bottomBox, backgroud) = createRefs()
        Box(modifier = Modifier
            .width(92.dp)
            .height(28.dp)
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = Grey900)
            .constrainAs(roundButton) {
                end.linkTo(parent.end, margin = 16.dp)
                top.linkTo(parent.top, margin = 16.dp)
            }) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (centerLine,leftIcon,rightIcon) = createRefs()
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .alpha(0.3f)
                        .padding(vertical = 8.dp)
                        .background(color = Color.White)
                        .constrainAs(centerLine) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Box(modifier = Modifier.constrainAs(leftIcon){
                    start.linkTo(parent.start)
                    end.linkTo(centerLine.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }){
                    Row (modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                        Box(modifier = Modifier
                            .clip(CircleShape)
                            .background(color = Color.White)
                            .size(4.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier
                            .clip(CircleShape)
                            .background(color = Color.White)
                            .size(8.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier
                            .clip(CircleShape)
                            .background(color = Color.White)
                            .size(4.dp))
                    }
                }

                Box(modifier = Modifier.constrainAs(rightIcon) {
                    start.linkTo(centerLine.end)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }){
                    Button(modifier = Modifier.size(16.dp), shape = CircleShape ,border = BorderStroke(width = 3.dp, color = Color.White), contentPadding = PaddingValues(5.dp), onClick = {}){
                        Button(modifier = Modifier.size(8.dp), border = BorderStroke(width = 8.dp, color = Color.White), shape = CircleShape ,onClick = {}){}
                    }
                }
            }
        }

        Text(text = "桂电畅行证", color = Color.White, modifier = Modifier.constrainAs(passText){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        })

        Text(text = "花江检查点", color = Color.White, fontWeight = FontWeight.W700, fontSize = 20.sp, modifier = Modifier.constrainAs(locationText){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(passText.bottom, margin = 8.dp)
        })

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(720.dp)
            .constrainAs(backgroud) {
                bottom.linkTo(parent.bottom)
            }){
            BackgroundLines(modifier = Modifier.fillMaxSize())
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(230.dp)
            .background(color = Color.White)
            .constrainAs(centerBox) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(locationText.bottom, margin = 12.dp)
            }){

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (timeText, image, nameText) = createRefs()
                Text(text = time, color = Green, fontSize = 24.sp , fontWeight = FontWeight.W600 , modifier = Modifier.constrainAs(timeText){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 8.dp)
                })

                val imgLoader = ImageLoader.Builder(LocalContext.current)
                    .components {
                        if (SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()

                val model = if (imageUri?.encodedPath.isNullOrEmpty()) R.drawable.xnl else ImageRequest.Builder(context = LocalContext.current).data(imageUri).crossfade(true).build()

                val mPainter = rememberAsyncImagePainter(model = model, imgLoader, contentScale = ContentScale.FillBounds)
                Image(
                    painter = mPainter,
                    contentDescription = "",
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .clickable {
                            if (albumPermissionsState.allPermissionsGranted){
                                openAlbumLauncher.launch("image/*")
                            }else {
                                albumPermissionsState.launchMultiplePermissionRequest()
                            }
                        }
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(timeText.bottom, margin = 16.dp)
                        }
                )

                val name = if (gateViewState.username.isNotEmpty()) gateViewState.username.replaceRange(startIndex = 0, endIndex = gateViewState.username.length - 1, "*") else "*认"
                Text(text = "$name 可以通行", fontSize = 20.sp, fontWeight = FontWeight.W700,color = NameColor, modifier = Modifier
                    .clickable { showDialog = true }
                    .constrainAs(nameText) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(image.bottom, margin = 8.dp)
                    })
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(80.dp)
            .background(color = Blue)
            .constrainAs(blueBox) {
                top.linkTo(centerBox.bottom, margin = 12.dp)
            }){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "注册 \"金钟罩\" 、国家反诈中心", fontSize = 24.sp, color = Color.White)
                Text(text = "科技防诈骗让你远离诈骗侵害", fontSize = 18.sp, color = Color.White)
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(40.dp)
            .background(color = Color.White)
            .constrainAs(agreeBox) {
                top.linkTo(blueBox.bottom, margin = 12.dp)
            }){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Text(text = "已同意", modifier = Modifier.background(color = Color.Green), color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.W600)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "桂电花江通信证")
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(68.dp)
            .background(color = Color.White)
            .constrainAs(bottomBox) {
                bottom.linkTo(parent.bottom)
            }){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp)
            ){
                Button(modifier = Modifier
                    .width(150.dp)
                    .height(44.dp),onClick = { /*TODO*/ }, elevation = ButtonDefaults.elevation(0.dp), border = BorderStroke(
                    width = 1.dp,
                    color = Grey400
                )
                ) {
                    Text(text = "返回首页", color = Color.Black, fontSize = 16.sp)
                }

                Button(modifier = Modifier
                    .width(150.dp)
                    .height(48.dp),onClick = { /*TODO*/ }, elevation = ButtonDefaults.elevation(0.dp), border = BorderStroke(
                    width = 1.dp,
                    color = Grey400
                )
                ) {
                    Text(text = "出现记录", color = Color.Black, fontSize = 16.sp)
                }
            }
        }
    }


    if (showDialog){
        InputNameDialog(
            onDismissRequest = {showDialog = false},
            onDismiss = {showDialog = false},
            onConfirm = {showDialog = false}
        )
    }
}

@Composable
fun InputNameDialog(
    onDismissRequest: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    viewModel: GateViewModel = viewModel()
){
    var username by remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismissRequest) {
        Card (modifier = Modifier.clip(shape = RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "提示：", modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp), color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray,
                        textColor = Color.Black
                    ),
                    placeholder = {
                    Text(text = "请输入名字：", color = Color.Gray, fontSize = 16.sp)
                })

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { onDismiss() }, modifier = Modifier.weight(1f)) {
                        Text(text = "取消", color = Color.Black, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onConfirm()
                        viewModel.intentHandler(GateViewAction.SetUsername(username = username))
                    },
                        modifier = Modifier.weight(1f)) {
                        Text(text = "确认", color = Color.Black, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}