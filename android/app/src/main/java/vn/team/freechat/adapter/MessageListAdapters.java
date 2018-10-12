package vn.team.freechat.adapter;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tavandung12 on 10/6/18.
 */

public final class MessageListAdapters {

    private final Map<String, MessageListAdapter> apdapters;
    private static final MessageListAdapters INSTANCE = new MessageListAdapters();

    private MessageListAdapters() {
        this.apdapters = new HashMap<>();
    }

    public static MessageListAdapters getInstance() {
        return INSTANCE;
    }

    public MessageListAdapter getAdapter(Context context, String targetName) {
        synchronized (apdapters) {
            MessageListAdapter adapter = apdapters.get(targetName);
            if(adapter == null) {
                adapter = new MessageListAdapter(context);
                apdapters.put(targetName, adapter);
            }
            return adapter;
        }
    }

}
