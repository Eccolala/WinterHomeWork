package gyx.winterhomework;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MyApplication extends Application {
    public static RequestQueue queue;
    @Override
    public void onCreate() {

        //volley 的配置
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());
    }
    public static RequestQueue getHttpQueue(){
        return queue;
    }
}
