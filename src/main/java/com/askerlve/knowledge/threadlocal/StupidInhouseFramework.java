package com.askerlve.knowledge.threadlocal;

/**
 * @author Askerlve
 * @Description: TODO
 * @date 2018/7/27上午9:53
 */
public abstract class StupidInhouseFramework {

    private final String title;

    protected StupidInhouseFramework(String title) {
        this.title = title;
        draw();
    }

    public abstract void draw();

    public String toString() {
        return "StupidInhouseFramework " + title;
    }

}
