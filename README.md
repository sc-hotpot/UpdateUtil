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
 
 
provider

        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.fileProvider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
	
	xml: 
	


	<?xml version="1.0" encoding="utf-8"?>
	<paths>
    		<files-path
       		 	name="files"
       		 	path="/"/>
    		<cache-path
        		name="cache"
        		path="/"/>
    		<external-path
        		name="external"
        		path="/"/>
    		<external-files-path
			name="external_file_path"
        		path="/"/>
    		<external-cache-path
       		 	name="external_cache_path"
       		 	path="/"/>
	</paths>
	
	
  Use::

  DownLoadUtil util= DownLoadUtil.getInstance(this);
            util.startDownload("fileurl","upload.apk","sdfsdf");
            util.goDownloadManager();
            
            
            
  如果文件是http://  开头  且直接失败，请添加 networkSecurityConfig 配置允许http请求
  
  
