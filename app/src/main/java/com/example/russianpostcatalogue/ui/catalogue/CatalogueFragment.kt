package com.example.russianpostcatalogue.ui.catalogue

import android.content.Context
import android.icu.text.Edits
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.russianpostcatalogue.R
import com.example.russianpostcatalogue.domain.model.CatalogueItem
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.catalogue_item.view.*
import kotlinx.android.synthetic.main.fragment_catalogue.*

class CatalogueFragment : Fragment() {

    private lateinit var adapter: CatalogueItemsListAdapter
    private var goodsNames = ArrayList<String>()
    private var goodsPrices = ArrayList<String>()


    private  var catalogueItems = ArrayList<CatalogueItem>(9)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_catalogue, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goodsNames.addAll(resources.getStringArray(R.array.goods_names))
        goodsPrices.addAll(resources.getStringArray(R.array.goods_prices))
        fillInItems()
        initRecyclerView()
    }

    private fun fillInItems() {
        var i = 0
        while (i < 9) {
            catalogueItems.add(
                CatalogueItem(
                    goodsNames[i],
                    goodsPrices[i],
                    i+1
                ))
            i++
//            Log.e("item: ", catalogueItems[i].date + " | " + catalogueItems[i].author + " | " + catalogueItems[i].imgId)
        }
    }

    private fun initRecyclerView() {
        adapter = CatalogueItemsListAdapter(requireContext())
        fragCatalogue_rv.adapter = adapter
        adapter.updateItems(catalogueItems)
    }
}

class CatalogueItemsListAdapter(
    val context: Context,
    var catalogueItemsList: MutableList<CatalogueItem> = mutableListOf()
) : RecyclerView.Adapter<CatalogueItemsListAdapter.CatalogueItemViewHolder>() {

    fun updateItems(newItems: List<CatalogueItem>) {
        catalogueItemsList.apply {
            clear()
            addAll(newItems)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.catalogue_item, parent, false)

        return CatalogueItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = catalogueItemsList.size

    override fun onBindViewHolder(holder: CatalogueItemViewHolder, position: Int) {
        holder.bind(catalogueItemsList[position])
    }

    inner class CatalogueItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(catalogueItem: CatalogueItem) {
            containerView.catalogue_item_date.text = catalogueItem.date
            containerView.catalogue_item_author.text = catalogueItem.author
            when (catalogueItem.imgId) {
                1 -> { Picasso.with(context).load(R.drawable.img_1).into(containerView.catalogue_item_image) }
                2 -> { containerView.catalogue_item_image.setImageDrawable(context.getDrawable(R.drawable.img_2)) }
                3 -> { containerView.catalogue_item_image.setImageDrawable(context.getDrawable(R.drawable.img_3)) }
                4 -> { containerView.catalogue_item_image.setImageDrawable(context.getDrawable(R.drawable.img_4)) }
                5 -> { containerView.catalogue_item_image.setImageDrawable(context.getDrawable(R.drawable.img_5)) }
                6 -> { containerView.catalogue_item_image.setImageDrawable(context.getDrawable(R.drawable.img_6)) }
                7 -> { containerView.catalogue_item_image.setImageDrawable(context.getDrawable(R.drawable.img_7)) }
                8 -> { containerView.catalogue_item_image.setImageDrawable(context.getDrawable(R.drawable.img_8)) }
                9 -> { containerView.catalogue_item_image.setImageDrawable(context.getDrawable(R.drawable.img_9)) }
            }
        }


    }
}