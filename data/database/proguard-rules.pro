# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# missing rules
-dontwarn java.lang.invoke.StringConcatFactory