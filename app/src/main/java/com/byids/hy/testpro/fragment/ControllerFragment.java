package com.byids.hy.testpro.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byids.hy.testpro.R;

/**
 * Created by hy on 2016/8/29.
 * 控制部分的fragment
 * com.byids.hy.testpro.fragment.ControllerFragment
 */
public class ControllerFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.controller_fragment_layout,null);
        return view;
    }
}
