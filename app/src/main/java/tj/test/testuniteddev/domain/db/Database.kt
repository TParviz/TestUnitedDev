package tj.test.testuniteddev.domain.db

import android.content.Context
import androidx.room.Room

object Database {
    fun build(context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).fallbackToDestructiveMigration().build()
}
