package vn.team.freechat_kotlin

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.tvd12.ezyfoxserver.client.request.EzyRequest
import vn.team.freechat_kotlin.adapter.MessageListAdapter
import vn.team.freechat_kotlin.adapter.MessageListAdapters
import vn.team.freechat_kotlin.data.Message
import vn.team.freechat_kotlin.data.MessageReceived
import vn.team.freechat_kotlin.data.MessageSent
import vn.team.freechat_kotlin.request.SendSystemMessageRequest
import vn.team.freechat_kotlin.request.SendUserMessageRequest

/**
 * Created by tavandung12 on 10/5/18.
 */

class MessageActivity : AppActivity() {

    private var targetContact: String? = null
    private var messageController: Controller?  = null
    private var connectionController: Controller? = null

    private var loadingView: View? = null
    private var backButtonView: View? = null
    private var headerView: View? = null
    private var targetBoxView: View? = null
    private var targetNameView: TextView? = null
    private var targetLastMessageView: TextView? = null
    private var chatboxView: View? = null
    private var messageInputView: EditText? = null
    private var sendButtonView: ImageButton? = null
    private var messageListView: RecyclerView? = null
    private var messageListAdapter: MessageListAdapter? = null
    private var messageListLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        initComponents()
        initViews()
        setViewsData()
        setupViewControllers()
    }

    override fun onStart() {
        super.onStart();
        connectionController?.addView("show-loading",  object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView?.visibility = View.VISIBLE
            }
        })
        connectionController?.addView("hide-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView?.visibility = View.GONE
            }
        })
        messageController?.addView("add-message", object : IView {
            override fun update(viewId: String, data: Any?) {
                val message = data as MessageReceived
                addMessageItem(message);
                targetLastMessageView?.text = message.message
            }
        })
    }

    override fun onStop() {
        super.onStop();
        loadingView?.visibility = View.GONE
        messageController?.removeView("add-message")
    }

    private fun initComponents() {
        val mvc = Mvc.getInstance()
        targetContact = intent.getStringExtra("targetContact")
        messageController = mvc.getController("message");
        connectionController = mvc.getController("connection")
    }

    private fun initViews() {
        loadingView = findViewById(R.id.loading)
        headerView = findViewById(R.id.header)
        backButtonView = headerView?.findViewById(R.id.back)
        targetBoxView = headerView?.findViewById(R.id.targetBox)
        targetNameView = targetBoxView?.findViewById(R.id.targetName)
        targetLastMessageView = targetBoxView?.findViewById(R.id.lastMessage)
        messageListView = findViewById(R.id.messageList)
        chatboxView = findViewById(R.id.chatbox)
        messageInputView = chatboxView?.findViewById(R.id.messageInput)
        sendButtonView = chatboxView?.findViewById(R.id.sendButton)
        targetNameView?.setText(targetContact)
        targetLastMessageView?.setText("")

        val adapters = MessageListAdapters.getInstance()
        messageListAdapter = adapters.getAdapter(this, targetContact!!)
        messageListView?.setAdapter(messageListAdapter)
        messageListLayoutManager = newMessageListLayoutManager()
        messageListView?.setLayoutManager(messageListLayoutManager)
    }

    private fun setViewsData() {
        targetNameView?.setText(targetContact)
        targetLastMessageView?.setText("")
    }

    private fun setupViewControllers() {
        backButtonView?.setOnClickListener({ _ ->
                backToContactView()
        });
        sendButtonView?.setOnClickListener({ _ ->
            val messageText = messageInputView?.getText().toString()
            val request : EzyRequest
            if(targetContact.equals("System", true))
                request = SendSystemMessageRequest(messageText)
            else
                request = SendUserMessageRequest(targetContact!!, messageText)
            app!!.send(request);
            val message = MessageSent(messageText, targetContact!!)
            addMessageItem(message);
            messageInputView?.setText("")
        });
    }

    private fun addMessageItem(message: Message) {
        messageListAdapter?.addItem(message);
        messageListAdapter?.notifyDataSetChanged()
        messageListView?.smoothScrollToPosition(messageListAdapter?.getItemCount()!!)
    }

    private fun backToContactView() {
        onBackPressed();
    }

    private fun newMessageListLayoutManager() : LinearLayoutManager {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        return layoutManager
    }
}