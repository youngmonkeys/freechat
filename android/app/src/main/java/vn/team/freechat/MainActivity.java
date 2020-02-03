package vn.team.freechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tvd12.com.ezyfoxserver.client.R;
import vn.team.freechat.mvc.IController;
import vn.team.freechat.mvc.IModel;
import vn.team.freechat.mvc.IView;
import vn.team.freechat.mvc.Mvc;
import vn.team.freechat.socket.SocketClientProxy;

public class MainActivity extends AppCompatActivity {

    private View loadingView;
    private EditText usernameView;
    private EditText passwordView;
    private Button loginButtonView;
    private IController loginController;
    private IController connectionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        initViews();
        initComponents();
        setViewControllers();
        setSocketClient();
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
        connectionController.addView("show-lost-ping", new IView() {
            @Override
            public void update(Object data) {
                showLostPing((Integer)data);
            }
        });
        connectionController.addView("show-try-connect", new IView() {
            @Override
            public void update(Object data) {
                showTryConnect((Integer)data);
            }
        });
        connectionController.addView("show-contacts", new IView() {
            @Override
            public void update(Object data) {
                startMessageActivity();
            }
        });
        loginController.addView("show-failure", new IView() {
            @Override
            public void update(Object data) {
                showLoginFailure((String)data);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        loadingView.setVisibility(View.GONE);
        connectionController.removeView("show-contacts");
        connectionController.removeView("show-lost-ping");
        connectionController.removeView("show-try-connect");
        loginController.removeView("show-failure");
    }

    private void initViews() {
        loadingView = findViewById(R.id.loading);
        usernameView = findViewById(R.id.loginUsername);
        passwordView = findViewById(R.id.loginPassword);
        loginButtonView = findViewById(R.id.loginButton);
    }

    private void initComponents() {
        Mvc mvc = Mvc.getInstance();
        loginController = mvc.getController("login");
        connectionController = mvc.getController("connection");
    }

    private void setViewControllers() {
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mvc mvc = Mvc.getInstance();
                IModel model = mvc.getModel();
                IModel connection = model.newChild("connection");
                connection.put("username", usernameView.getText().toString());
                connection.put("password", passwordView.getText().toString());
                loadingView.setVisibility(View.VISIBLE);
                SocketClientProxy clientProxy = SocketClientProxy.getInstance();
                clientProxy.connect();
            }
        });
    }

    private void setSocketClient() {
        SocketClientProxy clientProxy = SocketClientProxy.getInstance();
        clientProxy.setup();
    }

    private void showLoginFailure(String reason) {
        Toast toast = Toast.makeText(this,
                "login failed: " + reason,
                Toast.LENGTH_LONG);
        toast.show();
    }

    private void showLostPing(int count) {
        Toast toast = Toast.makeText(this,
                "ping to server lost, count: " + count,
                Toast.LENGTH_LONG);
        toast.show();
    }

    private void showTryConnect(int count) {
        Toast toast = Toast.makeText(this,
                "try connect: " + count,
                Toast.LENGTH_LONG);
        toast.show();
    }

    private void startMessageActivity() {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("username", usernameView.getText().toString());
        startActivity(intent);
    }
}
