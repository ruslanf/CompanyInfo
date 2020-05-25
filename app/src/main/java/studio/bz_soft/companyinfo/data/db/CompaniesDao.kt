package studio.bz_soft.companyinfo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import studio.bz_soft.companyinfo.data.models.db.Companies

@Dao
interface CompaniesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(companies: Companies)

    @Update
    suspend fun update(companies: Companies)

    @Delete
    suspend fun delete(companies: Companies)

    @Query("Select * from companies")
    fun getAllFromCompanies(): LiveData<List<Companies>>

    @Query("Select * from companies where companyId = :companyId")
    suspend fun getRecords(companyId: String): List<Companies>
}