package kr.co.korean.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
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
}
