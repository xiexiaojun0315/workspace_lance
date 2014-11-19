package com.lance.view.util;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LoginUserRoleGrantsVOImpl;
import com.lance.model.vo.LoginUserRoleGrantsVORowImpl;
import com.lance.model.vo.LoginUserVOImpl;

import com.lance.model.vo.LoginUserVORowImpl;

import java.util.HashMap;
import java.util.Map;

import oracle.jbo.RowSetIterator;

/**
 * 用于批量显示
 */
public class LoginUserUtil {

    //{UserName,[DisplayName,最大RoleName,[全部RoleName]]}
    public static Map USER_INFO_MAP = new HashMap();

    public static final String[] CACHE_LOGIN_USER_FIELD = { "DisplayName" };
    public static final String[] CACHE_LOGIN_USER_ROLE_FIELD = { "RoleName" };
    

    public LoginUserUtil() {
        super();
    }

    public Map findUserInfoByByUserIds(String[] ids) {
        return null;
    }

    @SuppressWarnings("unchecked")
    public void initUserInfoMap() {
        LanceRestAMImpl am = LUtil.findLanceAM();
        Map roleGrants = initRoleGrantsMap(am);

        LoginUserVOImpl vo = am.getLoginUser1();
        RowSetIterator it = vo.createRowSetIterator(null);
        LoginUserVORowImpl row;
        LoginUser loginUser;
        while (it.hasNext()) {
            row = (LoginUserVORowImpl) it.next();
            loginUser = new LoginUser();
            loginUser.displayName = row.getDisplayName();
            loginUser.userName = row.getUserName();
            
            USER_INFO_MAP.put(row.getUserName(), loginUser);
        }
        it.closeRowSetIterator();
    }

    @SuppressWarnings("unchecked")
    public Map initRoleGrantsMap(LanceRestAMImpl am) {
        Map roleGrants = new HashMap();
        LoginUserRoleGrantsVOImpl vo = am.getLoginUserRoleGrants1();
        LoginUserRoleGrantsVORowImpl row;
        RowSetIterator it = vo.createRowSetIterator(null);
        while (it.hasNext()) {
            row = (LoginUserRoleGrantsVORowImpl) it.next();
            roleGrants.put(row.getUserName(), row.getRoleName());
        }
        it.closeRowSetIterator();
        return roleGrants;
    }


    public class LoginUser {
        public String userName;
        public String displayName;
        private String maxRoleName;

        public void setAllRoles(String role) {
        }
    }

}
