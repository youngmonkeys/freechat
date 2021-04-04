package com.tvd12.freechat.adapter


import android.content.Context
import java.util.*

/**
 * Created by tavandung12 on 10/6/18.
 */

class MessageListAdapters private constructor() {

    private val adapters: MutableMap<Long, MessageListAdapter>

    init {
        this.adapters = HashMap()
    }

    companion object {
        private val INSTANCE = MessageListAdapters()
        fun getInstance() : MessageListAdapters = INSTANCE
    }

    fun getAdapter(context: Context, channelId: Long): MessageListAdapter {
        var adapter: MessageListAdapter? = adapters[channelId]
        if (adapter == null) {
            adapter = MessageListAdapter(context)
            adapters[channelId] = adapter
        }
        return adapter
    }

}