J2WBase
===================================
接受不完美的自己，享受不断完善的自己 我们的承诺是，每天都要有进步!

Gradle 版本
-----------------------------------
classpath 'com.android.tools.build:gradle:1.0.0'
版本 - gradle-2.2.1-all.zip

新项目引用
-----------------------------------
build.gradle

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

Gradle:

    android {
        //配置信息
        packagingOptions {
        exclude 'META-INF/services/javax.annotation.processing.Processor'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/NOTICE.txt'
        }
    }
    //依赖
    compile 'j2w.team:base:1.0'

[MVP](https://github.com/J2W/mvn-repo-j2w/blob/master/Explain/J2W_MVP.md)
-----------------------------------

### View层
说明: 通过接口跟Presenter层交互，所以方法都以接口定义 然后具体actvity实现

####暂时提供以下接口和实现类
接口:<br />
1.J2WActivityIView 普通<br />
2.J2WActionBarIView 自定义标题栏<br />
3.J2WFragmentIView 普通fragment<br />
4.J2WListFragmentIView 列表fragment <br />
实现类:<br />
1.J2WBaseActivity 普通<br />
2.J2WBaseCustomActoinBarActivity 自定义标题栏<br />
3.J2WBaseFragment 普通fragment<br />
4.J2WBaseListFragment 列表fragment<br />

### Presenter层
####暂时提供以下接口和实现类
接口:<br />
1.J2WIPresenter<br />
实现类:<br />
1.J2WPresenter<br />
帮助类:<br />
1.J2WHelper modules模块接口<br />

####提供@Background 注解 子线程执行方法
参数提供:<br />
1.SINGLE 串行执行<br />
2.MULTI  并行执行<br />
参考代码:<br />
1.默认为MULTI<br />

    @Background
    @Override public void setValues() {
    
    }
         
2.设置串行<br />

    @Background(SINGLE)
    @Override public void setValues() {
    
    }
    
3.J2WPresenter 提供方法内部错误后捕捉异常方法<br />
说明: 实现该方法处理自己的异常处理  <br />

    /**
     * 方法错误处理
     * s 参数  方法名称
     * throwable 参数  异常接口
     */
    public void methodError(String s, Throwable throwable) {
        L.e(throwable.toString());
    }
      
### 使用说明:
简介: 简单用 展示一个列表为例子 , 以后继续完善 <br />
1.定义View 层接口<br />

    public interface TestListIView extends J2WListFragmentIView {
       //显示列表  void setData(java.util.List list) 默认父类方法 
       //可以加其他方法, 这里暂时什么都不写！
    }
2.定义Presneter 层 接口<br />

    public interface TestListIPresenter extends J2WIPresenter {
        public void setValues(); //加载数据后 显示列表
    }
3.创建Presneter 业务处理类<br />
说明：继承 J2WPresenter<TestListIView>  第一个泛型 是 View层接口<br />

    public class TestListFragmentPresenter extends J2WPresenter<TestListIView> implements TestListIPresenter //业务逻辑层接口 { 
    	@Override public String initTag() {
    		return "TestListFragmentPresenter";
    	}
    
        //当前类方法出现异常的时候调用该方法
        @Override
        public void methodError(String s, Throwable throwable) {
            L.i(s+":"+throwable.toString());
        }

    
        @Background   //注解为在子线程执行方法
    	@Override public void setValues() {
    	    List<String> list = new ArrayList<String>();
    		list.add("a");
    		list.add("a");
    		list.add("a");
    		list.add("a");
    		list.add("a");
    		list.add("a");
    		list.add("a");
    		
    		try {
                Thread.sleep(3000); //延迟3秒后显示
            } catch (InterruptedException e) {
            	e.printStackTrace();
            }
    		
    		getView().setData(list);//调用View层接口
    	}
    }
