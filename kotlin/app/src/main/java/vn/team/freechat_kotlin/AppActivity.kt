package vn.team.freechat_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.tvd12.ezyfoxserver.client.EzyClient
import com.tvd12.ezyfoxserver.client.EzyClients
import com.tvd12.ezyfoxserver.client.entity.EzyApp
import com.tvd12.ezyfoxserver.client.entity.EzyZone

/**
 * Created by tavandung12 on 10/9/18.
 */

open class AppActivity : AppCompatActivity() {

    protected var client: EzyClient? = null
    protected var zone: EzyZone? = null
    protected var app: EzyApp? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client = EzyClients.getInstance().defaultClient
        zone = client?.zone
        app = zone?.appManager?.app
    }
}
