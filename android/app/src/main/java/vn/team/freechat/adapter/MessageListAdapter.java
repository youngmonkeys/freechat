package vn.team.freechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tvd12.com.ezyfoxserver.client.R;
import vn.team.freechat.data.Message;
import vn.team.freechat.data.MessageReceived;

/**
 * Created by tavandung12 on 10/5/18.
 */

public class MessageListAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<Message> items;

    private static final int VIEW_TYPE_MESSAGE_SEDING = 1;
    private static final int VIEW_TYPE_MESSAGE_SENT = 2;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 3;

    public MessageListAdapter(Context context) {
        this(context, new ArrayList<Message>());
    }

    public MessageListAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.items = messages;
    }

    public void addItem(Message item) {
        this.items.add(item);
    }

    public void addItems(Collection<Message> items) {
        this.items.addAll(items);
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_MESSAGE_SENT:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.component_message_item_sent, parent, false);
                return new MessageSentHolder(view);
            case VIEW_TYPE_MESSAGE_RECEIVED:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.component_message_item_received, parent, false);
                return new MessageReceivedHolder(view);
            default:
                throw new IllegalArgumentException("has no view with type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = getItem(position);
        ((MessageHolder)holder).bind(message);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Message getItem(int position) {
        Message item = items.get(position);
        return item;
    }

    @Override
    public int getItemViewType(int position) {
        Message item = getItem(position);
        EzyConstant type = item.getType();
        return type.getId();
    }
}

class MessageHolder<T extends Message> extends RecyclerView.ViewHolder {

    private TextView messageView;
    private TextView timeView;


    public MessageHolder(View itemView) {
        super(itemView);
        this.messageView = itemView.findViewById(R.id.message);
        this.timeView = itemView.findViewById(R.id.time);
    }

    public void bind(T message) {
        this.messageView.setText(message.getMessage());
        this.timeView.setText(message.getSentTimeString());
    }
}

class MessageSentHolder extends MessageHolder {

    public MessageSentHolder(View itemView) {
        super(itemView);
    }
}

class MessageReceivedHolder extends MessageHolder<MessageReceived> {

    private ImageView avatarView;
    private TextView fromView;


    public MessageReceivedHolder(View itemView) {
        super(itemView);
        this.avatarView = itemView.findViewById(R.id.avatar);
        this.fromView = itemView.findViewById(R.id.from);
    }

    @Override
    public void bind(MessageReceived message) {
        super.bind(message);
        fromView.setText(message.getFrom());
    }
}