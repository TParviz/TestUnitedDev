package tj.test.testuniteddev.domain.db.likes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "titleId") var titleId: String,

    @ColumnInfo(name = "isLiked") var isLiked: Boolean,

    @ColumnInfo(name = "commentCount") var commentCount: Int,
)