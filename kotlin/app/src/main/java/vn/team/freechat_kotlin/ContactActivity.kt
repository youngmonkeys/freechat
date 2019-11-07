package vn.team.freechat_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.tvd12.ezyfoxserver.client.entity.EzyArray
import vn.team.freechat_kotlin.adapter.ContactListAdapter
import vn.team.freechat_kotlin.model.ContactListItemModel
import vn.team.freechat_kotlin.request.GetContactsRequest
import java.util.*

/**
 * Created by tavandung12 on 10/1/18.
 */

class ContactActivity : AppActivity() {

    private var username: String? = null
    private var connectionController: Controller? = null
    private var contactController: Controller? = null

    private var loadingView : View? = null
    private var profileBox: View? = null
    private var profileUsernameView: TextView? = null
    private var profileStatusMessageView: TextView? = null
    private var contactListView: ListView? = null
    private var contactListAdapter: ContactListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        initComponents()
        initViews()
        setViewsData()
        setViewControllers()
        sendGetContactsRequest()
    }

    override fun onStart() {
        super.onStart()
        connectionController?.addView("show-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView?.visibility = View.VISIBLE
            }
        })
        connectionController?.addView("hide-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView?.visibility = View.GONE
            }
        })
        contactController?.addView("add-contacts", object : IView {
            override fun update(viewId: String, data: Any?) {
                addContacts(data as EzyArray)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        loadingView?.setVisibility(View.GONE)
        contactController?.removeView("add-contacts")
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
        profileUsernameView = profileBox?.findViewById(R.id.username)
        profileStatusMessageView = profileBox?.findViewById(R.id.statusMessage)
        contactListView = findViewById(R.id.contactList)
        contactListAdapter = ContactListAdapter(this)
        contactListView?.adapter = contactListAdapter
    }

    private fun setViewsData() {
        profileUsernameView?.text = username
        profileStatusMessageView?.text = "hello world"
    }

    private fun setViewControllers() {
        contactListView?.setOnItemClickListener {_, view: View, _, _ ->
            loadingView?.visibility = View.VISIBLE
            onContactItemClick(view)
        }
    }

    private fun addContacts(usernames: EzyArray) {
        val models = ArrayList<ContactListItemModel>()
        if(usernames.isEmpty) return
        for (i in 1..usernames.size())
            models.add(ContactListItemModel(usernames.get(i - 1, String::class.java), ""))
        contactListAdapter?.addItemModels(models)
        contactListAdapter?.notifyDataSetChanged()
    }

    private fun sendGetContactsRequest() {
        app?.send(GetContactsRequest(0, 50))
    }

    private fun onContactItemClick(view: View) {
        val usernameView = view.findViewById<TextView>(R.id.username)
        val username = usernameView.getText().toString()
        val intent = Intent(this, MessageActivity::class.java)
        intent.putExtra("targetContact", username)
        startActivity(intent)
    }
}
