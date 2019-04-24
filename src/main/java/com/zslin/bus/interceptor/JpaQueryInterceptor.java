package com.zslin.bus.interceptor;

import org.hibernate.EmptyInterceptor;

public class JpaQueryInterceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {
        int isc = 1;
        if(isc==0) return sql;
        int uid = 10;
        if(sql.indexOf("from t_personal")>=0) {
            //仅仅只对t_personal和t_family两张表进行处理
            String sql1 = handlePersonSql(sql,uid);
            return sql1;
        }
        if(sql.indexOf("from t_family")>=0) {
            System.out.println(sql);
            String sql1 = handleFamilySql(sql,uid);
            return sql1;
        }
        return sql;
    }

    private String handlePersonSql(String sql,int uid) {
        String result = "";
        String s = "from t_personal personal0_";
        int index = sql.indexOf(s);
        String s1  = sql.substring(0,index+s.length());
        System.out.println(s1);
        String s2 = sql.substring(index+s.length());

        String sc = ",t_user_town tut";

        if(s2.indexOf("where")>=0) {
            int tindex = s2.indexOf("where");
            String sw1 = s2.substring(0,tindex+"where".length());
            String sw2 = s2.substring(tindex+"where".length());
            String swc = " tut.town_id=personal0_.xzid and tut.user_id=10 and ";
            result = s1+sc+sw1+swc+sw2;
        } else {
            sc = sc+" where tut.town_id=personal0_.xzid and tut.user_id=10 ";
            result = s1+sc+s2;
        }
        return result;
    }

    private String handleFamilySql(String sql,int uid) {
        String result = "";
        String s = "from t_family family0_";
        int index = sql.indexOf(s);
        String s1  = sql.substring(0,index+s.length());
        System.out.println(s1);
        String s2 = sql.substring(index+s.length());

        String sc = ",t_user_town tut";

        if(s2.indexOf("where")>=0) {
            int tindex = s2.indexOf("where");
            String sw1 = s2.substring(0,tindex+"where".length());
            String sw2 = s2.substring(tindex+"where".length());
            String swc = " tut.town_id=family0_.xzid and tut.user_id=10 and ";
            result = s1+sc+sw1+swc+sw2;
        } else {
            sc = sc+" where tut.town_id=family0_.xzid and tut.user_id=10 ";
            result = s1+sc+s2;
        }
        return result;
    }
}
