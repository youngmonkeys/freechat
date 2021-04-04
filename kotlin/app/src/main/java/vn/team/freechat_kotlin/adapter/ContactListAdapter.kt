package vn.team.freechat_kotlin.adapter


import android.annotation.SuppressLint
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

class ContactListAdapter(context: Context) : BaseAdapter() {

    private val contacts : MutableList<ContactListItemModel>
    private val contactByChannelId: MutableMap<Long, ContactListItemModel>
    private val inflater : LayoutInflater = LayoutInflater.from(context)

    init {
        this.contacts = ArrayList()
        this.contactByChannelId = HashMap()
        this.contacts.add(ContactListItemModel(0L, listOf("Bot")))
    }

    fun addItemModels(itemModels: Collection<ContactListItemModel>) {
        itemModels.forEach {
            if (!contactByChannelId.containsKey(it.channelId)) {
                contacts.add(it)
                contactByChannelId[it.channelId] = it
            }
        }
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?) : View {
        val view = inflater.inflate(R.layout.component_contact_list_item, parent, false)
        val usernameView = view.findViewById<TextView>(R.id.username)
        val lastMessageView = view.findViewById<TextView>(R.id.lastMessage)
        val model = contacts[position]
        usernameView.text = model.getUsersString()
        lastMessageView.text = model.lastMessage
        return view
    }

    override fun getCount(): Int {
        return contacts.size
    }

    override fun getItem(position: Int): ContactListItemModel {
        return contacts[position]
    }

    override fun getItemId(position: Int): Long {
        val item = contacts[position]
        return item.channelId
    }
}
