package vn.team.freechat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tvd12.ezyfoxserver.client.request.EzyRequest;

import tvd12.com.ezyfoxserver.client.R;
import vn.team.freechat.adapter.MessageListAdapter;
import vn.team.freechat.adapter.MessageListAdapters;
import vn.team.freechat.data.Message;
import vn.team.freechat.data.MessageReceived;
import vn.team.freechat.data.MessageSent;
import vn.team.freechat.mvc.Controller;
import vn.team.freechat.mvc.IController;
import vn.team.freechat.mvc.IView;
import vn.team.freechat.mvc.Mvc;
import vn.team.freechat.request.SendSystemMessageRequest;
import vn.team.freechat.request.SendUserMessageRequest;

/**
 * Created by tavandung12 on 10/5/18.
 */

public class MessageActivity extends AppActivity {

    private String targetContact;
    private IController messageController;
    private IController connectionController;

    private View loadingView;
    private View backButtonView;
    private TextView targetNameView;
    private TextView targetLastMessageView;
    private EditText messageInputView;
    private ImageButton sendButtonView;
    private RecyclerView messageListView;
    private MessageListAdapter messageListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initComponents();
        initViews();
        setViewsData();
        setupViewControllers();
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
        messageController.addView("add-message", new IView() {
            @Override
            public void update(Object data) {
                addMessage((MessageReceived)data);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        loadingView.setVisibility(View.GONE);
        messageController.removeView("add-message");
    }

    private void initComponents() {
        Mvc mvc = Mvc.getInstance();
        targetContact = getIntent().getStringExtra("targetContact");
        messageController = mvc.getController("message");
        connectionController = mvc.getController("connection");
    }

    private void initViews() {
        loadingView = findViewById(R.id.loading);
        View headerView = findViewById(R.id.header);
        backButtonView = headerView.findViewById(R.id.back);
        View targetBoxView = headerView.findViewById(R.id.targetBox);
        targetNameView = targetBoxView.findViewById(R.id.targetName);
        targetLastMessageView = targetBoxView.findViewById(R.id.lastMessage);
        messageListView = findViewById(R.id.messageList);
        View chatboxView = findViewById(R.id.chatbox);
        messageInputView = chatboxView.findViewById(R.id.messageInput);
        sendButtonView = chatboxView.findViewById(R.id.sendButton);
        targetNameView.setText(targetContact);
        targetLastMessageView.setText("");

        MessageListAdapters adapters = MessageListAdapters.getInstance();
        messageListAdapter = adapters.getAdapter(this, targetContact);
        messageListView.setAdapter(messageListAdapter);
        LinearLayoutManager messageListLayoutManager = newMessageListLayoutManager();
        messageListView.setLayoutManager(messageListLayoutManager);
    }

    private void setViewsData() {
        targetNameView.setText(targetContact);
        targetLastMessageView.setText("");
    }

    private void setupViewControllers() {
        backButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToContactView();
            }
        });
        sendButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EzyRequest request;
                String messageText = messageInputView.getText().toString();
                if(targetContact.equalsIgnoreCase("System"))
                    request = new SendSystemMessageRequest(messageText);
                else
                    request = new SendUserMessageRequest(targetContact, messageText);
                app.send(request);
                Message message = new MessageSent(messageText, targetContact);
                addMessageItem(message);
                messageInputView.setText("");
            }
        });
    }

    private void addMessage(MessageReceived message) {
        addMessageItem(message);
        targetLastMessageView.setText(message.getMessage());
    }

    private void addMessageItem(Message message) {
        messageListAdapter.addItem(message);
        messageListAdapter.notifyDataSetChanged();
        messageListView.smoothScrollToPosition(messageListAdapter.getItemCount());
    }

    private void backToContactView() {
        onBackPressed();
    }

    private LinearLayoutManager newMessageListLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        return layoutManager;
    }
}
