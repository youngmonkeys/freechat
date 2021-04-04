package vn.team.freechat_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

import com.tvd12.ezyfoxserver.client.entity.EzyArray

import vn.team.freechat_kotlin.adapter.SearchContactListAdapter
import vn.team.freechat_kotlin.extension.map
import vn.team.freechat_kotlin.model.SearchContactModel
import vn.team.freechat_kotlin.socket.SocketRequests

/**
 * Created by tavandung12 on 10/1/18.
 */

class ContactSearchActivity : AppCompatActivity() {

    private lateinit var contactController: Controller
    private lateinit var connectionController: Controller

    private lateinit var loadingView: View
    private lateinit var backButtonView: View
    private lateinit var searchButtonView: View
    private lateinit var keywordView: EditText
    private lateinit var contactListView: ListView
    private lateinit var contactListAdapter: SearchContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_search)
        initComponents()
        initViews()
        setViewControllers()
    }

    override fun onStart() {
        super.onStart()
        connectionController.addView("show-loading", object: IView {
            override fun update(viewId: String, data: Any?) {
                loadingView.visibility = View.VISIBLE
            }
        })
        connectionController.addView("hide-loading", object: IView {
            override fun update(viewId: String, data: Any?) {
                loadingView.visibility = View.GONE
            }
        })
        contactController.addView("search-contacts", object: IView {
            override fun update(viewId: String, data: Any?) {
                loadingView.visibility = View.GONE
                addContacts(data as EzyArray)
            }
        })

        contactController.addView("add-contacts", "search", object: IView {
            override fun update(viewId: String, data: Any?) {
                backToContactView()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        sendGetSuggestContacts()
    }

    override fun onStop() {
        super.onStop()
        loadingView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        contactController.removeView("search-contacts")
        contactController.removeView("add-contacts", "search")
    }

    private fun initComponents() {
        val mvc = Mvc.getInstance()
        contactController = mvc.getController("contact")
        connectionController = mvc.getController("connection")
    }

    private fun initViews() {
        loadingView = findViewById(R.id.loading)
        keywordView = findViewById(R.id.keyword)
        backButtonView = findViewById(R.id.back)
        searchButtonView = findViewById(R.id.search)
        contactListView = findViewById(R.id.contactList)
        contactListAdapter = SearchContactListAdapter(this)
        contactListView.adapter = contactListAdapter
    }

    private fun setViewControllers() {
        backButtonView.setOnClickListener {
            loadingView.visibility = View.GONE
            backToContactView()
        }
        searchButtonView.setOnClickListener {
            loadingView.visibility = View.VISIBLE
            sendSearchContacts()
        }
        keywordView.setOnEditorActionListener(object: TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sendSearchContacts()
                    return true
                }
                return false
            }
        })
        contactListView.setOnItemClickListener { parent, _, position, _ ->
            loadingView.visibility = View.VISIBLE
            val adapter = parent.adapter as SearchContactListAdapter
            onContactItemClick(adapter.getItemModel(position).username)
        }
    }

    private fun sendSearchContacts() {
        val keyword = keywordView.text.toString()
        if(keyword.isEmpty())
            SocketRequests.sendGetSuggestContacts()
        else
            SocketRequests.sendSearchContacts(keyword)
    }

    private fun sendGetSuggestContacts() {
        SocketRequests.sendGetSuggestContacts()
    }

    private fun addContacts(contacts: EzyArray) {
        val models = contacts.map {
            SearchContactModel(
                username = it.get<String>("username"),
                fullName = it.get<String>("fullName")
            )
        }
        contactListAdapter.setItemModels(models)
        contactListAdapter.notifyDataSetChanged()
    }

    private fun onContactItemClick(username: String) {
        SocketRequests.sendAddContact(username)
    }

    private fun backToContactView() {
        onBackPressed()
    }
}
