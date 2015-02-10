package j2w.team.modules.appconfig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import android.content.res.Resources;

import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by sky on 9/10/14.
 */
public abstract class J2WProperties {

    /**
     * 获取TAG标记 *
     */
    public abstract String initTag();

    public int asd;
    /**
     * 获取文件路径 *
     */
    private File propertyFilePath;

    /**
     * 编码格式
     */
    private static final String DEFAULT_CODE = "gbk";

    private static final String DEFAUT_ANNOTATION_VALUE = "";

    /**
     * 默认文件名 *
     */
    private String mPropertiesFileName;
    /**
     * 默认文件后缀名 *
     */
    private static final String EXTENSION = ".properties";

    /**
     * 配置文件工具类 *
     */
    private java.util.Properties mProperties = new java.util.Properties();

    /**
     * 类型 *
     */
    public static final int OPEN_TYPE_ASSETS = 1;// 打开asset文件夹下的文件

    public static final int OPEN_TYPE_DATA = 2;// 打开应用程序data文件夹下的文件

    /**
     * 构造函数
     *
     * @param propertiesType 获取类型
     */
    public J2WProperties(int propertiesType) {
        this(propertiesType, "config");
    }

    public J2WProperties(int propertiesType, String propertiesFileName) {
        propertyFilePath = J2WHelper.getInstance().getApplicationContext().getExternalCacheDir();
        mPropertiesFileName = propertiesFileName;
        switch (propertiesType) {
            case OPEN_TYPE_ASSETS:
                Resources mResources = J2WHelper.getInstance().getApplicationContext().getResources();
                openAssetProperties(mResources);
                break;
            case OPEN_TYPE_DATA:
                openDataProperties();
                break;
        }
    }

    /**
     * asset文件夹下的文件 *
     */
    private void openAssetProperties(Resources resources) {
        L.tag(initTag());
        L.i("openProperties()");
        try {
            InputStream inputStream = resources.getAssets().open(mPropertiesFileName + EXTENSION);
            mProperties.load(inputStream);
            loadPropertiesValues();
        } catch (IOException e) {
            L.tag(initTag());
            L.e("openAssetProperties失败");
        }
    }

    /**
     * data文件夹下的文件 *
     */
    private void openDataProperties() {
        L.tag(initTag());
        L.i("openDataProperties()");
        InputStream in = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mPropertiesFileName);
            stringBuilder.append(EXTENSION);
            File file = new File(propertyFilePath, stringBuilder.toString());
            /** 处理配置文件的变化 **/
            if (!file.exists()) {
                file.createNewFile();
            }
            in = new BufferedInputStream(new FileInputStream(file));
            mProperties.load(in);
        } catch (Exception e) {
            L.tag(initTag());
            L.e("openDataProperties失败");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    L.e("" + ex);
                }
            }
        }

        loadPropertiesValues();
    }

    /**
     * 所有返回类型
     */
    private int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(mProperties.getProperty(key));
        } catch (Exception e) {
            L.tag(initTag());
            L.e("%s 解析失败, 解析类型  %s ", key, "int");
            return defaultValue;
        }
    }

    private float getFloat(String key, float defaultValue) {
        try {
            return Float.parseFloat(mProperties.getProperty(key));
        } catch (Exception e) {
            L.tag(initTag());
            L.e("%s 解析失败, 解析类型  %s ", key, "float");
            return defaultValue;
        }
    }

    private double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(mProperties.getProperty(key));
        } catch (Exception e) {
            L.tag(initTag());
            L.e("%s 解析失败, 解析类型  %s ", key, "double");
            return defaultValue;
        }
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(mProperties.getProperty(key));
        } catch (Exception e) {
            L.tag(initTag());
            L.e("%s 解析失败, 解析类型  %s ", key, "boolean");
            return defaultValue;
        }
    }

    private String getString(String key, String defaultValue) {
        String result = mProperties.getProperty(key, defaultValue);
        try {
            result = new String(result.getBytes("ISO-8859-1"), DEFAULT_CODE);
        } catch (UnsupportedEncodingException e) {
            L.tag(initTag());
            L.e("%s 解析失败, 解析类型  %s ", key, "String");
            return defaultValue;
        }
        return result;
    }

    /**
     * 解析文件 - 赋值 *
     * 获取所有的属性名 并赋值
     */
    private void loadPropertiesValues() {
        L.tag(initTag());
        L.i("loadPropertiesValues()");
        Class<? extends J2WProperties> thisClass = this.getClass();
        Field[] fields = thisClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Property annotation = field.getAnnotation(Property.class);

                if (annotation.value().equals(DEFAUT_ANNOTATION_VALUE)) {
                    setFieldValue(field, fieldName);
                } else {
                    setFieldValue(field, annotation.value());
                }
            }
        }
    }

    /**
     * 获取某个属性名 并且赋值
     */
    private void setPropertyValue(String key) {
        Class<? extends J2WProperties> thisClass = this.getClass();
        Field[] fields = thisClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (fieldName.equals(key)) {
                setFieldValue(field, fieldName);
            }
        }
    }

    /**
     * 设置属性值 *
     */
    private void setFieldValue(Field field, String propertiesName) {
        L.tag(initTag());
        L.i("loadPropertiesValues()");

        Object value = getPropertyValue(field.getType(), propertiesName);
        try {
            field.set(this, value);
        } catch (IllegalAccessException e) {
            L.tag(initTag());
            L.e("setFieldValue失败 ， 属性名 %s 文件名 %s", field.getName(), propertiesName);
        }
    }

    /**
     * 获取类型 *
     */
    private Object getPropertyValue(Class<?> clazz, String key) {
        L.tag(initTag());
        L.i("getPropertyValue()");
        if (clazz == String.class) {
            return getString(key, "");
        } else if (clazz == float.class || clazz == Float.class) {
            return getFloat(key, 0);
        } else if (clazz == double.class || clazz == Double.class) {
            return getDouble(key, 0);
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            return getBoolean(key, false);
        } else if (clazz == int.class || clazz == Integer.class) {
            return getInt(key, 0);
        } else {
            return null;
        }
    }

    public <T> void putValue(String key, T value) {
        mProperties.put(key, String.valueOf(value));
        commit();
        setPropertyValue(key);
    }

    /**
     * 提交
     */
    public void commit() {
        OutputStream out = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mPropertiesFileName);
            stringBuilder.append(EXTENSION);
            File file = new File(propertyFilePath, stringBuilder.toString());
            if (!file.exists()) {
                file.createNewFile();
            }
            synchronized (mProperties) {
                out = new BufferedOutputStream(new FileOutputStream(file));
                mProperties.store(out, "");
            }
        } catch (FileNotFoundException ex) {
            L.e("" + ex);
        } catch (IOException ex) {
            L.e("" + ex);
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException ex) {
                    L.e("" + ex);
                }
            }
        }
    }
}
