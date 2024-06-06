package com.shariarunix.attendify.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.Gravity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.shariarunix.attendify.data.entity.UserEntity
import com.shariarunix.attendify.domain.model.UserModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Date
import kotlin.coroutines.resumeWithException

@SuppressLint("SimpleDateFormat")
fun getTimeInFormat(millis: Long, format: String): String {
    val date = Date(millis)
    val formatter = SimpleDateFormat(format)
    return formatter.format(date)
}

fun singleClick(onClick: () -> Unit): () -> Unit {
    var latest: Long = 0
    return {
        val now = System.currentTimeMillis()
        if (now - latest >= 1000) {
            onClick()
            latest = now
        }
    }
}

fun UserEntity.toUserModel(): UserModel {
    return UserModel(
        userID = this.userID,
        userName = this.userName,
        userEmail = this.userEmail,
        userPhone = this.userPhone,
        userProfileImage = this.userProfileImage,
        teacher = this.teacher,
        createdAt = this.createdAt?.toDateTime("dd MMM, yyyy"),
    )
}

@SuppressLint("SimpleDateFormat")
fun Timestamp.toDateTime(format: String = "dd/MM/yyyy HH:mm:ss"): String {
    return SimpleDateFormat(format).format(this.toDate())
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T = suspendCancellableCoroutine { cont ->
    addOnCompleteListener {
        if (it.exception != null) {
            cont.resumeWithException(it.exception!!)
        } else {
            cont.resume(it.result, null)
        }
    }
}

@Composable
fun SetDialogGravity(gravity: Int) {
    val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
    dialogWindowProvider.window.setGravity(gravity)
}