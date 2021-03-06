package com.ybase.mvp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhr on 2017/1/16.
 *
 */

public class BaseActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