4.创建AdapterItem<br />

    public class TestItemAdapter extends J2WBaseAdapterItem<String> {
    	@InjectView(R.id.text) View test;
    
    	@Override public int getItemLayout() {
    		return R.layout.item_test;
    	}
    
    	@Override public void init(View view) {
    		ButterKnife.inject(this,view);
    	}
    
        @OnClick(R.id.text)
        public void onClickTest(){
            Toast.makeText(J2WHelper.getInstance().getApplicationContext(), "点击了", Toast.LENGTH_SHORT).show();
    
        }
        
    	@Override public void bindData(String s) {
    		((TextView) test).setText(s);
    	}
    }

5.创建View 显示类<br />

    public class TestListFragment extends J2WBaseListFragment<TestListIPresenter,TestListFragmentPresenter> implements TestListIView //View层接口 {
    
    	@Override public String initTag() {iew
    		return "TestListFragment";
    	}
    
    	@Override public J2WBaseAdapterItem getJ2WAdapterItem() {
    		return new TestItemAdapter(); // 第4步 的类
    	}
    
    	@Override public void initData(Bundle savedInstanceState) {
    		super.initData(savedInstanceState);
    		getPresenter().setValues(); //调用业务逻辑类
    	}
    	
    }
    
说明: J2WBaseListFragment<TestListIPresenter,TestListFragmentPresenter><br />
1.第一个泛型是Presenter接口，第二个泛型是Presenter接口的实现类<br />
2.View层 里 直接调用 getPresenter() 就能拿到业务逻辑类 引用！<br />
3.Presenter层 里 直接调用 getView() 就能拿到显示类 引用！<br />

注意: 销毁引用处理内部已经完成！如果当前视图被销毁，View层方法不会被回掉，不用担心空指针或其他情况！

[modules](https://github.com/J2W/mvn-repo-j2w/blob/master/Explain/J2W_MODULES.md)
-----------------------------------
### 线程池 
#### 功能:主要是架构使用-开发人员不要使用
##### 提供两种线程池
1.串行线程池

    J2WHelper.getThreadPoolHelper().getSingleExecutorService()

    例子:
    J2WHelper.getThreadPoolHelper().getSingleExecutorService().execute(new J2WAsyncCall(method.getName()) {
    				@Override protected void execute() {
    				    //耗时操作
    				}
    			});
    
2.并行线程池
    
    J2WHelper.getThreadPoolHelper().getExecutorService()
 
    例子:
	J2WHelper.getThreadPoolHelper().getExecutorService().execute(new J2WAsyncCall(method.getName()) {
				    @Override protected void execute() {
				        //耗时操作
				    }
			    });

### J2WProperties
#### 功能:替代 SharedPreference 来存储数据

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

### Http网络层
#### 功能：替代传统的网络写法，底层使用okhttp
##### 创建网络适配器 - 请使用单例模式
网络层生成器 :  J2WRestAdapter <bar />
网络层生成器帮助类 : J2WHelper.getJ2WRestBuilder()<br />

1.设置地址 J2WEndpoint<br />
说明: 必须给一个默认地址<br />
用法:<br />

    J2WRestAdapter mJ2WRestAdapter = J2WHelper.getJ2WRestBuilder()
                                              .setEndpoint("https://api.github.com")
                                              .build();
    

2.设置拦截器 J2WRequestInterceptor<br />
说明: 想给每一个请求添加一个标题或者参数<br />
用法: <br />

    //自定义拦截器
    J2WRequestInterceptor requestInterceptor = new J2WRequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                //添加头信息
                request.addHeader("User-Agent", "Retrofit-Sample-App");
                //添加请求参数
                request.addQueryParam("query","123456");
            }
    };

    J2WRestAdapter mJ2WRestAdapter = J2WHelper.getJ2WRestBuilder()
                                              .setEndpoint("https://api.github.com")
                                              .setRequestInterceptor(requestInterceptor)
                                              .build();

3.设置数据转换器<br />
说明: 请求和响应的数据转换，默认使用的是GSON<br />
用法:<br />

    //自定义数据转换器
    J2WConverter j2WConverter = new J2WConverter() {
            @Override
            public Object fromBody(ResponseBody responseBody, Type type) throws IOException {
                return null;
            }
    
            @Override
            public RequestBody toBody(Object o, Type type) {
                return null;
            }
    };
    
    J2WRestAdapter mJ2WRestAdapter = J2WHelper.getJ2WRestBuilder()
                                              .setEndpoint("https://api.github.com")
                                              .setConverter(j2WConverter)
                                              .build();
     
