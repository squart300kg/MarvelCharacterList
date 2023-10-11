package kr.co.korean.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["id", "characterId"], unique = true),
    ]
)
data class MarvelCharacter(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "characterId") val characterId: Int,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "urlCount") val urlCount:Int,
    @ColumnInfo(name = "comicCount") val comicCount:Int,
    @ColumnInfo(name = "storyCount") val storyCount:Int,
    @ColumnInfo(name = "eventCount") val eventCount:Int,
    @ColumnInfo(name = "seriesCount") val seriesCount:Int,
)