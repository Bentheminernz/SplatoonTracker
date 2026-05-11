package nz.benlawrence.splatoontracker.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object SplatoonAPIClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "SplatoonTracker/1.0 (Android; ben@benlawrence.nz)")
                .build()
            chain.proceed(request)
        }
        .build()

    val splatoonAPI: Splatoon3InkAPI = Retrofit.Builder()
        .baseUrl("https://splatoon3.ink/data/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Splatoon3InkAPI::class.java)

    private val coralHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "SplatoonTracker/1.0 (Android; ben@benlawrence.nz)")
                .build()
            chain.proceed(request)
        }
        .connectTimeout(45, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val coralAPI: CoralAPI = Retrofit.Builder()
//        .baseUrl("http://192.168.178.55:3000/")
        .baseUrl("http://10.0.2.2:3000/")
        .client(coralHttpClient)  // <-- use this client instead
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CoralAPI::class.java)
}