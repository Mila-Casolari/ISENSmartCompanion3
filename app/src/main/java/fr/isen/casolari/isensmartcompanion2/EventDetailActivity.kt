package fr.isen.casolari.isensmartcompanion2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val event = intent.getSerializableExtra("event") as? IsenEvent
        if (event != null) {
            Log.d("EventDetailActivity", "Événement reçu : ${event.title}") // ✅ Vérification de la réception
        } else {
            Log.e("EventDetailActivity", "Erreur : Aucun événement reçu !") // ❌ Si l'objet n'est pas reçu
        }
        setContent {
            EventDetailScreen(event, onBackPressed = { finish()})
        }
    }
}

@Composable
fun EventDetailScreen(event: IsenEvent?, onBackPressed: () -> Unit) {
    if (event == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Aucun détail disponible", fontSize = 20.sp)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = event.title,
                fontSize = 26.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "📅 Date: ${event.date}", fontSize = 18.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "📍 Lieu: ${event.location}", fontSize = 18.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "📌 Catégorie: ${event.category}",
                        fontSize = 18.sp,
                        color = Color.Blue
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Description", fontSize = 22.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = event.description, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(20.dp))

            // ✅ Bouton Retour pour revenir à la liste des événements
            Button(onClick = { onBackPressed() }) {
                Text(text = "Retour aux événements")
            }
        }
    }
}