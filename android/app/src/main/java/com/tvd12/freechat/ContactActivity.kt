package com.tvd12.freechat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.tvd12.ezyfoxserver.client.entity.EzyArray
import com.tvd12.freechat.adapter.ContactListAdapter
import com.tvd12.freechat.extension.getStrings
import com.tvd12.freechat.extension.map
import com.tvd12.freechat.manager.StateManager
import com.tvd12.freechat.model.ContactListItemModel
import com.tvd12.freechat.socket.SocketRequests

/**
 * Created by tavandung12 on 10/1/18.
 */

class ContactActivity : AppActivity() {

    private lateinit var username: String
    private lateinit var connectionController: Controller
    private lateinit var contactController: Controller

    private lateinit var loadingView : View
    private lateinit var profileBox: View
    private lateinit var searchButton: ImageButton
    private lateinit var contactButton: ImageButton
    private lateinit var groupButton: ImageButton
    private lateinit var profileUsernameView: TextView
    private lateinit var profileStatusMessageView: TextView
    private lateinit var contactListView: ListView
    private lateinit var contactListAdapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        initComponents()
        initViews()
        setViewsData()
        setViewControllers()
    }

    override fun onStart() {
        super.onStart()
        connectionController.addView("show-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView.visibility = View.VISIBLE
            }
        })
        connectionController.addView("hide-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView.visibility = View.GONE
            }
        })
        contactController.addView("add-contacts", object : IView {
            override fun update(viewId: String, data: Any?) {
                addContacts(data as EzyArray)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        sendGetContactsRequest()
    }

    override fun onStop() {
        super.onStop()
        loadingView.visibility = View.GONE
        contactController.removeView("add-contacts")
    }

    private fun initComponents() {
        val mvc = Mvc.getInstance()
        username = intent.getStringExtra("username")
        contactController = mvc.getController("contact")
        connectionController = mvc.getController("connection")
    }

    private fun initViews() {
        loadingView = findViewById(R.id.loading)
        profileBox = findViewById(R.id.profileBox)
        searchButton = findViewById(R.id.search)
        groupButton = findViewById(R.id.group)
        contactButton = findViewById(R.id.contact)
        profileUsernameView = profileBox.findViewById(R.id.username)
        profileStatusMessageView = profileBox.findViewById(R.id.statusMessage)
        contactListView = findViewById(R.id.contactList)
        contactListAdapter = ContactListAdapter(this)
        contactListView.adapter = contactListAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun setViewsData() {
        profileUsernameView.text = username
        profileStatusMessageView.text = "hello world"
    }

    private fun setViewControllers() {
        searchButton.setOnClickListener { onSearchButtonClick() }
        groupButton.setOnClickListener { onSearchButtonClick() }
        contactButton.setOnClickListener { onSearchButtonClick() }
        contactListView.setOnItemClickListener {parent, _, position, _ ->
            loadingView.visibility = View.VISIBLE
            val item = parent.adapter.getItem(position) as ContactListItemModel
            onContactItemClick(item)
        }
    }

    private fun addContacts(contacts: EzyArray) {
        val models = contacts.map {
            ContactListItemModel(
                channelId = it.get("channelId", Long::class.java),
                users = it.getStrings("users")
            )
        }
        contactListAdapter.addItemModels(models)
        contactListAdapter.notifyDataSetChanged()
    }

    private fun sendGetContactsRequest() {
        SocketRequests.sendGetContacts()
    }

    private fun onSearchButtonClick() {
        val intent = Intent(this, ContactSearchActivity::class.java)
        startActivity(intent)
    }

    private fun onContactItemClick(item: ContactListItemModel) {
        StateManager.getInstance().currentChatContact = item
        val intent = Intent(this, MessageActivity::class.java)
        startActivity(intent)
    }
}
