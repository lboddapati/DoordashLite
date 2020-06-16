package com.interview.doordashlite.ui.restaurantdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interview.doordashlite.R
import com.interview.doordashlite.models.MenuCondensed

class MenuListAdapter(
    private val menus: List<MenuCondensed>,
    private val presenter: RestaurantDetailContract.Presenter
): RecyclerView.Adapter<MenuListItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MenuListItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.menu_list_item, parent, false),
        presenter
    )

    override fun getItemCount() = menus.size

    override fun onBindViewHolder(holder: MenuListItemViewHolder, position: Int) {
        holder.bind(menus[position])
    }

}

class MenuListItemViewHolder(
    itemView: View,
    presenter: RestaurantDetailContract.Presenter
): RecyclerView.ViewHolder(itemView) {
    private val name = itemView.findViewById<TextView>(R.id.name)
    private val status = itemView.findViewById<TextView>(R.id.status)

    private lateinit var selectedMenu: MenuCondensed

    init {
        itemView.setOnClickListener { presenter.onMenuSelected(selectedMenu) }
    }

    fun bind(menu: MenuCondensed) {
        selectedMenu = menu
        name.text = menu.subtitle
        status.text = menu.status
    }
}