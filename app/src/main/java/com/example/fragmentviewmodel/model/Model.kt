package com.example.fragmentviewmodel.model

import com.example.fragmentviewmodel.utils.Utils
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*

class Model {

    suspend fun HttpGetData(): List<Note> {
        var input: ByteArray
        var list: List<Note>
        println("start: ${Thread.currentThread().name}")
        // Connect to server and get the post
        try {
            val httpClient = HttpClient(Android)
            var resp =
                httpClient.get<HttpResponse>(Utils.ASBRecruitEndPoint) {
                    headers {
                        append("Accept", "application/json")
                    }
                }
            if (resp.status == HttpStatusCode.OK) {
                println("resp.status == HttpStatusCode.OK")
                //println(resp.body)
                input = resp.readBytes()
                println("input:")
                println(input)
                return parseData(input)

            } else {
                println("httpStatusCode:")
                println(resp.status)
            }
            httpClient.close()
        } catch (e: java.net.UnknownHostException) {
            println("exception of http")
            println(e)
        }
        println("async end: ${Thread.currentThread().name}")
        return listOf<Note>()
    }

    private fun parseData(notesByte: ByteArray): List<Note> {
        val gson = Gson()
        println("notesByte: ")
        println(notesByte)
        val notesStr = String(notesByte)
        var notes = gson.fromJson(notesStr, Array<Note>::class.java).asList()
        for (i in 0 until notes.size - 1) {
            var id = "id:" + notes[i].id.toString()
            var transactionDate = "transactionDate:" + notes[i].transactionDate.toString()
            var summary = "summary: " + notes[i].summary
            var debit = "debit:" + notes[i].debit
            var credit = "debit:" + notes[i].credit
        }
        return notes
    }

}

data class Note(
    val id: String,
    val transactionDate: String,
    val summary: String,
    val debit: Int,
    val credit: Int,
)