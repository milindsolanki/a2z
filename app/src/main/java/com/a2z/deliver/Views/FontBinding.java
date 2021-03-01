package com.a2z.deliver.views;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FontBinding {
    @BindingAdapter({"font"})
    public static void setFont(View v, String fontName) {
        if (v instanceof TextView)
            ((TextView)v).setTypeface(CustomFontFamily.getInstance().getFont(fontName));
        else if (v instanceof EditText)
            ((EditText)v).setTypeface(CustomFontFamily.getInstance().getFont(fontName));
        else if (v instanceof Button)
            ((Button)v).setTypeface(CustomFontFamily.getInstance().getFont(fontName));
    }

    @BindingAdapter("android:typeface")
    public static void setTypeface(View v, String style) {
        switch (style) {
            case "bold":
                if (v instanceof TextView)
                    ((TextView)v).setTypeface(null, Typeface.BOLD);
                else if (v instanceof EditText)
                    ((EditText)v).setTypeface(null, Typeface.BOLD);
                if (v instanceof Button)
                    ((Button)v).setTypeface(null, Typeface.BOLD);
                break;
            default:
                if (v instanceof TextView)
                    ((TextView)v).setTypeface(null, Typeface.NORMAL);
                else if (v instanceof EditText)
                    ((EditText)v).setTypeface(null, Typeface.NORMAL);
                if (v instanceof Button)
                    ((Button)v).setTypeface(null, Typeface.NORMAL);
                break;
        }
    }
} 