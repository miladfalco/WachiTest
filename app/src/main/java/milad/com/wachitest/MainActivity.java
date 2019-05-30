package milad.com.wachitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import milad.com.wachitest.network.APIService;
import milad.com.wachitest.util.Constant;
import milad.com.wachitest.util.DialogOne;
import milad.com.wachitest.util.DialogThree;
import milad.com.wachitest.util.DialogTwo;
import milad.com.wachitest.util.RetrofitUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_username)
    EditText username;
    @BindView(R.id.edit_password)
    EditText password;
    @BindView(R.id._btn_send)
    Button send;
    @BindView(R.id.progress_one)
    ProgressBar progressBar;

    private DialogOne dialogOne;
    private DialogTwo dialogTwo;
    private DialogThree dialogThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id._btn_send)
    public void send() {
        progressBar.setVisibility(View.VISIBLE);
        dialogOne = new DialogOne(this);
        dialogTwo = new DialogTwo(this);
        dialogThree = new DialogThree(this);
        String userName = username.getText().toString();
        String passWord = password.getText().toString();


        APIService apiService = RetrofitUtil.getINSTANCE(userName, passWord, Constant.BASE_URL).create(APIService.class);
        apiService.sendRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String message = response.message();
                Log.i("==>>>", "onResponse: " + message);

                switch (message) {
                    case "OK":
                        progressBar.setVisibility(View.GONE);
                        dialogOne.dialogBuild().show();
                        break;
                    case "Not Found":
                        progressBar.setVisibility(View.GONE);
                        dialogTwo.dialogBuild().show();
                        break;
                    case "Payment Required":
                        progressBar.setVisibility(View.GONE);
                        dialogThree.dialogBuild().show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}

