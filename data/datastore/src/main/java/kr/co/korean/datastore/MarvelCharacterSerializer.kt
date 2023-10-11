package kr.co.korean.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class MarvelCharacterSerializer @Inject constructor(): Serializer<MarvelCharacters> {
    override val defaultValue: MarvelCharacters =
        MarvelCharacters.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MarvelCharacters =
        try {
            MarvelCharacters.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: MarvelCharacters, output: OutputStream) {
        t.writeTo(output)
    }
}