4.设置错误处理<br />
说明: 自定义异常处理<br />
用法:<br />

    //自定义错误处理
    class MyErrorHandler implements J2WErrorHandler {
            @Override public Throwable handleError(J2WError cause) {
                Response r = cause.getResponse();
                if (r != null &&  r.code()== 401) {
                    //错误处理
                    return 返回自定义异常
                }
                return cause;
            }
    }

    J2WRestAdapter mJ2WRestAdapter = J2WHelper.getJ2WRestBuilder()
                                              .setEndpoint("https://api.github.com")
                                              .setErrorHandler(new MyErrorHandler())
                                              .build();
##### 提供注解
1.GET 和 Path <br />

    @GET("/users/{user}/repos")
    List<Repo> listRepos(@Path("user") String user);

2.GET 和 Query <br />

    @GET("/group")
    List<User> groupList(@Query("sort") String sort);
    
3.GET 和 QueryMap <br />

    @GET("/group")
    List<User> groupList(@QueryMap Map<String, String> options);
    
4.POST 和 Body <br />

    @POST("/users/new")
    void createUser(@Body User user, Callback<User> cb);

5.Headers <br />

    @Headers("Cache-Control: max-age=640000")
    @GET("/widget/list")
    List<Widget> widgetList();
    
    @Headers({
        "Accept: application/vnd.github.v3.full+json",
        "User-Agent: Retrofit-Sample-App"
    })
    @GET("/widget/list")
    List<Widget> widgetList();
    
6.Header 可动态更新 <br />

    @GET("/user")
    void getUser(@Header("Authorization") String authorization, Callback<User> callback)

##### 使用方式

1.定义网络接口 - 这里使用github接口<br />

    public interface GitHubService {
        @GET("/users/{user}/repos")
        List<Repo> listRepos(@Path("user") String user);
    }
    
2.使用适配器<br />

    J2WRestAdapter mJ2WRestAdapter = J2WHelper.getJ2WRestBuilder()
                                              .setEndpoint("https://api.github.com")
                                              .build();
    //创建并返回接口实例
    GitHubService service ＝ mJ2WRestAdapter.create(GitHubService.class);
            
    //直接使用
    List<Repo> list = service.listRepos("octocat");


### fragmentactivity管理器
#### 功能 : activity堆栈管理
堆栈管理帮助类 : J2WHelper.getScreenHelper()<br />
##### 提供接口
    	/**
    	 * 获取当前活动的activity
    	 *
    	 * @return
    	 */
    	public FragmentActivity currentActivity();
    
    	/**
    	 * 入栈
    	 *
    	 * @param activity
    	 */
    	public void pushActivity(FragmentActivity activity);
    
    	/**
    	 * 出栈
    	 *
    	 * @param activity
    	 */
    	public void popActivity(FragmentActivity activity);
    
    	/**
    	 * 退出堆栈中所有Activity, 当前的Activity除外
    	 *
    	 * @param clazz
    	 *            当前活动窗口
    	 */
    	public void popAllActivityExceptMain(Class clazz);
    	

[common](https://github.com/J2W/mvn-repo-j2w/blob/master/Explain/J2W_COMMON.md)
-----------------------------------
还没来得及写


AndroidManifest 权限
-----------------------------------
<!-- SDCard中创建与删除文件权限 --><br />
    
    <uses-permissioandroid:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
<!-- 读写权限 --><br />
    
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/><br />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><br />
<!-- 网络权限 --><br />

    <uses-permission android:name="android.permission.INTERNET" />


混淆过滤
-----------------------------------
#### greendao
-libraryjars libs/greendao-1.3.7.jar<br />
-keep class de.greenrobot.dao.** {*;}<br />
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {<br />
    public static java.lang.String TABLENAME;<br />
}<br />
-keep class **$Properties<br />
#### Gson
-keepattributes Signature<br />
-keep class sun.misc.Unsafe { *; }<br />
-keep class com.google.gson.examples.android.model.** { *; }<br />
