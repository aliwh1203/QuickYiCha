package com.aliwh.android.quickyicha.bean;

/**
 * 城市实体类
 */
public class CityBean {

    private String acronym; //城市名首字母简写
    public String name; //城市名
    public String pinyin; //拼音

    public CityBean(String acronym, String name, String pinyin) {
        this.acronym = acronym;
        this.name = name;
        this.pinyin = pinyin;
    }

    public CityBean() {
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "City{" +
                "acronym='" + acronym + '\'' +
                ", name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
