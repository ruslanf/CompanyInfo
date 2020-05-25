package studio.bz_soft.companyinfo.ui.companies.company

import studio.bz_soft.companyinfo.data.http.Either
import studio.bz_soft.companyinfo.data.models.CompanyModel
import studio.bz_soft.companyinfo.data.models.db.Company
import studio.bz_soft.companyinfo.data.repository.RepositoryInterface

class CompanyController(
    private val repository: RepositoryInterface
) : CompanyInterface {
    override suspend fun getCompanyInfo(id: Int): Either<Exception, List<CompanyModel>> =
        repository.getCompanyInfo(id)

    override suspend fun insertCompany(company: Company) {
        repository.insertCompany(company)
    }

    override suspend fun getRecords(companyId: String): List<Company> =
        repository.getCompanyRecords(companyId)
}