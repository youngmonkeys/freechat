package vn.team.freechat_kotlin.adapter


import android.content.Context
import java.util.*

/**
 * Created by tavandung12 on 10/6/18.
 */

class MessageListAdapters {

    private val apdapters: MutableMap<String, MessageListAdapter>

    private constructor() {
        this.apdapters = HashMap()
    }

    companion object {
        private val INSTANCE = MessageListAdapters()
        fun getInstance() : MessageListAdapters = INSTANCE
    }

    fun getAdapter(context: Context, targetName: String): MessageListAdapter {
        synchronized(apdapters) {
            var adapter: MessageListAdapter? = apdapters[targetName]
            if (adapter == null) {
                adapter = MessageListAdapter(context)
                apdapters.put(targetName, adapter)
            }
            return adapter
        }
    }

}