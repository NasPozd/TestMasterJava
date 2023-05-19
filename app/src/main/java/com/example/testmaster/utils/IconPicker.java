package com.example.testmaster.utils;

import com.example.testmaster.R;

public class IconPicker {
    private static Integer[] icons = {
            R.drawable.ic_pic1,
            R.drawable.ic_pic2,
            R.drawable.ic_pic3,
            R.drawable.ic_pic4,
            R.drawable.ic_pic5,
            R.drawable.ic_pic6,
            R.drawable.ic_pic7,
            R.drawable.ic_pic8
    };

    private static int currentIconIndex=0;

    public static int getIcon(){
        currentIconIndex=(currentIconIndex+1)%icons.length;
        return icons[currentIconIndex];
        }
}
