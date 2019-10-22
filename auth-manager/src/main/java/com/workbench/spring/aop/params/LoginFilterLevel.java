package com.workbench.spring.aop.params;

/**
 * Created by SongCQ on 2018/6/14.
 */
public class LoginFilterLevel {

    private int level = MAX_LEVEL;//全部过滤

    private static int MAX_LEVEL = 10086;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
