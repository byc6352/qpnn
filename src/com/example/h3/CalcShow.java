/**
 * 
 */
package com.example.h3;


import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import util.BackgroundMusic;
import util.ConfigCt;
import util.SpeechUtil;

/**
 * @author ASUS
 *
 */
public class CalcShow {
	private static CalcShow current;
	public  Context context;
	private SpeechUtil speaker ;
	private static final int  MSG_RESULT=0x17;//��Ϣ���壺	
	public FloatingWindowPic fwp;//����������ʱ��
	private BackgroundMusic mBackgroundMusic;
	public static final String ACTION_CALC_PIC_END = "com.byc.qip.CALC_PIC_END ";//���������Ϣ 
	public int mTime=8*60*60;
	 private CalcShow(Context context) {
		 this.context = context;
		 speaker=SpeechUtil.getSpeechUtil(context);
		 mBackgroundMusic=BackgroundMusic.getInstance(context);
		 int LinearLayoutID=util.ResourceUtil.getLayoutId(context, "float_click_delay_show");
		 fwp=FloatingWindowPic.getFloatingWindowPic(context,LinearLayoutID);
		 int w=Config.screenWidth-50;
		 int h=Config.screenHeight-50;
		 fwp.SetFloatViewPara(50, 50,w,h);
	 }
	public static synchronized CalcShow getInstance(Context context) {
		if(current == null) {
			current = new CalcShow(context);
		}
		return current;
	}
	/*��ʾ��������*/
	public void showPic() {
    	
    	mBackgroundMusic.playBackgroundMusic( "dd2.mp3", true);
		fwp.ShowFloatingWindow(); 
    	fwp.c=8*60*60*50;
    	fwp.mSendMsg=ACTION_CALC_PIC_END;
    	fwp.mShowPicType=FloatingWindowPic.SHOW_PIC_GREEN;
    	fwp.StartSwitchPics();
    }
	/*��ʾ���֣�*/
	public void showTxt() {
		mTime=8*60*60;
		final Handler handler= new Handler(); 
		Runnable runnable = new Runnable() {    
			@Override    
		    public void run() { 
				String aTxt=getTxt(mTime);
				Toast.makeText(context, aTxt, Toast.LENGTH_LONG).show();
				speaker.speak(aTxt);
				mTime=mTime-10;
				if(mTime<=0){
					mBackgroundMusic.stopBackgroundMusic();
					aTxt="���������ɣ�";
					Toast.makeText(context,aTxt , Toast.LENGTH_LONG).show();
					speaker.speak(aTxt);
					fwp.c=0;
					return;
				}
				handler.postDelayed(this, 1000*10);    
		    }    
		};
		handler.postDelayed(runnable, 1000*10);  
	}
	/*��ʾ���֣�*/
	public String getTxt(int aTime) {
		int h=aTime/3600;
		int m=(aTime%3600)/60;
		int s=(aTime%3600)%60;
		String aTxt="�����������"+ConfigCt.AppName+"���ݣ��벻Ҫ������ʣ��ʱ�䣺"+h+"Сʱ"+m+"����"+s+"�룡";
		return aTxt;
	}
}
