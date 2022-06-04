package android.example.core.db

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase

object Database {
    const val DB_NAME = "main.db"

    inline fun<reified T: RoomDatabase> getInstance(
        application: Application
    ): T = Room.databaseBuilder(application, T::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .build()
}