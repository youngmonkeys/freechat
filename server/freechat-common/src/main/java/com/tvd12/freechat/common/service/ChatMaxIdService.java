package com.tvd12.freechat.common.service;

import com.tvd12.ezydata.database.repository.EzyMaxIdRepository;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ChatMaxIdService {

    private final EzyMaxIdRepository maxIdRepository;

    public Long incrementAndGet(String key) {
        return maxIdRepository.incrementAndGet(key);
    }
}
