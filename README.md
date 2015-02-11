J2WBase
===================================
接受不完美的自己，享受不断完善的自己 我们的承诺是，每天都要有进步!

Gradle 版本
-----------------------------------
classpath 'com.android.tools.build:gradle:1.0.0'
版本 - gradle-2.2.1-all.zip

[MVP](https://github.com/J2W/mvn-repo-j2w/blob/master/Explain/J2W_MVP.md)
-----------------------------------

### View层
还没来得及写

### Presenter层
还没来得及写


[modules](https://github.com/J2W/mvn-repo-j2w/blob/master/Explain/J2W_MODULES.md)
-----------------------------------
### J2WProperties
1.继承J2WProperties,实现默认方法,并且使用单例模式
2.访问类型暂时提供两种
   (1)OPEN_TYPE_ASSETS // 打开asset文件夹下的文件
   (2)OPEN_TYPE_DATA   // 默认路径: SDCard/Android/data/你的应用包名/cache/
3.文件名称默认config.properties，调用父类构造函数
4.使用@Property注解属性，默认key值是属性名, 也可修改 @Property("name")
5.例子-声明
public class Config extends J2WProperties {
    /** 1.单例模式 **/
    private final static TestConfig testConfig = new TestConfig("新名字");

    public static TestConfig getInstance() {
        return testConfig;
    }
    /** 重新起名字，也可以使用默认名称 **/
    public TestConfig(String configName){
        super(configName);
    }
    public static TestConfig getInstance() {
        return testConfig;
    }
    /**TAG 标记 打印日志使用**/
    @Override
    public String initTag() {
        return "TestConfig";
    }
    /** 3.访问类型 **/
    @Override
    public int initType() {
        return J2WProperties.OPEN_TYPE_DATA;
    }
    /** 4.注解变量**/
    @Property
    public String MY_NAME;
    @Property
    public int MY_AGE;
    @Property
    public boolean MY_B;
}
6.例子-使用 
(1).直接赋值
TestConfig.getInstance().MY_NAME = "金灿是谁啊";
TestConfig.getInstance().MY_AGE = 0;
TestConfig.getInstance().MY_B = false;
(2).提交 修改properties 文件内容
TestConfig.getInstance().commit();


[common](https://github.com/J2W/mvn-repo-j2w/blob/master/Explain/J2W_COMMON.md)
-----------------------------------
还没来得及写

