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
import vn.team.freechat.model.SearchContactModel;

/**
 * Created by tavandung12 on 10/3/18.
 */

public class SearchContactListAdapter extends ArrayAdapter<SearchContactModel> {

    private final List<SearchContactModel> items;
    private static final int item_layout_id = R.layout.component_search_contact_list_item;

    public SearchContactListAdapter(Context context) {
        this(context, new ArrayList<SearchContactModel>());
    }

    public SearchContactListAdapter(Context context, List<SearchContactModel> items) {
        super(context, item_layout_id, items);
        this.items = items;
    }

    public SearchContactModel getItemModel(int position) {
        return this.items.get(position);
    }

    public void setItemModels(Collection<SearchContactModel> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(item_layout_id, parent, false);
        TextView usernameView = view.findViewById(R.id.username);
        TextView fullNameView = view.findViewById(R.id.fullName);
        SearchContactModel model = items.get(position);
        usernameView.setText(model.getUsername());
        fullNameView.setText(model.getFullName());
        return view;
    }
}
