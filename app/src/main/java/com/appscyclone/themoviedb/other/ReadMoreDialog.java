package com.appscyclone.themoviedb.other;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.appscyclone.themoviedb.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class ReadMoreDialog extends Dialog {
    @BindView(R.id.dialogReadMore_tvContent)
    TextView tvContent;
    private String mContent;
    public ReadMoreDialog(@NonNull Context context,String content) {
        super(context);
        this.mContent=content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_read_more);
        ButterKnife.bind(this);


        tvContent.setText(mContent);
    }

    @OnClick(R.id.dialogReadMore_ivClose)
    public void onClickClose(View view){
        if (view.getId()==R.id.dialogReadMore_ivClose){
            dismiss();
        }
    }
}
