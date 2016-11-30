package com.evjeny.projecthider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Евгений on 13.09.2016.
 */
public class CustomToast {
    public static void showToast(Context c,String text, int iconResId, int lenght) {
        Toast t = new Toast(c);
        LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View root = li.inflate(R.layout.custom_toast, null);
        ImageView icon = (ImageView) root.findViewById(R.id.toast_icon);
        TextView tv = (TextView) root.findViewById(R.id.toast_tv);
        t.setView(root);
        tv.setText(text);
        icon.setBackgroundResource(iconResId);
        t.setDuration(lenght);
        t.show();
    }
}
