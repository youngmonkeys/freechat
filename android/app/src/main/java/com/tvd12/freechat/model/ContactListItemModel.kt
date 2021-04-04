package com.tvd12.freechat.model

/**
 * Created by tavandung12 on 10/3/18.
 */

data class ContactListItemModel(
        val channelId: Long,
        val users: List<String>,
        var lastMessage: String? = ""
) {
    fun getUsersString(): String {
        return users.joinToString(", ")
    }
}