package vn.team.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

    private IController contactController;
    private IController connectionController;

    private View loadingView;
    private View backButtonView;
    private View searchButtonView;
    private EditText keywordView;
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
                loadingView.setVisibility(View.GONE);
                addContacts((EzyArray)data);
            }
        });

        contactController.addView("add-search-contacts", new IView() {
            @Override
            public void update(Object data) {
                backToContactView();
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
        contactController = mvc.getController("contact");
        connectionController = mvc.getController("connection");
    }

    private void initViews() {
        loadingView = findViewById(R.id.loading);
        keywordView = findViewById(R.id.keyword);
        backButtonView = findViewById(R.id.back);
        searchButtonView = findViewById(R.id.search);
        contactListView = findViewById(R.id.contactList);
        contactListAdapter = new SearchContactListAdapter(this);
        contactListView.setAdapter(contactListAdapter);
    }

    private void setViewControllers() {
        backButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.setVisibility(View.GONE);
                backToContactView();
            }
        });
        searchButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingView.setVisibility(View.VISIBLE);
                sendSearchContacts();
            }
        });
        keywordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sendSearchContacts();
                    return true;
                }
                return false;
            }
        });
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadingView.setVisibility(View.VISIBLE);
                SearchContactListAdapter adapter = (SearchContactListAdapter) parent.getAdapter();
                onContactItemClick(adapter.getItemModel(position).getUsername());
            }
        });
    }

    private void sendSearchContacts() {
        String keyword = keywordView.getText().toString();
        if(keyword.isEmpty())
            SocketRequests.sendGetSuggestContacts();
        else
            SocketRequests.sendSearchContacts(keyword);
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

    private void onContactItemClick(String username) {
        SocketRequests.sendAddContact(username);
    }

    private void backToContactView() {
        onBackPressed();
    }
}
