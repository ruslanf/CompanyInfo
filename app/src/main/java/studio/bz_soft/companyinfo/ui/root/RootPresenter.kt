package studio.bz_soft.companyinfo.ui.root

import android.content.Context
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import studio.bz_soft.companyinfo.data.models.CompaniesModel
import studio.bz_soft.companyinfo.data.models.db.Companies
import studio.bz_soft.companyinfo.root.showError
import studio.bz_soft.companyinfo.R
import studio.bz_soft.companyinfo.data.http.Left
import studio.bz_soft.companyinfo.data.http.Right
import studio.bz_soft.companyinfo.root.state.LoadingStateInterface
import kotlin.coroutines.CoroutineContext

class RootPresenter(
    private val appContext: Context
) : CoroutineScope, KoinComponent {

    var loadingStateInterface: LoadingStateInterface? = null

    private val logTag = RootPresenter::class.java.simpleName

    private val controller by inject<RootController>()

    private var job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    private var listCompaniesModel: List<CompaniesModel>? = null
    private var ex: Exception? = null

    fun synchronize() {
        loadingStateInterface?.loadingStarted()
        launch {
            coroutineScope {
                val request = async(SupervisorJob(job) + Dispatchers.IO) {
                    when (val r = controller.getCompaniesList()) {
                        is Right -> { listCompaniesModel = r.value }
                        is Left -> { ex = r.value }
                    }
                }
                request.await()
            }
            ex?.let {
                showError(appContext, it, R.string.root_sync_message_error, logTag)
            } ?: run {
                coroutineScope {
                    listCompaniesModel?.let { news ->
                        var listCompanies: List<Companies> = mutableListOf()
                        val records = async(SupervisorJob(job) + Dispatchers.IO) {
                            news.forEach { listCompanies = controller.getRecords(it.id!!) }
                        }
                        records.await()
                        val db = async(SupervisorJob(job) + Dispatchers.IO) {
                            if (listCompanies.isEmpty()) {
                                news.forEach {
                                    controller.insertCompanies(Companies(companyId = it.id!!, name = it.name!!, img = it.img!!))
                                }
                            } else {
                                listCompanies.forEach { controller.insertCompanies(it) }
                            }
                        }
                        db.await()
                    }
                }
            }
            loadingStateInterface?.loadingFinished()
        }
    }
}