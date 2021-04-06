package com.example.ussd19.monitor;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class MonitorApp extends Application
{
    // actual store of statistics
    private final ArrayList<HashMap<String,Object>> processList = new ArrayList<HashMap<String,Object>>();

    // fast-access index by package name (used for lookup)
    private ArrayList<String> packages = new ArrayList<String>();

    public ArrayList<HashMap<String,Object>> getProcessList()
    {
        return processList;
    }

    public ArrayList<String> getPackages()
    {
        return packages;
    }

    // TODO: you need to save and load the instance data
    // TODO: you need to address synchronization issues
}
