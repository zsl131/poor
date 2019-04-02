package com.zslin.bus.common.tools;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dao.IMenuDao;
import com.zslin.basic.model.Menu;
import com.zslin.basic.tools.PinyinToolkit;
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
public class BuildAdminMenuTools {

    @Autowired
    private IMenuDao menuDao;

    public void buildAdminMenus() {
//        String pn = "com/zslin/*/dao/*Service.class";

//        String pn = "com/zslin/*/dao/**Service.class";
        buildByPn("com/zslin/basic/service/*Service.class", "com/zslin/bus/*/service/*Service.class");
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
                if(am.hasAnnotation(AdminAuth.class.getName())) {
//                    addMenu(am, cname);
                    buildMenus(am, cname);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildMenus(AnnotationMetadata am, String cname) {
//        System.out.println("======="+cname);
        Map<String, Object> mapp = am.getAnnotationAttributes(Service.class.getName());
        Map<String, Object> classRes = am.getAnnotationAttributes(AdminAuth.class.getName()); //类

        String serviceName = (String) mapp.get("value");
        String tempServiceName = cname.substring(cname.lastIndexOf(".")+1, cname.length());
        if(serviceName==null || "".equals(serviceName)) {
            serviceName = tempServiceName.substring(0, 1).toLowerCase() + tempServiceName.substring(1, tempServiceName.length());
        }

        Menu clsMenu = buildClassMenu(serviceName, classRes);

        Set<MethodMetadata> set = am.getAnnotatedMethods(AdminAuth.class.getName());
        for(MethodMetadata mm : set) {
            Map<String, Object> methodRes = mm.getAnnotationAttributes(AdminAuth.class.getName());
            String methodName = mm.getMethodName();
            buildMethodMenu(clsMenu, methodName, methodRes);
        }
    }

    private Menu buildClassMenu(String serviceName, Map<String, Object> classRes) {
        String cpsn = (String) classRes.get("psn");
        boolean isEnglish = cpsn.getBytes().length==cpsn.length(); //无汉字
        String cpsnEn = isEnglish?cpsn: PinyinToolkit.cn2Spell(cpsn, "_"); //类上的父菜单SN
        Menu cpm = menuDao.findBySn(cpsnEn);
        if(cpm==null) {
            cpm = new Menu();
            cpm.setOrderNum(1);
            cpm.setDisplay(1);
            cpm.setHref("#");
            cpm.setIcon("");
            cpm.setName(cpsn);
            cpm.setSn(cpsnEn);
            cpm.setType("1");
            menuDao.save(cpm);
        }
        ///以上是处理类上的父菜单
        Menu cm = menuDao.findBySn(serviceName); //类菜单
        if(cm==null) {
            cm = new Menu();
            cm.setOrderNum((Integer) classRes.get("orderNum"));
            cm.setDisplay(1);
            String href = (String) classRes.get("url");
            if(href!=null && !"#".equals(href) && !href.startsWith("/")) {href = "/" + href;}
            cm.setHref(href);
            cm.setIcon((String) classRes.get("icon"));
            cm.setName((String) classRes.get("name"));
            cm.setSn(serviceName);
            cm.setType((String) classRes.get("type"));
            cm.setPid(cpm.getId());
            cm.setPname(cpm.getName());
            cm.setPsn(cpm.getSn());
            menuDao.save(cm);
        }

        return cm;
    }

    private void buildMethodMenu(Menu cm, String methodName, Map<String, Object> methodRes) {
        String sn = cm.getSn()+"."+methodName;
        Menu m = menuDao.findBySn(sn);
        if(m==null) {
            m = new Menu();
            m.setPsn(cm.getSn());
            m.setPname(cm.getName());
            m.setPid(cm.getId());
            m.setSn(sn);
            m.setName((String) methodRes.get("name"));
            m.setIcon((String) methodRes.get("icon"));
            m.setDisplay(1);
            String href = (String) methodRes.get("url");
            if(href!=null && !"#".equals(href) && !href.startsWith("/")) {href = "/" + href;}

            m.setHref(href);
            m.setOrderNum((Integer) methodRes.get("orderNum"));
            m.setType((String) methodRes.get("type"));
            menuDao.save(m);
        }
    }
}
