package com.linh.core.data.remote.di

import com.linh.core.data.remote.users.createGithubUsersApi
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataRemoteModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true // Unlike Retrofit and Gson, when not setting this to true, Ktor will throw an exception if response has more fields than the model
                    }
                )
            }

            expectSuccess = true // Throws error for non-2xx responses, so we can handle it in our app
        }
    }
    single {
        Ktorfit.Builder()
            .baseUrl(GITHUB_API_BASE_URL)
            .httpClient(get<HttpClient>())
            .build()
    }
    single {
        get<Ktorfit>().createGithubUsersApi()
    }
}

const val GITHUB_API_BASE_URL = "https://api.github.com/"