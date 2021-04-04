package vn.team.freechat_kotlin.manager

import vn.team.freechat_kotlin.model.ContactListItemModel

class StateManager private constructor() {

    lateinit var currentChatContact: ContactListItemModel

    companion object {
        private val INSTANCE = StateManager()
        fun getInstance() = INSTANCE
    }
}