package com.appscyclone.themoviedb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appscyclone.themoviedb.R;
import com.appscyclone.themoviedb.model.AuthModel;
import com.appscyclone.themoviedb.model.GuestSessionIdModel;
import com.appscyclone.themoviedb.networks.ApiInterface;
import com.appscyclone.themoviedb.networks.ApiUtils;
import com.appscyclone.themoviedb.other.LoggingDialog;
import com.appscyclone.themoviedb.utils.ConstantUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.actLogin_etUsername)
    EditText etUsername;
    @BindView(R.id.actLogin_etPassword)
    EditText etPassword;
    @BindView(R.id.actLogin_tvVia)
    TextView tvVia;

    private String mToken, mSession,mGuestSessionId;
    private boolean isCheckLogin = false;
    private LoggingDialog mLoggingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        getToken();
        getGuestSessionId();
    }


    @OnClick(R.id.actLogin_tvLogin)
    public void onLogin() {
     String user=etUsername.getText().toString().trim();
     String pass=etPassword.getText().toString().trim();
     if(!TextUtils.isEmpty(user)&& !TextUtils.isEmpty(pass)){
         mLoggingDialog=  new LoggingDialog(this);
         mLoggingDialog.show();
         checkLogIn(etUsername.getText().toString(), etPassword.getText().toString());
     }else {
         Toast.makeText(this,getString( R.string.please_input), Toast.LENGTH_SHORT).show();
     }
    }

    private void initView() {
        tvVia.setText(Html.fromHtml(getString(R.string.sign_up_via_website_themoviedb_org)));
    }

    public void checkLogIn(String user, String pass) {
        final Map<String,String> map = new HashMap<>();
        map.put("username", user);
        map.put("password", pass);
        map.put("request_token", mToken);
        final ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.checkLogin(map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                AuthModel authModel = new Gson().fromJson(response.body(), AuthModel.class);
                try {
                    isCheckLogin = authModel.isSuccess();
                    if (isCheckLogin) {
                        getSession();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                    mLoggingDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }

    public void getToken() {
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getToken();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                AuthModel model = new Gson().fromJson(response.body(), AuthModel.class);
                mToken = model.getRequestToken();
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }

    public void getSession() {
        Map<String,String> map = new HashMap<>();
        map.put(ConstantUtils.REQUEST_TOKEN, mToken);
        ApiInterface apiInterface = ApiUtils.getSOService();
        Call<JsonObject> call = apiInterface.getSession(map);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                AuthModel model = new Gson().fromJson(response.body(), AuthModel.class);
                mSession = model.getSessionID();
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(ConstantUtils.SESSION,mSession);
                intent.putExtra(ConstantUtils.GUEST_SESSION_ID,mGuestSessionId);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }
    public void getGuestSessionId(){
        ApiInterface apiInterface=ApiUtils.getSOService();
        Call<JsonObject> call=apiInterface.getGuestSessionId();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                GuestSessionIdModel model=new Gson().fromJson(response.body(),GuestSessionIdModel.class);
                mGuestSessionId=model.getGuestSessionId();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
