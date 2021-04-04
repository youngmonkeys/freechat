package com.tvd12.freechat.extension

import com.tvd12.ezyfoxserver.client.entity.EzyArray
import com.tvd12.ezyfoxserver.client.entity.EzyObject
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory

fun <R> EzyArray.map(mapper: (EzyObject) -> R): List<R> {
    val answer = ArrayList<R>()
    for(i in 0 until size()) {
        answer.add(mapper.invoke(get(i, EzyObject::class.java)))
    }
    return answer
}

fun EzyArray.toStrings(): List<String> =
    toList(String::class.java)

fun EzyObject.getStrings(key: String) =
    get(key, EzyArray::class.java, EzyEntityFactory.EMPTY_ARRAY).toStrings()