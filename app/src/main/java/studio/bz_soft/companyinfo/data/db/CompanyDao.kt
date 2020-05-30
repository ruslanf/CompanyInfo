package studio.bz_soft.companyinfo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import studio.bz_soft.companyinfo.data.models.db.Company

@Dao
interface CompanyDao : BaseDao<Company> {
    @Query("Select * from company")
    fun getAllFromCompany(): LiveData<List<Company>>

    @Query("Select * from company where companyId = :companyId")
    suspend fun getRecords(companyId: String): List<Company>

    @Query("Select * from company where companyId = :companyId")
    fun getCompanyInfo(companyId: String): LiveData<Company>
}