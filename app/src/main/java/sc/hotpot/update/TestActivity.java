package sc.hotpot.update;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import sc.hotpot.updatelib.DownLoadUtil;

public class TestActivity  extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        findViewById(R.id.button).setOnClickListener((v)->{
            DownLoadUtil util= DownLoadUtil.getInstance(this);
            util.startDownload("fileurl","upload.apk","sdfsdf");
            util.goDownloadManager();
//            util.startDownload("https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u=1603468086,1674922376&fm=85&app=92&f=JPEG?w=121&h=75&s=E0C3D71E4B407CC840CD0D54030050F2","upload.apk","sdfsdf");

        });
    }
}
