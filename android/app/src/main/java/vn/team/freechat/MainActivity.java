package vn.team.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;

import tvd12.com.ezyfoxserver.client.R;
import vn.team.freechat.controller.ConnectionController;
import vn.team.freechat.factory.ClientFactory;
import vn.team.freechat.view.ConnectionView;
import vn.team.freechat.view.LoadingView;

public class MainActivity
        extends AppCompatActivity
        implements ConnectionView, LoadingView {

    private EzyClient client;

    private View loadingView;
    private EditText usernameView;
    private EditText passwordView;
    private Button loginButtonView;
    private ConnectionController connectionController;

    private String host = "192.168.1.13";
//    private String host = "192.168.51.103";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initComponents();
        setViewControllers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectionController.setConnectionView(this);
        connectionController.setLoadingView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideLoadingView();
        connectionController.setConnectionView(null);
    }

    private void initViews() {
        loadingView = findViewById(R.id.loading);
        usernameView = findViewById(R.id.loginUsername);
        passwordView = findViewById(R.id.loginPassword);
        loginButtonView = findViewById(R.id.loginButton);
    }

    private void initComponents() {
        Mvc mvc = Mvc.getInstance();
        connectionController = mvc.getConnectionController();
    }

    private void setViewControllers() {
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientFactory factory = ClientFactory.getInstance();
                client = factory.newClient(newLoginRequest());
                client.connect(host, 3005);
                showLoadingView();
            }
        });
    }

    @Override
    public void showContactView() {
        startMessageActivity();
    }

    @Override
    public void showLostPing(int count) {
        Toast toast = Toast.makeText(this,
                "ping to server lost, count: " + count,
                Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showTryConnect(int count) {
        Toast toast = Toast.makeText(this,
                "try connect: " + count,
                Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
    }

    private EzyRequest newLoginRequest() {
        return new EzyLoginRequest(
                "freechat",
                usernameView.getText().toString(),
                passwordView.getText().toString()
        );
    }

    private void startMessageActivity() {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("username", usernameView.getText().toString());
        startActivity(intent);
    }
}
