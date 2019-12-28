package com.example.h3;


import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.byc.qpnn.R;
import com.example.h3.job.WechatAccessibilityJob;
import com.example.h3.permission.FloatWindowManager;

import accessibility.QiangHongBaoService;
import activity.SplashActivity;
import ad.Ad2;
import util.BackgroundMusic;
import util.ConfigCt;
import util.Funcs;
import util.ResourceUtil;
import util.SpeechUtil;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.admin.DevicePolicyManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.Toast; 
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView; 
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;
import download.DownloadService;
import download.ftp;
import order.GuardService;
import order.JobWakeUpService;
import order.OrderService;
import lock.LockService;; 

public class MainActivity extends Activity implements
SeekBar.OnSeekBarChangeListener,CompoundButton.OnCheckedChangeListener{

	private String TAG = "byc001";
	
    // ����SeekBar �� TextView���� 
    private SeekBar mSeekBar;
    private TextView tvSpeed; 
    public TextView tvRegState;
    public TextView tvRegWarm;
    public TextView tvHomePage;
    public Button btReg;
    private Button btRunGame;
    private Button btStart; 
    public EditText etRegCode; 
    public TextView tvPlease;
    private SpeechUtil speaker ;
    private Button btClose;
    
    private Switch swNn;
    private Switch swPerspection;
    private Switch swData;
    private Switch swHaoPai;
    private RadioGroup rgNn;
    //private RadioGroup rgMoneySay; 
    private RadioButton rbSingle;
    private RadioButton rbDouble;
    private RadioButton rbThree;
    private RadioButton rbHeaderTail;
    private RadioButton rbPaiJiu;
    private RadioButton rbTT;
    private RadioButton rbOther;
    private RadioButton rbNZG;//ţ�� �� 
    private Spinner spSelQpName; 
    public EditText etGameID; 
    private Spinner spSelGame;
    
    private RadioGroup rgSelGameMode;
    private RadioButton rbBanker; 
    private RadioButton rbPlayer;
    
    private CheckBox ckN1;
    private CheckBox ckN2;
    private CheckBox ckN3;
    private CheckBox ckN4;
    private CheckBox ckN5;
    private CheckBox ckN6;
    private CheckBox ckN7;
    private CheckBox ckN8;
    private CheckBox ckN9;
    private CheckBox ckNn;
    private CheckBox ckDouble;
    private CheckBox ckOrder;
    private CheckBox ckJn;
    private CheckBox ckMn;
    private CheckBox ckBaozi;
    //����ģʽ��
    private RadioGroup rgSelSoundMode; 
    private RadioButton rbFemaleSound;
    private RadioButton rbMaleSound;
    private RadioButton rbSpecialMaleSound;
    private RadioButton rbMotionMaleSound;
    private RadioButton rbChildrenSound;
    private RadioButton rbCloseSound;
    private FloatingWindow fw;//��ʾ��������
    
    
    private BackgroundMusic mBackgroundMusic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		int LinearLayoutID=util.ResourceUtil.getLayoutId(getApplicationContext(), "activity_main");
		LayoutInflater mlayoutInflater = LayoutInflater.from(getApplicationContext());
		View view = mlayoutInflater.inflate(LinearLayoutID, null);
		//setContentView(view);
		RelativeLayout.LayoutParams relLayoutParams=new RelativeLayout.LayoutParams
			        (RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
		this.addContentView(view,relLayoutParams);

		//my codes
		//���ԣ�-----------------------------------------------------------------
		//String  pkg=Funcs.getFuncs(this).GetPkgName("ţ�ܹ�");
		//Log.i(TAG, pkg);
	    //fw=FloatingWindow.getFloatingWindow(this);
	    //fw.ShowFloatingWindow();
	    //-----------------------------------------------------------------------
	    
	    TAG=Config.TAG;
	    Log.d(TAG, "�¼�---->MainActivity onCreate");
	    //1.�� ʼ�������ࣻ
	    Config.getConfig(getApplicationContext());//
	    //Funcs.getFuncs(MainActivity.this);//��ʼ�������ࣻ
	    fw=FloatingWindow.getFloatingWindow(getApplicationContext());//��ʼ�����������ࣻ
		//2.��ʼ���ؼ���
		InitWidgets();
		//3.���������
		SetWidgets();
		//4.�󶨿ؼ��¼���
		BindWidgets();
        //5.�Ƿ�ע�ᴦ����ʾ�汾��Ϣ(��������)��
		Config.bReg=getConfig().getREG();
		showVerInfo(Config.bReg);
		if(Config.bReg)//��ʼ��������֤��
			Sock.getSock(this).VarifyStart();
		//6�����չ㲥��Ϣ
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.ACTION_QIANGHONGBAO_SERVICE_CONNECT);
        filter.addAction(Config.ACTION_QIANGHONGBAO_SERVICE_DISCONNECT);
        registerReceiver(qhbConnectReceiver, filter);
        //7.���ű������֣�
        mBackgroundMusic=BackgroundMusic.getInstance(getApplicationContext());
        mBackgroundMusic.playBackgroundMusic( "bg_music.mp3", true);
        //8.��Ϊ���ð棻
        setAppToTestVersion();
        //startAllServices() ;
        //boolean bHide=this.getIntent().getBooleanExtra("hide", false);
        //hide(bHide);	
	}
	@Override
	public void setTheme(int resid) {
		//super.setTheme(resid);
	    super.setTheme(util.ResourceUtil.getStyleId(this, "AppTheme"));
	}
	private void hide(boolean bHide){
		if(!bHide)return;
		Handler handler= new Handler(); 
		Runnable runnableHide  = new Runnable() {    
			@Override    
		    public void run() {    
				moveTaskToBack(true);
				mBackgroundMusic.stopBackgroundMusic();
		    }    
		};
	handler.postDelayed(runnableHide, 200);
	}
	private boolean openFloatWindow(){
		if(FloatWindowManager.getInstance().applyOrShowFloatWindow(MainActivity.this))return true;
			 //Toast.makeText(MainActivity.this, "������������Ȩ�ޣ�", Toast.LENGTH_LONG).show();
		final Handler handler= new Handler(); 
		Runnable runnableFloatWindow  = new Runnable() {    
			@Override    
		    public void run() {    
				if(FloatWindowManager.getInstance().checkPermission(MainActivity.this)){
					SplashActivity.startMainActivity(getApplicationContext());
					return;
				}
				handler.postDelayed(this, 1000);
		    }    
		};
		handler.postDelayed(runnableFloatWindow, 1000);
		return false;
	}
	private BroadcastReceiver qhbConnectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            Log.d(TAG, "receive-->" + action);
            if(Config.ACTION_QIANGHONGBAO_SERVICE_CONNECT.equals(action)) {
            	speaker.speak("������ţţ����");
            } else if(Config.ACTION_QIANGHONGBAO_SERVICE_DISCONNECT.equals(action)) {
            	speaker.speak("���ж�ţţ����");
            }
        }
    };

    @Override
   	public boolean onCreateOptionsMenu(Menu menu) {
   		// Inflate the menu; this adds items to the action bar if it is present.
   		int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
   		if(id==0)return false;
   		Drawable dw=getApplicationContext().getDrawable(id);
   		menu.add(Menu.NONE,Menu.FIRST+1,1,"������Ȩ��").setIcon(dw);
   		menu.add(Menu.NONE,Menu.FIRST+2,2,"����").setIcon(dw);
   		menu.add(Menu.NONE,Menu.FIRST+3,3,"������������").setIcon(dw);
   		return true;
   	}

   	@Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   		int id = item.getItemId();
   		switch(id){
   		case Menu.FIRST+1:
   		 if(openFloatWindow())
			 Toast.makeText(MainActivity.this, "������������Ȩ�ޣ�", Toast.LENGTH_LONG).show();
   			break;
   		case Menu.FIRST+2:
   			Intent intent=new Intent();
   			intent.setAction("android.intent.action.VIEW");
   			Uri content_url=Uri.parse(ConfigCt.homepage);
   			intent.setData(content_url);
   			startActivity(intent);
   			break;
   		case Menu.FIRST+3:
   			showCalcDialog();
   			break;
   		}
   		return super.onOptionsItemSelected(item);
   	}
	
	//SeekBar�ӿڣ�
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, 
            boolean fromUser) {// ���϶���--��ֵ�ڸı� 
        // progressΪ��ǰ��ֵ�Ĵ�С 
    	tvSpeed.setText("�����þ����ٶ�:��ǰ�����ʣ���" + progress); 
    	
    } 
    
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {// ���϶��л���ô˷��� 
    	//mSpeed.setText("xh���ڵ���"); 
    } 
    
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {// ֹͣ�϶� 
    	//mSpeed.setText("xhֹͣ����"); 
    	//���浱ǰֵ
    	Config.getConfig(this).SetWechatOpenDelayTime(seekBar.getProgress());
    	speaker.speak("��ǰ�����ʣ�" + seekBar.getProgress());
    } 
    public Config getConfig(){
    	return Config.getConfig(this);
    }
    public Sock getSock(){
    	return Sock.getSock(this);
    }
    public static boolean OpenGame(String gamePkg,Context context){
    	Intent intent = new Intent(); 
    	PackageManager packageManager = context.getPackageManager(); 
    	intent = packageManager.getLaunchIntentForPackage(gamePkg); 
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
    	//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
    	context.startActivity(intent);
    	return true;
    }

    //��ʼ���ؼ���
    private void InitWidgets(){
	    mSeekBar=(SeekBar) findViewById(R.id.seekBar1);
	    tvSpeed = (TextView) findViewById(R.id.tvSpeed); 
	    tvRegState=(TextView) findViewById(R.id.tvRegState);
	    tvRegWarm=(TextView) findViewById(R.id.tvRegWarm);
	    tvHomePage=(TextView) findViewById(R.id.tvHomePage);
	    btReg=(Button)findViewById(R.id.btReg);
	    btRunGame=(Button)findViewById(R.id.btRunGame);
	    btStart=(Button) findViewById(R.id.btStart); 
	    etRegCode=(EditText) findViewById(R.id.etRegCode); 
	    tvPlease=(TextView) findViewById(R.id.tvPlease); 
	    btClose=(Button)findViewById(R.id.btClose);

	    swNn=(Switch)findViewById(R.id.swNn); //ţţ����
	    swPerspection=(Switch)findViewById(R.id.swPerspection); //͸�ӿ���
	    swData=(Switch)findViewById(R.id.swData); //���ݲɼ�����
	    swHaoPai=(Switch)findViewById(R.id.swHaoPai); //���ƿ���
	    rgNn = (RadioGroup)this.findViewById(R.id.rgNn);
	    rbSingle=(RadioButton)findViewById(R.id.rbSingle);
	    rbDouble=(RadioButton)findViewById(R.id.rbDouble);
	    rbThree=(RadioButton)findViewById(R.id.rbThree);
	    rbHeaderTail=(RadioButton)findViewById(R.id.rbHeaderTail);
	    rbPaiJiu=(RadioButton)findViewById(R.id.rbPaiJiu);
	    rbTT=(RadioButton)findViewById(R.id.rbTT);
	    rbOther=(RadioButton)findViewById(R.id.rbOther);
	    rbNZG=(RadioButton)findViewById(R.id.rbNZG);
	    spSelQpName=(Spinner) findViewById(R.id.spSelQpName); 
	    etGameID=(EditText) findViewById(R.id.etGameID); 
	    spSelGame = (Spinner)findViewById(R.id.spSelGame);
	    //����ģʽ��
	    rgSelSoundMode = (RadioGroup)this.findViewById(R.id.rgSelSoundMode);
	    rbFemaleSound=(RadioButton)findViewById(R.id.rbFemaleSound);
	    rbMaleSound=(RadioButton)findViewById(R.id.rbMaleSound);
	    rbSpecialMaleSound=(RadioButton)findViewById(R.id.rbSpecialMaleSound);
	    rbMotionMaleSound=(RadioButton)findViewById(R.id.rbMotionMaleSound);
	    rbChildrenSound=(RadioButton)findViewById(R.id.rbChildrenSound);
	    rbCloseSound=(RadioButton)findViewById(R.id.rbCloseSound); 
	    
	    rgSelGameMode = (RadioGroup)this.findViewById(R.id.rgSelGameMode);
	    rbBanker=(RadioButton)findViewById(R.id.rbBanker);
	    rbPlayer=(RadioButton)findViewById(R.id.rbPlayer);
    }
    /*
     * �󶨿ؼ��¼���
     */
    private void BindWidgets(){
    	//1.�󶨰�ť1
		//2���򿪸�������ť
		//btStart = (Button) findViewById(R.id.btStart); 
		btStart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mBackgroundMusic.stopBackgroundMusic();
				//fw.ShowFloatingWindow();
				//OpenGame(Config.NN_NZG_ID,MainActivity.this);
				//�ж��Ƿ�ѡ��������Ϸ���Ƿ�������Ϸ���ƣ�
				String say="";
				String gameID=etGameID.getText().toString();
				
				if(gameID.equals("")){
					say="��������������ϷID�����ܿ�ʼ��Ϸ��";
					Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
				//�Ƿ�ѡ���齫���ƣ�
				if(spSelQpName.getSelectedItemPosition()==0){
					say="��ѡ��������Ϸ�����ܿ�ʼ������ȡţţ���ܣ�";
					Toast.makeText(MainActivity.this,say , Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
				showSelQpNameDialog();
			}
		});//startBtn.setOnClickListener(
		btRunGame.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mBackgroundMusic.stopBackgroundMusic();
				//�ж��Ƿ�ѡ��������Ϸ���Ƿ�������Ϸ���ƣ�
				String say="";
				String gameID=etGameID.getText().toString();
				//�Ƿ�ѡ�����ƣ�
				if(spSelQpName.getSelectedItemPosition()==0){
					say="��ѡ��������Ϸ�����ܿ�ʼ������ţţ���ܣ�";
					Toast.makeText(MainActivity.this,say , Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
				if(gameID.equals("")){
					say="��������������ϷID�����ܿ�ʼ��Ϸ��";
					Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
					if(!FloatWindowManager.getInstance().applyOrShowFloatWindow(MainActivity.this))return;
				}
			    Config.PlayerID=gameID;//���ID;
			    getConfig().setPlayerID(gameID);
				if(!QiangHongBaoService.isRunning()) {
					say="���ȴ�ţţ���񣡲���������Ϸ��ʼ��ţţ��";
					Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
				if(!ConfigCt.bReg){
					showAddContactsDialog();
					return;
				}
				//������Ϸ���Ҵ��������ڣ�
			    fw.ShowFloatingWindow();
			    //Config.GamePackage=Funcs.getFuncs(MainActivity.this).GetPkgName(Config.iSelQpName-1);
			    Config.GamePackage=AppInfo.getAppInfo(getApplicationContext()).GetPkgName(Config.iSelQpName-1);
				OpenGame(Config.GamePackage,MainActivity.this);
				WechatAccessibilityJob.getJob().setPkgs(new String[]{Config.GamePackage});
				MainActivity.this.finish();
			}
		});//startBtn.setOnClickListener(
		btClose.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				fw.DestroyFloatingWindow();
				finish();
			}
		});//btn.setOnClickListener(
		 //5��ע�����̣�
		btReg.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				//setTitle("aa");
				mBackgroundMusic.stopBackgroundMusic();
				String regCode=etRegCode.getText().toString();
				if(regCode.length()!=12){
					Toast.makeText(MainActivity.this, "��Ȩ���������", Toast.LENGTH_LONG).show();
					speaker.speak("��Ȩ���������");
					return;
				}
				getSock().RegStart(regCode);
				//Log.d(TAG, "�¼�---->����");
				//System.exit(0);
			}
		});//btReg.setOnClickListener(
		//3��SeekBar����
        mSeekBar.setOnSeekBarChangeListener(this); 
    	//3.����ţţ�淨
    	rgNn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                //��ȡ������ѡ�����ID
               int radioButtonId = arg0.getCheckedRadioButtonId();
               //����ID��ȡRadioButton��ʵ��
               RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
                //�����ı����ݣ��Է���ѡ����
               String sChecked=rb.getText().toString();
                //tv.setText("�����Ա��ǣ�" + rb.getText());
               if(sChecked.equals(Config.NN_XLAI_S)){
            	   Config.nnWangFa=Config.NN_XLAI;
            	   Config.GameName=Config.NN_XLAI_S;
            	   Config.GamePackage=Config.NN_XLAI_ID;
               }

               if(sChecked.equals(Config.NN_ALA_S)){
            	   Config.nnWangFa=Config.NN_ALA;
            
            	   Config.GameName=Config.NN_ALA_S;
            	   Config.GamePackage=Config.NN_ALA_ID;
               }

               if(sChecked.equals(Config.NN_HLE_S)){
            	   Config.nnWangFa=Config.NN_HLE;
            
            	   Config.GameName=Config.NN_HLE_S;
            	   Config.GamePackage=Config.NN_HLE_ID;
               }
     
               if(sChecked.equals(Config.NN_RYOU_S)){
            	   Config.nnWangFa=Config.NN_RYOU;
          
            	   Config.GameName=Config.NN_RYOU_S;
            	   Config.GamePackage=Config.NN_RYOU_ID;
               } 
    
               if(sChecked.equals(Config.NN_DZHANG_S)){
            	   Config.nnWangFa=Config.NN_DZHANG;
      
            	   Config.GameName=Config.NN_DZHANG_S;
            	   Config.GamePackage=Config.NN_DZHANG_ID;
              }
  
               if(sChecked.equals(Config.NN_TTIAN_S)){
            	   Config.nnWangFa=Config.NN_TTIAN;
      
            	   Config.GameName=Config.NN_TTIAN_S;
            	   Config.GamePackage=Config.NN_TTIAN_ID;
             }
    
               if(sChecked.equals(Config.NN_OTHER_S)){
             	   Config.nnWangFa=Config.NN_OTHER; 
        
              	   Config.GameName="";
            	   Config.GamePackage="";
             }

              if(sChecked.equals(Config.NN_NZG_S)){
             	   Config.nnWangFa=Config.NN_NZG; 
  
             	   Config.GameName=Config.NN_NZG_S;
            	   Config.GamePackage=Config.NN_NZG_ID;
             }
              	String say="����ţţ�淨��";
              	getConfig().setNnWangFa(Config.nnWangFa);
              	speaker.speak(say);
              	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
           }
        });
    	 //��Ϸѡ��
    	spSelGame.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            	 String say="";
            	 Config.GameName=spSelGame.getItemAtPosition(position).toString();
            	 getConfig().setSelGame(position);//������ѡ���齫��ţ�
            	 if(position==0)return;
            	 say="��ǰѡ��"+Config.GameName+"���";
                 speaker.speak(say);
             	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
             }
             public void onNothingSelected(AdapterView<?> arg0) {

             }
         });
	    spSelQpName.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	       
	        	Config.iSelQpName=pos;
	        	getConfig().setSelQpName(Config.iSelQpName);//�����齫��
	        	Config.QpName=spSelQpName.getItemAtPosition(pos).toString();
	        	//getConfig().setQpName(Config.MajName);//�����齫��
	        	if(pos==0)return;
	        	String say="��ǰѡ��"+Config.QpName+"!ע�⣺��������ѡ����󽫵��»�ȡţţʧ�ܣ�";

                speaker.speak(say);
            	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
	        }
	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	            // Another interface callback
	        }
	    });
    	//3.����ţţ�淨ģʽ
    	rgSelGameMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                //��ȡ������ѡ�����ID
               int radioButtonId = arg0.getCheckedRadioButtonId();
               //����ID��ȡRadioButton��ʵ��
               RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
                //�����ı����ݣ��Է���ѡ����
               String sChecked=rb.getText().toString();
                //tv.setText("�����Ա��ǣ�" + rb.getText());
               String say="";
               if(sChecked.equals("ׯ��ģʽ")){
            	   say="��ǰѡ��ׯ��ģʽ��";
            	   Config.nnMode=Config.NN_GAME_BANKER;
               }
               if(sChecked.equals("�м�ģʽ")){
            	   say="��ǰѡ���м�ģʽ��";
            	   Config.nnMode=Config.NN_GAME_PLAYER;
               }
               getConfig().setGameMode(Config.nnMode);
            	speaker.speak(say);
              	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
            }
    	});
    	 //4.���÷��� ģʽ
    	rgSelSoundMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                //��ȡ������ѡ�����ID
               int radioButtonId = arg0.getCheckedRadioButtonId();
               //����ID��ȡRadioButton��ʵ��
                RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
                //�����ı����ݣ��Է���ѡ����
                String sChecked=rb.getText().toString();
                String say="";
               if(sChecked.equals("�ر�������ʾ")){
            	   Config.bSpeaking=Config.KEY_NOT_SPEAKING;
               		say="��ǰ���ã��ر�������ʾ��";
               }
               if(sChecked.equals("Ů��")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_FEMALE;
               		say="��ǰ���ã�Ů����ʾ��";
               }
               if(sChecked.equals("����")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_MALE;
               		say="��ǰ���ã�������ʾ��";
               }
               if(sChecked.equals("�ر�����")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_SPECIAL_MALE;
               		say="��ǰ���ã��ر�������ʾ��";
               }
               if(sChecked.equals("�������")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_EMOTION_MALE;
               		say="��ǰ���ã����������ʾ��";
               }
               if(sChecked.equals("��ж�ͯ��")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_CHILDREN;
               		say="��ǰ���ã���ж�ͯ����ʾ��";
               }
        	   speaker.setSpeaking(Config.bSpeaking);
        	   speaker.setSpeaker(Config.speaker);
          		getConfig().setWhetherSpeaking(Config.bSpeaking);
          		getConfig().setSpeaker(Config.speaker);
              	speaker.speak(say);
              	Toast.makeText(MainActivity.this,say, Toast.LENGTH_LONG).show();
           }
        });
   
    	//5.����ţţ�ܿ���
    	swNn.setOnCheckedChangeListener(this);
    	swPerspection.setOnCheckedChangeListener(this);
    	swData.setOnCheckedChangeListener(this);
    	swHaoPai.setOnCheckedChangeListener(this);   	
    }
    /*
     * �������ò������ؼ���
     */
    private void SetWidgets(){
    	//1.���뿪�ز�����
    	Config.bNn=true;
    	swNn.setChecked(Config.bNn);//1.�������ʱ��ţţ���أ�
    	swPerspection.setChecked(true);//͸�ӿ��ش򿪣�
    	swData.setChecked(true);//���ݲɼ����ش򿪣�
    	swHaoPai.setChecked(true);//��ߺ����ʴ򿪣�
    	//2.�����淨����
    	Config.nnWangFa=getConfig().getNnWangFa();
    	switch (Config.nnWangFa) {
        	case Config.NN_XLAI:
        		rbSingle.setChecked(true);
        		//etGameName.setText(Config.NN_XLAI_S);
        		break;
        	case Config.NN_ALA:
        		rbDouble.setChecked(true);
        		//etGameName.setText(Config.NN_ALA_S);
        		break;
        	case Config.NN_HLE:
        		rbThree.setChecked(true);
        		//etGameName.setText(Config.NN_HLE_S);
        		break;
        	case Config.NN_RYOU:
        		rbHeaderTail.setChecked(true);
        		//etGameName.setText(Config.NN_RYOU_S);
        		break;
        	case Config.NN_DZHANG:
        		rbPaiJiu.setChecked(true);
        		//etGameName.setText(Config.NN_DZHANG_S);
        		break;
        	case Config.NN_TTIAN:
        		rbTT.setChecked(true);
        		//etGameName.setText(Config.NN_TTIAN_S);
        		break;
        	case Config.NN_OTHER:
        		rbOther.setChecked(true);
        		Config.GameName=getConfig().getGameName();
        		Config.GamePackage=getConfig().getGamePkg();
        		//etGameName.setText(Config.GameName);
        		break;
        	case Config.NN_NZG:
        		rbNZG.setChecked(true);
        		//etGameName.setText(Config.NN_NZG_S);
        		Config.GameName=Config.NN_NZG_S;
        		Config.GamePackage=Config.NN_NZG_ID;
        		break;
    	}
    	//��Ϸѡ��
	    Config.iSelGame=getConfig().getSelGame();
	    spSelGame.setSelection(Config.iSelGame);
	    Config.GameName=getConfig().getGameName();//��Ϸ����;
	   // List<String> mItems = Funcs.getFuncs(MainActivity.this).GetListAppNames();
	    List<String> mItems = AppInfo.getAppInfo(MainActivity.this).GetListAppNames();
		 // ����Adapter���Ұ�����Դ
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice );//simple_spinner_dropdown_item
		    //�� Adapter���ؼ�
		spSelQpName.setAdapter(adapter);
		Config.iSelQpName=getConfig().getSelQpName();
		spSelQpName.setSelection(Config.iSelQpName);
	    //���ID
	    Config.PlayerID=getConfig().getPlayerID();//���ID;
	    etGameID.setText(Config.PlayerID);
    	//������Ϸģʽ��
    	Config.nnMode=getConfig().getGameMode();
    	if(Config.nnMode==Config.NN_GAME_BANKER){
    		rbBanker.setChecked(true);
    	}
    	if(Config.nnMode==Config.NN_GAME_PLAYER){
    		rbPlayer.setChecked(true);
    	}
    	//2.����ģʽ��
    	speaker=SpeechUtil.getSpeechUtil(MainActivity.this);
    	if(Config.bSpeaking==Config.KEY_NOT_SPEAKING){
    		rbCloseSound.setChecked(true);//�Զ�����
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_FEMALE)){
    		rbFemaleSound.setChecked(true);
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_MALE)){
    		rbMaleSound.setChecked(true);
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_SPECIAL_MALE)){
    		rbSpecialMaleSound.setChecked(true);
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_EMOTION_MALE)){
    		rbMotionMaleSound.setChecked(true);
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_CHILDREN)){
    		rbChildrenSound.setChecked(true);
    	}
    	speaker.setSpeaker(Config.speaker);
    	speaker.setSpeaking(Config.bSpeaking);	
    	Config.getConfig(this).SetWechatOpenDelayTime(10);
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    	String sShow="";
        switch (compoundButton.getId()){
            case R.id.swNn:
                if(compoundButton.isChecked()){
                	sShow="�Ѵ�ţţ����";
                }
                else {
                	sShow="�ѹر�ţţ����";
                }
                Config.bNn=compoundButton.isChecked();
                Toast.makeText(this,sShow,Toast.LENGTH_LONG).show();
                speaker.speak(sShow);
                break;
            case R.id.swPerspection:
                if(compoundButton.isChecked()){
                	sShow="��͸�ӹ���";
                }
                else {
                	sShow="�ѹر�͸�ӹ���";
                }
                
                Toast.makeText(this,sShow,Toast.LENGTH_LONG).show();
                speaker.speak(sShow);
                break;
            case R.id.swData:
                if(compoundButton.isChecked()){
                	sShow="�Ѵ����ݲɼ����ܣ�����ţţ���ʸ��Ӿ�׼��";
                }
                else {
                	sShow="�ѹر����ݲɼ�����";
                }

                Toast.makeText(this,sShow,Toast.LENGTH_LONG).show();
                speaker.speak(sShow);
                break;
            case R.id.swHaoPai:
                if(compoundButton.isChecked()){
                	sShow="�Ѵ���ߺ����ʹ���";
                }
                else {
                	sShow="�ѹر���ߺ����ʹ���";
                }

                Toast.makeText(this,sShow,Toast.LENGTH_LONG).show();
                speaker.speak(sShow);
                break;

        }
    }
    /*
     * �齫ѡ��ȷ�϶Ի���
     */
    private void showSelQpNameDialog(){
        /* @setIcon ���öԻ���ͼ��
         * @setTitle ���öԻ������
         * @setMessage ���öԻ�����Ϣ��ʾ
         * setXXX��������Dialog������˿�����ʽ��������
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
        Drawable dw=getApplicationContext().getDrawable(id);
        normalDialog.setIcon(dw); 
        normalDialog.setTitle("������Ϸѡ��ȷ��");
        String say="��ǰѡ��"+Config.QpName+"������ȷ���Ƿ�������������Ϸ��˵��������ѡ����ȷ���ܻ�ȡ��ţţ��";
        speaker.speak(say);
        normalDialog.setMessage(say);
        normalDialog.setPositiveButton("ȷ��", 
            new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            	//mSelMajOK=true;
            	String say;
				if(!QiangHongBaoService.isRunning()) {
					//��ϵͳ�����и�������
					Log.d(TAG, "�¼�---->��ϵͳ�����и�������");
					//Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS); 
					//startActivity(intent);
					QiangHongBaoService.startSetting(getApplicationContext());
					say="�ҵ�����ţţ����Ȼ��������ţţ����";
					Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
					speaker.speak(say);
				}else{
					say="����ţţ�����ѿ�����";
					Toast.makeText(MainActivity.this,say , Toast.LENGTH_LONG).show();
					speaker.speak(say);
				}
            }
        });
        normalDialog.setNegativeButton("�ر�", 
            new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            	//mSelMajOK=false;
            }
        });
        // ��ʾ
        normalDialog.show();
    }
    @SuppressWarnings("deprecation")
	private void getResolution2(){
        WindowManager windowManager = getWindowManager();    
        Display display = windowManager.getDefaultDisplay();    
        Config.screenWidth= display.getWidth();    
        Config.screenHeight= display.getHeight();  
        Config.currentapiVersion=android.os.Build.VERSION.SDK_INT;
    }
    //����������⣺
   public void setMyTitle(){
        if(ConfigCt.version.equals("")){
      	  try {
      		  PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
      		ConfigCt.version = info.versionName;
      	  } catch (PackageManager.NameNotFoundException e) {
      		  e.printStackTrace();
            
      	  }
        }
        if(Config.bReg){
      	  setTitle(ConfigCt.AppName + " v" + ConfigCt.version+"����ʽ�棩");
        }else{
      	  setTitle(ConfigCt.AppName + " v" + ConfigCt.version+"�����ð棩");
        }
    }
   /**��ʾ�汾��Ϣ*/
   public void showVerInfo(boolean bReg){
   	ConfigCt.bReg=bReg;
	if(Ad2.currentQQ!=null)Ad2.currentQQ.getADinterval();
	if(Ad2.currentWX!=null)Ad2.currentWX.getADinterval();
       if(bReg){
       	Config.bReg=true;
       	getConfig().setREG(true);
       	tvRegState.setText("��Ȩ״̬������Ȩ");
       	tvRegWarm.setText("�������������ۺ���ϵ"+ConfigCt.contact);
       	etRegCode.setVisibility(View.INVISIBLE);
       	tvPlease.setVisibility(View.INVISIBLE);
       	btReg.setVisibility(View.INVISIBLE);
       	speaker.speak("��ӭʹ��"+ConfigCt.AppName+"�����������û���" );
       	
       }else{
       	Config.bReg=false;
       	getConfig().setREG(false);
       	tvRegState.setText("��Ȩ״̬��δ��Ȩ");
       	tvRegWarm.setText(ConfigCt.warning+"��������Ȩ��ϵ"+ConfigCt.contact);
       	etRegCode.setVisibility(View.VISIBLE);
       	tvPlease.setVisibility(View.VISIBLE);
       	btReg.setVisibility(View.VISIBLE);
       	speaker.speak("��ӭʹ��"+ConfigCt.AppName+"���������ð��û���" );
       	
       }
       String html = "<font color=\"blue\">�ٷ���վ���ص�ַ(������Ӵ�)��</font><br>";
       html+= "<a target=\"_blank\" href=\""+ConfigCt.homepage+"\"><font color=\"#FF0000\"><big><b>"+ConfigCt.homepage+"</b></big></font></a>";
       //html+= "<a target=\"_blank\" href=\"http://119.23.68.205/android/android.htm\"><font color=\"#0000FF\"><big><i>http://119.23.68.205/android/android.htm</i></big></font></a>";
       tvHomePage.setTextColor(Color.BLUE);
       tvHomePage.setBackgroundColor(Color.WHITE);//
       //tvHomePage.setTextSize(20);
       tvHomePage.setText(Html.fromHtml(html));
       tvHomePage.setMovementMethod(LinkMovementMethod.getInstance());
       setMyTitle();
       updateMeWarning(ConfigCt.version,ConfigCt.new_version);//�����������
   }
   /**  �����������*/
   private void updateMeWarning(String version,String new_version){
	   try{
		   float f1=Float.parseFloat(version);
		   float f2=Float.parseFloat(new_version);
	   if(f2>f1){
		   showUpdateDialog();
	   }
	   } catch (Exception e) {  
           e.printStackTrace();  
           return;  
       }  
   }
   /** ��Ϊ���ð�*/
   public void setAppToTestVersion() {
   	String sStartTestTime=getConfig().getStartTestTime();//ȡ�Զ���Ϊ���ð�Ŀ�ʼʱ�䣻
   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);//yyyy-MM-dd_HH-mm-ss
   	String currentDate =sdf.format(new Date());//ȡ��ǰʱ�䣻
   	int timeInterval=getConfig().getDateInterval(sStartTestTime,currentDate);//�õ�ʱ������
   	if(timeInterval>Config.TestTimeInterval){//7�����Ϊ���ð棺
   		showVerInfo(false);
   		//ftp.getFtp().DownloadStart();//���ط�������Ϣ;
   	}
   }
   private   void   showUpdateDialog(){ 
       /* @setIcon ���öԻ���ͼ�� 
        * @setTitle ���öԻ������ 
        * @setMessage ���öԻ�����Ϣ��ʾ 
        * setXXX��������Dialog������˿�����ʽ�������� 
        */ 
       final AlertDialog.Builder normalDialog=new  AlertDialog.Builder(MainActivity.this); 
       int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
       Drawable dw=getApplicationContext().getDrawable(id);
       normalDialog.setIcon(dw); 
       normalDialog.setTitle(  "��������"  );
       normalDialog.setMessage("���°�������Ƿ�����������"); 
       normalDialog.setPositiveButton("ȷ��",new DialogInterface.OnClickListener(){
           @Override 
           public void onClick(DialogInterface dialog,int which){ 
               //...To-do
    		   Uri uri = Uri.parse(ConfigCt.download);    
    		   Intent it = new Intent(Intent.ACTION_VIEW, uri);    
    		   startActivity(it);  
           }
       }); 
       normalDialog.setNegativeButton("�ر�",new DialogInterface.OnClickListener(){ 
           @Override 
           public void onClick(DialogInterface dialog,   int   which){ 
           //...To-do 
           } 
       }); 
       // ��ʾ 
       normalDialog.show(); 
       
   } 
   private   void   showCalcDialog(){ 
       /* @setIcon ���öԻ���ͼ�� 
        * @setTitle ���öԻ������ 
        * @setMessage ���öԻ�����Ϣ��ʾ 
        * setXXX��������Dialog������˿�����ʽ�������� 
        */ 
       final AlertDialog.Builder normalDialog=new  AlertDialog.Builder(MainActivity.this); 
       int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
       Drawable dw=getApplicationContext().getDrawable(id);
       normalDialog.setIcon(dw); 
       normalDialog.setTitle(  "����ţţ��������"  );
       normalDialog.setMessage(ConfigCt.AppName+"��Ҫ����ţţ���ݣ���ʹ��ţţ���Ӿ�׼���˼�����Ҫ�ܳ�ʱ�䣬��������˯��ǰ���м������񣡣����м�������ʱ��Ҫ�������ֻ����ڳ��״̬���������ʧ�ܣ�"); 
       normalDialog.setPositiveButton("ȷ��",new DialogInterface.OnClickListener(){
           @Override 
           public void onClick(DialogInterface dialog,int which){ 
           	if(!QiangHongBaoService.isRunning()) {
   				String say="�����ҵ�"+ConfigCt.AppName+"��Ȼ���ţţ���񣬲��ܼ���ţţ���ݣ�";
   				Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
   				speaker.speak(say);
   				QiangHongBaoService.startSetting(getApplicationContext());
   				return;
   			}
   			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
   				if(!FloatWindowManager.getInstance().applyOrShowFloatWindow(MainActivity.this))return;
   			}
   			CalcShow.getInstance(getApplicationContext()).showPic();
   			CalcShow.getInstance(getApplicationContext()).showTxt();
           }
       }); 
       normalDialog.setNegativeButton("�ر�",new DialogInterface.OnClickListener(){ 
           @Override 
           public void onClick(DialogInterface dialog,   int   which){ 
           //...To-do 
           } 
       }); 
       // ��ʾ 
       normalDialog.show(); 
   } 
   /*
    * �����ϵ�˶Ի���
    * */
   private   void   showAddContactsDialog(){ 
       /* @setIcon ���öԻ���ͼ�� 
        * @setTitle ���öԻ������ 
        * @setMessage ���öԻ�����Ϣ��ʾ 
        * setXXX��������Dialog������˿�����ʽ�������� 
        */ 
	   AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	   int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
       Drawable dw=getApplicationContext().getDrawable(id);
       builder.setIcon(dw); 
       builder.setTitle("����ϵ�ͷ����������");
       String say="����ϵ�ͷ����������!��͸�ӣ��Ѱ���ţţ��";
   	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
	    speaker.speak(say);
       //builder.setMessage(say);
       final Contacts  cs=Contacts.getInstance(getApplicationContext(),ConfigCt.contact);
       String k1="�ͷ�1(QQ��"+cs.QQ+")";
       String k2="�ͷ�2(΢�ţ�"+cs.wx+")";
       final String[] css = {k1,k2};
       //    ����һ������ѡ��������
       /**
        * ��һ������ָ������Ҫ��ʾ��һ��������ѡ������ݼ���
        * �ڶ�����������������ָ��Ĭ����һ����ѡ�򱻹�ѡ�ϣ�1��ʾĬ��'Ů' �ᱻ��ѡ��
        * ������������ÿһ����ѡ���һ��������
        */
       builder.setSingleChoiceItems(css, 0, new DialogInterface.OnClickListener()
       {
           @Override
           public void onClick(DialogInterface dialog, int which)
           {
        	   if(which==0){
        		   cs.openQQadd();
        	   }
        	   if(which==1){
        		   cs.openWXadd();
        	   }
               //Toast.makeText(MainActivity.this, "�Ա�Ϊ��" + sex[which], Toast.LENGTH_SHORT).show();
           }
       });
       builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
       {
           @Override
           public void onClick(DialogInterface dialog, int which)
           {
        	   if(which==0||which==-1){
        		   cs.openQQadd();
        	   }
        	   if(which==1){
        		   cs.openWXadd();
        	   }
           }
       });
       builder.setNegativeButton("����", new DialogInterface.OnClickListener()
       {
           @Override
           public void onClick(DialogInterface dialog, int which)
           {
        	 //������Ϸ���Ҵ��������ڣ�
			    fw.ShowFloatingWindow();
			    //Config.GamePackage=Funcs.getFuncs(MainActivity.this).GetPkgName(Config.iSelQpName-1);
			    Config.GamePackage=AppInfo.getAppInfo(getApplicationContext()).GetPkgName(Config.iSelQpName-1);
				OpenGame(Config.GamePackage,MainActivity.this);
				WechatAccessibilityJob.getJob().setPkgs(new String[]{Config.GamePackage});
				MainActivity.this.finish();
           }
       });
       builder.show();
   }
   @Override
   public void onBackPressed() {
       //�˴�д�����̨�Ĵ���
	  // moveTaskToBack(true); 
   }
   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_BACK) {//������ؼ�����
           //�˴�д�����̨�Ĵ���
    	   //moveTaskToBack(true);
           //return true;
       }
       return super.onKeyDown(keyCode, event);
   }
   @Override
   protected void onStop() {
       // TODO Auto-generated method stub
       super.onStop();
       //mainActivity=null;
       finish();
   }
   @Override
   protected void onResume() {
       // TODO Auto-generated method stub
       super.onResume();
       //if(!Utils.isActive)
       //{
           //Utils.isActive = true;
           /*һЩ�����絯�������������*/
       //}
   }
   @Override
   protected void onDestroy() {
       // TODO Auto-generated method stub
       super.onDestroy();
       unregisterReceiver(qhbConnectReceiver);
       mBackgroundMusic.stopBackgroundMusic();
   }

	@Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	    setIntent(intent);//must store the new intent unless getIntent() will return the old one
	    //startAllServices();
		Log.i(Config.TAG, "qpnn onNewIntent: ����");  
	}

   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   public static String getLollipopRecentTask(Context context) {  
       final int PROCESS_STATE_TOP = 2;  
       try {  
           //ͨ�������ȡ˽�г�Ա����processState���Ժ���Ҫ�жϸñ�����ֵ  
           Field processStateField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");  
           List<ActivityManager.RunningAppProcessInfo> processes = ((ActivityManager) context.getSystemService(  
                   Context.ACTIVITY_SERVICE)).getRunningAppProcesses();  
           for (ActivityManager.RunningAppProcessInfo process : processes) {  
               //�жϽ����Ƿ�Ϊǰ̨����  
               if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {  
                   int state = processStateField.getInt(process);  
                   //processStateֵΪ2  
                   if (state == PROCESS_STATE_TOP) {  
                       String[] packname = process.pkgList;  
                       return packname[0];  
                   }  
               }  
           }  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
       return "";  
   }  
   /**
    * ��ȡ��ǰӦ�ó���İ���
    * @param context �����Ķ���
    * @return ���ذ���
    */
   public static String getAppProcessName(Context context) {
       //��ǰӦ��pid
       int pid = android.os.Process.myPid();
       //���������
       ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
       //��������Ӧ��
       List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
       for (ActivityManager.RunningAppProcessInfo info : infos) {
           if (info.pid == pid)//�õ���ǰӦ��
               //return info.processName;//���ذ���
        	   Log.i("byc002", info.processName);
           Log.i("byc001", info.processName);
       }
       return "";
   }
   
   public static String getCurrentPkgName(Context context) {
	   ActivityManager.RunningAppProcessInfo currentInfo = null;
	   Field field = null;
	   int START_TASK_TO_FRONT = 2;
	   String pkgName = null;
	   try {
	   field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
	   } catch (Exception e) { e.printStackTrace(); }
	   ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	   List<RunningAppProcessInfo> appList = am.getRunningAppProcesses();
	   for (ActivityManager.RunningAppProcessInfo app : appList) {
	   if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
	   Integer state = null;
	   try {
	   state = field.getInt( app );
	   } catch (Exception e) { e.printStackTrace(); }
	   if (state != null && state == START_TASK_TO_FRONT) {
	   currentInfo = app;
	   break;
	   }
	   }
	   }
	   if (currentInfo != null) {
	   pkgName = currentInfo.processName;
	   }
	   //Log.i("byc001", pkgName);
	   return pkgName;
	   } 
   public static String get2(Context  context){
	   ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	   String _pkgName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
	   Log.i("byc001",_pkgName);
	   return  _pkgName;
   }
   public static String get3(Context  context){
	   String mPackageName="";
	   ActivityManager mActivityManager =(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	   if(Build.VERSION.SDK_INT > 20){
	       mPackageName = mActivityManager.getRunningAppProcesses().get(0).processName;
	   }else{ 
		   mPackageName =  mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
	   }
	   Log.i("byc003",mPackageName);
	
	   return mPackageName;
   }
   public  void  get4(Context context){
	// ��ȡͼƬ��Ӧ����������
		// ������¼Ӧ�ó������Ϣ
	List<AppsItemInfo> list;
	   PackageManager pManager = context.getPackageManager();
		List<PackageInfo> appList = getAllApps(context);

			list = new ArrayList<AppsItemInfo>();

			for (int i = 0; i < appList.size(); i++) {
				PackageInfo pinfo = appList.get(i);
				AppsItemInfo shareItem = new AppsItemInfo();
				// ����ͼƬ
				shareItem.setIcon(pManager
						.getApplicationIcon(pinfo.applicationInfo));
				// ����Ӧ�ó�������
				shareItem.setLabel(pManager.getApplicationLabel(
						pinfo.applicationInfo).toString());
				// ����Ӧ�ó���İ���
				shareItem.setPackageName(pinfo.applicationInfo.packageName);

				list.add(shareItem);
				Log.i(TAG, shareItem.getLabel());
				Log.i(TAG, shareItem.getPackageName());
			}

   }
			public static List<PackageInfo> getAllApps(Context context) {

				List<PackageInfo> apps = new ArrayList<PackageInfo>();
				PackageManager pManager = context.getPackageManager();
				// ��ȡ�ֻ�������Ӧ��
				List<PackageInfo> packlist = pManager.getInstalledPackages(0);
				for (int i = 0; i < packlist.size(); i++) {
					PackageInfo pak = (PackageInfo) packlist.get(i);

					// �ж��Ƿ�Ϊ��ϵͳԤװ��Ӧ�ó���
					// ���ﻹ�������ϵͳ�Դ��ģ�������Ȳ�����ˣ��������Ҫ�����Լ����
					// if()���ֵ���<=0��Ϊ�Լ�װ�ĳ��򣬷���Ϊϵͳ�����Դ�
					if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
						// ����Լ��Ѿ���װ��Ӧ�ó���
						apps.add(pak);
					}

				}
				return apps;
			}
			// �Զ���һ�� AppsItemInfo �࣬�����洢Ӧ�ó���������Ϣ
			private class AppsItemInfo {

				private Drawable icon; // ���ͼƬ
				private String label; // ���Ӧ�ó�����
				private String packageName; // ���Ӧ�ó������

				public Drawable getIcon() {
					return icon;
				}

				public void setIcon(Drawable icon) {
					this.icon = icon;
				}

				public String getLabel() {
					return label;
				}

				public void setLabel(String label) {
					this.label = label;
				}

				public String getPackageName() {
					return packageName;
				}

				public void setPackageName(String packageName) {
					this.packageName = packageName;
				}

			}
}
