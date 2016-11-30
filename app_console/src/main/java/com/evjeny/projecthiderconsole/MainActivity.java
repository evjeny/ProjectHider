package com.evjeny.projecthiderconsole;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    EditText command;
    TextView files;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        command = (EditText) findViewById(R.id.command);
        files = (TextView) findViewById(R.id.files);
        initFiles();
    }
    public void doit(View v) {
        if(c(command)) {
            String text = command.getText().toString();
            if(text.startsWith("write(")&&text.endsWith(")")) {
                String[] attrs = text.replace("write(","").replace(")","").split(";");
                if(attrs.length==2&&new File(attrs[0]).exists()) {
                    writeFile(attrs[1], readFile(new File(attrs[0])));
                    Toast.makeText(MainActivity.this, "File writtine to internal: "
                            +attrs[1], Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "File doesnt exists", Toast.LENGTH_SHORT).show();
                }
                initFiles();
            } else if(text.startsWith("read(")&&text.endsWith(")")) {
                String[] attrs = text.replace("read(","").replace(")","").split(";");
                if(attrs.length==2&&new File(getFilesDir()+File.separator+attrs[1]).exists()) {
                    File nf = new File(attrs[0]);
                    try {
                        FileInputStream fis = openFileInput(attrs[1]);
                        byte[] buffer = readFile(new File(getFilesDir()+"/"+attrs[1]));
                        FileOutputStream fos = new FileOutputStream(new File(attrs[0]));
                        fos.write(buffer);
                        fos.close();
                        Toast.makeText(MainActivity.this, "File writtine to SD: "
                                +attrs[1], Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "File not found: "+
                            attrs[1], Toast.LENGTH_SHORT).show();
                }
                initFiles();
            } else if(text.startsWith("delete(")&&text.endsWith(")")) {
                String arg = text.replace("delete(","").replace(")","");
                File todo = new File(getFilesDir()+"/"+arg);
                if(todo.exists()) {
                    todo.delete();
                    Toast.makeText(MainActivity.this, "File has been deleted: "+
                            arg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "File not found: "+
                            arg, Toast.LENGTH_SHORT).show();
                }
                initFiles();
            } else if(text.equals("list()")) {
                initFiles();
            } else if(text.startsWith("view(")&&text.endsWith(")")) {
                String arg = text.replace("view(","").replace(")","");
                File toview = new File(getFilesDir()+File.separator+arg);
                if(toview.exists()) {
                    if(toview.isDirectory()) {
                        String[] fls = toview.list();
                        String to = "FOLDER\n";
                        for(int i = 0;i<fls.length;i++) {
                            to += fls[i] + "\n";
                        }
                        files.setText(to);
                    } else if(toview.isFile()) {
                        byte[] s = readFile(toview);
                        files.setText("FILE\n"+new String(s));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "File not found: "+
                            arg, Toast.LENGTH_SHORT).show();
                }
            } else if(text.startsWith("decodeimage(")&&text.endsWith(")")) {
                String[] attrs = text.replace("decodeimage(", "").replace(")","").split(";");
                if(attrs.length>=4) {
                    if(new File(attrs[0]).exists()) {
                        try {
                            Bitmap bmp = BitmapFactory.decodeFile(attrs[0]);
                            FileOutputStream fos = new FileOutputStream(new File(attrs[3]));
                            Bitmap.CompressFormat cp = Bitmap.CompressFormat.PNG;
                            switch (attrs[1]) {
                                case "JPEG":
                                    cp = Bitmap.CompressFormat.JPEG;
                                    break;
                                case "PNG":
                                    cp = Bitmap.CompressFormat.PNG;
                                    break;
                                case "WEBP":
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                                        cp = Bitmap.CompressFormat.WEBP;
                                    } else {
                                        cp = Bitmap.CompressFormat.JPEG;
                                    }
                                    break;
                                default:
                                    cp = Bitmap.CompressFormat.JPEG;
                            }
                            bmp.compress(cp, Integer.valueOf(attrs[2]), fos);
                            Toast.makeText(MainActivity.this, "File written to SD: "+
                                    attrs[3], Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "There is no file!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "There aren't attributes!", Toast.LENGTH_SHORT).show();
                }
            } else if(text.startsWith("decodetext(")&&text.endsWith(")")) {
                String[] attrs = text.replace("decodetext(", "").replace(")","").split(";");
                if(new File(attrs[0]).exists()&&attrs.length==4) {
                    try {
                        FileOutputStream fos = new FileOutputStream(attrs[3]);
                        OutputStreamWriter writer = new OutputStreamWriter(fos, attrs[2]);
                        writer.write(new String(readFile(new File(attrs[0])), attrs[1]));
                        writer.flush();
                        writer.close();
                        Toast.makeText(MainActivity.this, "Changed encoding for: "+
                                attrs[0], Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    public void help(View v) {
        Intent helpintent = new Intent(this, HelpActivity.class);
        startActivity(helpintent);
    }
    private boolean c(EditText et) {
        if(!et.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }
    private byte[] readFile(FileInputStream fis, int size) {
        byte[] buffer = new byte[size];
        try {
            fis.read(buffer);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace(); }
        return buffer;
    }
    private byte[] readFile(File file) {
        byte[] buffer = new byte[(int)file.length()];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(buffer);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace(); }
        return buffer;
    }
    private void writeFile(String filename, byte[] buffer) {
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
    private void initFiles() {
        String[] fls = getFilesDir().list();
        String to = "";
        for(int i = 0;i<fls.length;i++) {
            to += fls[i] + "\n";
        }
        files.setText(to);
    }
}
