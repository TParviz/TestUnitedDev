package tj.test.testuniteddev.domain.db.likes

import androidx.room.*

@Dao
interface MoviesInfoDao {
    @Query("SELECT * FROM moviesinfoentity")
    suspend fun getAll(): List<MoviesInfoEntity>

    @Query("SELECT * FROM moviesinfoentity WHERE titleId LIKE :titleId")
    suspend fun findByTitle(titleId: String): MoviesInfoEntity

    @Insert
    suspend fun insertAll(info: MoviesInfoEntity)

    @Delete
    fun delete(info: MoviesInfoEntity)

    @Update
    suspend fun updateInfo(info: MoviesInfoEntity)
}