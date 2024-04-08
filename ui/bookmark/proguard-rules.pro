# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.E  ntity class *
-dontwarn androidx.room.paging.**
-keep class kr.co.architecture.database.entity.MarvelCharacter { *; }

# missing_rules.txt
-dontwarn java.lang.invoke.StringConcatFactory