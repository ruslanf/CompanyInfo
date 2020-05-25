package studio.bz_soft.companyinfo.ui.companies.company

import android.content.Context
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import studio.bz_soft.companyinfo.R
import studio.bz_soft.companyinfo.data.http.Left
import studio.bz_soft.companyinfo.data.http.Right
import studio.bz_soft.companyinfo.data.models.CompanyModel
import studio.bz_soft.companyinfo.data.models.db.Company
import studio.bz_soft.companyinfo.root.showError
import studio.bz_soft.companyinfo.root.state.LoadingStateInterface
import kotlin.coroutines.CoroutineContext

class CompanyPresenter(
    private val appContext: Context
) : CoroutineScope, KoinComponent {

    var loadingStateInterface: LoadingStateInterface? = null

    private val logTag = CompanyPresenter::class.java.simpleName

    private val controller by inject<CompanyController>()

    private var job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    private var companyModel: List<CompanyModel>? = null
    private var ex: Exception? = null

    fun synchronize(companyId: Int) {
        loadingStateInterface?.loadingStarted()
        launch {
            coroutineScope {
                val request = async(SupervisorJob(job) + Dispatchers.IO) {
                    when (val r = controller.getCompanyInfo(companyId)) {
                        is Right -> { companyModel = r.value }
                        is Left -> { ex = r.value }
                    }
                }
                request.await()
            }
            ex?.let {
                showError(appContext, it, R.string.root_sync_message_error, logTag)
            } ?: run {
                coroutineScope {
                    companyModel?.let { company ->
                        var listCompany: List<Company> = mutableListOf()
                        val records = async(SupervisorJob(job) + Dispatchers.IO) {
                            company.forEach {
                                listCompany = controller.getRecords(it.id!!)
                            }
                        }
                        records.await()
                        if (listCompany.isEmpty()) {
                            val dbInsert = async(SupervisorJob(job) + Dispatchers.IO) {
                                company.forEach {
                                    controller.insertCompany(
                                        Company(
                                            companyId = it.id!!,
                                            name = it.name!!,
                                            image = it.img!!,
                                            desc = it.desc!!,
                                            latitude = it.latitude!!,
                                            longitude = it.longitude!!,
                                            url = it.web!!,
                                            phone = it.phoneNumber!!
                                        )
                                    )
                                }
                            }
                            dbInsert.await()
                        } else {
                            val dbUpdate = async(SupervisorJob(job) + Dispatchers.IO) {
                                listCompany.forEach { controller.insertCompany(it) }
                            }
                            dbUpdate.await()
                        }
                    }
                }
            }
            loadingStateInterface?.loadingFinished()
        }
    }
}