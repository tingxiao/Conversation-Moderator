package com.loyola.talktracer.factory;

import android.app.Activity;

public class ActivityFactory  {

    public static Activity activity = null;

    public static void setActivity(Activity act)
    {
        activity = act;
    }

    public static  Activity getActivity()
    {
        return activity;
    }
}
