package fr.isen.casolari.isensmartcompanion2


import retrofit2.Call
import retrofit2.http.GET

interface EventApiService {
    @GET("events.json") // ✅ Remplace par l'endpoint correct de ton API
    fun getEvents(): Call<List<IsenEvent>> // ✅ Récupère une liste d'événements
}
