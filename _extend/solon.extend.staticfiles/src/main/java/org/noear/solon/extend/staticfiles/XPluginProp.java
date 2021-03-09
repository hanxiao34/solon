package org.noear.solon.extend.staticfiles;

import org.noear.solon.Solon;

/**
 * @author noear
 * @since 1.3
 */
class XPluginProp {
    static final String PROP_ENABLED = "org.noear.solon.extend.staticfiles.enabled";
    static final String PROP_MAX_AGE = "org.noear.solon.extend.staticfiles.maxAge";

    static final String RES_LOCATION = "/static/";

    /**
     * 是否启用
     */
    public static boolean enabled() {
        return Solon.cfg().getBool(PROP_ENABLED, true);
    }

    static int maxAge = 0;

    /**
     * 客户端缓存秒数
     */
    public static int maxAge() {
        if (maxAge < 1) {
            maxAge = Solon.cfg().getInt(PROP_MAX_AGE, 6000);
        }

        return maxAge;
    }
}