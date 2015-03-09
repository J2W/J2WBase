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
    compile 'j2w.team:base:1.1'

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
    
####提供@Presenter 注解 给View层注入业务类
说明：<br />
1.TestListFragmentPresenter 是 TestListIPresenter 的 实现类<br />
    
    @Presenter(TestListFragmentPresenter.class)
    public class TestListFragment extends J2WBaseListFragment<TestListIPresenter>{
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

    @Presenter(TestListFragmentPresenter.class) //注入业务类
    public class TestListFragment extends J2WBaseListFragment<TestListIPresenter> implements TestListIView //View层接口 {
    
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
    
说明: 
1.View层 里 直接调用 getPresenter() 就能拿到业务逻辑类 引用！<br />
2.Presenter层 里 直接调用 getView() 就能拿到显示类 引用！<br />

注意: 销毁引用处理内部已经完成！如果当前视图被销毁，View层方法不会被回掉，不用担心空指针或其他情况！

### Viewpager
说明: 继承 J2WBaseViewPagerAcitvity<br />
####配合J2WBaseFragment 使用

        //J2WBaseFragment 提供延迟初始化, 当Viewpager滑动到当前页面，初始化一次，以后不在调用
        @Override
        public void initDelayedData() {
            super.initDelayedData();
        }
        //当Viewpager滑动到当前页面，每次都需要刷新的话 重写onResume
        @Override
        public void onResume() {
            super.onResume();

        }
        //当Viewpager离开当前页面 会执行 onPause(); 做处理
        @Override
        public void onPause() {
            super.onPause();

        }

####提供修改TabsItem接口

        public int getTabsBackgroundResource(); // 背景颜色

        public boolean getTabsShouldExpand();// 设置Tab是自动填充满屏幕的

        public int getTabsDividerColor();// 设置Tab的分割线是透明的

        public int getTabsTitleColor(); // 设置Tab的文字颜色

        public int getTabsTitleSize();// 设置Tab标题文字的大小

        public int getTabsSelectedTitleColor();// 设置选中Tab文字的颜色

        public int getTabsIndicatorColor();// 设置Tab Indicator 指示灯的颜色

        public int getTabsOnClickTitleColor();//设置点击颜色

        public int getTabsUnderlineColor();//设置Tab底部线的颜色

        public int getTabsUnderlineHeight();//设置Tab底部线的高度

    
####提供4种适配器类型
1.DefaultPagerAdapter  - 文本<br />

        @Override public ViewPagerModel[] getViewPagerModels() {
            	ViewPagerModel viewPagerModel = new ViewPagerModel();
            	viewPagerModel.title = "我的主页";
            	viewPagerModel.fragment = new Fragment1();
            
            	ViewPagerModel viewPagerModel1 = new ViewPagerModel();
            	viewPagerModel1.title = "我的病人";
            	viewPagerModel1.fragment = new Fragment2();
            
            	ViewPagerModel viewPagerModel2 = new ViewPagerModel();
            	viewPagerModel2.title = "我的工作";
            	viewPagerModel2.fragment = new Fragment3();
            
            	ViewPagerModel viewPagerModel3 = new ViewPagerModel();
            	viewPagerModel3.title = "我的工作";
            	viewPagerModel3.fragment = new Fragment4();
            	return new ViewPagerModel[] { viewPagerModel, viewPagerModel1, viewPagerModel2, viewPagerModel3 };
        }
        
        //适配器类型
        @Override public PagerAdapter getPagerAdapter() {
           		return new DefaultPagerAdapter();
        }
        
2.DefaultIconPagerAdapter - 图标<br />
    
    	@Override public ViewPagerModel[] getViewPagerModels() {
    		ViewPagerModel viewPagerModel = new ViewPagerModel();
            viewPagerModel.icon = R.drawable.tab_infusion;
    		viewPagerModel.fragment = new Fragment1();
    
    		ViewPagerModel viewPagerModel1 = new ViewPagerModel();
            viewPagerModel1.icon = R.drawable.tab_mypatient;
    
            viewPagerModel1.fragment = new Fragment2();
    
    		ViewPagerModel viewPagerModel2 = new ViewPagerModel();
            viewPagerModel2.icon = R.drawable.tab_mywork;
    
            viewPagerModel2.fragment = new Fragment3();
    
    		ViewPagerModel viewPagerModel3 = new ViewPagerModel();
            viewPagerModel3.icon = R.drawable.tab_personal;
    
            viewPagerModel3.fragment = new Fragment4();
    		return new ViewPagerModel[] { viewPagerModel, viewPagerModel1, viewPagerModel2, viewPagerModel3 };
    	}
    
    	@Override public String initTag() {
    		return "TestViewPagerActivity";
    	}
    
    	@Override public PagerAdapter getPagerAdapter() {
    		return new DefaultIconPagerAdapter();
    	}
    	
3.DefaultCountPagerAdapter - 文本 + 数量<br />

    	@Override public ViewPagerModel[] getViewPagerModels() {
    		ViewPagerModel viewPagerModel = new ViewPagerModel();
    		viewPagerModel.title = "我的主页";
    		viewPagerModel.fragment = new Fragment1();
    
    		ViewPagerModel viewPagerModel1 = new ViewPagerModel();
    		viewPagerModel1.title = "我的病人";
    		viewPagerModel1.fragment = new Fragment2();
    
    		ViewPagerModel viewPagerModel2 = new ViewPagerModel();
    		viewPagerModel2.title = "我的工作";
    		viewPagerModel2.fragment = new Fragment3();
    
    		ViewPagerModel viewPagerModel3 = new ViewPagerModel();
    		viewPagerModel3.title = "我的工作";
    		viewPagerModel3.fragment = new Fragment4();
    		return new ViewPagerModel[] { viewPagerModel, viewPagerModel1, viewPagerModel2, viewPagerModel3 };
    	}
    
    	@Override public PagerAdapter getPagerAdapter() {
    		return new DefaultCountPagerAdapter();
    	}
    
        @Override
        public void initData(Bundle savedInstanceState) {
            super.initData(savedInstanceState);
            setTitleCount(0,"20");
            setTitleCount(1,"30");
            setTitleCount(2,"40");
            setTitleCount(3,"50");
        }
        
4.CustomPagerAdapter - 自定义<br />

    	int	icon[]	= { R.drawable.tab_infusion, R.drawable.tab_mypatient, R.drawable.tab_mywork, R.drawable.tab_personal };
    
        String value[] = {"我的主页","我的工作","我的工1","我的工2"};
    
    	@Override public int getViewPagerItemLayout() {
    		return R.layout.tab_content;
    	}
    
    	@Override public ViewPagerModel[] getViewPagerModels() {
    		ViewPagerModel viewPagerModel = new ViewPagerModel();
    		viewPagerModel.title = "我的主页";
    		viewPagerModel.fragment = new Fragment1();
    
    		ViewPagerModel viewPagerModel1 = new ViewPagerModel();
    		viewPagerModel1.title = "我的病人";
    		viewPagerModel1.fragment = new Fragment2();
    
    		ViewPagerModel viewPagerModel2 = new ViewPagerModel();
    		viewPagerModel2.title = "我的工作";
    		viewPagerModel2.fragment = new Fragment3();
    
    		ViewPagerModel viewPagerModel3 = new ViewPagerModel();
    		viewPagerModel3.title = "我的工作";
    		viewPagerModel3.fragment = new Fragment4();
    		return new ViewPagerModel[] { viewPagerModel, viewPagerModel1, viewPagerModel2, viewPagerModel3 };
    	}
    
    	@Override public String initTag() {
    		return "TestViewPagerActivity";
    	}
    
    	@Override public PagerAdapter getPagerAdapter() {
    		return new CustomPagerAdapter();
    	}
    
    	@Override public void initTab(View view, int position) {
    		ImageView mImageView = (ImageView) view.findViewById(R.id.tab_imageview);
    		TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
    		mImageView.setBackgroundResource(icon[position]);
    		mTextView.setText(value[position]);
    	}

#### style - 可以修改tab高度 默认48dp

    <style name="J2W.Layout.ViewPagerTabStrip">
        <item name="android:layout_height">100dp</item>
    </style>

### TabHost
说明: <br />
1.继承 J2WBaseTabHostAcitvity<br />
2.内容fragment和TabsItem修改接口，与viewpager一致<br />
参考代码：

    int	icon[]	= { R.drawable.tab_infusion, R.drawable.tab_mypatient, R.drawable.tab_mywork, R.drawable.tab_personal };

    String value[] = {"我的主页","我的工作","我的工1","我的工2"};

    @Override public String initTag() {
        return "TestViewPagerActivity";
    }

    @Override
    public int getTabsContentLayout() {
        return R.layout.tab_content;
    }

    @Override public void initTab(View view, int position) {
        ImageView mImageView = (ImageView) view.findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setBackgroundResource(icon[position]);
        mTextView.setText(value[position]);
    }

    @Override public ViewPagerModel[] getViewPagerModels() {
        ViewPagerModel viewPagerModel = new ViewPagerModel();
        viewPagerModel.title = "我的主页";
        viewPagerModel.fragment = new Fragment1();

        ViewPagerModel viewPagerModel1 = new ViewPagerModel();
        viewPagerModel1.title = "我的病人";
        viewPagerModel1.fragment = new Fragment2();

        ViewPagerModel viewPagerModel2 = new ViewPagerModel();
        viewPagerModel2.title = "我的工作";
        viewPagerModel2.fragment = new Fragment3();

        ViewPagerModel viewPagerModel3 = new ViewPagerModel();
        viewPagerModel3.title = "我的工作";
        viewPagerModel3.fragment = new Fragment4();
        return new ViewPagerModel[] { viewPagerModel, viewPagerModel1, viewPagerModel2, viewPagerModel3 };
    }

    //选中状态设置
    private View oldView;
    private int oldPosition;
    @Override
    public void onExtraPageSelected(View view,int position) {
        super.onExtraPageSelected(view,position);
        if(oldView != null){
            ImageView mImageView = (ImageView) oldView.findViewById(R.id.tab_imageview);
            mImageView.setBackgroundResource(icon[oldPosition]);
        }

        ImageView mImageView = (ImageView) view.findViewById(R.id.tab_imageview);
        mImageView.setBackgroundResource(R.drawable.ic_launcher);

        oldView = view;
        oldPosition = position;
    }
#### style - 可以修改tab高度 默认48dp

    <style name="J2W.Layout.TabHost">
        <item name="android:layout_height">100dp</item>
    </style>

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
    	
    	
### Dialog
####暂时提供以下接口和实现类
#####进度条对话框
1.调用
    
    ProgressDailogFragment.createBuilder()
        .setTitle("title")//设置标题
        .setMessage("message")//设置内容
        .setRequestCode(1)//设置请求编码
        .show();//显示

#####简单对话框
1.调用
    
    //说明一下,三个按钮如果text过长 从上往下排序显示，否则从左往右显示
    SimpleDialogFragment.createBuilder()
        .setTitle("title")//设置标题
        .setMessage("message")//设置内容
        .setRequestCode(1)//设置请求编码
        .setPositiveButtonText("正面按钮")
        .setNegativeButtonText("反面按钮")
        .setNeutralButtonText("中性按钮")
        .show();

2.拦截点击事件
    
    //三个按钮 - 也可以单独拦截一个按钮 三个接口:
    1.INegativeButtonDialogListener
    2.IPositiveButtonDialogListener
    3.INeutralButtonDialogListener
    
    public class MainActivity implements IDialogListener{
    
        @Override public void onNegativeButtonClicked(int i) {
            L.i("onNegativeButtonClicked" + i);
        }

        @Override public void onNeutralButtonClicked(int i) {
            L.i("onNeutralButtonClicked" + i);
        }

        @Override public void onPositiveButtonClicked(int i) {
            L.i("onPositiveButtonClicked" + i);
        }
    }

#####列表对话框
    
    ListDialogFragment.createBuilder()
        .setTitle("Your favorite character:")//标题
        .setItems(new String[]{"Jayne", "Malcolm", "Kaylee", "Wash", "Zoe", "River"})//设置数据
        .setRequestCode(12) //请求编码
        .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE) //设置列表属性
        .show();

    setChoiceMode 有三个属性
    1.AbsListView.CHOICE_MODE_SINGLE 列表是单选对话框
    2.AbsListView.CHOICE_MODE_MULTIPLE 列表是多选对话框
    3.AbsListView.CHOICE_MODE_NONE 列表是简单对话框

