/**
 * 
 */
package com.example.h3;
import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
/**
 * @author ASUS
 *
 */
public class AppInfo {
	 private static AppInfo current;
	    private Context context;
	    public static final String TAG = "byc001";//���Ա�ʶ��
	    
	    public List<AppsItemInfo> mAppInfos;//�Ѱ�װ��app��Ϣ��
	    public String[] mAppNames;//�Ѱ�װ��app���ƣ�
	    public List<String> mListAppNames;//�Ѱ�װ��app���ƣ�
	    
	    private AppInfo(Context context) {
	        this.context = context;
	        getAppInfos(context);
	    }
	    public static synchronized AppInfo getAppInfo(Context context) {
	        if(current == null) {
	            current = new AppInfo(context);
	        }
	        return current;
	    }
	    /*
	     * ��ȡapp�б�
	     * 
	     * 
	     */
	    public List<String> GetListAppNames(){
	    	if(mListAppNames!=null)return mListAppNames;
	    	mListAppNames = new ArrayList<String>();
	    	mListAppNames.add("��ѡ��ţţ��Ϸ");
	    	for (AppsItemInfo app: mAppInfos) {
	    		mListAppNames.add(app.label);
	    	}
	    	return mListAppNames;
	    	
	    }
	    /*
	     * ��ȡapp�б�
	     * 
	     * 
	     */
	    public String[] GetAppNames(){
	    	if(mAppNames!=null)return mAppNames;
	    	mAppNames = new String[mAppInfos.size()];
	    	int i=0;
	    	for (AppsItemInfo app: mAppInfos) {
	    		mAppNames[i]=app.getLabel();
	    		i=i+1;
	    	}
	    	return mAppNames;
	    	
	    }
	    /*
	     * ������Ϸ����ȡ������
	     * GameName����Ϸ���ƣ�
	     * PkgName��������
	     */
	    public String GetPkgName(int position){
	    	AppsItemInfo appinfo=mAppInfos.get(position);
	    	return appinfo.packageName;
	    }
	    /*
	     * ������Ϸ����ȡ������
	     * GameName����Ϸ���ƣ�
	     * PkgName��������
	     */
	    public String GetPkgName(String GameName){
	    	for(AppsItemInfo appinfo : mAppInfos){
	    		String appName=appinfo.getLabel();
	    		//Log.i(TAG, appName);
	    		if(appName.equals(GameName)){
	    			return  appinfo.getPackageName();
	    		}
	    	}
	    	return "";
	    }
	    /*
	     * ��ȡ�Ѱ�װ������app��Ϣ ��
	     */
	    public  void  getAppInfos(Context context){
	    	// ��ȡͼƬ��Ӧ����������
	    		// ������¼Ӧ�ó������Ϣ
	    	
	    	   PackageManager pManager = context.getPackageManager();
	    		List<PackageInfo> appList = getAllApps(context);

	    		mAppInfos = new ArrayList<AppsItemInfo>();

	    			for (int i = 0; i < appList.size(); i++) {
	    				PackageInfo pinfo = appList.get(i);
	    				AppsItemInfo shareItem = new AppsItemInfo();
	    				// ����ͼƬ
	    				//shareItem.setIcon(pManager
	    						//.getApplicationIcon(pinfo.applicationInfo));
	    				// ����Ӧ�ó�������
	    				shareItem.setLabel(pManager.getApplicationLabel(
	    						pinfo.applicationInfo).toString());
	    				// ����Ӧ�ó���İ���
	    				shareItem.setPackageName(pinfo.applicationInfo.packageName);

	    				mAppInfos.add(shareItem);
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

