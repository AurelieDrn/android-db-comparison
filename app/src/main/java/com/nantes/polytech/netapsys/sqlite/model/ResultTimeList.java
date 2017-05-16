package com.nantes.polytech.netapsys.sqlite.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HASSAN29 on 18/12/2016.
 */

public class ResultTimeList {
    private long time;
    private List<?> list;

    public ResultTimeList() {
        time = 0;
        list = new ArrayList<>();
    }

    public ResultTimeList(long t, List<?> l) {
        this.time = t;
        this.list = l;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
