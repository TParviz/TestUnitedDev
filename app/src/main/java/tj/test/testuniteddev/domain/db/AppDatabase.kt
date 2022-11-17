package tj.test.testuniteddev.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import tj.test.testuniteddev.domain.db.likes.MoviesInfoDao
import tj.test.testuniteddev.domain.db.likes.MoviesInfoEntity

@Database(
    entities = [MoviesInfoEntity::class],
    version = 2,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesInfoDao(): MoviesInfoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: tj.test.testuniteddev.domain.db.Database.build(context)
                .also { instance = it }
        }
    }
}
