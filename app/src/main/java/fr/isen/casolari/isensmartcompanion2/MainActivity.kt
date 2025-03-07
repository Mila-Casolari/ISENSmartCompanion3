package fr.isen.casolari.isensmartcompanion2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.ai.client.generativeai.GenerativeModel
import fr.isen.casolari.isensmartcompanion2.ui.theme.ISENSmartCompanion2Theme
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanion2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AssistantScreen()
                }
                AppNavigator()
            }
        }
    }
}

data class Message(val text: String, val isUser: Boolean)

/*@Composable
fun AssistantScreen() {
    val context = LocalContext.current

    val apiKey = BuildConfig.GEMINI_API_KEY
    val model = GenerativeModel(apiKey, "gemini-1.5-flash")

    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<Message>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF8FC)), // Couleur de fond légèrement grisâtre
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            //  Logo
            Image(
                painter = painterResource(id = R.drawable.logoisen), // Assure-toi que ton logo est dans res/drawable
                contentDescription = "Logo ISEN",
                modifier = Modifier
                    .size(100.dp) // Taille du logo
            )

            Spacer(modifier = Modifier.height(16.dp))
            // Titre
            Text(
                text = "ISEN",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB71C1C), // Rouge foncé
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Sous-titre
            Text(
                text = "Smart Companion",
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                items(messages.reversed()){ message ->
                    MessageBubble(message)
                }
            }

            // Champ de saisie en bas de l'écran
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFFEDEDED), shape = RoundedCornerShape(50.dp)) // Fond gris clair arrondi
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    placeholder = { Text("Posez votre question...") },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Bouton circulaire avec icône
                IconButton(
                    onClick = {
                        if(userInput.text.isNotBlank()) {


                            Toast.makeText(context, "Question Submitted", Toast.LENGTH_SHORT).show()

                            messages = messages + Message(userInput.text, isUser = true)

                            val aiResponse = generateAIResponse(userInput.text)
                            messages = messages + Message(aiResponse, isUser = false)

                            userInput = TextFieldValue("")
                        }
                        
                        // Action à effectuer quand on clique sur le bouton
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB71C1C)) // Rouge foncé
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_media_next), // Icône de flèche
                        contentDescription = "Envoyer",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

fun generateAIResponse(input: String): String {
    return when {
        input.contains("bonjour", ignoreCase = true) -> "Bonjour ! Comment puis-je vous aider ?"
        input.contains("temps", ignoreCase = true) -> "Je ne peux pas voir la météo, mais vous pouvez consulter une application météo."
        input.contains("nom", ignoreCase = true) -> "Je suis ISEN Smart Companion !"
        input.contains("aide", ignoreCase = true) -> "Bien sûr ! Dites-moi en quoi je peux vous aider."
        input.contains("merci", ignoreCase = true) -> "Avec plaisir ! 😊"
        else -> "Je suis une IA factice, mais je fais de mon mieux pour vous répondre !"
    }
}*/

@Composable
fun AssistantScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<Message>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF8FC)), // Couleur de fond légèrement grisâtre
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            //  Logo
            Image(
                painter = painterResource(id = R.drawable.logoisen), // Assure-toi que ton logo est dans res/drawable
                contentDescription = "Logo ISEN",
                modifier = Modifier.size(100.dp) // Taille du logo
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Titre
            Text(
                text = "ISEN",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB71C1C), // Rouge foncé
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Sous-titre
            Text(
                text = "Smart Companion",
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            // Affichage des messages
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                items(messages.reversed()) { message ->
                    MessageBubble(message)
                }
            }

            // Champ de saisie en bas de l'écran
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFFEDEDED), shape = RoundedCornerShape(50.dp)) // Fond gris clair arrondi
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    placeholder = { Text("Posez votre question...") },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Bouton circulaire avec icône
                IconButton(
                    onClick = {
                        if (userInput.text.isNotBlank()) {
                            val question = userInput.text
                            userInput = TextFieldValue("") // Réinitialiser le champ après envoi
                            messages = messages + Message(question, isUser = true)

                            coroutineScope.launch {
                                try {
                                    val aiResponse = GeminiAI.analyzeText(question)
                                    messages = messages + Message(aiResponse, isUser = false)
                                } catch (e: Exception) {
                                    messages = messages + Message("Erreur : ${e.message}", isUser = false)
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB71C1C)) // Rouge foncé
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_media_next), // Icône de flèche
                        contentDescription = "Envoyer",
                        tint = Color.White
                    )
                }
            }
        }
    }
}


/*@Composable
fun AssistantScreen() {
    val context = LocalContext.current
    val apiKey = BuildConfig.GEMINI_API_KEY // ✅ Récupération sécurisée de la clé API
    Log.d("AssistantScreen", "Clé API Gemini récupérée: $apiKey")
    val model = GenerativeModel(apiKey, "gemini-1.5-flash") // ✅ Utilisation du modèle Gemini 1.5 Flash
    val coroutineScope = rememberCoroutineScope()

    var userInput by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // ✅ Affichage du titre + logo
        Text(text = "Assistant IA Gemini", fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Liste des messages sous forme de conversation
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // ✅ Permet de prendre tout l’espace dispo
        ) {
            items(messages) { message ->
                MessageBubble(message)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Champ de texte pour écrire une question
        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Posez votre question...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Bouton pour envoyer la question
        Button(
            onClick = {
                if (userInput.isNotBlank()) {
                    val question = userInput
                    userInput = "" // ✅ Efface l’input après envoi
                    messages = messages + (question to "⏳ Analyse en cours...")

                    coroutineScope.launch {
                        try {
                            Log.d("AssistantScreen", "Envoi de la requête à Gemini avec la question: $question")

                            val aiResponse = GeminiAI.analyzeText(question)

                            Log.d("AssistantScreen", "✅ Réponse IA reçue: $aiResponse")

                            messages = messages.dropLast(1) + (question to aiResponse)

                        } catch (e: Exception) {
                            Log.e("AssistantScreen", "Erreur: ${e.message}")
                                messages = messages.dropLast(1) + (question to "Erreur : ${e.message}")
                        }
                    }


                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Envoyer")
        }
    }
}*/

/*@Composable
fun MessageBubble(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = message.text,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .background(
                    if (message.isUser) Color(0xFFB71C1C) else Color(0xFF757575),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        )
    }
}*/

@Composable
fun MessageBubble(message: Message) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = message.text,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .background(
                    if (message.isUser) Color(0xFFB71C1C) else Color(0xFF757575),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewAssistantScreen() {
    AssistantScreen()
}


@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { AssistantScreen() }
            composable("events") { EventsScreen() }
            //composable("history") { HistoryScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color(0xFFFAF8FC)
    ) {
        val items = listOf(
            BottomNavItem("Home", "home"),
            BottomNavItem("Events", "events"),
            BottomNavItem("History", "history")
        )


        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { /* Icône si besoin */ },
                label = { Text(item.label) }
            )
        }
    }
}

data class BottomNavItem(val label: String, val route: String)
