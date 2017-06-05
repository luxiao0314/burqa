package com.github.mzule.activityrouter.compiler.demo;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/4/18 14:07
 * @Version
 */
//实现Proxy接口
public class A implements Proxy {
    @Override
    public void init() { System.out.println("1"); }
    //B作为A的代理,调用A的init方法
    public static void main(String[] args) {
        B b = new B(new A());
        b.init();
    }
}

//实现Proxy接口
class B implements Proxy {
    private A mA;
    public B(A a) { mA = a; }
    @Override
    public void init() { mA.init(); }
}

interface Proxy { void init(); }
