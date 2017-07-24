package com.coolweather.mondel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHH on 2017/7/14.
 */

public class City {

    private int id;
    private String name;
    private String code;
    private List<County> countyList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<County> getCountyList() {
        return countyList;
    }

    public void setCountyList(List<County> countyList) {
        this.countyList = countyList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
