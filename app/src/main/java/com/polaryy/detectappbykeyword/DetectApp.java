package com.polaryy.detectappbykeyword;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DetectApp {
    public static DetectApp instance = new DetectApp();

    //Lucky patcher as an example
    public String[] dexCheckFiles = new String[] {
        "LuckyPatcher",
        "GameGuardian"
    };

    public void GetDexForKeywordsOfApps() {
        try {
            List<PackageInfo> list = MainActivity.currentActivity.getPackageManager().getInstalledPackages(0);
            for (int i = 0; i < list.size(); i++) {
                PackageInfo packageInfo = list.get(i);
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    ApplicationInfo appInfo = packageInfo.applicationInfo;

                    if (!appInfo.packageName.equals(MainActivity.currentActivity.getPackageName())) {
                        ZipFile zipFile = new ZipFile(appInfo.sourceDir);
                        ZipEntry zipEntry = zipFile.getEntry("classes.dex");

                        if (zipEntry != null) {
                            InputStream inputStream = zipFile.getInputStream(zipEntry);

                            InputStreamReader isReader = new InputStreamReader(inputStream);
                            BufferedReader reader = new BufferedReader(isReader);
                            String s;
                            while ((s = reader.readLine()) != null) {
                                for (String s1 : dexCheckFiles) {
                                    if (s.contains(s1)) {
                                        String detectedString = "App was detected on the device: (" + s1 + " | " + appInfo.packageName + ")";
                                        MainActivity.ShowMessageBox(detectedString);
                                    }
                                }
                            }
                            reader.close();
                        } else {
                            Log.e("", "Zip entry is null");
                        }
                    }
                }
            }

        } catch (IOException ex) {
            Log.e("", "Failed to get PackageCodePath" + ex);
        }
    }
}