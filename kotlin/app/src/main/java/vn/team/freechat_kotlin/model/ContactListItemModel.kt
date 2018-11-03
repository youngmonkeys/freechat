package vn.team.freechat_kotlin.model

import java.util.concurrent.atomic.AtomicLong





/**
 * Created by tavandung12 on 10/3/18.
 */

class ContactListItemModel  {

    private var id: Long = 0
    private var username: String? = null
    private var lastMessage: String? = null

    companion object {

        private val COUNTER = AtomicLong()
    }

    init {
        this.id = COUNTER.incrementAndGet()
    }

    constructor(username: String, lastMessage: String) {
        this.username = username
        this.lastMessage = lastMessage
    }

    fun getId(): Long {
        return id
    }

    fun getUsername(): String {
        return username!!
    }

    fun getLastMessage(): String {
        return lastMessage!!
    }
}