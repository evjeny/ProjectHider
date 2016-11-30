package com.evjeny.projecthiderconsole;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Evjeny on 29.11.2016.
 * at 21:35
 */
public class HelpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        TextView tv = (TextView) findViewById(R.id.help_tv);
        String helpstr = "write(p1;p2)\nUse it to write file to internal storage\n"+
        "p1 - path to file on sd\np2 - name of file in internal storage \n\n"+
        "read(p1;p2)\nUse it to write file from internal to external storage\n"+
        "p1 - path to file on sd\np2 - name of file in internal storage \n\n"+
        "delete(p1)\nUse it to delete file from internal storage\n"+
        "p1 - name of file in internal storage\n\n"+
        "list()\nUse it to get list of files in root dir of internal storage\n\n"+
        "view(p1)\nUse it to view text-files or dirs in internal storage\n"+
        "p1 - name of file or dir in internal storage\n\n"+
        "decodeimage(p1,p2,p3,p4)\nDecode image to another one\n"+
        "p1 - path to image on SD\np2 - format of image (JPEG, PNG, WEBP)\n"+
        "p3 - quality of image (1-99)\np4 - path to new image\n\n"+
        "decodetext(p1,p2,p3,p4)\nDecode text file to file with another encoding\n"+
        "p1 = path to text file\np2 - current encoding of file\np3 - new encoding of file\n"+
        "p4 - path to new file";
        tv.setText(helpstr);
    }
}
