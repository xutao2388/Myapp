/**
 * ****************************************************************************
 * Copyright (C) 2012-2014 GREE, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ****************************************************************************
 */
/**
 *
 */
package com.funzio.pure2D.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.funzio.pure2D.DisplayObject;
import com.funzio.pure2D.containers.Container;
import com.funzio.pure2D.gl.gl10.textures.TextureOptions;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author long.ngo
 */
public class UIManager {
    protected static final String TAG = UIManager.class.getSimpleName();

    // singleton
    private static UIManager sInstance;

    private Context mContext;
    private Resources mResources;
    private String mPackageName;
   // private UITextureManager mTextureManager;
    private TextureOptions mTextureOptions;
    private UILoader mLoader;

    //private UIConfigVO mConfigVO;
    private Thread.UncaughtExceptionHandler mExceptionHandler;

    private UIManager() {
        mLoader = new UILoader(this);
    }

    public static UIManager getInstance() {
        if (sInstance == null) {
            sInstance = new UIManager();
        }

        return sInstance;
    }

    public void setContext(final Context context) {
        Log.w(TAG, "setContext(): " + context);
        mContext = context;

        if (context != null) {
            mResources = context.getResources();
            mPackageName = context.getApplicationContext().getPackageName();
        }
    }

    public Context getContext() {
        return mContext;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void reset() {
        Log.w(TAG, "reset()");

    }

    public Thread.UncaughtExceptionHandler getExceptionHandler() {
        return mExceptionHandler;
    }

    public UIManager setExceptionHandler(final Thread.UncaughtExceptionHandler exceptionHandler) {
        mExceptionHandler = exceptionHandler;
        return this;
    }

    /**
     * Load a config file, synchronously
     *
     * @param assets
     * @param filePath
     */
//    public UIConfigVO loadConfig(final String filePath) {
//        Log.v(TAG, "load(): " + filePath);
//
//        final ReadTextFileTask readTask = new ReadTextFileTask(mResources.getAssets(), filePath);
//        if (readTask.run()) {
//            Log.v(TAG, "Load success: " + filePath);
//
//            try {
//                mConfigVO = new UIConfigVO(new JSONObject(readTask.getContent()));
//                if (mConfigVO.texture_manager.cache_dir == null || mConfigVO.texture_manager.cache_dir.length() == 0) {
//                    // default cache dir
//                    mConfigVO.texture_manager.cache_dir = Environment.getExternalStorageDirectory() + "/Android/data/" + mPackageName + "/";
//                }
//
//                // Log.e("long", mConfigVO.texture_manager.cache_dir);
//            } catch (JSONException e) {
//                Log.e(TAG, "Load failed: " + filePath, e);
//            }
//
//        } else {
//            Log.e(TAG, "Load failed: " + filePath);
//        }
//
//        return mConfigVO;
//    }
//
//    public UIConfigVO getConfig() {
//        return mConfigVO;
//    }

    /**
     * Load a XML layout
     *
     * @param parser
     * @return
     * @throws UIException
     */
    public DisplayObject load(final XmlPullParser parser) throws UIException {
        try {
            return mLoader.load(parser);
        } catch (Exception e) {
            if (mExceptionHandler != null) {
                mExceptionHandler.uncaughtException(Thread.currentThread(), e);
            }

            throw new UIException(e);
        }
    }

    /**
     * Load content of <merge> in a XML into a specified parent
     *
     * @param parser
     * @param parent
     * @return
     * @throws UIException
     */
    public DisplayObject loadMerge(final XmlPullParser parser, final Container parent) throws UIException {
        try {
            return mLoader.loadMerge(parser, parent);
        } catch (Exception e) {
            if (mExceptionHandler != null) {
                mExceptionHandler.uncaughtException(Thread.currentThread(), e);
            }

            throw new UIException(e);
        }
    }

    /**
     * Load content of <merge> in a XML into a specified parent
     *
     * @param xmlResource
     * @param parent
     * @return
     * @throws UIException
     */
    public DisplayObject loadMerge(final int xmlResource, final Container parent) throws UIException {
        try {
            return mLoader.loadMerge(mResources.getXml(xmlResource), parent);
        } catch (Exception e) {
            if (mExceptionHandler != null) {
                mExceptionHandler.uncaughtException(Thread.currentThread(), e);
            }

            throw new UIException(e);
        }
    }

    public DisplayObject load(final String xmlString) throws UIException {
        try {
            return mLoader.load(xmlString);
        } catch (Exception e) {
            if (mExceptionHandler != null) {
                mExceptionHandler.uncaughtException(Thread.currentThread(), e);
            }

            throw new UIException(e);
        }
    }

    public DisplayObject load(final int xmlResource) throws UIException {
        try {
            return mLoader.load(mResources.getXml(xmlResource));
        } catch (Exception e) {
            if (mExceptionHandler != null) {
                mExceptionHandler.uncaughtException(Thread.currentThread(), e);
            }

            throw new UIException(e);
        }
    }

//    public void setTextureManager(final UITextureManager textureManager) {
//        Log.i(TAG, "setTextureManager(): " + textureManager);
//
//        mTextureManager = textureManager;
//
//        // apply the config
//        if (mTextureManager != null) {
//            mTextureManager.setUIManager(this); // 2-way link
//        }
//
//    }
//
//    public UITextureManager getTextureManager() {
//        return mTextureManager;
//    }

    public String evalString(final String input) {
        String value = input;
//        if (value != null) {
//            if (value.startsWith(Pure2DURI.STRING)) {
//                // localized string
//                String id = value.substring(Pure2DURI.STRING.length());
//                value = mResources.getString(mResources.getIdentifier(id, UIConfig.TYPE_STRING, mPackageName));
//            }
//
//            // process variables
//            value = value.replace(UIConfig.$CACHE_DIR, mConfigVO.texture_manager.cache_dir);
//            value = value.replace(UIConfig.$CDN_URL, mConfigVO.texture_manager.cdn_url);
//        }

        return value;
    }

    public XmlPullParser getXMLByName(final String xmlName) {
        final int id = mResources.getIdentifier(xmlName, "xml", mPackageName);
        return mResources.getXml(id);
    }

    public static class UIException extends RuntimeException {
        public UIException(final Throwable throwable) {
            super(throwable);
        }
    }

}
