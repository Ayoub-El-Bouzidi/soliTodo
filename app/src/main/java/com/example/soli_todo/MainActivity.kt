package com.example.retrofitnoco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.soli_todo.Todo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

@Composable
fun TodoApp() {
    var titre by remember { mutableStateOf("Chargement...") }

    // ⚠️ On appelle Retrofit ici avec enqueue (asynchrone mais sans coroutine)
    LaunchedEffect(Unit) {
        val call = RetrofitClient.api.getTodo()
        call.enqueue(object : Callback<Todo> {
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                if (response.isSuccessful) {
                    val todo = response.body()
                    if (todo != null) {
                        titre = todo.title
                    } else {
                        titre = "Réponse vide"
                    }
                } else {
                    titre = "Erreur HTTP ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                titre = "Erreur réseau : ${t.message}"
            }
        })
    }

    // UI simple
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = titre, style = MaterialTheme.typography.bodyLarge)
        }
    }
}