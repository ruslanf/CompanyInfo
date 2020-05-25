package studio.bz_soft.companyinfo.data.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
class Companies(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "companyId") val companyId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "img") val img: String
)