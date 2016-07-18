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
       /* //��ȡһ������
        sp = this.getSharedPreferences("config", 0);
       String qq = sp.getString("qq", "");
       String psw =  sp.getString("psw", "");
        et_qq.setText(qq);
        et_psw.setText(psw);*/

		try {
			//�����ļ�������
			File file = new File(Environment.getExternalStorageDirectory(),"info.txt");
			if(file.exists()&&file.length()>0){  //�ж��ļ��Ƿ����
				FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String info = br.readLine(); //��ȡQQ�ʺź�����
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
    	//��ȡqq���뼰qq����
    	String qq = et_qq.getText().toString().trim();
    	String psw = et_psw.getText().toString().trim();
    	//�����ж�
    	if(TextUtils.isEmpty(qq)||TextUtils.isEmpty(psw)){ //����Ϊ��
    		Toast.makeText(this, "�˺Ż������벻��Ϊ��", 0).show();
    		return;
    	}
    	if(cb_remeber.isChecked()){//�ж��Ƿ��ס����
    		Log.i(TAG, "��ס����");
    	}else{
    		Log.i(TAG, "����ס����");
    	}
    	/*Editor edit = sp.edit();
    	edit.putString("qq", qq);//�洢qq����
    	edit.putString("psw", psw);//�洢qq����
    	edit.commit();  //ִ��
*/    	
    	
    		try {
    			//���sd���Ƿ����
    			if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
    				//������
    				Toast.makeText(this, "sd��������", 0).show();
    				return;
    			}
    			//�жϿ��ÿռ�
    			long size = Environment.getExternalStorageDirectory().getFreeSpace();
    			//ת���ַ��ܸ�ʽ
    			String info = Formatter.formatFileSize(this, size);
    			Toast.makeText(this, "���ÿռ�:"+info, 0).show();
    				
				File file = new File(Environment.getExternalStorageDirectory(),"info.txt"); //�����ļ�
				FileOutputStream fos = new FileOutputStream(file); //�����ļ������
				fos.write((qq+"##"+psw).getBytes());
				fos.close();
				Toast.makeText(this, "���ݱ���ɹ�", 0).show();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(this, "���ݱ���ʧ��", 0).show();
			}
		
    }
}
