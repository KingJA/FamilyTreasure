package com.tdr.familytreasure.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.tdr.familytreasure.R;
import com.tdr.familytreasure.util.AppUtil;
import com.tdr.familytreasure.util.MyUtils;
import com.tdr.familytreasure.util.ToastUtil;
import com.tdr.familytreasure.zxing.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Linus_Xie on 2016/8/26.
 */
public class QrCodeActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "QrCodeActivity";
    public static final String IMAGE_DIR = Environment.getExternalStorageDirectory() + File.separator + "截屏";
    public static final String SCREEN_SHOT ="screenshot.png";
    private Context mContext;
    private String code = "";
    private Bitmap bitmapQrCode;
    private String name;
    private String idcard;
    private String photo;
    private TextView tv_name;
    private TextView tv_idcard;
    private ImageView iv_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        mContext = this;
        code = getIntent().getStringExtra("code");
        name = getIntent().getStringExtra("name");
        idcard = getIntent().getStringExtra("idcard");
        photo = getIntent().getStringExtra("photo");
        initView();
        initData();
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private ImageView image_qrCode;

    private void initView() {
        image_back = (ImageView) findViewById(R.id.fl_menu);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_idcard = (TextView) findViewById(R.id.tv_idcard);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        text_title.setText("关爱二维码");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("截屏");
        text_deal.setVisibility(View.VISIBLE);
        text_deal.setOnClickListener(this);

        image_qrCode = (ImageView) findViewById(R.id.image_qrCode);

    }


    private void initData() {
        tv_name.setText(name);
        tv_idcard.setText("身份证号:" + idcard);
        iv_avatar.setImageBitmap(MyUtils.stringtoBitmap(photo));
        try {
            bitmapQrCode = Utils.Create2DCode(code);
            image_qrCode.setImageBitmap(bitmapQrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_menu:
                finish();
                break;

            case R.id.text_deal:
                screenshot();
                break;
        }
    }

    private void screenshot() {
        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                //二次截图
                Bitmap  saveBitmap = Bitmap.createBitmap(AppUtil.getScreenWidth(), AppUtil.getScreenHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(saveBitmap);
                Paint paint = new Paint();
                canvas.drawBitmap(bmp, new Rect(0, 0,AppUtil.getScreenWidth(), AppUtil.getScreenHeight()),
                        new Rect(0, 0, AppUtil.getScreenWidth(), AppUtil.getScreenHeight()), paint);

                File imageDir = new File(IMAGE_DIR);
                if (!imageDir.exists()) {
                    imageDir.mkdir();
                }
                String imageName = SCREEN_SHOT;
                File file = new File(imageDir, imageName);
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream os = new FileOutputStream(file);
                saveBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();

                //将截图保存至相册并广播通知系统刷新
                MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), imageName, null);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                sendBroadcast(intent);
                ToastUtil.showToast("已成功将截图保存到/SD卡/截屏 文件夹下");
//                successHandler.sendMessage(Message.obtain());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            initHandler.sendMessage(Message.obtain());
        }

    }
}
