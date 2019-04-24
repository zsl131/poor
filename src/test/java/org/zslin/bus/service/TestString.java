package org.zslin.bus.service;

public class TestString {

    private static String getAlias(String sql,String str) {
        int index = sql.indexOf(str);
        int n = index+str.length();
        String tstr = sql.substring(n);
        tstr = tstr.trim();
        return tstr.split(" ")[0];

    }
    public static void main(String[] args) {
        String sql = "select personal0_.xb as col_0_0_, count(personal0_.id) as col_1_0_ from" +
                " t_personal personal0_ where a=b group by personal0_.xb";
        String s = "from t_personal";
        System.out.println(getAlias(sql,s)+"----------------------");
        int index = sql.indexOf(s);
        String s1  = sql.substring(0,index+s.length());
        System.out.println(s1);
        String s2 = sql.substring(index+s.length());

        int uid = 10;

        String sc = ",t_user_town tut";

        if(s2.indexOf("where")>=0) {
            int tindex = s2.indexOf("where");
            String sw1 = s2.substring(0,tindex+"where".length());
            String sw2 = s2.substring(tindex+"where".length());
            System.out.println(sw1);
            String swc = " tut.town_id=personal0_.xzid and tut.user_id=10 and ";
            System.out.println(sw1+swc+sw2);
            String sqlr = s1+sc+sw1+swc+sw2;
            System.out.println(sqlr);
        } else {
            sc = sc+" where tut.town_id=personal0_.xzid and tut.user_id=10 ";
            String sqlr = s1+sc+s2;
            System.out.println(sqlr);
        }
    }
}
