package vn.team.freechat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tvd12.ezyfoxserver.client.util.Lists;

import java.util.Collection;
import java.util.List;

import tvd12.com.ezyfoxserver.client.R;
import vn.team.freechat.model.ContactListItemModel;

/**
 * Created by tavandung12 on 10/3/18.
 */

public class ContactListAdapter extends ArrayAdapter<ContactListItemModel> {

    private final List<ContactListItemModel> items;
    private static final int item_layout_id = R.layout.component_contact_list_item;

    public ContactListAdapter(Context context) {
        this(context, Lists.newArrayList(new ContactListItemModel("System")));
    }

    public ContactListAdapter(Context context, List<ContactListItemModel> items) {
        super(context, item_layout_id, items);
        this.items = items;
    }

    public void addItemModel(ContactListItemModel model) {
        items.add(model);
    }

    public void addItemModels(Collection<ContactListItemModel> itemModels) {
        items.addAll(itemModels);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(item_layout_id, parent, false);
        TextView usernameView = view.findViewById(R.id.username);
        TextView lastMessageView = view.findViewById(R.id.lastMessage);
        ContactListItemModel model = items.get(position);
        usernameView.setText(model.getUsername());
        lastMessageView.setText(model.getLastMessage());
        return view;
    }
}
