package kr.co.korean.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class MarvelCharacterSerializer @Inject constructor(): Serializer<MarvelCharacter> {
    override val defaultValue: MarvelCharacter =
        MarvelCharacter.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MarvelCharacter =
        try {
            MarvelCharacter.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: MarvelCharacter, output: OutputStream) {
        t.writeTo(output)
    }

    val Context.marvelCharacterDataStore: DataStore<MarvelCharacter> by dataStore(
        fileName = "MarvelCharacter"::class.java.simpleName,
        serializer = MarvelCharacterSerializer
    )

}
