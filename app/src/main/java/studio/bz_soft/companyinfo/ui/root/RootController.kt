package studio.bz_soft.companyinfo.ui.root

import studio.bz_soft.companyinfo.data.http.Either
import studio.bz_soft.companyinfo.data.models.CompaniesModel
import studio.bz_soft.companyinfo.data.models.db.Companies
import studio.bz_soft.companyinfo.data.repository.RepositoryInterface

class RootController(
    private val repository: RepositoryInterface
) : RootInterface {
    override suspend fun getCompaniesList(): Either<Exception, List<CompaniesModel>> =
        repository.getCompaniesList()

    override suspend fun insertCompanies(companies: Companies) {
        repository.insertCompanies(companies)
    }

    override suspend fun getRecords(companyId: String): List<Companies> = repository.getCompaniesRecords(companyId)
}