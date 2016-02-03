package gyx.winterhomework;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private List<DataBean> mDatas;

    public RecyclerAdapter(Context context,List<DataBean> mDatas){
        this.context = context;
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item,parent,false);
        MyViewHolder mViewHolder = new MyViewHolder(view);
        return mViewHolder;
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
        holder.tv.setText(mDatas.get(pos).getName());
        //使用Picasso加载图片
        Picasso.with(context).load(
                mDatas.get(pos).getPicSmall())
                .into(holder.iv);
    }


}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView tv;
    ImageView iv;
    public MyViewHolder(View parent){
        super(parent);
        tv = (TextView) parent.findViewById(R.id.id_recyclerText);
        iv = (ImageView)parent.findViewById(R.id.id_recyclerImage);
    }
}
