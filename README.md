# UpdateUtil
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.sc-hotpot:UpdateUtil:0.1'
	}
  
  
  permision 
  
  
  
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
 
  Use::

  DownLoadUtil util= DownLoadUtil.getInstance(this);
            util.startDownload("fileurl","upload.apk","sdfsdf");
            util.goDownloadManager();
            
            
            
  如果文件是http://  开头  且直接失败，请添加 networkSecurityConfig 配置允许http请求
  
