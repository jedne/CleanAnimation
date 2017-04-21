package com.avazu.testplugin.TestViewPager.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avazu.testplugin.R;
import com.avazu.testplugin.TestViewPager.ui.PageRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerView extends AppCompatActivity {
    private PageRecyclerView mRecyclerView = null;
    private List<String> dataList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);

        initData();

        mRecyclerView = (PageRecyclerView) findViewById(R.id.custom_swipe_view);
        // 设置指示器
        // 设置行数和列数
//        mRecyclerView.setPageSize(3, 3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // 设置数据
        mRecyclerView.setAdapter(new myAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class myAdapter extends RecyclerView.Adapter<MyHolder>{
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MyRecyclerView.this).inflate(R.layout.fragment_test_view_pager, parent, false);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mRecyclerView.getWidth() - 50, mRecyclerView.getHeight());
            view.setLayoutParams(lp);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            int realposition = position - 1;
            if(position <= 0) {
                realposition = dataList.size() - 1;
            }
            else if(position >= dataList.size() + 1){
                realposition = 0;
            }
            holder.tv.setText(dataList.get(realposition));
//            holder.itemView.setScaleX(0.9f);
//            holder.itemView.setScaleY(0.9f);
            holder.itemView.setBackgroundColor(Color.parseColor("#" + position % 3 + "F" + position % 3 + "F" + position % 3 + "F"));
        }

        @Override
        public int getItemCount() {
            return dataList.size() + 2;
        }
    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("this is page :" + String.valueOf(i));
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView tv = null;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.fragment_textview);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyRecyclerView.this, getAdapterPosition() + "", Toast.LENGTH_SHORT).show();
                }
            });
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    myAdapter.remove(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
