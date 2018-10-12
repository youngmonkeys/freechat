package vn.team.freechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.entity.EzyApp;
import com.tvd12.ezyfoxserver.client.entity.EzyZone;

/**
 * Created by tavandung12 on 10/9/18.
 */

public class AppActivity extends AppCompatActivity {

    protected EzyClient client;
    protected EzyZone zone;
    protected EzyApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = EzyClients.getInstance().getDefaultClient();
        zone = client.getZone();
        app = zone.getAppManager().getApp();
    }
}
