package studio.bz_soft.companyinfo.data.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
class Company(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "companyId") val companyId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "img") val image: String,
    @ColumnInfo(name = "description") val desc: String,
    @ColumnInfo(name = "lat") val latitude: Double,
    @ColumnInfo(name = "lon") val longitude: Double,
    @ColumnInfo(name = "www") val url: String,
    @ColumnInfo(name = "phone") val phone: String
)