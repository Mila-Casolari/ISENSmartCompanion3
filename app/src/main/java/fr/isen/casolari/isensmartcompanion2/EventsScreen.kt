package fr.isen.casolari.isensmartcompanion2


import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun EventsScreen() {
    val context = LocalContext.current
    var eventList by remember { mutableStateOf<List<IsenEvent>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        RetrofitInstance.api.getEvents().enqueue(object : Callback<List<IsenEvent>> {
            override fun onResponse(call: Call<List<IsenEvent>>, response: Response<List<IsenEvent>>) {
                Log.e("EventsScreen", "Réponse brute API: ${response.errorBody()?.string() ?: response.body().toString()}")

                if (response.isSuccessful) {
                    eventList = response.body() ?: emptyList()
                    isLoading = false
                    Log.d("EventsScreen", "Données reçues : ${eventList.size} événements")
                } else {
                    Log.e("EventsScreen", "Erreur API: ${response.code()}")
                    errorMessage = "Erreur API: ${response.code()}"
                    isLoading = false
                }
            }

            override fun onFailure(call: Call<List<IsenEvent>>, t: Throwable) {
                Log.e("EventsScreen", "Échec de l'appel API: ${t.message}")
                errorMessage = "Échec de l'appel API: ${t.message}"
                isLoading = false
            }
        })
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Text(text = "Événements ISEN", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))



            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(eventList) { event ->
                    EventItem(event, onClick = {
                        val intent = Intent(context, EventDetailActivity::class.java).apply {
                            putExtra("event", event) // ✅ Passage de l'événement
                        }
                        context.startActivity(intent)
                    })
                }
            }

    }
}

@Composable
fun EventItem(event: IsenEvent, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "📅 ${event.date}", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Gray)
            Text(text = "📍 ${event.location}", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "📌 ${event.category}", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Blue)
        }
    }
}

