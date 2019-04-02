package com.zslin.basic.utils;

import com.zslin.basic.repository.SimpleSpecificationBuilder;
import com.zslin.basic.repository.SpecificationOperator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zsl-pc on 2016/9/16.
 */
public class ParamFilterUtil<T> {

    //参数名前缀
    private static final String PARAM_PRE = "filter_";
    private static final String PARAM_SPE = "-";

    public static ParamFilterUtil getInstance() {
        return new ParamFilterUtil();
    }

    public Specification<T> buildSearch(Model model, HttpServletRequest request, SpecificationOperator... ops) {
        Map<String, Object> args = new HashMap<>();
        Map<String, String[]> paramMap = request.getParameterMap();
        SimpleSpecificationBuilder builder = new SimpleSpecificationBuilder();
        builder.add(ops); //先添加
        for(String key : paramMap.keySet()) {
            //这个参数是需要进行过虑的
            if(key.startsWith(PARAM_PRE)) {
                try {
                    String field = key.replaceFirst(PARAM_PRE, ""); //获取出字段名称
                    String parVal = paramMap.get(key)[0];
//                    String [] val_array = parVal.split(PARAM_SPE);
//                    String operate = val_array[0]; //比较符号
//                    String fieldVal = val_array[1]; //对应值
                    //以下面方法的好处是解决value中有“-”的问题
                    String operate = parVal.substring(0, parVal.indexOf(PARAM_SPE)); //比较符号
                    String fieldVal = parVal.substring(parVal.indexOf(PARAM_SPE)+1, parVal.length());
//                    SimpleSpecificationBuilder ssb = new SimpleSpecificationBuilder(field, operate, fieldVal);
                    builder.add(field, operate, fieldVal);
                    args.put(key, fieldVal);
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("args", args);
        return builder.generate();
    }
}
