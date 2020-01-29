package vn.team.freechat.adapter;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tavandung12 on 10/6/18.
 */

public final class MessageListAdapters {

    private final Map<Long, MessageListAdapter> apdapters;
    private final static MessageListAdapters INSTANCE = new MessageListAdapters();

    private MessageListAdapters() {
        this.apdapters = new HashMap<>();
    }

    public static MessageListAdapters getInstance() {
        return INSTANCE;
    }

    public MessageListAdapter getAdapter(Context context, long targetChannelId) {
        synchronized (apdapters) {
            MessageListAdapter adapter = apdapters.get(targetChannelId);
            if(adapter == null) {
                adapter = new MessageListAdapter(context);
                apdapters.put(targetChannelId, adapter);
            }
            return adapter;
        }
    }

}
