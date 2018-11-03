package vn.team.freechat_kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import vn.team.freechat_kotlin.R
import vn.team.freechat_kotlin.data.Message
import vn.team.freechat_kotlin.data.MessageReceived
import vn.team.freechat_kotlin.data.MessageSent
import java.util.*
import java.util.Collection


/**
 * Created by tavandung12 on 10/5/18.
 */

class MessageListAdapter : RecyclerView.Adapter<MessageHolder<Message>> {

    private val context : Context
    private val inflater : LayoutInflater
    private val items : MutableList<Message>

    companion object {
        private val VIEW_TYPE_MESSAGE_SEDING = 1
        private val VIEW_TYPE_MESSAGE_SENT = 2
        private val VIEW_TYPE_MESSAGE_RECEIVED = 3
    }

    constructor(context: Context) : super() {
        this.context = context
        this.inflater = LayoutInflater.from(context)
        this.items = ArrayList<Message>()
    }

    fun addItem(item: Message) {
        this.items.add(item)
    }

    fun addItems(items: Collection<Message>) {
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent : ViewGroup?, viewType: Int) : MessageHolder<Message> {
        var view : View?
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
                throw IllegalArgumentException ("has no view with type: " + viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: MessageHolder<Message>?, position: Int) {
        val message = getItem(position)
        (holder as MessageHolder<Message>).bind(message)
    }

    override fun getItemCount() : Int {
        return items.size
    }

    private fun getItem(position: Int) : Message {
        val item = items[position]
        return item
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val type = item.getType()
        return type.id
    }
}

open class MessageHolder<E> : RecyclerView.ViewHolder {

    private val messageView : TextView
    private val timeView : TextView

    constructor(itemView: View) : super(itemView) {
        this.messageView = itemView.findViewById(R.id.message)
        this.timeView = itemView.findViewById(R.id.time)
    }

    open fun bind(message: E) {
        val m = message as Message
        this.messageView.text = m.message;
        this.timeView.text = m.getSendTimeString()
    }
}

class MessageSentHolder : MessageHolder<MessageSent> {

    constructor(itemView: View) : super(itemView)
}

class MessageReceivedHolder : MessageHolder<MessageReceived> {

    private var avatarView: ImageView? = null
    private var fromView: TextView? = null


    constructor(itemView: View) : super(itemView) {
        this.avatarView = itemView.findViewById(R.id.avatar)
        this.fromView = itemView.findViewById(R.id.from)
    }

    override fun bind(message: MessageReceived) {
        super.bind(message)
        fromView?.text = message.from
    }
}