package studio.bz_soft.companyinfo.data.db

import android.app.Application
import androidx.lifecycle.LiveData
import studio.bz_soft.companyinfo.data.models.db.Companies
import studio.bz_soft.companyinfo.data.models.db.Company

class DbClient(application: Application) : DbClientInterface {

    private val db by lazy { RoomDB.getDataBase(application) }
    private val companiesDao by lazy { db.companiesDao() }
    private val companyDao by lazy { db.companyDao() }

    override suspend fun insertCompanies(companies: Companies) {
        companiesDao.insert(companies)
    }

    override suspend fun deleteCompanies(companies: Companies) {
        companiesDao.delete(companies)
    }

    override suspend fun updateCompanies(companies: Companies) {
        companiesDao.update(companies)
    }

    override fun getAllFromCompanies(): LiveData<List<Companies>> = companiesDao.getAllFromCompanies()
    override suspend fun getCompaniesRecords(companyId: String): List<Companies> = companiesDao.getRecords(companyId)

    override suspend fun insertCompany(company: Company) {
        companyDao.insert(company)
    }

    override suspend fun deleteCompany(company: Company) {
        companyDao.delete(company)
    }

    override suspend fun updateCompany(company: Company) {
        companyDao.update(company)
    }

    override fun getAllFromCompany(): LiveData<List<Company>> = companyDao.getAllFromCompany()
    override suspend fun getCompanyRecords(companyId: String): List<Company> = companyDao.getRecords(companyId)
    override fun getCompanyInfo(companyId: String): LiveData<Company> = companyDao.getCompanyInfo(companyId)
}