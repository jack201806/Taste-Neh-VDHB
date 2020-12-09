package com.example.app.Details;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.app.R;
import com.example.app.fragments.MyFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Detail extends AppCompatActivity {
    private ImageView back;
    private RelativeLayout head;
    private RelativeLayout name;
    private RelativeLayout phone;
    private ImageView myHead;
    private TextView userName;
    private TextView userPhone;
    //头像
    private Bitmap bitmap;
    //图片链接，姓名，电话
    private String url;
    //自定义toast样式
    private View my_toast;
    private Toast toast;
    private TextView tv;

    //3个int，判断相机还是相册
    private static final int CAMERA_C0DE = 48;
    private static final int ALBUM_C0DE = 60;
    private static int flag_img = 0;
    //修改头像
    private AlertDialog dialog_0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        //使用自定义Toast
        my_toast = getLayoutInflater().inflate(R.layout.my_toast,null);
        toast = new Toast(getApplicationContext());
        toast.setView(my_toast);
        tv = my_toast.findViewById(R.id.toast_info);
        tv = my_toast.findViewById(R.id.toast_info);

        back=findViewById(R.id.back);
        head=findViewById(R.id.head);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        myHead=findViewById(R.id.my_head);
        userName=findViewById(R.id.user_name);
        userPhone=findViewById(R.id.user_phone);
        url=getIntent().getStringExtra("url");
        //设置默认值
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("url")).circleCrop().into(myHead);
        userName.setText(getIntent().getStringExtra("name"));
        userPhone.setText(getIntent().getStringExtra("phone"));


        //返回键，提交修改信息到MyFragment
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] buf = new byte[1024*1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                buf= baos.toByteArray();
                Intent i=new Intent(getApplicationContext(), MyFragment.class);
                i.putExtra("userName",userName.getText().toString());
                i.putExtra("userPhone",userPhone.getText().toString());
                i.putExtra("userHead", buf);
                setResult(51,i);
                finish();
            }
        });
        //获取照片
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestImg();
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Name.class);
                startActivityForResult(intent,11);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Phone.class);
                startActivityForResult(intent,11);
            }
        });
    }

    //换头像
    private void requestImg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        AlertDialog dialog = null;
        builder.setTitle("请选择")
                .setItems(new String[]{"相机","相册","取消"},new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                requestImg();
                                //  相机
                                toCamera();
                                flag_img = CAMERA_C0DE;
                                break;
                            case 1:
                                requestImg();
                                //  相册
                                toAlbum();
                                flag_img = ALBUM_C0DE;
                                break;
                            case 2:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
        dialog = builder.create();
        dialog_0 = dialog;
        //这句话必须加！！！否则选择列表根本不会显示（调试后得到的结论）
        dialog.show();
    }
    private void toAlbum() {
        Intent album = new Intent(Intent.ACTION_PICK);
        album.setType("image/*");
        album.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(album,1);
        showToast("跳转相册成功");
    }
    private void toCamera() {
        //  调用系统内置拍照程序进行拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    //  申请获取相机、存储等权限
    private void request_img_permission() {
        //  申请获取相机权限
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA
        )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    1
            );
        }

        //  申请获取读写权限
        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.READ_EXTERNAL_STORAGE
        )!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1
            );
        }

        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.WRITE_EXTERNAL_STORAGE
        )!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1
            );
        }

        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.MANAGE_EXTERNAL_STORAGE
        )!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                    1
            );
        }
    }
    /**
     * 从“相机”或“相册”返回结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && flag_img==CAMERA_C0DE){
            bitmap = (Bitmap) data.getExtras().get("data");
            myHead.setImageBitmap(bitmap);
            saveBitmap(bitmap);
            dialog_0.dismiss();
            flag_img = 0;
        }
        if (requestCode==1 && resultCode==RESULT_OK && flag_img==ALBUM_C0DE){
            Uri uri = data.getData();
            String img_url = uri.getPath();
            ContentResolver resolver = this.getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                myHead.setImageBitmap(bitmap);
                saveBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            dialog_0.dismiss();
            flag_img = 0;
        }
        if (requestCode==11 && resultCode==13 ){
            userName.setText(data.getStringExtra("userName"));
        }
        if (requestCode==11&& resultCode==14 ){
            userPhone.setText(data.getStringExtra("userPhone"));
        }
    }

    /**
     * 将“相机”或“相册”里的照片，保存到手机上
     * @param bitmap
     */
    private void saveBitmap(Bitmap bitmap) {
        String external_dir = this.getFilesDir().getAbsolutePath()+"/imgs/";
        OutputStream out = null;
        String filename = "";// 声明文件名
        //  以保存时间为文件名
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyyMMdd_HHmmss");
        filename = "cake_online"+sdf.format(date)+".png";
        //  如果是新增的蛋糕，就是cake_online_yyyyMMdd_HHmmss.png
        //  如果是修改的蛋糕，仍然是原有的名称
        File file = new File(external_dir,
                url!=null?url:filename);
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.close();
            showToast("照片已保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //定义showToast方法
    private void showToast(String str) {
        tv.setText(str);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}