package vn.team.freechat_kotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.tvd12.ezyfoxserver.client.EzyClient
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest
import com.tvd12.ezyfoxserver.client.request.EzyRequest
import vn.team.freechat_kotlin.factory.ClientFactory

class MainActivity : AppCompatActivity() {

    private var client: EzyClient? = null

    private var loadingView: View? = null
    private var usernameView: EditText? = null
    private var passwordView: EditText? = null
    private var loginButtonView: Button? = null
    private var connectionController: Controller? = null

    private val host = "192.168.1.13"
//    private val host = "192.168.51.103"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initComponents()
        setViewControllers();
    }

    override fun onStart() {
        super.onStart()
        connectionController?.addView("show-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView?.visibility = View.VISIBLE
            }
        })
        connectionController?.addView("hide-loading", object : IView {
            override fun update(viewId: String, data: Any?) {
                loadingView?.visibility = View.GONE
            }
        })
        connectionController?.addView("show-lost-ping", object: IView {
            override fun update(viewId: String, data: Any?) {
                showLostPing(data as Int)
            }
        })
        connectionController?.addView("show-try-connect", object: IView {
            override fun update(viewId: String, data: Any?) {
                showTryConnect(data as Int)
            }
        })
        connectionController?.addView("show-contacts", object: IView {
            override fun update(viewId: String, data: Any?) {
                startMessageActivity()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        connectionController?.removeView("show-lost-ping")
        connectionController?.removeView("show-try-connect")
        connectionController?.removeView("show-contacts")
    }

    private fun initComponents() {
        val mvc = Mvc.getInstance()
        connectionController = mvc.getController("connection")
    }

    private fun initViews() {
        loadingView = findViewById(R.id.loading)
        usernameView = findViewById<View>(R.id.loginUsername) as EditText?
        passwordView = findViewById<View>(R.id.loginPassword) as EditText?
        loginButtonView = findViewById<View>(R.id.loginButton) as Button?
    }

    private fun setViewControllers() {
        loginButtonView?.setOnClickListener({
            val factory = ClientFactory.getInstance()
            client = factory.newClient(newLoginRequest())
            client?.connect(host, 3005)
            loadingView?.visibility = View.VISIBLE
        })
    }

    private fun showLostPing(count: Int) {
        val toast = Toast.makeText(this,
                "ping to server lost, count: " + count,
                Toast.LENGTH_LONG)
        toast.show()
    }

    private fun showTryConnect(count: Int) {
        val toast = Toast.makeText(this,
                "try connect: " + count,
                Toast.LENGTH_LONG)
        toast.show()
    }

    private fun newLoginRequest(): EzyRequest {
        return EzyLoginRequest(
                "freechat",
                usernameView?.getText().toString(),
                passwordView?.getText().toString()
        )
    }

    private fun startMessageActivity() {
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("username", usernameView?.getText().toString())
        startActivity(intent)
    }
}
