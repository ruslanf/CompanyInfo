package studio.bz_soft.companyinfo.data.repository

import androidx.lifecycle.LiveData
import studio.bz_soft.companyinfo.data.db.DbClientInterface
import studio.bz_soft.companyinfo.data.models.db.*

class DatabaseRepository(
    private val dbClient: DbClientInterface
) : DatabaseRepositoryInterface {

    override suspend fun insertCompanies(companies: Companies) {
        dbClient.insertCompanies(companies)
    }

    override suspend fun deleteCompanies(companies: Companies) {
        dbClient.deleteCompanies(companies)
    }

    override suspend fun updateCompanies(companies: Companies) {
        dbClient.updateCompanies(companies)
    }

    override fun getAllFromCompanies(): LiveData<List<Companies>> = dbClient.getAllFromCompanies()
    override suspend fun getCompaniesRecords(companyId: String): List<Companies> = dbClient.getCompaniesRecords(companyId)

    override suspend fun insertCompany(company: Company) {
        dbClient.insertCompany(company)
    }

    override suspend fun deleteCompany(company: Company) {
        dbClient.deleteCompany(company)
    }

    override suspend fun updateCompany(company: Company) {
        dbClient.updateCompany(company)
    }

    override fun getAllFromCompany(): LiveData<List<Company>> = dbClient.getAllFromCompany()
    override suspend fun getCompanyRecords(companyId: String): List<Company> = dbClient.getCompanyRecords(companyId)
    override fun getCompanyInfo(companyId: String): LiveData<Company> = dbClient.getCompanyInfo(companyId)
}