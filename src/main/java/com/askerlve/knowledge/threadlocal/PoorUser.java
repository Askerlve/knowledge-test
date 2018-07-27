package com.askerlve.knowledge.threadlocal;

/**
 * @author Askerlve
 * @Description: 当我们执行PoorUser，会报一个NullPointerException。该字段是Long类型的包装类。
 * draw()方法是从父类中调用的，此时PoorUser的构造函数尚未被调用。 因此它仍然是null，当它被拆箱时会导致NullPointerException。 我们可以使用ThreadLocal来解决这个问题，即使这不是典型的用例，但是看起来很有趣。
 * @date 2018/7/27上午9:53
 */
public class PoorUser extends StupidInhouseFramework {

    private final Long density;

    public PoorUser(String title, long density) {
        super(title);
        this.density = density;
    }

    public void draw() {
        long density_fudge_value = density + 30 * 113;
        System.out.println("draw ... " + density_fudge_value);
    }

    public static void main(String[] args) {
        StupidInhouseFramework sif = new PoorUser("Poor Me", 33244L);
        sif.draw();
    }

}
