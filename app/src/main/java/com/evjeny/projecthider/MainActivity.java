package com.evjeny.projecthider;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.widget.*;
import android.view.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Евгений on 12.09.2016.
 */
public class MainActivity extends Activity {
    EditText path, name;
    TextView files;
    public static final int RES_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        path = (EditText) findViewById(R.id.path);
        name = (EditText) findViewById(R.id.name);
        files = (TextView) findViewById(R.id.files);
        initFiles();
        ActionBar bar = getActionBar();
        /**bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab main = bar.newTab();
        main.setText(R.string.main_tab);
        main.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        });
        bar.addTab(main);
        ActionBar.Tab settings = bar.newTab();
        settings.setText(R.string.settings);
        settings.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                ft.
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        });
        bar.addTab(settings);**/
    }
    public void write(View v) {
        Context context = getApplicationContext();
        if(c(path)&&c(name)) {
            File f = new File(path.getText().toString());
            if(f.exists()) {
                byte[] buffer = readFile(f);
                writeFile(name.getText().toString(), buffer);
                CustomToast.showToast(getApplicationContext(), s(R.string.wti)+
                name.getText().toString(), R.mipmap.ic_launcher, Toast.LENGTH_SHORT);
            } else {
                CustomToast.showToast(getApplicationContext(), s(R.string.error)+
                        "404 ept", R.mipmap.ic_launcher, Toast.LENGTH_SHORT);
            }
        }
        initFiles();
    }
    public void read(View v) {
        if (c(path)&&c(name)) {
            try {
                FileInputStream fis = openFileInput(name.getText().toString());
                byte[] buffer = readFile(new File(getFilesDir()+"/"+name.getText().toString()));
                FileOutputStream fos = new FileOutputStream(new File(path.getText().toString()));
                fos.write(buffer);
                fos.close();
                CustomToast.showToast(getApplicationContext(), s(R.string.wtsd)+
                        name.getText().toString(), R.mipmap.ic_launcher, Toast.LENGTH_SHORT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                CustomToast.showToast(getApplicationContext(), s(R.string.error)+
                        e.toString(), R.mipmap.ic_launcher, Toast.LENGTH_SHORT);
            } catch (IOException e) {
                e.printStackTrace();
                CustomToast.showToast(getApplicationContext(), s(R.string.error)+
                        e.toString(), R.mipmap.ic_launcher, Toast.LENGTH_SHORT);
            }
        }
        initFiles();
    }
    public void delete(View v) {
        if(c(name)) {
            File todo = new File(getFilesDir()+"/"+name.getText().toString());
            if(todo.exists()) {
                todo.delete();
                CustomToast.showToast(getApplicationContext(), s(R.string.hbd)+
                        name.getText().toString(), R.mipmap.ic_launcher, Toast.LENGTH_SHORT);
            } else {
                CustomToast.showToast(getApplicationContext(), s(R.string.error)+
                    "404 ept", R.mipmap.ic_launcher, Toast.LENGTH_SHORT);
            }
        }
        initFiles();
    }
    public void pickFile(View v) {
        Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
        pick.setType("file/*");
        startActivityForResult(pick, RES_CODE);
    }
    public boolean c(EditText editText) {
        if(editText.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }
    public String s(int resid) {
        return  getString(resid);
    }
    public byte[] readFile(FileInputStream fis, int size) {
        byte[] buffer = new byte[size];
        try {
            fis.read(buffer);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace(); }
        return buffer;
    }
    public byte[] readFile(File file) {
        byte[] buffer = new byte[(int)file.length()];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(buffer);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace(); }
        return buffer;
    }
    public void writeFile(String filename, byte[] buffer) {
        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(buffer);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initFiles() {
        String[] fls = getFilesDir().list();
        String to = "";
        for(int i = 0;i<fls.length;i++) {
            to += fls[i] + "\n";
        }
        files.setText(to);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RES_CODE:
                if(resultCode==RESULT_OK) {
                    String fp = data.getData().getPath();
                    path.setText(fp);
                } else {
                    CustomToast.showToast(getApplicationContext(), s(R.string.error)+
                            "wtf", R.mipmap.ic_launcher, Toast.LENGTH_SHORT);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
