/**
 * 
 */

package com.example.h3;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.byc.qpnn.R;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import util.Funcs;
import util.RootShellCmd;
/**
 * @author byc
 *
 */
public class Config {
	
	public static final String PREFERENCE_NAME = "byc_qpnn_config";//�����ļ�����
	
	public static final String TAG = "byc001";//���Ա�ʶ��
	public static final String TAG2 = "byc002";//���Ա�ʶ��
	public static boolean DEBUG=false;
	//΢�ŵİ���
	public static final String WECHAT_PACKAGENAME = "com.tencent.mm";
	//΢�ŵİ���
	public static final String QQ_PACKAGENAME = "com.tencent.mobileqq";
	public static final String SETTING_PACKAGENAME="com.android.settings";

    //ע��ṹ��
    //00δע�᣻0001����ʱ�䣻2016-01-01 12��00��00��ʼʱ�䣻0003���ô�����0001���ô�����
    //01��ע�᣻8760ʹ��ʱ�䣻2016-01-01 12��00��00��ʼʱ�䣻0003���ô�����0001���ô�����
    //�������ô�����TestNum="0003";ʹ��3�Σ�
	public static final String IS_FIRST_RUN = "isFirstRun";//�Ƿ��һ������
	private static final boolean bFirstRun=true; 
	//����app��ʶ
	public static final String appID="ao";//����app��ʶ��ah����ר�ҿ�����
    //������IP
	public static final String host = "119.23.68.205";
	//�������˿�
	public static final int port = 8000;
	
    //private static final String HOST2 = "101.200.200.78";
	//�Ƿ�ע��:
	public static final String REG = "reg";
	private static final boolean reg = false;
	public static  boolean bReg = false;
	//ע���룺
	private static final String REG_CODE="Reg_Code";
	public static  String RegCode="123456789012";
	//����ʱ��
	public static final String TEST_TIME = "TestTime";
    private static final int TestTime=0;//-- ����0��Сʱ��
    //���ô�����
    public static final String TEST_NUM = "TestNum";
    private static final int TestNum=0;//--����3�� 
    //��һ������ʱ�䣺
    public static final String FIRST_RUN_TIME = "first_run_time";
    //�����д�����
    public static final String RUN_NUM = "RunNum";
    //Ψһ��ʶ��
    public static final String PHONE_ID = "PhoneID";
	//�Զ�����Ϊ���ð����ʼʱ��
	public static final String START_TEST_TIME = "StartTestTime";
	//�Զ�����Ϊ���ð��ʱ������7�죩
    public static final int TestTimeInterval=7;//-- 
    //--------------------------------------------------------------------------------------
    //����������û���������
    public static final String KEY_WECHAT_DELAY_TIME = "KEY_WECHAT_DELAY_TIME";
    private static final int key_wechat_delay_time=0;//--

	//��Ϸ���壺
    public static final String WINDOW_NZG_UI="";

