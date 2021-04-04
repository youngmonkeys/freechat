package vn.team.freechat_kotlin.data

import android.annotation.SuppressLint
import com.tvd12.ezyfoxserver.client.codec.EzyObjectDeserializable
import com.tvd12.ezyfoxserver.client.constant.EzyConstant
import com.tvd12.ezyfoxserver.client.entity.EzyObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by tavandung12 on 10/7/18.
 */

abstract class Message(
    var message: String,
    var sentTime: Date = Date()
) : EzyObjectDeserializable {

    override fun deserialize(data: EzyObject) {
        this.message = data.get("message")
    }

    @SuppressLint("SimpleDateFormat")
    fun getSendTimeString(): String {
        val format = SimpleDateFormat("hh:mm a")
        return format.format(sentTime)
    }

    abstract fun getType() : EzyConstant
}
