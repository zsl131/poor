package com.zslin.bus.common.tools;

import com.zslin.bus.common.annotations.ApiCodeClass;
import com.zslin.bus.common.annotations.ApiCodeMethod;
import com.zslin.bus.common.iservice.IApiCodeSerivce;
import com.zslin.bus.common.model.ApiCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by zsl on 2018/7/5.
 */
@Component
public class BuildApiCodeTools {

    @Autowired
    private IApiCodeSerivce apiCodeSerivce;

    public void buildApiCode() {
//        String pn = "com/zslin/*/dao/*Service.class";

//        String pn = "com/zslin/*/dao/**Service.class";
        buildByPn("com/zslin/*/service/*Service.class", "com/zslin/bus/*/service/*Service.class");
    }

    private void buildByPn(String ...pns) {
        for(String pn : pns) {
            build(pn);
        }
    }

    private void build(String pn) {
        try {
            //1、创建ResourcePatternResolver资源对象
            ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
            //2、获取路径中的所有资源对象
            Resource[] ress = rpr.getResources(pn);
            //3、创建MetadataReaderFactory来获取工程
            MetadataReaderFactory fac = new CachingMetadataReaderFactory();
            //4、遍历资源
            for(Resource res:ress) {
                MetadataReader mr = fac.getMetadataReader(res);
                String cname = mr.getClassMetadata().getClassName();
                AnnotationMetadata am = mr.getAnnotationMetadata();
                if(am.hasAnnotation(ApiCodeClass.class.getName())) {
//                    addMenu(am, cname);
                    addCode(am, cname);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCode(AnnotationMetadata am, String cname) {
        Map<String, Object> mapp = am.getAnnotationAttributes(Service.class.getName());
        String serviceName = (String) mapp.get("value");
        String tempServiceName = cname.substring(cname.lastIndexOf(".")+1, cname.length());
        if(serviceName==null || "".equals(serviceName)) {
            serviceName = tempServiceName.substring(0, 1).toLowerCase() + tempServiceName.substring(1, tempServiceName.length());
        }
        Set<MethodMetadata> set = am.getAnnotatedMethods(ApiCodeMethod.class.getName());
        for(MethodMetadata mm : set) {
            Map<String, Object> classRes = mm.getAnnotationAttributes(ApiCodeMethod.class.getName());
            String methodName = mm.getMethodName();
            String code = (String) classRes.get("value");
            if(code==null || "".equals(code)) {
                code = tempServiceName.replace("Service", "") + "-" + methodName; //
            }
            String remark = (String) classRes.get("remark");
            if(remark==null || "".equals(remark)) {
                remark = cname + "." + methodName;
            }

//            System.out.println(serviceName+"===="+code+"=="+remark);

            ApiCode ac = apiCodeSerivce.findByCode(code);
            if(ac==null) {
                ac = new ApiCode();
                ac.setCode(code);
                ac.setMethodName(methodName);
                ac.setServiceName(serviceName);
            }
            ac.setRemark(remark);
            apiCodeSerivce.save(ac);
        }
    }
}
