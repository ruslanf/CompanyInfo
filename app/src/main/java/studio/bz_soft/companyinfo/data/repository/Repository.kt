package studio.bz_soft.companyinfo.data.repository

import androidx.lifecycle.LiveData
import org.koin.core.KoinComponent
import studio.bz_soft.companyinfo.data.http.ApiClientInterface
import studio.bz_soft.companyinfo.data.http.Either
import studio.bz_soft.companyinfo.data.http.safeRequest
import studio.bz_soft.companyinfo.data.models.*
import studio.bz_soft.companyinfo.data.models.db.Companies
import studio.bz_soft.companyinfo.data.models.db.Company

class Repository(
    private val database: DatabaseRepositoryInterface,
    private val storage: LocalStorageInterface,
    private val client: ApiClientInterface
) : RepositoryInterface, KoinComponent {

    override suspend fun getCompaniesList(): Either<Exception, List<CompaniesModel>> =
        safeRequest { client.getCompaniesList() }

    override suspend fun getCompanyInfo(id: Int): Either<Exception, List<CompanyModel>> =
        safeRequest { client.getCompanyInfo(id) }

    // DB functions
    override suspend fun insertCompanies(companies: Companies) {
        database.insertCompanies(companies)
    }

    override suspend fun deleteCompanies(companies: Companies) {
        database.deleteCompanies(companies)
    }

    override suspend fun updateCompanies(companies: Companies) {
        database.updateCompanies(companies)
    }

    override fun getAllFromCompanies(): LiveData<List<Companies>> = database.getAllFromCompanies()

    override suspend fun getCompaniesRecords(companyId: String): List<Companies> = database.getCompaniesRecords(companyId)

    override suspend fun insertCompany(company: Company) {
        database.insertCompany(company)
    }

    override suspend fun deleteCompany(company: Company) {
        database.deleteCompany(company)
    }

    override suspend fun updateCompany(company: Company) {
        database.updateCompany(company)
    }

    override fun getAllFromCompany(): LiveData<List<Company>> = database.getAllFromCompany()
    override suspend fun getCompanyRecords(companyId: String): List<Company> = database.getCompanyRecords(companyId)
    override fun getCompanyInfo(companyId: String): LiveData<Company> = database.getCompanyInfo(companyId)
}