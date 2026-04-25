package nz.benlawrence.splatoontracker.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SplatoonAPIClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "SplatoonTracker/1.0 (Android; ben@benlawrence.nz)")
                .build()
            chain.proceed(request)
        }
        .build()

    val splattonAPI: SplatoonAPI = Retrofit.Builder()
        .baseUrl("https://splatoon3.ink/data/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SplatoonAPI::class.java)
}