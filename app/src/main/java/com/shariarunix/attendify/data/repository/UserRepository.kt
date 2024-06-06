package com.shariarunix.attendify.data.repository


import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.shariarunix.attendify.data.SharedPref
import com.shariarunix.attendify.data.entity.UserEntity
import com.shariarunix.attendify.domain.model.UserModel
import com.shariarunix.attendify.utils.Resource
import com.shariarunix.attendify.utils.toUserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val mStore: FirebaseFirestore,
    private val sPref: SharedPref
) {

    companion object {
        const val USER_COLLECTION = "users"
    }

    private var _userData = MutableStateFlow(UserModel())
    val userData = _userData.asStateFlow()

    suspend fun saveUserDataToFireStore(userEntity: UserEntity): Resource<Unit> {
        return try {
            mStore
                .collection(USER_COLLECTION)
                .document(userEntity.userID)
                .set(userEntity)
                .await()
            sPref.rememberUser(true)
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    fun fetchUserData(userId: String) {
        try {
            mStore
                .collection(USER_COLLECTION)
                .document(userId)
                .addSnapshotListener { documentSnapshot, error ->
                    if (error != null) {
                        Log.d("BONK", "UserData: $error")
                    }
                    Log.d("BONK", "UserData: $documentSnapshot")

                    documentSnapshot?.let {
                        val userMap = it.data as? Map<String, Any>
                        val userEntity = UserEntity(
                            userID = userMap?.get("userID") as? String ?: "",
                            userName = userMap?.get("userName") as? String ?: "",
                            userEmail = userMap?.get("userEmail") as? String ?: "",
                            userPhone = userMap?.get("userPhone") as? String,
                            userProfileImage = userMap?.get("userProfileImage") as? String,
                            teacher = userMap?.get("teacher") as? Boolean ?: false,
                            createdAt = userMap?.get("createdAt") as? Timestamp,
                            updatedAt = userMap?.get("updatedAt") as? Timestamp
                        )
                        _userData.value = userEntity.toUserModel()
                    }
                }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun changeUserData(
        userName: String,
        userPhone: String,
        isTeacher: Boolean,
    ): Resource<Unit> {
        return try {
            if (userName != _userData.value.userName) {
                Log.d("BONK", "UserDataChanged: $userName")
                mStore
                    .collection(USER_COLLECTION)
                    .document(_userData.value.userID)
                    .set(
                        hashMapOf("userName" to userName),
                        SetOptions.merge()
                    ).await()
            }
            if (userPhone != _userData.value.userPhone) {
                Log.d("BONK", "UserDataChanged: $userPhone")
                mStore
                    .collection(USER_COLLECTION)
                    .document(_userData.value.userID)
                    .set(
                        hashMapOf("userPhone" to userPhone),
                        SetOptions.merge()
                    ).await()
            }

            if (isTeacher != _userData.value.teacher) {
                Log.d("BONK", "UserDataChanged: $isTeacher")
                mStore
                    .collection(USER_COLLECTION)
                    .document(_userData.value.userID)
                    .set(
                        hashMapOf("teacher" to isTeacher),
                        SetOptions.merge()
                    )
                    .await()
            }

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}