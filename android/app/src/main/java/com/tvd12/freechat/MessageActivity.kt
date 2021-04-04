package com.tvd12.freechat

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.tvd12.freechat.adapter.MessageListAdapter
import com.tvd12.freechat.adapter.MessageListAdapters
import com.tvd12.freechat.data.Message
import com.tvd12.freechat.data.MessageReceived
import com.tvd12.freechat.data.MessageSent
import com.tvd12.freechat.manager.StateManager
import com.tvd12.freechat.socket.SocketRequests

/**
 * Created by tavandung12 on 10/5/18.
 */

class MessageActivity : AppActivity() {

    private var targetChannelId: Long = 0L
    private lateinit var messageController: Controller
    private lateinit var  connectionController: Controller

    private lateinit var  loadingView: View
    private lateinit var  backButtonView: View
    private lateinit var  headerView: View
    private lateinit var  targetBoxView: View
    private lateinit var  targetNameView: TextView
    private lateinit var  targetLastMessageView: TextView
    private lateinit var  chatboxView: View
    private lateinit var  messageInputView: EditText
    private lateinit var  sendButtonView: ImageButton
    private lateinit var  messageListView: RecyclerView
    private lateinit var  messageListAdapter: MessageListAdapter
    private lateinit var  messageListLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        initComponents()
        initViews()
        setViewsData()
        setupViewControllers()
    }

    override fun onStart() {
        super.onStart()
        connectionController.addView("show-loading",  object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView.visibility = View.VISIBLE
            }
        })
        connectionController.addView("hide-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView.visibility = View.GONE
            }
        })
        messageController.addView("add-message", object : IView {
            override fun update(viewId: String, data: Any?) {
                val message = data as MessageReceived
                addMessageItem(message)
                targetLastMessageView.text = message.message
            }
        })
    }

    override fun onStop() {
        super.onStop()
        loadingView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        messageController.removeView("add-message")
    }

    private fun initComponents() {
        val mvc = Mvc.getInstance()
        targetChannelId = StateManager.getInstance().currentChatContact.channelId
        messageController = mvc.getController("message")
        connectionController = mvc.getController("connection")
    }

    private fun initViews() {
        loadingView = findViewById(R.id.loading)
        headerView = findViewById(R.id.header)
        backButtonView = headerView.findViewById(R.id.back)
        targetBoxView = headerView.findViewById(R.id.targetBox)
        targetNameView = targetBoxView.findViewById(R.id.targetName)
        targetLastMessageView = targetBoxView.findViewById(R.id.lastMessage)
        messageListView = findViewById(R.id.messageList)
        chatboxView = findViewById(R.id.chatbox)
        messageInputView = chatboxView.findViewById(R.id.messageInput)
        sendButtonView = chatboxView.findViewById(R.id.sendButton)
        targetLastMessageView.text = ""

        val adapters = MessageListAdapters.getInstance()
        messageListAdapter = adapters.getAdapter(this, targetChannelId)
        messageListView.adapter = messageListAdapter
        messageListLayoutManager = newMessageListLayoutManager()
        messageListView.layoutManager = messageListLayoutManager
    }

    private fun setViewsData() {
        targetLastMessageView.text = ""
        targetNameView.text = StateManager.getInstance().currentChatContact.getUsersString()
    }

    private fun setupViewControllers() {
        backButtonView.setOnClickListener {
            backToContactView()
        }
        sendButtonView.setOnClickListener {
            val messageText = messageInputView.text.toString()
            if (targetChannelId == 0L) {
                SocketRequests.sendSystemMessage(messageText)
            }
            else {
                SocketRequests.sendUserMessage(
                    targetChannelId,
                    messageText
                )
            }
            val message = MessageSent(messageText)
            addMessageItem(message)
            messageInputView.setText("")
        }
    }

    private fun addMessageItem(message: Message) {
        messageListAdapter.addItem(message)
        messageListAdapter.notifyDataSetChanged()
        messageListView.smoothScrollToPosition(messageListAdapter.itemCount!!)
    }

    private fun backToContactView() {
        onBackPressed()
    }

    private fun newMessageListLayoutManager() : LinearLayoutManager {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        return layoutManager
    }
}