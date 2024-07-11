package com.tvd12.freechat.app.data;


import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@EzyObjectBinding(read = false)
public class Error implements Serializable {
    private static final long serialVersionUID = -28224378850292849L;

    @EzyValue("c")
    private final int code;

    @EzyValue("m")
    private final String message;
}
