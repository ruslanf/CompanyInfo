package studio.bz_soft.companyinfo.ui.companies.company

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.fragment_company_info.view.*
import org.koin.android.ext.android.inject
import studio.bz_soft.companyinfo.R
import studio.bz_soft.companyinfo.data.models.db.Company
import studio.bz_soft.companyinfo.data.models.viewmodels.CompanyViewModel
import studio.bz_soft.companyinfo.root.Constants
import studio.bz_soft.companyinfo.root.Constants.KEY_COMPANY_ID
import studio.bz_soft.companyinfo.root.GlideApp
import studio.bz_soft.companyinfo.ui.root.RootActivity
import kotlin.properties.Delegates

class CompanyFragment : Fragment() {

    private val companyViewModel by inject<CompanyViewModel>()
    private val presenter by inject<CompanyPresenter>()

    private var companyId by Delegates.notNull<Int>()
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            companyId = getInt(KEY_COMPANY_ID, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_company_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            phoneTV.setOnClickListener { phoneClickListener(phoneTV.text.toString()) }

            webTV.apply {
                paintFlags = Paint.UNDERLINE_TEXT_FLAG
                setOnClickListener { webClickListener(webTV.text.toString()) }
            }
            mapTV.apply {
                paintFlags = Paint.UNDERLINE_TEXT_FLAG
                setOnClickListener { mapClickListener() }
            }

            companyViewModel.getCompanyInfo(companyId.toString()).observe(viewLifecycleOwner, Observer { company ->
                company?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                    render(this, it)
                } ?: run { loadCompanyInfo() }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as RootActivity).mainBottomNavigationMenu.visibility = View.GONE
    }

    private fun loadCompanyInfo() {
        presenter.synchronize(companyId)
    }

    private fun webClickListener(url: String) {
        val customTabs = CustomTabsIntent.Builder().build()
        customTabs.launchUrl(context, Uri.parse("http://$url"))
    }

    private fun phoneClickListener(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(intent)
    }

    private fun mapClickListener() {
        if (latitude != 0.0 && longitude != 0.0) {
            val geo = Uri.parse("geo:$latitude,$longitude")
            val intent = Intent(Intent.ACTION_VIEW, geo).apply {
                setPackage("com.google.android.apps.maps")
            }
            startActivity(intent)
        }
    }

    private fun render(v: View, company: Company) {
        v.apply {
            val image = "${Constants.API_MAIN_URL}${company.image}?w=360"
            GlideApp.with(this@apply)
                .asBitmap()
                .load(image)
                .placeholder(R.drawable.ic_no_image_light)
                .fallback(R.drawable.ic_no_image_light)
                .circleCrop()
                .into(companyIV)
            with(company) {
                titleTV.text = name
                phoneTV.text = phone
                webTV.text = url
                descriptionTV.text = desc
            }
        }
    }
}