#####自定义对话框
说明：为了扩展，可以自定义一个对话框
    
    //1.继承简单对话框
    public class JayneHatDialogFragment extends SimpleDialogFragment {
    
        @Override
        public BaseDialogFragment.Builder build(BaseDialogFragment.Builder builder) {
            //设置标题栏
            builder.setTitle("Jayne's hat");
            //设置布局文件
            builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.view_jayne_hat, null));
            //设置按钮
            builder.setPositiveButton("I want one", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (IPositiveButtonDialogListener listener : getPositiveButtonDialogListeners()) {
                        listener.onPositiveButtonClicked(mRequestCode);
                    }
                    dismiss();
                }
            });
            return builder;
        }
    }
    
    

#####统一拦截取消

    public class MainActivity implements IDialogCancelListener{
        @Override public void onCancelled(int i) {//i 是请求编码
        		L.i("取消" + i);
        }
    }

#####统一修改样式
    
    //架构里默认样式 - google一下下面属性, 就知道怎么修改样式了
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
            <item name="colorPrimary">@color/primary</item> 
            <item name="colorPrimaryDark">@color/accent</item> 
            <item name="colorAccent">@color/accent</item> //修改系统样式颜色
            <item name="android:progressBarStyle">@style/Widget.J2W.ProgressBar</item>
    </style>
    
    

