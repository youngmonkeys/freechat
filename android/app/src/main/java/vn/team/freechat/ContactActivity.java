package vn.team.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;

import java.util.ArrayList;
import java.util.List;

import tvd12.com.ezyfoxserver.client.R;
import vn.team.freechat.adapter.ContactListAdapter;
import vn.team.freechat.data.ChannelUsers;
import vn.team.freechat.model.ContactListItemModel;
import vn.team.freechat.mvc.IController;
import vn.team.freechat.mvc.IView;
import vn.team.freechat.mvc.Mvc;
import vn.team.freechat.socket.SocketRequests;

/**
 * Created by tavandung12 on 10/1/18.
 */

public class ContactActivity extends AppCompatActivity {

    private String username;
    private IController contactController;
    private IController connectionController;

    private View loadingView;
    private View profileBox;
    private TextView profileUsernameView;
    private TextView profileStatusMessageView;
    private ListView contactListView;
    private ContactListAdapter contactListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initComponents();
        initViews();
        setViewsData();
        setViewControllers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectionController.addView("show-loading", new IView() {
            @Override
            public void update(Object data) {
                loadingView.setVisibility(View.VISIBLE);
            }
        });
        connectionController.addView("hide-loading", new IView() {
            @Override
            public void update(Object data) {
                loadingView.setVisibility(View.GONE);
            }
        });
        contactController.addView("add-contacts", new IView() {
            @Override
            public void update(Object data) {
                addContacts((EzyArray)data);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendGetContactsRequest();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loadingView.setVisibility(View.GONE);
        contactController.removeView("add-actions");
    }

    private void initComponents() {
        Mvc mvc = Mvc.getInstance();
        username = getIntent().getStringExtra("username");
        contactController = mvc.getController("contact");
        connectionController = mvc.getController("connection");
    }

    private void initViews() {
        loadingView = findViewById(R.id.loading);
        profileBox = findViewById(R.id.profileBox);
        profileUsernameView = profileBox.findViewById(R.id.username);
        profileStatusMessageView = profileBox.findViewById(R.id.statusMessage);
        contactListView = findViewById(R.id.contactList);
        contactListAdapter = new ContactListAdapter(this);
        contactListView.setAdapter(contactListAdapter);
    }

    private void setViewsData() {
        profileUsernameView.setText(username);
        profileStatusMessageView.setText("hello world");
    }

    private void setViewControllers() {
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadingView.setVisibility(View.VISIBLE);
                ContactListAdapter adapter = (ContactListAdapter) parent.getAdapter();
                onContactItemClick(adapter.getItemModel(position).getContact());
            }
        });
    }

    private void sendGetContactsRequest() {
        SocketRequests.sendGetContacts();
    }

    private void addContacts(EzyArray contacts) {
        List<ContactListItemModel> models = new ArrayList<>();
        for(int i = 0 ; i < contacts.size() ; ++i) {
            EzyObject item = contacts.get(i, EzyObject.class);
            long channelId = item.get("channelId", long.class);
            String[] users = item.get("users", String[].class);
            ChannelUsers contact = new ChannelUsers(channelId, users);
            models.add(new ContactListItemModel(contact));
        }
        contactListAdapter.addItemModels(models);
        contactListAdapter.notifyDataSetChanged();
    }

    private void onContactItemClick(ChannelUsers contact) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("targetContact", contact);
        startActivity(intent);
    }
}
