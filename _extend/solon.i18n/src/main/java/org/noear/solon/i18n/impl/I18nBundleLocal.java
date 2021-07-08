package org.noear.solon.i18n.impl;

import org.noear.solon.i18n.I18nBundle;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

/**
 * 国际化包，本地实现
 *
 * @author noear
 * @since 1.5
 */
public class I18nBundleLocal implements I18nBundle {
    ResourceBundle bundle;
    Locale locale;
    Map<String, String> map;

    public I18nBundleLocal(String bundleName, Locale locale) {
        if (locale == null) {
            this.bundle = ResourceBundle.getBundle(bundleName);
        } else {
            this.bundle = ResourceBundle.getBundle(bundleName, locale);
        }

        this.locale = locale;
    }

    /**
     * 转换为Map数据
     */
    @Override
    public synchronized Map<String, String> toMap() {
        if (map == null) {
            map = new LinkedHashMap<>();

            for (String key : bundle.keySet()) {
                map.put(key, get(key));
            }
        }

        return map;
    }


    /**
     * 获取国际化内容
     *
     * @param code 代码
     */
    @Override
    public String get(String code) {
        return new String(bundle.getString(code).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    /**
     * 获取国际化内容
     *
     * @param code 代码
     * @param args 参数
     */
    @Override
    public String getAndFormat(String code, Object... args) {
        String tml = get(code);

        MessageFormat mf = new MessageFormat(tml);
        if (locale != null) {
            mf.setLocale(locale);
        }

        return mf.format(args);
    }
}