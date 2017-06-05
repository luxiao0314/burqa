package com.mvvm.lux.burqa.manager.login.intercept;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 24/05/2017 17:58
 * @Version
 */
public class Permission {
    public Boolean needLogin;   //需要登录
    public Boolean needBind;    //需要绑卡
    public Boolean needRealName;    //需要实名
    public Boolean needGesture; //需要手势密码
    public Boolean needSuumAuth;    //需要suum认证
}
