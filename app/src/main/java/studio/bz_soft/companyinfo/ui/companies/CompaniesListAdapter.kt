package studio.bz_soft.companyinfo.ui.companies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cell_companies.view.*
import studio.bz_soft.companyinfo.R
import studio.bz_soft.companyinfo.data.models.db.Companies
import studio.bz_soft.companyinfo.root.Constants.API_MAIN_URL
import studio.bz_soft.companyinfo.root.GlideApp
import studio.bz_soft.companyinfo.root.delegated.AdapterDelegateInterface
import studio.bz_soft.companyinfo.root.delegated.BaseHolder

sealed class CompaniesListElement {
    data class CompaniesListItem(val companies: Companies): CompaniesListElement()
}

class CompaniesItemHolder(v: View, val onClick: (Companies) -> Unit): BaseHolder<CompaniesListElement>(v) {

    override fun bindModel(item: CompaniesListElement) {
        super.bindModel(item)
        when (item) {
            is CompaniesListElement.CompaniesListItem -> itemView.apply {
                val image = "$API_MAIN_URL${item.companies.img}?w=360"
                GlideApp.with(itemView)
                    .asBitmap()
                    .load(image)
                    .placeholder(R.drawable.ic_no_image_light)
                    .fallback(R.drawable.ic_no_image_light)
                    .circleCrop()
                    .into(itemView.companyIV)
                companiesTitleTV.text = item.companies.name
                setOnClickListener { onClick(item.companies) }
            }
        }
    }
}

class CompaniesListItemDelegate(private val onClick: (Companies) -> Unit):
    AdapterDelegateInterface<CompaniesListElement> {

    override fun isForViewType(items: List<CompaniesListElement>, position: Int): Boolean {
        return items[position] is CompaniesListElement.CompaniesListItem
    }

    override fun createViewHolder(parent: ViewGroup): BaseHolder<CompaniesListElement> {
        return CompaniesItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_companies, parent, false),
            onClick
        )
    }

    override fun bindViewHolder(items: List<CompaniesListElement>, position: Int, holder: BaseHolder<CompaniesListElement>) {
        holder.bindModel(items[position])
    }
}