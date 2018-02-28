package com.appscyclone.themoviedb.other;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProcessDialog extends Dialog{
    @BindView(R.id.dialog_tvTitle)
    TextView tvTitle;
    String mTitle;
    public ProcessDialog(@NonNull Context context,String mTitle) {
        super(context);
        this.mTitle=mTitle;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_process);
        ButterKnife.bind(this);
        tvTitle.setText(mTitle);
    }
}
