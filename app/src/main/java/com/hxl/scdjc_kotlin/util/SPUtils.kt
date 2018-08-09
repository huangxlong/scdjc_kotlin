package com.hxl.scdjc_kotlin.util


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.support.v4.util.SimpleArrayMap
import com.hxl.scdjc_kotlin.app.App

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : utils about shared preference
</pre> *
 */
@SuppressLint("ApplySharedPref")
class SPUtils {
    private var sp: SharedPreferences? = null

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    val all: Map<String, *>
        get() = sp!!.all

    private constructor(spName: String) {
        sp = App.sApp.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    private constructor(spName: String, mode: Int) {
        sp = App.sApp.getSharedPreferences(spName, mode)
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String, value: String, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putString(key, value).commit()
        } else {
            sp!!.edit().putString(key, value).apply()
        }
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getString(key: String, defaultValue: String = ""): String? {
        return sp!!.getString(key, defaultValue)
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String, value: Int, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putInt(key, value).commit()
        } else {
            sp!!.edit().putInt(key, value).apply()
        }
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getInt(key: String, defaultValue: Int = -1): Int {
        return sp!!.getInt(key, defaultValue)
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String, value: Long, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putLong(key, value).commit()
        } else {
            sp!!.edit().putLong(key, value).apply()
        }
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getLong(key: String, defaultValue: Long = -1L): Long {
        return sp!!.getLong(key, defaultValue)
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String, value: Float, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putFloat(key, value).commit()
        } else {
            sp!!.edit().putFloat(key, value).apply()
        }
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getFloat(key: String, defaultValue: Float = -1f): Float {
        return sp!!.getFloat(key, defaultValue)
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String, value: Boolean, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putBoolean(key, value).commit()
        } else {
            sp!!.edit().putBoolean(key, value).apply()
        }
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sp!!.getBoolean(key, defaultValue)
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String,
            value: Set<String>,
            isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().putStringSet(key, value).commit()
        } else {
            sp!!.edit().putStringSet(key, value).apply()
        }
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or `defaultValue` otherwise
     */
    @JvmOverloads
    fun getStringSet(key: String,
                     defaultValue: Set<String> = emptySet()): Set<String>? {
        return sp!!.getStringSet(key, defaultValue)
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return `true`: yes<br></br>`false`: no
     */
    operator fun contains(key: String): Boolean {
        return sp!!.contains(key)
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun remove(key: String, isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().remove(key).commit()
        } else {
            sp!!.edit().remove(key).apply()
        }
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun clear(isCommit: Boolean = false) {
        if (isCommit) {
            sp!!.edit().clear().commit()
        } else {
            sp!!.edit().clear().apply()
        }
    }

    companion object {

        private val SP_UTILS_MAP = SimpleArrayMap<String, SPUtils>()

        /**
         * Return the single [SPUtils] instance
         *
         * @return the single [SPUtils] instance
         */
        val instance: SPUtils
            get() = getInstance("", Context.MODE_PRIVATE)

        /**
         * Return the single [SPUtils] instance
         *
         * @param mode Operating mode.
         * @return the single [SPUtils] instance
         */
        fun getInstance(mode: Int): SPUtils {
            return getInstance("", mode)
        }

        /**
         * Return the single [SPUtils] instance
         *
         * @param spName The name of sp.
         * @param mode   Operating mode.
         * @return the single [SPUtils] instance
         */
        @JvmOverloads
        fun getInstance(spName: String, mode: Int = Context.MODE_PRIVATE): SPUtils {
            var spName = spName
            if (isSpace(spName)) spName = "spUtils"
            var spUtils: SPUtils? = SP_UTILS_MAP.get(spName)
            if (spUtils == null) {
                spUtils = SPUtils(spName, mode)
                SP_UTILS_MAP.put(spName, spUtils)
            }
            return spUtils
        }

        private fun isSpace(s: String?): Boolean {
            if (s == null) return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }
    }
}
/**
 * Return the single [SPUtils] instance
 *
 * @param spName The name of sp.
 * @return the single [SPUtils] instance
 */
/**
 * Put the string value in sp.
 *
 * @param key   The key of sp.
 * @param value The value of sp.
 */
/**
 * Return the string value in sp.
 *
 * @param key The key of sp.
 * @return the string value if sp exists or `""` otherwise
 */
/**
 * Put the int value in sp.
 *
 * @param key   The key of sp.
 * @param value The value of sp.
 */
/**
 * Return the int value in sp.
 *
 * @param key The key of sp.
 * @return the int value if sp exists or `-1` otherwise
 */
/**
 * Put the long value in sp.
 *
 * @param key   The key of sp.
 * @param value The value of sp.
 */
/**
 * Return the long value in sp.
 *
 * @param key The key of sp.
 * @return the long value if sp exists or `-1` otherwise
 */
/**
 * Put the float value in sp.
 *
 * @param key   The key of sp.
 * @param value The value of sp.
 */
/**
 * Return the float value in sp.
 *
 * @param key The key of sp.
 * @return the float value if sp exists or `-1f` otherwise
 */
/**
 * Put the boolean value in sp.
 *
 * @param key   The key of sp.
 * @param value The value of sp.
 */
/**
 * Return the boolean value in sp.
 *
 * @param key The key of sp.
 * @return the boolean value if sp exists or `false` otherwise
 */
/**
 * Put the set of string value in sp.
 *
 * @param key   The key of sp.
 * @param value The value of sp.
 */
/**
 * Return the set of string value in sp.
 *
 * @param key The key of sp.
 * @return the set of string value if sp exists
 * or `Collections.<String>emptySet()` otherwise
 */
/**
 * Remove the preference in sp.
 *
 * @param key The key of sp.
 */
/**
 * Remove all preferences in sp.
 */