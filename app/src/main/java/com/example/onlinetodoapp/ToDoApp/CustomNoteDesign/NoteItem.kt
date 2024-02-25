package com.example.onlinetodoapp.ToDoApp.CustomNoteDesign

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.onlinetodoapp.ui.theme.RedOrange

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit,
    onEditClick:()->Unit,
    onCompleteClick:()->Unit,
    checkColor:Color,
    editColor:Color,
    title: String,
    description: String,
    deleteIcon: Int,
    checkIcon: Int,
    editIcon: Int,
    editButtonEnabled:Boolean,
    bgColor: Color
) {
    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(bgColor.toArgb()),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )

                drawRoundRect(
                    color = Color(
                        (ColorUtils.blendARGB(bgColor.toArgb(), 0x000000, 0.3f))
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),

                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .padding(end = 75.dp)
        ) {
            Text(
                text = title, style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description, style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            IconButton(
                modifier = Modifier.size(37.dp),
                onClick = onEditClick,
                enabled = editButtonEnabled
            )
            {
                Icon(
                    painter = painterResource(id = editIcon),
                    contentDescription = null,
                    tint = editColor
                )
            }
            IconButton(
                modifier = Modifier.size(37.dp),
                onClick = onDeleteClick
            )
            {
                Icon(
                    painter = painterResource(id = deleteIcon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            IconButton(
                modifier = Modifier.size(37.dp),
                onClick = onCompleteClick
            )
            {
                Icon(
                    painter = painterResource(id = checkIcon),
                    contentDescription = null,
                    tint = checkColor
                )
            }
        }
    }
}


@Composable
fun Completed(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    title: String,
    description: String,
    bgColor: Color
) {
    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(bgColor.toArgb()),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )

                drawRoundRect(
                    color = Color(
                        (ColorUtils.blendARGB(bgColor.toArgb(), 0x000000, 0.3f))
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),

                    )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .padding(end = 60.dp)
        ) {
            Text(
                text = title, style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description, style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


//                    Box(
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Card(
//                            shape = RoundedCornerShape(16.dp),
//                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
//                        ) {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clip(RoundedCornerShape(16.dp))
//                                    .background(
//                                        if (task.color == "babyBlue") BabyBlue
//                                        else if (task.color == "lightBlue") LightBlue
//                                        else if (task.color == "redOrange") RedOrange
//                                        else Default
//                                    )
//                                    .padding(16.dp),
//                                horizontalArrangement = Arrangement.Start,
//                                verticalAlignment = Alignment.Top
//                            ) {
//                                Column(
//                                    modifier = Modifier.width(178.dp),
//                                    verticalArrangement = Arrangement.SpaceEvenly
//                                ) {
//                                    Text(
//                                        text = task.title,
//                                        color = PurpleDark,
//                                        fontWeight = FontWeight.SemiBold,
//                                        fontSize = 16.sp,
//                                        lineHeight = 20.sp
//                                    )
//                                    Spacer(modifier = Modifier.height(4.dp))
//                                    Text(
//                                        text = task.description,
//                                        fontSize = 14.sp,
//                                        color = Color.Black,
//                                        textAlign = TextAlign.Justify
//                                    )
//
//                                }
//
//                                Spacer(modifier = Modifier.weight(1f))
//                                IconButton(
//                                    enabled = !task.completed,
//                                    modifier = Modifier.size(40.dp),
//                                    onClick = {
//                                        navController.navigate("${Routes.EditTask.route}${task.id}")
//                                        taskViewModel.setCurrentTask(task)
//                                    })
//                                {
//                                    Icon(
//                                        painter = painterResource(id = R.drawable.vector_10),
//                                        contentDescription = null,
//                                        tint = if (task.completed) Color.Gray.copy(.5f) else Color.Unspecified
//                                    )
//                                }
//                                IconButton(
//                                    modifier = Modifier.size(40.dp),
//                                    onClick = {
//                                        taskViewModel.deleteTask(task.id, context)
//                                    })
//                                {
//                                    Icon(
//                                        painter = painterResource(id = R.drawable.vector__5_),
//                                        contentDescription = null,
//                                        tint = Color.Unspecified
//                                    )
//                                }
//                                IconButton(
//                                    modifier = Modifier.size(40.dp),
//                                    onClick = {
//                                        taskViewModel.toggleTaskCompletion(task.id)
//                                        if (!task.completed) {
//                                            Toast.makeText(
//                                                context,
//                                                "Task completed",
//                                                Toast.LENGTH_SHORT
//                                            )
//                                                .show()
//                                        } else {
//                                            Toast.makeText(
//                                                context,
//                                                "Task completion unchecked,",
//                                                Toast.LENGTH_SHORT
//                                            )
//                                                .show()
//                                        }
//                                    })
//                                {
//                                    Icon(
//                                        painter = painterResource(id = R.drawable.vector__6_),
//                                        contentDescription = null,
//                                        tint = if (task.completed) Color.Red else Color.Unspecified
//                                    )
//                                }
//                            }
//
//                        }
//                    }