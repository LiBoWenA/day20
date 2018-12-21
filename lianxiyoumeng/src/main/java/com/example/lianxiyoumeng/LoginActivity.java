package com.example.lianxiyoumeng;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.qrcode.encoder.QRCode;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class LoginActivity extends AppCompatActivity {

    private ImageView imageView;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageView = findViewById(R.id.img);
        //生成二维码
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        creadQRcode();

    }
    public void creadQRcode(){
        QRcode qrCode = new QRcode(this,imageView,name);
        qrCode.execute(name);
    }

    static class QRcode extends AsyncTask<String,Void,Bitmap>{

        private WeakReference<Context> mContext;
        private WeakReference<ImageView> mimageview;
        public QRcode(Context context,ImageView imageView,String name){
            mContext = new WeakReference<>(context);
            mimageview = new WeakReference<>(imageView);
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String string = strings[0];
            if (TextUtils.isEmpty(string)){
                return  null;
            }
            int size = mContext.get().getResources().getDimensionPixelOffset(R.dimen.qr_code_size);
            return QRCodeEncoder.syncEncodeQRCode(string,size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null){
                mimageview.get().setImageBitmap(bitmap);
            }else{
                Toast.makeText(mContext.get(),"生成失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
