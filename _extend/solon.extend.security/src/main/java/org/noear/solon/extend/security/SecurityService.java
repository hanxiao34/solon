package org.noear.solon.extend.security;

import org.noear.solon.extend.security.annotation.Logical;

/**
 * @author noear
 * @since 1.3
 */
public interface SecurityService {
    /**
     * 用账号密码登录
     * */
    boolean login(String username, String password);

    /**
     * 用领牌登录
     * */
    boolean login(String token);

    /**
     * 验证登录状态
     * */
    boolean verifyLogined();

    /**
     * 验证路径（一般使用路径验证）
     * */
    boolean verifyPath(String path);

    /**
     * 验证权限（有特殊情况用权限验证）
     * */
    boolean verifyPermissions(String[] permissions, Logical logical);

    /**
     * 验证角色（有特殊情况用角色验证）
     * */
    boolean verifyRoles(String[] roles, Logical logical);

}