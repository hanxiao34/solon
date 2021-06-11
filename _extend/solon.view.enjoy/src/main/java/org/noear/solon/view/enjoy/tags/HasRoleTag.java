package org.noear.solon.view.enjoy.tags;

import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.extend.auth.AuthUtil;
import org.noear.solon.extend.auth.annotation.Logical;
import org.noear.solon.extend.auth.tags.TagAttrs;

/**
 * @author noear
 * @since 1.4
 */
@Component("view:hasRole")
public class HasRoleTag extends Directive {
    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        String nameStr = (String) scope.get(TagAttrs.ATTR_name);
        String logicalStr = (String) scope.get(TagAttrs.ATTR_logical);

        if (Utils.isEmpty(nameStr)) {
            return;
        }

        String[] names = nameStr.split(",");

        if (names.length == 0) {
            return;
        }

        if (AuthUtil.verifyRoles(names, Logical.of(logicalStr))) {
            stat.exec(env, scope, writer);
        }
    }

    /**
     * 从 #xxx 指令参数中获取角色名称数组
     */
    private String[] getNamesArray(Scope scope) {
        Object[] values = exprList.evalExprList(scope);
        String[] ret = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof String) {
                ret[i] = (String) values[i];
            } else {
                throw new IllegalArgumentException("Name can only be strings");
            }
        }
        return ret;
    }
}