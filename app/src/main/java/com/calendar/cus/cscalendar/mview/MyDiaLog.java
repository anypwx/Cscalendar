package com.calendar.cus.cscalendar.mview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.calendar.cus.cscalendar.R;

/**
 * Created by grg on 2017/3/10.
 */

public class MyDiaLog extends DialogFragment implements View.OnClickListener{

    private String text;
    private TextView dialog_text;
    private TextView dialog_btn_left;
    private TextView dialog_btn_right;
    private DiaLogBtnListener diaLogBtnListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_item, container,false);
        text = getArguments().getString("text");
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view){
        dialog_text = (TextView) view.findViewById(R.id.dialog_text);
        dialog_btn_left = (TextView) view.findViewById(R.id.dialog_btn_left);
        dialog_btn_right = (TextView) view.findViewById(R.id.dialog_btn_right);
        dialog_text.setText(text);
    }
    private void initListener(){
        dialog_btn_left.setOnClickListener(this);
        dialog_btn_right.setOnClickListener(this);
    }

    public void setDiaLogBtnListener(DiaLogBtnListener diaLogBtnListener){
        this.diaLogBtnListener = diaLogBtnListener;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.dialog_btn_left) {
            if (null != diaLogBtnListener) {
                diaLogBtnListener.leftBtn();
            }

        } else if (i == R.id.dialog_btn_right) {
            if (null != diaLogBtnListener) {
                diaLogBtnListener.rightBtn();
            }

        }
    }

    public interface DiaLogBtnListener{
        void leftBtn();
        void rightBtn();
    }
}