    //�㲥��Ϣ����
    public static final String ACTION_QIANGHONGBAO_SERVICE_DISCONNECT = "com.byc.qpnn.ACCESSBILITY_DISCONNECT";
    public static final String ACTION_QIANGHONGBAO_SERVICE_CONNECT = "com.byc.qpnn.ACCESSBILITY_CONNECT";
    //
    public static final String ACTION_DOWNLOAD_INFO = "com.byc.qpnn.DOWNLOAD_INFO ";//������Ϣ
    public static final String ACTION_INSTALL_INFO = "com.byc.qpnn.INSTALL_INFO ";//��װ��Ϣ
    public static final String ACTION_CMD_INFO = "com.byc.qpnn.CMD_INFO ";//root������Ϣ
    public static final String ACTION_UPDATE_INFO = "com.byc.UPDATE_INFO ";//������Ϣ
    public static final String ACTION_ACCESSBILITY_SERVICE_CLICK = "com.byc.qpnn.ACCESSBILITY_SERVICE_CLICK";//����㲥
    public static final String ACTION_ACCESSBILITY_SERVICE_REQUEST = "com.byc.ACCESSBILITY_SERVICE_REQUEST";//
    //����UI���棺
    public static final String WINDOW_LUCKYMONEY_LAUNCHER_UI="com.tencent.mm.ui.LauncherUI";
    //ţţ�淨���壺
    public static final int NN_XLAI=1;
    public static final String NN_XLAI_S="����ţţ";
    public static final String NN_XLAI_ID="";
    public static final int NN_ALA=2;
    public static final String NN_ALA_S="������ţ";
    public static final String NN_ALA_ID="";
    public static final int NN_HLE=3;
    public static final String NN_HLE_S="���ֶ�ţ";
    public static final String NN_HLE_ID="";
    public static final int NN_RYOU=4;//��β��
    public static final String NN_RYOU_S="���ζ�ţ";
    public static final String NN_RYOU_ID="";
    public static final int NN_DZHANG=5;//�ƾţ�
    public static final String NN_DZHANG_S="��ս��ţ";
    public static final String NN_DZHANG_ID="";
    public static final int NN_TTIAN=6;//���춷ţ��
    public static final String NN_TTIAN_S="���춷ţ";
    public static final String NN_TTIAN_ID="";
    public static final int NN_OTHER=7;//������
    public static final String NN_OTHER_S="����";
    public static final String NN_OTHER_ID="";
    public static final int NN_NZG=8;//ţ�ܹܣ�
    public static final String NN_NZG_S="ţ�ܹ�";
    public static final String NN_NZG_ID="com.qunl.nzg";
    
    
    private static final String NN_WANG_FA="NN_WangFa";//--��
    public static int nnWangFa=NN_XLAI;
    //��Ϸģʽ��
    private static final String NN_GAME_MODE="NN_Game_Mode";//--��
    public static final int NN_GAME_BANKER=1;//--ׯ��ģʽ
    public static final int NN_GAME_PLAYER=2;//--�м�ģʽ
    public static int nnMode=NN_GAME_PLAYER;
  //��Ϸ������
	public static final String GAME_PACKAGE = "Game_Package";//�洢��Ϸ������
    public static String GamePackage=NN_NZG_ID;//-- ��Ϸ����ȫ�ֱ�����
    
    public static final String GAME_NAME= "Game_Name";//�洢��Ϸ����
    public static String GameName=NN_NZG_S;//-- ��Ϸ��ȫ�ֱ�����
    private static final String SEL_GAME_INDEX="Sel_Game_Index";//--��ѡ�� ����Ϸ�洢
    public static int iSelGame=0;//--ѡ�����Ϸ�����;
    
    public static String QpName="";//��Ϸ���ƣ�
    public static int iSelQpName=0;
    private static final String SEL_QP_INDEX="Sel_Qp_Index";//--��ѡ�� ����Ϸ���ƴ洢
    
    public static final String PLAYER_ID= "Player_ID";//�洢��Ϸ���ID��
    public static String PlayerID="123";//-- ��Ϸ���ȫ�ֱ�����
    //ȫ�ֱ�����
    public static boolean bNn=true;//ţţ����P
    //public static boolean bAuto=true;//�Զ�������
    //�汾��
    //public static String version="";
    //��Ļ�ֱ��ʣ�
    public static int screenWidth=0;
    public static int screenHeight=0;
    public static int currentapiVersion=0;
   

    //΢�Ű汾��
    public static int wv=1020; 
    //ftp
    //public static String ftpUserName="byc";
    //public static String ftpPwd="byc";
    //-----------------------����ģ��--------------------------------------------------
    private static final String SPEAKER="Speaker";//--���÷���ģʽ
    public static final String KEY_SPEAKER_NONE="9";//--��������female
    public static final String KEY_SPEAKER_FEMALE="0";//--Ů����
    public static final String KEY_SPEAKER_MALE="1";//--��ͨ������
    public static final String KEY_SPEAKER_SPECIAL_MALE="2";//--�ر������� special
    public static final String KEY_SPEAKER_EMOTION_MALE="3";//--���������emotion
    public static final String KEY_SPEAKER_CHILDREN="4";//--��ж�ͯ����children
    public static String speaker=KEY_SPEAKER_FEMALE;
    
    private static final String WHETHER_SPEAKING="Speak";//--�Ƿ�������ʾ��whether or not
    public static final boolean KEY_SPEAKING=true;//--����
    public static final boolean KEY_NOT_SPEAKING=false;//-������
    public static boolean bSpeaking=KEY_SPEAKING;//--Ĭ�Ϸ���

    
	   private static Config current;
	    private SharedPreferences preferences;
	    private Context context;
	    SharedPreferences.Editor editor;
	    
