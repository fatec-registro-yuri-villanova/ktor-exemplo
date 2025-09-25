package com.fatec.ktor.repository

import com.fatec.ktor.data.model.Feriado
import com.fatec.ktor.network.ApiCallHandler
import com.fatec.ktor.network.ApiResult
import com.fatec.ktor.network.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class FeriadoRepository {

    private val client: HttpClient = KtorClient.client

    suspend fun getFeriados(ano:String): ApiResult<List<Feriado>>{
        return ApiCallHandler.safeApiCall {
            client.get("https://brasilapi.com.br/api/feriados/v1/$ano")
        }
    }
}