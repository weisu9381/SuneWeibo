# SuneWeibo
## 导入到android studio需要版本匹配
* 翻墙，改DNS 
* 先不要打开studio,先修改 gradle-wrapper.properties  
* 最外面的 build.gradle:
```
    dependencies {  
        classpath 'com.android.tools.build:gradle:2.3.0'
        }  
```
* 除了app下的build.gradle, 其它目录下的build.gradle也要改：（这里会有提示，还好）
  * compile 'com.android.support:appcompat-v7:25.2.0'（我的targetSdkVersion是25）

导入WeiboSDK:  https://github.com/ksharpdabu/SinaWeibo_SDK_Perfect_Demo
导入该module以后，记得在 app的build.gradle 添加 compile project(':WeiboSDK')（要 删除module的话 先看看 settings.gradle）

复制WeiboSDKDemo下的AccessTokenKeeper和Constants到工程中(往Constants中添加AppKey啥的）
             * （注册微博开发者的时候先选择“网站接入”，不然邮件验证不了)

## mvp目录(imp目录是实现相应接口的类)

* model目录通常 调用 WeiboSDK下的openAPI来获得 数据
  * openAPI目录下的models目录是通过 jsonObject的方式解析 json数据
  * 回调机制：按 ctrl+H 可以查看 谁实现了该接口里面的内容，按 ctrl+点击鼠标左键，可以看到谁调用了该方法
  * 通常是model接口定义了interface ，model/imp下调用方法，presenter下使用 匿名内部类 重写了方法
*  通过presenter目录，传进view(没有数据)和model，通过model得到数据，再把数据在view上显示
*  ui目录下的activity和fragment实现了 view目录的接口（数据主要是有 PresentImp来显示的） 

## 实现web方式的登录(需要在AndroidManifest.xml添加三个activity)
```<!-- 登录web界面 -->
        <activity
            android:name="com.sina.weibo.sdk.component.WeiboSdkBrowser"
            android:configChanges="keyboardHidden|orientation" />
        <!-- 手机短信注册页面 -->
        <activity
            android:name="com.sina.weibo.sdk.register.mobile.MobileRegisterActivity"
            android:configChanges="keyboardHidden|orientation" />
        <!-- 注册选择国家页面 -->
        <activity
            android:name="com.sina.weibo.sdk.register.mobile.SelectCountryActivity"
            android:configChanges="keyboardHidden|orientation" />
       以及网络授权<uses-permission android:name="android.permission.INTERNET" />
```

 ```
       utils包下的 SDCardUtils工具类 需要 读写 Sd卡的权限：
               <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
               <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

底部栏的从左到右的名字： 首页home(或main)，消息message，发微博post，发现discovery(或discover)，我profile

## WebViewActicity网页登录
* Token类 保存Oauth2AccessToken授权需要的 4个令牌，TokenList类是  Token的集合，多个账号登录
* 与 TokenList 对应的是 mvp/model/imp/TokenListModelImp


## 可能会 遇到的问题：
* 【缺少.so库】在app的build.gradle添加：（生成jniLibs目录）
```          
          sourceSets {
             main {
                jniLibs.srcDirs = ['libs']
              }
            }
        往jniLibs下 新建文件夹armeabi-v7a等等，每个文件夹里面都 放入  libweibosdkcore.so
        (不然会报错：java.lang.UnsatisfiedLinkError)
```