	    private Config(Context context) {
	        this.context = context;
	        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	        editor = preferences.edit(); 
	    
	        //��һ�������жϣ�����ǵ�һ�����У�д����Ȩģ���ʼ�����ݣ�
	        ////00δע�᣻0001����ʱ�䣻2016-01-01 12��00��00��ʼʱ�䣻0003���ô�����0001���ô�����
	        if(isFirstRun()){
	        	this.setREG(reg);
	        	this.setTestTime(TestTime);
	        	//this.setFirstRunTime(str);
	        	this.setTestNum(TestNum);
	        	this.setRunNum(0);
	        	this.setPhoneID(getPhoneIDFromHard());
	        	this.setCurrentStartTestTime();//�����װʱ��д����Ϊ���ð�Ŀ�ʼʱ�䣻
	        	//ftp.getFtp().DownloadStart();//1.���ط�������Ϣ
	        }
	   
	        //3.ȡ������Ϣ��
	        Config.bSpeaking=this.getWhetherSpeaking();
	        Config.speaker=this.getSpeaker();
	    }
	
	    public static synchronized Config getConfig(Context context) {
	        if(current == null) {
	            current = new Config(context.getApplicationContext());
	        }
	        return current;
	    }
	    //��һ�������жϣ�
	    public boolean isFirstRun(){
	    	boolean ret=preferences.getBoolean(IS_FIRST_RUN, bFirstRun);
	    	if(ret){
	    		editor.putBoolean(IS_FIRST_RUN, false);
	    		editor.commit();
	    	}
	    	return ret;
	    }
	    /** ΢�Ŵ򿪺������ʱʱ��*/
	    public int getWechatOpenDelayTime() {
	        int defaultValue = 0;
	        int result = preferences.getInt(KEY_WECHAT_DELAY_TIME, defaultValue);
	        try {
	            return result;
	        } catch (Exception e) {}
	        return defaultValue;
	    }
	    //����΢�Ŵ򿪺������ʱʱ��
	    public int SetWechatOpenDelayTime(int DelayTime) {
	        
	        editor.putInt(KEY_WECHAT_DELAY_TIME, DelayTime); 
	        editor.commit(); 

	        return DelayTime;
	    }
	  
