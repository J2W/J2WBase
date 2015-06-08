J2WBase
===================================
接受不完美的自己，享受不断完善的自己 我们的承诺是，每天都要有进步!

Gradle 版本
-----------------------------------
1.classpath 'com.android.tools.build:gradle:1.0.0'<br />
2.版本 - gradle-2.2.1-all.zip<br />

新项目引用
-----------------------------------
Project-build.gradle

     buildscript {
         repositories {
             //从中央库里面获取依赖
             jcenter()
         }
         dependencies {
             classpath 'com.android.tools.build:gradle:1.0.0'
         }
     }
     
     allprojects {
         repositories {
             jcenter()
             //远程仓库
             maven { url "https://github.com/J2W/mvn-repo-j2w/raw/master/repository" }
         }
     }

App-build.gradle:

    android {
        //配置信息
        packagingOptions {
        exclude 'META-INF/services/javax.annotation.processing.Processor'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/NOTICE.txt'
        }
    }

Gradle依赖
-----------------------------------
App-build.gradle:<br />

    compile 'j2w.team:base:1.0.3'

AndroidManifest 权限
-----------------------------------
<!-- SDCard中创建与删除文件权限 --><br />
    
    <uses-permissioandroid:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
<!-- 读写权限 --><br />
    
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/><br />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><br />
<!-- 网络权限 --><br />

    <uses-permission android:name="android.permission.INTERNET" />

Wiki
-----------------------------------
###注意: View层改动 - 说明之后补上
[1.View层用法](https://github.com/J2W/J2WBase/wiki/1.View%E5%B1%82%E7%94%A8%E6%B3%95)<br />
[2.presenter用法](https://github.com/J2W/J2WBase/wiki/2.presenter%E7%94%A8%E6%B3%95)<br />
[3.View层和Presenter层 用例](https://github.com/J2W/J2WBase/wiki/3.View%E5%B1%82%E5%92%8CPresenter%E5%B1%82-%E7%94%A8%E4%BE%8B)<br />
[4.Modules 架构工具类](https://github.com/J2W/J2WBase/wiki/4.Modules-%E6%9E%B6%E6%9E%84%E5%B7%A5%E5%85%B7%E7%B1%BB)<br />
[5.Common widget 控件](https://github.com/J2W/J2WBase/wiki/5.Common-widget-%E6%8E%A7%E4%BB%B6)<br />


版本更新说明
-----------------------------------
###v1.0.4 - 测试中
1.增加View层Fragment提交后,根据状态 切换提交方式
###v1.0.3 - 稳定版 - APP线上正常使用
1.view层结构改版<br />
2.common 增加点击效果组件<br />
3.增加View接口<br/>
4.增加Presenter接口<br/>
5.修复ViewPager结构BUG<br/>
3.修复 代理BUG<br />
4.修复 线程池同步BUG<br/>
###v1.0.2
1.修复MVP的BUG<br />
2.增加工具类<br />
3.增加控件类<br />

###v1.0.1 - 删除maven远程仓库
1.新建整体包路径结构<br />
2.新建各大组建<br />
3.新建工具类<br />
3.新建MVP模式<br />

混淆过滤
-----------------------------------
####J2W
-keep class j2w.team.** { *; }
-dontwarn j2w.team.**

####JAVAX
-dontwarn javax.annotation.**
-keep class javax.annotation.**
-dontwarn javax.inject.**
-keep class javax.inject.**

#### View注入
-keep class * extends java.lang.annotation.Annotation { *; }

#### picasso
-dontwarn com.squareup.okhttp.**

#### butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#### okio
-dontwarn okio.**
-dontwarn in.srain.cube.**
-keep class in.srain.cube.**

####EventBus
-keepclassmembers class ** {
    public void onEvent(**);
}
-keepclassmembers class * extends j2w.team.common.event.J2WEvent {*;}

####  保留签名，解决泛型问题
-keepattributes Signature

####GSON
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.* { *; }