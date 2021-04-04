package com.tvd12.freechat.manager

import com.tvd12.freechat.model.ContactListItemModel

class StateManager private constructor() {

    var reconnnect = false
    lateinit var currentChatContact: ContactListItemModel

    companion object {
        private val INSTANCE = StateManager()
        fun getInstance() = INSTANCE
    }
}