package com.itheima.qq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private EditText et_qq;
	private EditText et_psw;
	private CheckBox cb_remeber;
	//private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_qq = (EditText) findViewById(R.id.et_qq);
        et_psw = (EditText) findViewById(R.id.et_psw);
        cb_remeber = (CheckBox) findViewById(R.id.cb_remeber);    
       /* //获取一个参数
        sp = this.getSharedPreferences("config", 0);
       String qq = sp.getString("qq", "");
       String psw =  sp.getString("psw", "");
        et_qq.setText(qq);
        et_psw.setText(psw);*/

		try {
			//创建文件输入流
			File file = new File(Environment.getExternalStorageDirectory(),"info.txt");
			if(file.exists()&&file.length()>0){  //判断文件是否存在
				FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String info = br.readLine(); //读取QQ帐号和密码
				String qq = info.split("##")[0];
				String psw = info.split("##")[1];
				et_qq.setText(qq);
				et_psw.setText(psw);
			} 
			}catch (IOException e) {
			e.printStackTrace();
			}
						
			
    }
    public void click(View view){
    	//获取qq号码及qq密码
    	String qq = et_qq.getText().toString().trim();
    	String psw = et_psw.getText().toString().trim();
    	//进行判断
    	if(TextUtils.isEmpty(qq)||TextUtils.isEmpty(psw)){ //输入为空
    		Toast.makeText(this, "账号或者密码不能为空", 0).show();
    		return;
    	}
    	if(cb_remeber.isChecked()){//判断是否记住密码
    		Log.i(TAG, "记住密码");
    	}else{
    		Log.i(TAG, "不记住密码");
    	}
    	/*Editor edit = sp.edit();
    	edit.putString("qq", qq);//存储qq号码
    	edit.putString("psw", psw);//存储qq密码
    	edit.commit();  //执行
*/    	
    	
    		try {
    			//检查sd卡是否存在
    			if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
    				//不存在
    				Toast.makeText(this, "sd卡不存在", 0).show();
    				return;
    			}
    			//判断可用空间
    			long size = Environment.getExternalStorageDirectory().getFreeSpace();
    			//转成字符窜格式
    			String info = Formatter.formatFileSize(this, size);
    			Toast.makeText(this, "可用空间:"+info, 0).show();
    				
				File file = new File(Environment.getExternalStorageDirectory(),"info.txt"); //创建文件
				FileOutputStream fos = new FileOutputStream(file); //创建文件输出流
				fos.write((qq+"##"+psw).getBytes());
				fos.close();
				Toast.makeText(this, "数据保存成功", 0).show();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(this, "数据保存失败", 0).show();
			}
		
    }
}
