package vn.team.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;

import java.util.ArrayList;
import java.util.List;

import tvd12.com.ezyfoxserver.client.R;
import vn.team.freechat.adapter.ContactListAdapter;
import vn.team.freechat.adapter.SearchContactListAdapter;
import vn.team.freechat.data.ChannelUsers;
import vn.team.freechat.model.SearchContactModel;
import vn.team.freechat.mvc.IController;
import vn.team.freechat.mvc.IView;
import vn.team.freechat.mvc.Mvc;
import vn.team.freechat.socket.SocketRequests;

/**
 * Created by tavandung12 on 10/1/18.
 */

public class ContactSearchActivity extends AppCompatActivity {

    private String username;
    private IController contactController;
    private IController connectionController;

    private View loadingView;
    private View backButtonView;
    private ListView contactListView;
    private SearchContactListAdapter contactListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_search);
        initComponents();
        initViews();
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
        contactController.addView("search-contacts", new IView() {
            @Override
            public void update(Object data) {
                addContacts((EzyArray)data);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendGetSuggestContacts();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loadingView.setVisibility(View.GONE);
        contactController.removeView("search-contacts");
    }

    private void initComponents() {
        Mvc mvc = Mvc.getInstance();
        username = getIntent().getStringExtra("username");
        contactController = mvc.getController("contact");
        connectionController = mvc.getController("connection");
    }

    private void initViews() {
        loadingView = findViewById(R.id.loading);
        backButtonView = findViewById(R.id.back);
        contactListView = findViewById(R.id.contactList);
        contactListAdapter = new SearchContactListAdapter(this);
        contactListView.setAdapter(contactListAdapter);
    }

    private void setViewControllers() {
        backButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToContactView();
            }
        });
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadingView.setVisibility(View.VISIBLE);
                ContactListAdapter adapter = (ContactListAdapter) parent.getAdapter();
                onContactItemClick(adapter.getItemModel(position).getContact());
            }
        });
    }

    private void sendGetSuggestContacts() {
        SocketRequests.sendGetSuggestContacts();
    }

    private void addContacts(EzyArray contacts) {
        List<SearchContactModel> models = new ArrayList<>();
        for(int i = 0 ; i < contacts.size() ; ++i) {
            EzyObject item = contacts.get(i, EzyObject.class);
            String username = item.get("username", String.class);
            String fullName = item.get("fullName", String.class, "");
            models.add(new SearchContactModel(username, fullName));
        }
        contactListAdapter.setItemModels(models);
        contactListAdapter.notifyDataSetChanged();
    }

    private void onContactItemClick(ChannelUsers contact) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("targetContact", contact);
        startActivity(intent);
    }

    private void backToContactView() {
        onBackPressed();
    }
}
