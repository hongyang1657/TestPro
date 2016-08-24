package com.byids.hy.testpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.byids.hy.testpro.activity.TestBean;

import java.util.List;

/**
 * Created by hy on 2016/8/19.
 */
public class TestAdapter extends BaseAdapter{

    private Context context;
    private List<TestBean> list;
    private LayoutInflater inflater;

    public TestAdapter(Context context,List<TestBean> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return null;
    }

    class ViewHolder{

    }
}
