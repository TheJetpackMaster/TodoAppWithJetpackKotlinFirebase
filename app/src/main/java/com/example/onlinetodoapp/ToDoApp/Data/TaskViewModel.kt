package com.example.onlinetodoapp.ToDoApp.Data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TaskViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    private val auth = FirebaseAuth.getInstance()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    private val _currentTask = MutableStateFlow<Task?>(null)
    val currentTask: StateFlow<Task?> = _currentTask

    private val _completedTasks = MutableStateFlow<List<Task>>(emptyList())
    val completedTasks: StateFlow<List<Task>> get() = _completedTasks


    suspend fun addTask(title: String, description: String,color:String, context: Context) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val newTask = Task(title = title, description = description, color = color)

            try {
                val userTasksCollection =
                    firestore.collection("users").document(userId).collection("tasks")

                userTasksCollection.add(newTask).await()

                fetchTasks(userId)

            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // User is not logged in
            Toast.makeText(
                context,
                "User not logged in",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun fetchTasks(userId: String) {
        val userTasksCollection = firestore.collection("users").document(userId).collection("tasks")

        userTasksCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val newTasks = snapshot.documents.mapNotNull { document ->
                    val task = document.toObject(Task::class.java)

                    task?.id = document.id
                    task
                }
                _tasks.value = newTasks
            }
        }
    }

    fun deleteTask(taskId: String?, context: Context) {
        if (taskId.isNullOrBlank()) {
            Toast.makeText(context, "Empty task id", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val userTasksCollection =
                firestore.collection("users").document(userId).collection("tasks")
            val documentReference = userTasksCollection.document(taskId)

            documentReference.delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show()
                    fetchTasks(userId)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }


    suspend fun updateTask(taskId: String, title: String, description: String,color:String, context: Context) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val userTasksCollection =
                firestore.collection("users").document(userId).collection("tasks")
            val documentReference = userTasksCollection.document(taskId)

            try {
                documentReference.update(
                    mapOf(
                        "title" to title,
                        "description" to description,
                        "color" to color
                    )
                ).await()

                fetchTasks(userId)

                Toast.makeText(
                    context,
                    "Task updated successfully",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Failed to update task: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // User is not logged in
            Toast.makeText(
                context,
                "User not logged in",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun setCurrentTask(task: Task?) {
        _currentTask.value = task
    }

    fun toggleTaskCompletion(taskId: String?) {
        if (taskId.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {
            try {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val userId = currentUser.uid
                    val userTasksCollection =
                        firestore.collection("users").document(userId).collection("tasks")
                    val documentReference = userTasksCollection.document(taskId)

                    documentReference.get().addOnSuccessListener { documentSnapshot ->
                        val completed = documentSnapshot.getBoolean("completed") ?: false

                        viewModelScope.launch {
                            documentReference.update("completed", !completed).await()
                        }

                        fetchTasks(userId)
                    }.addOnFailureListener { e ->
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun fetchCompletedTasks(userId: String) {
        val userTasksCollection = firestore.collection("users").document(userId).collection("tasks")

        userTasksCollection.whereEqualTo("completed", true)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val newCompletedTasks = snapshot.documents.mapNotNull { document ->
                        val task = document.toObject(Task::class.java)

                        task?.id = document.id
                        task
                    }
                    _completedTasks.value = newCompletedTasks
                }
            }
    }

//    fun removeTaskFromUI(task: Task) {
//        // Remove the task from the UI list without deleting it from the database
//        _tasks.value = _tasks.value.filter { it.id != task.id }
//    }

}