package j2w.team.modules.appconfig.old;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import j2w.team.common.log.L;

/**
 * 处理SharedPreferences相关. <br/>
 * put commit() 必须 <br/>
 * Created by yangqinghai on 13-12-23.
 */
public abstract class UtilConfig {

    private static final String TAG = UtilConfig.class.getSimpleName();

    /**
     * 配置信息缓存
     */
    protected Properties mProperties = new Properties();

    public UtilConfig() {
        loadProperties();
    }

    //文件名
    protected abstract String getConfigName();

    //获取文件路径
    protected abstract String getPropertyFilePath();

    public String getString(String key) {
        return getString(key, "");
    }

    /**
     * 获取key下保存的defValue
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        if (loadProperties()) {
            return mProperties.getProperty(key, defValue);
        } else {
            return defValue;
        }
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return Boolean.parseBoolean(getString(key, String.valueOf(defValue)));
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, Integer defValue) {
        return Integer.parseInt(getString(key, String.valueOf(defValue)));
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    public float getFloat(String key, float defValue) {
        String tmpStr = getString(key, String.valueOf(defValue));
        return Float.parseFloat(tmpStr);
    }

    public long getLong(String key, long defValue) {
        String tmpStr = getString(key, String.valueOf(defValue));
        return Long.parseLong(tmpStr);
    }

    public long getLong(String key) {
        return getLong(key, -1);
    }

    public void putString(String key, String value) {
        if (value == null) {
            return;
        }
        mProperties.put(key, value);
    }

    public void putBoolean(String key, boolean value) {
        putString(key, String.valueOf(value));
    }

    public void putInt(String key, Integer value) {
        putString(key, String.valueOf(value));
    }

    public void putFloat(String key, Float value) {
        putString(key, String.valueOf(value));
    }

    public void putLong(String key, long value) {
        putString(key, String.valueOf(value));
    }

    /**
     * 是否包含该key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        if (mProperties == null || key == null) {
            return false;
        }
        return mProperties.containsKey(key);
    }

    // 移除键
    public void remove(String key) {
        mProperties.remove(key);
    }

    /**
     * 提交，在put value过后必须调用 getConfigName()，由于可能之前没有uid，所以这里不能一开始就初始化configname,
     * 需要后续过程中再次获取configname
     */
    public void commit() {
        String configName = getConfigName();
        FileOutputStream fos = null;
        try {
            // 同步修改
            synchronized (mProperties) {
                fos = new FileOutputStream(getPropertyFilePath() + configName);
                mProperties.store(fos, "");
            }
        } catch (FileNotFoundException ex) {
            L.e(""+ ex);
        } catch (IOException ex) {
            L.e("" + ex);
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    L.e( ""+ ex);
                }
            }
        }
    }

    /**
     * 初始化Properties
     */
    protected boolean loadProperties() {
        if (null == mProperties || mProperties.size() == 0) {
            FileInputStream fis = null;
            try {
                synchronized (this){
                    File dir = new File(getPropertyFilePath());
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = null;
                    L.v("configname==" + getConfigName() + "==PROPERTY_FILE_PATH==" + getPropertyFilePath());
                    file = new File(getPropertyFilePath(), getConfigName());
                    /** 处理配置文件的变化 **/
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    fis = new FileInputStream(file);
                    mProperties.load(fis);
                }
                return true;
            } catch (FileNotFoundException ex) {
                L.e(""+ex);
                return false;
            } catch (IOException ex) {
                L.e( ""+ ex);
                return false;
            } finally {
                if (null != fis) {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        L.e( ""+ ex);
                    }
                }
            }
        }
        return true;
    }
}
