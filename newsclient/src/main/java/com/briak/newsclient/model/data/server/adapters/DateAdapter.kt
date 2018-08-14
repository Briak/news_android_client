package com.briak.newsclient.model.data.server.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter: JsonAdapter<Date>() {
    override fun fromJson(reader: JsonReader?): Date =
         SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale("us"))
                 .parse(reader?.nextString())

    override fun toJson(writer: JsonWriter?, value: Date?) {

    }
}