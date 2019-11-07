package vn.team.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;

import java.util.ArrayList;
import java.util.List;

import tvd12.com.ezyfoxserver.client.R;
import vn.team.freechat.adapter.ContactListAdapter;
import vn.team.freechat.model.ContactListItemModel;
import vn.team.freechat.mvc.Controller;
import vn.team.freechat.mvc.IController;
import vn.team.freechat.mvc.IView;
import vn.team.freechat.mvc.Mvc;
import vn.team.freechat.request.GetContactsRequest;

/**
 * Created by tavandung12 on 10/1/18.
 */

public class ContactActivity extends AppActivity {

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
        sendGetContactsRequest();
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
                onContactItemClick(view);
            }
        });
    }

    private void addContacts(EzyArray usernames) {
        List<ContactListItemModel> models = new ArrayList<>();
        for(int i = 0 ; i < usernames.size() ; i++)
            models.add(new ContactListItemModel(usernames.get(i, String.class), ""));
        contactListAdapter.addItemModels(models);
        contactListAdapter.notifyDataSetChanged();
    }

    private void sendGetContactsRequest() {
        app.send(new GetContactsRequest(0, 50));
    }

    private void onContactItemClick(View view) {
        TextView usernameView = view.findViewById(R.id.username);
        String username = usernameView.getText().toString();
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("targetContact", username);
        startActivity(intent);
    }
}
