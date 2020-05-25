package studio.bz_soft.companyinfo.ui.companies

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.fragment_companies.view.*
import org.koin.android.ext.android.inject
import studio.bz_soft.companyinfo.R
import studio.bz_soft.companyinfo.data.models.db.Companies
import studio.bz_soft.companyinfo.data.models.viewmodels.CompaniesViewModel
import studio.bz_soft.companyinfo.root.Constants.KEY_COMPANY_ID
import studio.bz_soft.companyinfo.root.delegated.DelegateAdapter
import studio.bz_soft.companyinfo.root.scrollToPosition
import studio.bz_soft.companyinfo.ui.root.RootActivity
import studio.bz_soft.companyinfo.ui.root.RootPresenter

class CompaniesFragment : Fragment() {

    private val companiesViewModel by inject<CompaniesViewModel>()
    private val presenter by inject<RootPresenter>()

    private val companiesListAdapter = DelegateAdapter(CompaniesListItemDelegate { companies ->
        val bundle = bundleOf(
            KEY_COMPANY_ID to companies.id
        )
        view?.findNavController()?.navigate(R.id.companyFragment, bundle)
    })

    private var recyclerViewState: Parcelable? = null
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_companies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            companiesRV.apply {
                adapter = companiesListAdapter
                layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
                recyclerViewState?.apply {
                    layoutManager?.onRestoreInstanceState(recyclerViewState)
                    scrollToPosition(companiesRV, position)
                }
            }
            swipeRefresh.setOnRefreshListener { refreshListener(this) }

            companiesViewModel.getCompanies().observe(viewLifecycleOwner, Observer {
                renderCompaniesList(it)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as RootActivity).mainBottomNavigationMenu.visibility = View.VISIBLE
    }

    private fun renderCompaniesList(companies: List<Companies>) {
        companiesListAdapter.apply {
            items.clear()
            items.addAll(
                companies.map { CompaniesListElement.CompaniesListItem(it) }
            )
            notifyDataSetChanged()
        }
    }

    private fun refreshListener(v: View) {
        v.apply {
            presenter.synchronize()
            swipeRefresh.isRefreshing = false
        }
    }
}