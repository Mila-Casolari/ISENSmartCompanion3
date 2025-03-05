package fr.isen.casolari.isensmartcompanion2

import java.io.Serializable


// ✅ Modèle de données pour un événement
data class IsenEvent(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val category: String
): Serializable

// ✅ Liste factice d'événements
/*val fakeEventsList = listOf(
    IsenEvent(
        id = 1,
        title = "Soirée BDE",
        description = "Une soirée organisée par le BDE pour tous les étudiants.",
        date = "10 Mars 2025",
        location = "Bar ISEN",
        category = "Fête"
    ),
    IsenEvent(
        id = 2,
        title = "Gala ISEN",
        description = "Le gala annuel de l'ISEN avec remise de diplômes et soirée de prestige.",
        date = "25 Avril 2025",
        location = "Palais des Congrès",
        category = "Cérémonie"
    ),
    IsenEvent(
        id = 3,
        title = "Journée de Cohésion",
        description = "Une journée pour renforcer l'esprit d'équipe entre étudiants.",
        date = "5 Septembre 2025",
        location = "Campus ISEN",
        category = "Team Building"
    ),
    IsenEvent(
        id = 4,
        title = "Hackathon",
        description = "Un concours de programmation pour tester vos compétences en développement.",
        date = "12 Novembre 2025",
        location = "Salle Informatique",
        category = "Compétition"
    ),
    IsenEvent(
        id = 5,
        title = "Conférence IA",
        description = "Une conférence sur l'intelligence artificielle avec des experts du domaine.",
        date = "20 Décembre 2025",
        location = "Amphithéâtre 3",
        category = "Conférence"
    )
)*/
