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
说明: 通过接口跟Presenter层交互，所以方法都以接口定义 然后具体actvity实现

#暂时提供以下接口和实现类
接口:
1.J2WActivityIView 普通
2.J2WActionBarIView 自定义标题栏
3.J2WFragmentIView 普通fragment
4.J2WListFragmentIView 列表fragment 
实现类:
1.J2WBaseActivity 普通
2.J2WBaseCustomActoinBarActivity 自定义标题栏
3.J2WBaseFragment 普通fragment
4.J2WBaseListFragment 列表fragment

### Presenter层
#暂时提供以下接口和实现类
接口:
1.J2WIPresenter
实现类:
1.J2WPresenter
帮助类:
1.J2WHelper modules模块接口


[modules](https://github.com/J2W/mvn-repo-j2w/blob/master/Explain/J2W_MODULES.md)
-----------------------------------
### J2WProperties
1.继承J2WProperties,实现默认方法,并且使用单例模式<br />
2.访问类型暂时提供两种<br />
   (1)OPEN_TYPE_ASSETS // 打开asset文件夹下的文件<br />
   (2)OPEN_TYPE_DATA   // 默认路径: SDCard/Android/data/你的应用包名/cache/<br />
3.文件名称默认config.properties，可调用父类构造函数修改文件名称<br />
4.使用@Property注解属性，默认key值是属性名, 也可修改 @Property("name")<br />
5.例子-声明<br />

    public class Config extends J2WProperties {
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
    
6.例子-使用<br />
(1).直接赋值<br />
TestConfig.getInstance().MY_NAME = "金灿是谁啊";<br />
TestConfig.getInstance().MY_AGE = 0;<br />
TestConfig.getInstance().MY_B = false;<br />
(2).提交 修改properties 文件内容<br />
TestConfig.getInstance().commit();<br />


[common](https://github.com/J2W/mvn-repo-j2w/blob/master/Explain/J2W_COMMON.md)
-----------------------------------
还没来得及写

