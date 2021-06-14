package com.tvd12.freechat

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tvd12.freechat.socket.SocketClientProxy

class MainActivity : AppCompatActivity() {

    private lateinit var loadingView: View
    private lateinit var usernameView: EditText
    private lateinit var passwordView: EditText
    private lateinit var loginButtonView: Button
    private lateinit var connectionController: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build()
        StrictMode.setThreadPolicy(policy)
        setContentView(R.layout.activity_main)
        initViews()
        initComponents()
        setViewControllers()
    }

    override fun onStart() {
        super.onStart()
        connectionController.addView("show-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView.visibility = View.VISIBLE
            }
        })
        connectionController.addView("show-authentication", object : IView {
            override fun update(viewId: String, data: Any?) {
                Toast.makeText(this@MainActivity, R.string.authentication_failed, Toast.LENGTH_LONG)
                        .show()
            }
        })
        connectionController.addView("hide-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView.visibility = View.GONE
            }
        })
        connectionController.addView("show-lost-ping", object: IView {
            override fun update(viewId: String, data: Any?) {
                showLostPing(data as Int)
            }
        })
        connectionController.addView("show-try-connect", object: IView {
            override fun update(viewId: String, data: Any?) {
                showTryConnect(data as Int)
            }
        })
        connectionController.addView("show-contacts", object: IView {
            override fun update(viewId: String, data: Any?) {
                startMessageActivity()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        connectionController.removeView("show-authentication")
        connectionController.removeView("show-lost-ping")
        connectionController.removeView("show-try-connect")
        connectionController.removeView("show-contacts")
    }

    private fun initComponents() {
        val mvc = Mvc.getInstance()
        connectionController = mvc.getController("connection")
    }

    private fun initViews() {
        loadingView = findViewById(R.id.loading)
        usernameView = findViewById(R.id.loginUsername)
        passwordView = findViewById(R.id.loginPassword)
        loginButtonView = findViewById(R.id.loginButton)
    }

    private fun setViewControllers() {
        loginButtonView.setOnClickListener {
            val connectionData = Mvc.getInstance()
                .getModel()
                .get<MutableMap<String, Any>>("connection")!!
            val oldUsername = connectionData["username"]
            val newUsername = usernameView.text.toString()
            val socketClient = SocketClientProxy.getInstance()
            if (oldUsername == newUsername && socketClient.isConnected()) {
                startMessageActivity()
            }
            else {
                loadingView.visibility = View.VISIBLE
                connectionData["username"] = newUsername
                connectionData["password"] = passwordView.text.toString()
                SocketClientProxy.getInstance().connectToServer()
            }
        }
    }

    private fun showLostPing(count: Int) {
        val toast = Toast.makeText(this,
            "ping to server lost, count: $count",
                Toast.LENGTH_LONG)
        toast.show()
    }

    private fun showTryConnect(count: Int) {
        val toast = Toast.makeText(this,
            "try connect: $count",
                Toast.LENGTH_LONG)
        toast.show()
    }

    private fun startMessageActivity() {
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("username", usernameView.text.toString())
        startActivity(intent)
    }
}
