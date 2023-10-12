package kr.co.korean.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class AppStateSerializer @Inject constructor(): Serializer<AppState> {
    override val defaultValue: AppState =
        AppState.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppState =
        try {
            AppState.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: AppState, output: OutputStream) {
        t.writeTo(output)
    }
}