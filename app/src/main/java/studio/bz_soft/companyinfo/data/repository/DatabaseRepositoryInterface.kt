package studio.bz_soft.companyinfo.data.repository

import androidx.lifecycle.LiveData
import studio.bz_soft.companyinfo.data.models.db.Companies
import studio.bz_soft.companyinfo.data.models.db.Company

interface DatabaseRepositoryInterface {
    suspend fun insertCompanies(companies: Companies)
    suspend fun deleteCompanies(companies: Companies)
    suspend fun updateCompanies(companies: Companies)
    fun getAllFromCompanies(): LiveData<List<Companies>>
    suspend fun getCompaniesRecords(companyId: String): List<Companies>

    suspend fun insertCompany(company: Company)
    suspend fun deleteCompany(company: Company)
    suspend fun updateCompany(company: Company)
    fun getAllFromCompany(): LiveData<List<Company>>
    suspend fun getCompanyRecords(companyId: String): List<Company>
    fun getCompanyInfo(companyId: String): LiveData<Company>
}