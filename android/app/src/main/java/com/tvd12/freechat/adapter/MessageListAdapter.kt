package com.tvd12.freechat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tvd12.freechat.R
import com.tvd12.freechat.data.Message
import com.tvd12.freechat.data.MessageReceived
import com.tvd12.freechat.data.MessageSent
import java.util.*


/**
 * Created by tavandung12 on 10/5/18.
 */

class MessageListAdapter(
    context: Context
) : RecyclerView.Adapter<MessageHolder<Message>>() {

    private val items : MutableList<Message>
    private val inflater : LayoutInflater = LayoutInflater.from(context)

    companion object {
        private const val VIEW_TYPE_MESSAGE_SEDING = 1
        private const val VIEW_TYPE_MESSAGE_SENT = 2
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 3
    }

    init {
        this.items = ArrayList()
    }

    fun addItem(item: Message) {
        this.items.add(item)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int) : MessageHolder<Message> {
        val view : View?
        return when (viewType) {
            VIEW_TYPE_MESSAGE_SENT -> {
                view = inflater.inflate(R.layout.component_message_item_sent, parent, false)
                MessageSentHolder(view) as MessageHolder<Message>
            }
            VIEW_TYPE_MESSAGE_RECEIVED -> {
                view = inflater.inflate(R.layout.component_message_item_received, parent, false)
                MessageReceivedHolder(view) as MessageHolder<Message>
            }
            else -> {
                throw IllegalArgumentException ("has no view with type: $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: MessageHolder<Message>, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    override fun getItemCount() : Int {
        return items.size
    }

    private fun getItem(position: Int) : Message {
        return items[position]
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val type = item.getType()
        return type.id
    }
}

open class MessageHolder<E>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val timeView : TextView = itemView.findViewById(R.id.time)
    private val messageView : TextView = itemView.findViewById(R.id.message)

    open fun bind(message: E) {
        val m = message as Message
        this.messageView.text = m.message
        this.timeView.text = m.getSendTimeString()
    }
}

class MessageSentHolder(itemView: View) : MessageHolder<MessageSent>(itemView)

class MessageReceivedHolder(itemView: View) : MessageHolder<MessageReceived>(itemView) {

    private var avatarView: ImageView? = null
    private var fromView: TextView? = null

    init {
        this.avatarView = itemView.findViewById(R.id.avatar)
        this.fromView = itemView.findViewById(R.id.from)
    }

    override fun bind(message: MessageReceived) {
        super.bind(message)
        fromView?.text = message.from
    }
}