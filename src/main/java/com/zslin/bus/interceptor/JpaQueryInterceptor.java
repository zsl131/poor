package com.zslin.bus.interceptor;

import com.zslin.bus.threadlocal.RequestDto;
import com.zslin.bus.threadlocal.SystemThreadLocalHolder;
import org.hibernate.EmptyInterceptor;

public class JpaQueryInterceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {

        RequestDto dto = SystemThreadLocalHolder.getRequestDto();

//        int isc = dto==null?1:dto.getIsAll();
        boolean isAll = (dto==null || dto.getIsAll());
        if(isAll) return sql;
        int objId = dto.getObjId();
        if(sql.contains("from t_personal")) {
            //仅仅只对t_personal和t_family两张表进行处理
            if(dto.isTownId()) {return handlePersonSqlByTownId(sql, objId);}
            else {return handlePersonSql(sql,objId);}
        }
        if(sql.contains("from t_family")) {
//            System.out.println(sql);
            if(dto.isTownId()) {return handleFamilySqlByTownId(sql, objId);}
            else {return handleFamilySql(sql, objId);}
        }
        return sql;
    }

    private String getAlias(String sql,String str) {
        int index = sql.indexOf(str);
        int n = index+str.length();
        String tstr = sql.substring(n);
        tstr = tstr.trim();
        return tstr.split(" ")[0];

    }

    private String handlePersonSql(String sql,int uid) {
        String result = "";
        String st = "from t_personal";
        String a = getAlias(sql,st);
        String s = st+" "+a;
        int index = sql.indexOf(s);
        String s1  = sql.substring(0,index+s.length());
//        System.out.println(s1);
        String s2 = sql.substring(index+s.length());

        String sc = ",t_user_town tut";

        if(s2.indexOf("where")>=0) {
            int tindex = s2.indexOf("where");
            String sw1 = s2.substring(0,tindex+"where".length());
            String sw2 = s2.substring(tindex+"where".length());
            String swc = " (tut.town_id="+a+".xzid OR tut.town_id="+a+".czid) and tut.user_id="+uid+" and "; //xzid:乡镇id，czid:村庄id
            result = s1+sc+sw1+swc+sw2;
        } else {
            sc = sc+" where (tut.town_id="+a+".xzid OR tut.town_id="+a+".czid) and tut.user_id="+uid+" "; //xzid:乡镇id，czid:村庄id
            result = s1+sc+s2;
        }
        return result;
    }

    private String handlePersonSqlByTownId(String sql,int townId) {
        String result = "";
        String st = "from t_personal";
        String a = getAlias(sql,st);
        String s = st+" "+a;
        int index = sql.indexOf(s);
        String s1  = sql.substring(0,index+s.length());
//        System.out.println(s1);
        String s2 = sql.substring(index+s.length());

//        String sc = ",t_user_town tut";

        if(s2.indexOf("where")>=0) {
            int tindex = s2.indexOf("where");
            String sw1 = s2.substring(0,tindex+"where".length());
            String sw2 = s2.substring(tindex+"where".length());
//            String swc = " (tut.town_id="+a+".xzid OR tut.town_id="+a+".czid) and tut.user_id="+uid+" and "; //xzid:乡镇id，czid:村庄id
            String swc = " ("+ a+".xzid="+townId+" OR "+a+".czid="+townId+") AND ";
            result = s1+sw1+swc+sw2;
        } else {
            String sc =" where ("+a+".xzid="+townId+" OR "+a+".czid="+townId+")"; //xzid:乡镇id，czid:村庄id
            result = s1+sc+s2;
        }
        return result;
    }

    private String handleFamilySql(String sql,int uid) {
        String result = "";
        String st = "from t_family";
        String a = getAlias(sql,st);
        String s = st+" "+a;
        int index = sql.indexOf(s);
        String s1  = sql.substring(0,index+s.length());
//        System.out.println(s1);
        String s2 = sql.substring(index+s.length());

        String sc = ",t_user_town tut";

        if(s2.indexOf("where")>=0) {
            int tindex = s2.indexOf("where");
            String sw1 = s2.substring(0,tindex+"where".length());
            String sw2 = s2.substring(tindex+"where".length());
            String swc = " (tut.town_id="+a+".xzid OR tut.town_id="+a+".czid) and tut.user_id="+uid+" and "; //xzid:乡镇id，czid:村庄id
            result = s1+sc+sw1+swc+sw2;
        } else {
            sc = sc+" where (tut.town_id="+a+".xzid OR tut.town_id="+a+".czid) and tut.user_id="+uid+" "; //xzid:乡镇id，czid:村庄id
            result = s1+sc+s2;
        }
        return result;
    }

    private String handleFamilySqlByTownId(String sql,int townId) {
        String result = "";
        String st = "from t_family";
        String a = getAlias(sql,st);
        String s = st+" "+a;
        int index = sql.indexOf(s);
        String s1  = sql.substring(0,index+s.length());
//        System.out.println(s1);
        String s2 = sql.substring(index+s.length());

//        String sc = ",t_user_town tut";

        if(s2.indexOf("where")>=0) {
            int tindex = s2.indexOf("where");
            String sw1 = s2.substring(0,tindex+"where".length());
            String sw2 = s2.substring(tindex+"where".length());
            String swc = " ("+a+".xzid="+townId+" OR "+a+".czid="+townId+") and "; //xzid:乡镇id，czid:村庄id
            result = s1+sw1+swc+sw2;
        } else {
            String sc = " where ("+a+".xzid="+townId+" OR "+a+".czid="+townId+") "; //xzid:乡镇id，czid:村庄id
            result = s1+sc+s2;
        }
        return result;
    }
}