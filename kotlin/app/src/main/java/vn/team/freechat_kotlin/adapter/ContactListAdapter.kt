package vn.team.freechat_kotlin.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import vn.team.freechat_kotlin.R
import vn.team.freechat_kotlin.model.ContactListItemModel

/**
 * Created by tavandung12 on 10/3/18.
 */

class ContactListAdapter : BaseAdapter {

    private val inflater : LayoutInflater
    private val items : MutableList<ContactListItemModel>

    constructor(context: Context) : super() {
        this.inflater = LayoutInflater.from(context);
        this.items = ArrayList<ContactListItemModel>()
        this.items.add(ContactListItemModel("System", ""))
    }

    fun addItemModel(model: ContactListItemModel) {
        items.add(model);
    }

    fun addItemModels(itemModels: Collection<ContactListItemModel> ) {
        items.addAll(itemModels);
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?) : View {
        val view = inflater.inflate(R.layout.component_contact_list_item, parent, false)
        val usernameView = view.findViewById<TextView>(R.id.username)
        val lastMessageView = view.findViewById<TextView>(R.id.lastMessage)
        val model = items.get(position)
        usernameView.setText(model.getUsername())
        lastMessageView.setText(model.getLastMessage())
        return view
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): ContactListItemModel {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        val item = items[position]
        return item.getId()
    }
}
