# An Unofficial burqa Android Client
## new:(集成rn分支在burqa分支,因为使用gitmodules管理子模块,如果代码拉取不下来建议使用sourceTree.master分支为纯native)
* 集成https://github.com/luxiao0314/rn_project.git (代码在food_p分支)react-native项目
* react-native项目:
  ** 动漫之家app中评论,轻小说,更新,最新小说,小说分类,分类列表,小说排行榜,小说详情,以及小说查看页面编写
  ** 完成native和rn页面跳转传值交互,rn在activity和fragment中嵌套
  ** 完成rn的统一容器JsAndroidActivity:
  
    1).跳转与传值一行代码:NativeModules.JsAndroid.jumpToActivity("lux://JsAndroidActivity?jsRouter=NovelDesPage&title=" + itemData.title + "&obj_id=" + itemData.obj_id);
    
    2).接收跳转与传值统一方法和rn容器,activity跳转使用ActivityRouter
    
      @ReactMethod
      public void jumpToActivity(String message) {
          try {
              Routers.open(IActivityManager.instance.currentActivity(), message);
          } catch (Exception e) {
              throw new JSApplicationIllegalArgumentException(
                      "不能打开Activity : " + e.getMessage());
          }
      }
    
      @Router("JsAndroidActivity")
      public class JsAndroidActivity extends BaseReactActivity {

      @Override
      protected String getMainComponentName() {
          return getIntent().getStringExtra("jsRouter");
      }
    
  ** 使用到react-native框架有:状态管理主要使用mobx框架,路由使用router-flux
  *  autoresponsive-react-native": "^1.0.9",
  *  mobx": "^3.1.9",
  *  mobx-react": "^4.1.8",
  *  react": "16.0.0-alpha.6",
  *  react-native": "0.44.0",
  *  react-native-camera": "^0.9.0",
  *  react-native-deprecated-custom-components": "^0.1.0",
  *  react-native-easy-toast": "^1.0.5",
  *  react-native-fs": "^2.3.3",
  *  react-native-photo-view": "^1.3.0",
  *  react-native-router-flux": "^3.40.1",
  *  react-native-scrollable-tab-view": "^ 0.6.x",
  *  react-native-slider": "^0.10.0",
  *  react-native-swiper": "^1.5.4",
  *  react-native-transformable-image": "0.0.18",
  *  react-native-viewpager": "^0.2.13",
  *  react-navigation": "^1.0.0-beta.5"
    


## Features

* 首页四大模块，收藏，首页，分类，推荐的实现。

* 使用rxJava+retrofit作为网络请求,并进行异常错误,response统一处理,见子模块framework

* 使用mvvm架构

* 使用多模块,并封装了framework子模块基类,widget处理三方自定义

* 使用activityRouter作为路由框架

* 使用fresco作为图片加载

* 使用realm的ram数据库和greendao数据库存储数据

* 漫画详情界面的实现。

* 推荐详情界面的实现。

* 漫画观看界面的实现。

* 漫画横屏切换界面的实现。

* 分类列表界面的实现。

* 搜索界面的实现。

## Screenshots
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160204.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160252.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160306.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160317.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160329.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160340.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160353.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160402.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160414.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160427.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160503.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160517.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160528.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160541.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160552.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160602.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160613.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160633.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160633.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160645.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160656.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160705.png)
![image](https://github.com/luxiao0314/burqa/raw/burqa/screenshots/WX20170629-160727.png)

## Compilation

## Download link
https://fir.im/may4

## UpdateLog

## API

大部分接口是使用android客户端抓包动漫之家获取的接口

## Statement

该项目仅供交流学习使用，如果该项目有侵犯布卡漫画和动漫之家版权问题，本人会及时删除此页面与整个项目。


## Thanks to the open source project

* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [RxCache](https://github.com/VictorAlbertos/RxCache)
* [okhttp](https://github.com/square/okhttp)
* [retrofit](https://github.com/square/retrofit)
* [glide](https://github.com/bumptech/glide)
* [FlycoTabLayout](https://github.com/H07000223/FlycoTabLayout)
* [FlowLayout](https://github.com/hongyangAndroid/FlowLayout)





