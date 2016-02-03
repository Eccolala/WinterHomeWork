package gyx.winterhomework;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;private  RecyclerAdapter mAdapter;
    private  List<DataBean> datas;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        //initAdapter();
        mAdapter = new RecyclerAdapter(this,datas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        volleyGet();




    }


    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#ff00ff"),Color.parseColor("#ff0f0f"),
                Color.parseColor("#0000ff"),Color.parseColor("#000000"));
        //setProgressViewOffset(boolean scale, int start, int end) 调整进度条距离屏幕顶部的距离
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));
        //设置监听器，这里就简单的每当刷新(圆形进度条出现)时，延迟5秒将刷新状态改为false，即刷新结束。进度条也会消失
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("TAG", " onRefresh() now:" + mSwipeRefreshLayout.isRefreshing());
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
    }

    private void volleyGet() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://www.imooc.com/api/teacher?type=4&num=30";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("info",response);
                //analysis json date
                try {
                    anaJson(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

    private void anaJson(String response) throws JSONException {

        //解析json数组
        JSONObject object = new JSONObject(response);
       datas = (List<DataBean>) JSON.parseArray(object.getString("data"), DataBean.class);
        for (int i = 0;i<datas.size();i++) {
            Log.d("info", datas.get(i).getName());
            Log.d("info", datas.get(i).getPicSmall());
        }
       initAdapter();


    }

    private void initAdapter() {
        mAdapter = new RecyclerAdapter(this,datas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

}
