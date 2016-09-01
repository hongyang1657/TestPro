package com.byids.hy.testpro.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.byids.hy.testpro.R;

/**
 * Created by hy on 2016/8/29.
 * 控制部分的fragment
 * com.byids.hy.testpro.fragment.ControllerFragment
 */
public class ControllerFragment extends Fragment{

    private View view;
    private TextView tvRoomName;
    private Button btShezhi;
    private Button btJiankong;
    private Button btMensuo;
    private Button btBufang;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.controller_fragment_layout,null);
        initView();
        return view;
    }

    private void initView(){
        tvRoomName = (TextView) view.findViewById(R.id.tv_blank);
        btShezhi = (Button) view.findViewById(R.id.bt_shezhi);
        btJiankong = (Button) view.findViewById(R.id.bt_jiankong);
        btMensuo = (Button) view.findViewById(R.id.bt_mensuo);
        btBufang = (Button) view.findViewById(R.id.bt_bufang);
        btShezhi.setOnClickListener(clickListener);
        btJiankong.setOnClickListener(clickListener);
        btMensuo.setOnClickListener(clickListener);
        btBufang.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.bt_shezhi:
                    Toast.makeText(getActivity(), "设置", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_jiankong:
                    Toast.makeText(getActivity(), "监控", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_mensuo:
                    Toast.makeText(getActivity(), "门锁", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_bufang:
                    Toast.makeText(getActivity(), "布防", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
