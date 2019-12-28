/**
 * 
 */
package com.example.h3.job;

import java.util.Timer;
import java.util.TimerTask;

import util.BackgroundMusic;
import com.example.h3.Config;
import com.example.h3.MainActivity;
import accessibility.QiangHongBaoService;
import notification.IStatusBarNotification;
import notification.NotifyHelper;
import notification.QHBNotificationService;
import accessibility.AccessibilityHelper;
import accessibility.BaseAccessibilityJob;
import util.SpeechUtil;
import com.byc.qpnn.R;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

/**
 * @author byc
 *
 */
public class WechatAccessibilityJob extends BaseAccessibilityJob  {
	
	private static WechatAccessibilityJob current;
	//-------------------------------�����ʱ---------------------------------------------
	
	private SpeechUtil speaker ;
	private String mPackageName;
	private int mCount=0;
	public WechatAccessibilityJob(){
		super(null);
	}

    @Override
    public void onCreateJob(QiangHongBaoService service) {
        super.onCreateJob(service);

        EventStart();
        speaker=SpeechUtil.getSpeechUtil(context);

    
    }
  
    @Override
    public void onStopJob() {
    	super.onStopJob();
    }
    public static synchronized WechatAccessibilityJob getJob() {
        if(current == null) {
            current = new WechatAccessibilityJob();
        }
        return current;
    }
  
    //----------------------------------------------------------------------------------------
    @Override
    public void onReceiveJob(AccessibilityEvent event) {
    	super.onReceiveJob(event);
    	if(!mIsEventWorking)return;
    	if(!mIsTargetPackageName){
    		//if(mCount>0)speaker.stopSpeaking();
    		return;
    	}   	
    	final int eventType = event.getEventType();
    	if(event.getClassName()==null)return;
    	String sClassName=event.getClassName().toString();    	
    
    	if(!Config.bNn)return;
    	String say="";
    	//++++++++++++++++++++++++++++++++++++����ı�+++++++++++++++++++++++++++++++++++++++++++++++++
		if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			mPackageName=event.getPackageName().toString();
			if(mPackageName.equals(Config.WECHAT_PACKAGENAME)||mPackageName.equals(Config.QQ_PACKAGENAME)){
				
			}else{
				if(mCount>3){
					if(Config.bReg)
						say="�ѻ�ȡ��ţţ����!!!!";
					else
						say="�������ð��û����빺��������ܻ�ȡţţ��";
					if(mCount>6){return;}//mCount=0;
				}
				else
					say="���ڶ�ȡţţ����...";
				speaker.speak(say);
				Toast.makeText(context, say, Toast.LENGTH_SHORT).show();
				mCount=mCount+1;
			}
			Log.d(TAG, "���ڸı� ---->" + sClassName);
			
		}//if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) 
		//++++++++++++++++++++++++++++++++++++���ݸı�+++++++++++++++++++++++++++++++++++++++++++++++++
		if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
			//Log.d(TAG, "���ݸı�---->" + sClassName);
		}//if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) 
	
		//if(!Config.bReg){
		//	say="�������ð��û����빺��������ܻ�ȡţţ��";
		//	speaker.speak(say);
		//	Toast.makeText(context, say, Toast.LENGTH_LONG).show();
		//}
    }
	/*
	 * (ˢ�´�������)
	 * @see accessbility.AccessbilityJob#onWorking()
	 */
	@Override
	public void onWorking(){
		//Log.i(TAG2, "onWorking");
		//installApp.onWorking();
	}
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)

    public void onNotificationPosted(IStatusBarNotification sbn) {
        Notification nf = sbn.getNotification();
        String text = String.valueOf(sbn.getNotification().tickerText);
        notificationEvent(text, nf);
    }
    /** ֪ͨ���¼�*/
    private void notificationEvent(String ticker, Notification nf) {
        String text = ticker;
        int index = text.indexOf(":");
        if(index != -1) {
            text = text.substring(index + 1);
        }
        text = text.trim();
       // transferAccounts.notificationEvent(ticker, nf);
        //if(text.contains(TransferAccounts.WX_TRANSFER_ACCOUNTS_ORDER)) { //�����Ϣ
        //    newHongBaoNotification(nf);
        //}
    }

    /**��֪ͨ����Ϣ*/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void newHongBaoNotification(Notification notification) {
    	//TransferAccounts.mWorking = true;
        //�����Ǿ�������΢�ŵ�֪ͨ����Ϣ��
        PendingIntent pendingIntent = notification.contentIntent;
        boolean lock = NotifyHelper.isLockScreen(getContext());

        if(!lock) {
            NotifyHelper.send(pendingIntent);
        } else {
            //NotifyHelper.showNotify(getContext(), String.valueOf(notification.tickerText), pendingIntent);
        }

        if(lock) {
           // NotifyHelper.playEffect(getContext(), getConfig());
        }
    }

}

