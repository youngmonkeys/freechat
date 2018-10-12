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
import vn.team.freechat.controller.ConnectionController;
import vn.team.freechat.controller.MessageController;
import vn.team.freechat.data.Message;
import vn.team.freechat.data.MessageReceived;
import vn.team.freechat.data.MessageSent;
import vn.team.freechat.request.SendSystemMessageRequest;
import vn.team.freechat.request.SendUserMessageRequest;
import vn.team.freechat.view.LoadingView;
import vn.team.freechat.view.MessageView;

/**
 * Created by tavandung12 on 10/5/18.
 */

public class MessageActivity
        extends AppActivity
        implements MessageView, LoadingView {

    private String targetContact;
    private MessageController messageController;
    private ConnectionController connectionController;

    private View loadingView;
    private View backButtonView;
    private View headerView;
    private View targetBoxView;
    private TextView targetNameView;
    private TextView targetLastMessageView;
    private View chatboxView;
    private EditText messageInputView;
    private ImageButton sendButtonView;
    private RecyclerView messageListView;
    private MessageListAdapter messageListAdapter;
    private LinearLayoutManager messageListLayoutManager;

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
        messageController.setMessageView(this);
        connectionController.setLoadingView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideLoadingView();
        messageController.setMessageView(null);
    }

    private void initComponents() {
        targetContact = getIntent().getStringExtra("targetContact");
        Mvc mvc = Mvc.getInstance();
        messageController = mvc.getMessageController();
        connectionController = mvc.getConnectionController();
    }

    private void initViews() {
        loadingView = findViewById(R.id.loading);
        headerView = findViewById(R.id.header);
        backButtonView = headerView.findViewById(R.id.back);
        targetBoxView = headerView.findViewById(R.id.targetBox);
        targetNameView = targetBoxView.findViewById(R.id.targetName);
        targetLastMessageView = targetBoxView.findViewById(R.id.lastMessage);
        messageListView = findViewById(R.id.messageList);
        chatboxView = findViewById(R.id.chatbox);
        messageInputView = chatboxView.findViewById(R.id.messageInput);
        sendButtonView = chatboxView.findViewById(R.id.sendButton);
        targetNameView.setText(targetContact);
        targetLastMessageView.setText("");

        MessageListAdapters adapters = MessageListAdapters.getInstance();
        messageListAdapter = adapters.getAdapter(this, targetContact);
        messageListView.setAdapter(messageListAdapter);
        messageListLayoutManager = newMessageListLayoutManager();
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
                String messageText = messageInputView.getText().toString();
                EzyRequest request = null;
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

    @Override
    public void addMessage(MessageReceived message) {
        addMessageItem(message);
        targetLastMessageView.setText(message.getMessage());
    }

    private void addMessageItem(Message message) {
        messageListAdapter.addItem(message);
        messageListAdapter.notifyDataSetChanged();
        messageListView.smoothScrollToPosition(messageListAdapter.getItemCount());
    }

    @Override
    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
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
