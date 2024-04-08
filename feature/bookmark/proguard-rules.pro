# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**
-keep class kr.co.korean.database.entity.MarvelCharacter { *; }

# missing_rules.txt
-dontwarn java.lang.invoke.StringConcatFactory