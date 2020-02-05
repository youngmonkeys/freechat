package vn.team.freechat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tvd12.com.ezyfoxserver.client.R;
import vn.team.freechat.model.ChatContactModel;

/**
 * Created by tavandung12 on 10/3/18.
 */

public class ContactListAdapter extends ArrayAdapter<ChatContactModel> {

    private final List<ChatContactModel> items;
    private final Map<Long, ChatContactModel> itemMap;
    private static final int item_layout_id = R.layout.component_contact_list_item;

    public ContactListAdapter(Context context) {
        this(context, new ArrayList<ChatContactModel>());
    }

    public ContactListAdapter(Context context, List<ChatContactModel> items) {
        super(context, item_layout_id, items);
        this.items = items;
        this.itemMap = new HashMap<>();
        this.addItemModel(ChatContactModel.systemModel());
    }

    public ChatContactModel getItemModel(int position) {
        return items.get(position);
    }

    public void addItemModel(ChatContactModel item) {
        if(!itemMap.containsKey(item.getId())) {
            this.items.add(item);
            this.itemMap.put(item.getId(), item);
        }
    }

    public void addItemModels(Collection<ChatContactModel> items) {
        for(ChatContactModel item : items)
            addItemModel(item);
    }

    public void setItemModels(Collection<ChatContactModel> items) {
        this.items.clear();
        this.itemMap.clear();
        this.addItemModels(items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(item_layout_id, parent, false);
        TextView usernameView = view.findViewById(R.id.username);
        TextView lastMessageView = view.findViewById(R.id.lastMessage);
        ChatContactModel model = items.get(position);
        usernameView.setText(model.getContactUser());
        lastMessageView.setText(model.getLastMessage());
        return view;
    }
}