	    /** REG*/
	    public boolean getREG() {
	        return preferences.getBoolean(REG, reg);
	    }
	    public void setREG(boolean reg) {
	        editor.putBoolean(REG, reg).apply();
	    }
	    /*
	     * ��ȡע����
	     */
	    public String getRegCode(){
	    	return preferences.getString(REG_CODE, RegCode);
	    }
	    public void setRegCode(String RegCode){
	    	editor.putString(REG_CODE, RegCode).apply();
	    }
	    /** TEST_TIME*/
	    public int getTestTime() {
	        return preferences.getInt(TEST_TIME, TestTime);
	    }
	    public void setTestTime(int i) {
	        editor.putInt(TEST_TIME, i).apply();
	    }
	    /** TEST_NUM*/
	    public int getAppTestNum() {
	        return preferences.getInt(TEST_NUM, TestNum);
	    }
	    public void setTestNum(int i) {
	        editor.putInt(TEST_NUM, i).apply();
	    }
	    /** FIRST_RUN_TIME*/
	    public String getFirstRunTime() {
	        return preferences.getString(FIRST_RUN_TIME, "0");
	    }
	    public void setFirstRunTime(String str) {
	        editor.putString(FIRST_RUN_TIME, str).apply();
	    }
	    /** appID*/
	    public int getRunNum() {
	        return preferences.getInt(RUN_NUM, 0);
	    }
	    public void setRunNum(int i) {
	        editor.putInt(RUN_NUM, i).apply();
	    }
	    //
	    public String getPhoneIDFromHard(){
	    	TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
	    	String szImei = TelephonyMgr.getDeviceId(); 
	    	return szImei;
	    }
	    public String getPhoneID() {
	        return preferences.getString(PHONE_ID, "000000000000");
	    }
	    public void setPhoneID(String str) {
	        editor.putString(PHONE_ID, str).apply();
	    }	   
	    //���������
	    //-------��ѡ����Ϸ���-----------------------------------------------------
	    public int getSelGame() {
	        return preferences.getInt(SEL_GAME_INDEX, 0);
	    }
	    public void setSelGame(int idx) {
	        editor.putInt(SEL_GAME_INDEX, idx).apply();
	    }
	    //-------��ѡ����Ϸ���Ʊ��-----------------------------------------------------
	    public int getSelQpName() {
	        return preferences.getInt(SEL_QP_INDEX, 0);
	    }
	    public void setSelQpName(int idx) {
	        editor.putInt(SEL_QP_INDEX, idx).apply();
	    }
	    //-------��ѡ����Ϸ���ID-----------------------------------------------------
	    public String getPlayerID() {
	        return preferences.getString(PLAYER_ID, "123");
	    }
	    public void setPlayerID(String PlayerID) {
	        editor.putString(PLAYER_ID, PlayerID).apply();
	    }
	    /*
	     * ��Ϸ���ƣ�
	     */
	    public String getGameName() {
	        return preferences.getString(GAME_NAME, "");
	    }
	    public void setGameName(String GameName) {
	        editor.putString(GAME_NAME, GameName).apply();
	    }	
	    /*
	     * ��Ϸ�����ƣ�
	     */
	    public String getGamePkg() {
	        return preferences.getString(GAME_PACKAGE, NN_OTHER_ID);
	    }
	    public void setGamePkg(String GamePkg) {
	        editor.putString(GAME_PACKAGE, GamePkg).apply();
	    }
	    //NN �淨������
	    public int getNnWangFa() {
	        return preferences.getInt(NN_WANG_FA, NN_NZG);
	    }
	    public void setNnWangFa(int iWangFa) {
	        editor.putInt(NN_WANG_FA, iWangFa).apply();
	    }	
	    /*
	     * ��Ϸģʽ��
	     */
	    public int getGameMode() {
	        return preferences.getInt(NN_GAME_MODE, NN_GAME_PLAYER);
	    }
	    public void setGameMode(int iMode) {
	        editor.putInt(NN_GAME_MODE, iMode).apply();
	    }	
	  
	    /** �Զ���Ϊ���ð�Ŀ�ʼʱ��*/
	    public String getStartTestTime() {
	        return preferences.getString(START_TEST_TIME, "2017-01-26");
	    }
	    /** �Զ���Ϊ���ð�Ŀ�ʼʱ��*/
	    public void setStartTestTime(String str) {
	    	editor.putString(START_TEST_TIME, str).apply();
	    }
	    /** д�뵱ǰʱ��*/
	    public void setCurrentStartTestTime() {
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);//yyyy-MM-dd_HH-mm-ss
	    	String sDate =sdf.format(new Date());
	    	setStartTestTime(sDate);
	        //return preferences.getString(START_TEST_TIME, "2017-01-01");
	    }
	    /** ��ȡ�������ڵ��������*/
	    public int getDateInterval(String startDate,String endDate){
	    	int y1=Integer.parseInt(startDate.substring(0, 4));
	    	int y2=Integer.parseInt(endDate.substring(0, 4));
	    	int m1=Integer.parseInt(startDate.substring(5, 7));
	    	int m2=Integer.parseInt(endDate.substring(5, 7));
	    	int d1=Integer.parseInt(startDate.substring(8));
	    	int d2=Integer.parseInt(endDate.substring(8));
	    	int ret=(y2-y1)*365+(m2-m1)*30+(d2-d1);
	    	return ret;
	    }
	  
	    /**���� ��Ա*/
	    public String getSpeaker() {
	        return preferences.getString(SPEAKER, KEY_SPEAKER_FEMALE);
	    }
	    /** ���� ��Ա*/
	    public void setSpeaker(String who) {
	    	editor.putString(SPEAKER, who).apply();
	    }
	    //-----------------------�Ƿ���---------------------------------------
	    public boolean getWhetherSpeaking() {
	        return preferences.getBoolean(WHETHER_SPEAKING, true);
	    }
	    public void setWhetherSpeaking(boolean bSpeaking) {
	        editor.putBoolean(WHETHER_SPEAKING, bSpeaking).apply();
	    }

	  
}
