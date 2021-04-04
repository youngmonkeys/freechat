package vn.team.freechat_kotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import vn.team.freechat_kotlin.R
import vn.team.freechat_kotlin.model.SearchContactModel
import java.util.*

/**
 * Created by tavandung12 on 10/3/18.
 */

const val item_layout_id = R.layout.component_search_contact_list_item

class SearchContactListAdapter(
    context: Context,
    private val items: MutableList<SearchContactModel> = ArrayList()
) : ArrayAdapter<SearchContactModel>(
    context,
    item_layout_id,
    items
) {
    fun getItemModel(position: Int): SearchContactModel {
        return this.items[position]
    }

    fun setItemModels(items: List<SearchContactModel>) {
        this.items.clear()
        this.items.addAll(items)
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(item_layout_id, parent, false)
        val usernameView: TextView = view.findViewById(R.id.username)
        val fullNameView: TextView = view.findViewById(R.id.fullName)
        val model = items[position]
        usernameView.text = model.username
        fullNameView.text = model.fullName
        return view
    }
}
