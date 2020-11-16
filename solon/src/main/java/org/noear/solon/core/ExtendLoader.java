package org.noear.solon.core;

import org.noear.solon.Solon;
import org.noear.solon.SolonApp;
import org.noear.solon.Utils;
import org.noear.solon.core.util.PrintUtil;

import java.io.File;
import java.util.function.Predicate;

/**
 * 外部扩展加载器（对于动态扩展）
 *
 * @author noear
 * @since 1.0
 * */
public class ExtendLoader {
    private static final ExtendLoader instance = new ExtendLoader();
    private static String path;

    /**
     * 扩展路径（绝对路径）
     */
    public static String path() {
        return path;
    }

    /**
     * 加载扩展文件夹（或文件）
     *
     * @param extend   扩展配置
     * @param autoMake 是否自动生成
     */
    public static void load(String extend, boolean autoMake) {
        load(extend, autoMake, null);
    }

    /**
     * 加载扩展文件夹（或文件）
     *
     * @param extend   扩展配置
     * @param filter   过滤器
     * @param autoMake 是否自动生成
     */
    public static void load(String extend, boolean autoMake, Predicate<String> filter) {
        if (Utils.isNotEmpty(extend)) {
            if (extend.startsWith("!")) {
                extend = extend.substring(1);
                autoMake = true;
            }

            extend = Utils.buildExt(extend, autoMake);

            if (extend != null) {
                //缓存扩展目径
                path = extend;

                //打印
                PrintUtil.blueln("solon.extend: " + path);

                //加载扩展内容
                instance.loadFile(new File(path), filter);
            }
        }
    }


    /**
     * 加载扩展具体的jar文件
     */
    public static boolean loadJar(File file) {
        try {
            JarClassLoader.global().loadJar(file.toURI().toURL());
            return true;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 卸载一个已加载的jar文件
     */
    public static boolean unloadJar(File file) {
        try {
            JarClassLoader.global().unloadJar(file.toURI().toURL());
            return true;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private ExtendLoader() {

    }

    /**
     * 加载一个具体的文件
     * <p>
     * 如果是目录的话，只处理一级
     *
     * @param filter 过滤器
     */
    private void loadFile(File file, Predicate<String> filter) {
        if (file.exists() == false) {
            return;
        }

        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loadFileDo(tmp, filter);
            }
        } else {
            loadFileDo(file, filter);
        }
    }


    /**
     * 加载一个具体的文件
     *
     * @param filter 过滤器
     */
    private void loadFileDo(File file, Predicate<String> filter) {
        if (file.isFile()) {
            String path = file.getAbsolutePath();

            //先尝试过滤
            if (filter != null) {
                if (filter.test(path) == false) {
                    return;
                }
            }

            try {
                //尝试加载jar包
                if (path.endsWith(".jar") || path.endsWith(".zip")) {
                    loadJar(file);
                    return;
                }

                //如果map不为null；尝试加载配置
                if (path.endsWith(".properties")) {
                    Solon.cfg().loadAdd(file.toURI().toURL());

                    PrintUtil.blueln("loaded: " + path);
                    return;
                }

                if (path.endsWith(".yml")) {
                    if (PropsLoader.global().isSupport(path) == false) {
                        throw new RuntimeException("Do not support the *.yml");
                    }

                    Solon.cfg().loadAdd(file.toURI().toURL());

                    PrintUtil.blueln("loaded: " + path);
                    return;
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }
}
