package com.shariarunix.attendify.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.shariarunix.attendify.data.SharedPref
import com.shariarunix.attendify.data.entity.UserEntity
import com.shariarunix.attendify.utils.Resource
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val userRepo: UserRepository,
    private val mAuth: FirebaseAuth,
    private val sPref: SharedPref
) {
    suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = mAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.uid?.let {
                sPref.saveUserID(it)
                sPref.dataLoaded(false)
            } // Save user id to shared preferences
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = mAuth.createUserWithEmailAndPassword(email, password).await()
                lateinit var userResult: Resource<Unit>
                result.user?.uid?.let {
                    sPref.saveUserID(it)
                    sPref.dataLoaded(false)
                    userResult = userRepo.saveUserDataToFireStore(
                        UserEntity(
                            userID = it,
                            userName = name,
                            userEmail = email,
                            createdAt = Timestamp.now(),
                            updatedAt = Timestamp.now()
                        )
                    )
                }
                if (userResult is Resource.Success) {
                    Resource.Success(result.user!!)
                } else {
                    throw Exception("User Data is not saved")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure(e)
            }
        }
    }

}