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
import vn.team.freechat.controller.ConnectionController;
import vn.team.freechat.controller.ContactController;
import vn.team.freechat.model.ContactListItemModel;
import vn.team.freechat.request.GetContactsRequest;
import vn.team.freechat.view.ContactView;
import vn.team.freechat.view.LoadingView;

/**
 * Created by tavandung12 on 10/1/18.
 */

public class ContactActivity
        extends AppActivity
        implements ContactView, LoadingView {

    private String username;
    private ContactController contactController;
    private ConnectionController connectionController;

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
        contactController.setContactView(this);
        connectionController.setLoadingView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideLoadingView();
        contactController.setContactView(null);
    }

    private void initComponents() {
        username = getIntent().getStringExtra("username");
        Mvc mvc = Mvc.getInstance();
        contactController = mvc.getContactController();
        connectionController = mvc.getConnectionController();
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
                showLoadingView();
                onContactItemClick(view);
            }
        });
    }

    @Override
    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void addContacts(EzyArray usernames) {
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