[common](https://github.com/J2W/mvn-repo-j2w/blob/master/Explain/J2W_COMMON.md)
-----------------------------------
###自动轮播 AutoScrollViewPager
####样式定义

    <RelativeLayout 
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" >

    <j2w.team.common.widget.infiniteviewpager.AutoScrollViewPager
        android:id="@+id/autoScrollViewPager"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        />


    <j2w.team.common.widget.infiniteviewpager.InfiniteCirclePageIndicator
        android:id="@+id/infiniteCirclePageIndicator"
        android:layout_width="fill_parent"
        android:layout_height="30dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"
        android:layout_above="@+id/autoScrollViewPager"

        //填充颜色
        app:fillColor="@color/default_circle_indicator_fill_color"
        //默认显示颜色
        app:pageColor="@android:color/holo_purple"
        //大小
        app:radius="4dp"
        //圆圈颜色
        app:strokeColor="@color/default_circle_indicator_stroke_color"
        app:strokeWidth="0dp"
      />
      </RelativeLayout>
####参考代码

        AutoScrollViewPager mViewPager = (AutoScrollViewPager) findViewById(R.id.sample_infinite_pager);
		NumbersAdapter numbersAdapter = new NumbersAdapter(getSupportFragmentManager());//适配器
		mViewPager.setAdapter(numbersAdapter);
		mViewPager.setInterval(3000);//轮播速度-毫秒
		mViewPager.startAutoScroll();//开始轮播
		InfiniteCirclePageIndicator mPageIndicator = (InfiniteCirclePageIndicator) findViewById(R.id.sample_infinite_page_indicator);
		mPageIndicator.setSnap(true);//是否viewpager滑动时跟随滑动
		mPageIndicator.setViewPager(mViewPager);//交给indicator

        private class NumbersAdapter extends InfiniteStatePagerAdapter {

            public int	count	= 3;

            public NumbersAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override public int getCount() {
                return count;
            }

            @Override public Fragment getItem(int i) {
                return ArrayFragment.newInstance(i);
            }

        }

</RelativeLayout>


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
