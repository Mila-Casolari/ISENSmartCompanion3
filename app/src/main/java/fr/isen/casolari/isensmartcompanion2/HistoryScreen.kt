package fr.isen.casolari.isensmartcompanion2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(db: MessageDao) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    var messages by remember { mutableStateOf<List<MessageEntity>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    //val db = AppDatabase.getDatabase(context)
    val messageDao = db.messageDao()

    LaunchedEffect(Unit) {
        messageDao.getAllMessages().collect { messages = it }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Historique des questions", fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        LazyColumn {
            items(messages) { message ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("👤 ${message.question}", fontSize = 18.sp)
                        Text("🤖 ${message.response}", fontSize = 16.sp, color = Color.Gray)
                        Text("📅 ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date(message.timestamp))}", fontSize = 12.sp, color = Color.LightGray)
                    }
                    IconButton(
                        onClick = { coroutineScope.launch { db.messageDao().deleteMessage(message) } }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Supprimer")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            //onClick = { coroutineScope.launch { messageDao.deleteMessage(message) } },
            onClick = { coroutineScope.launch { messageDao.deleteMessage(message) } },

            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Effacer l'historique")
        }
    }
}
