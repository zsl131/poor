package com.zslin.basic.tools;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.dao.IMenuDao;
import com.zslin.basic.model.Menu;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 权限管理工具类
 *  - 主要用于通过Annotation自动生成菜单
 * @author zslin.com 20160513
 *
 */
@Component
public class AuthTools {

	@Autowired
	private IMenuDao menuService;

	/**
	 * 遍历系统中的所有指定（AdminAuth）的资源
	 */
	public void buildSystemMenu() {
		String pn = "com/zslin/*/controller/**Controller.class";
		buildSystemMenu(pn);
	}

	/**
	 * 遍历系统中的所有指定（AdminAuth）的资源
	 * @param pn Controller所在路径，支持通配符
	 */
	public void buildSystemMenu(String pn) {
		try {
			//指定需要检索Annotation的路径，可以使用通配符
//			String pn = "com/zslin/*/controller/*/*Controller.class";
			//1、创建ResourcePatternResolver资源对象
			ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
			//2、获取路径中的所有资源对象
			Resource [] ress = rpr.getResources(pn);
			//3、创建MetadataReaderFactory来获取工程
			MetadataReaderFactory fac = new CachingMetadataReaderFactory();
			//4、遍历资源
			for(Resource res:ress) {
				MetadataReader mr = fac.getMetadataReader(res);
				String cname = mr.getClassMetadata().getClassName();
				AnnotationMetadata am = mr.getAnnotationMetadata();
				if(am.hasAnnotation(AdminAuth.class.getName())) {
					addMenu(am, cname);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加系统菜单
	 * - 如果存在则修改
	 * @param am
	 * @param pck
	 */
	private void addMenu(AnnotationMetadata am, String pck) {
//		Menu menu = new Menu();
		Map<String, Object> classRes = am.getAnnotationAttributes(AdminAuth.class.getName());
		Map<String, Object> mapp = am.getAnnotationAttributes(RequestMapping.class.getName());
//		String pack = pck.substring(0, pck.lastIndexOf("."));
		String cName = pck.substring(pck.lastIndexOf(".")+1, pck.length());
		
		String pUrl = ((String[]) mapp.get("value"))[0];
		if(!pUrl.endsWith("/")) {pUrl = pUrl + "/";}
		if(!pUrl.startsWith("/")) {pUrl = "/" + pUrl;}
		
		String resUrl = (String) classRes.get("url");
		if(resUrl!=null && !"#".equals(resUrl)) {resUrl = pUrl + resUrl;}

		String psn = (String) classRes.get("psn");

		/*menu.setDisplay((Integer)classRes.get("display"));
		menu.setIcon((String)classRes.get("icon"));
		menu.setName((String)classRes.get("name"));
		menu.setOrderNum((Integer) classRes.get("orderNum"));
		menu.setPsn(psn);
		menu.setType((String) classRes.get("type"));
		menu.setHref(resUrl);
		menu.setSn(cName);
		
		menuServiceImpl.addOrUpdate(menu);*/
		
		Set<MethodMetadata> set = am.getAnnotatedMethods(AdminAuth.class.getName());
		for(MethodMetadata mm : set) {
			Menu resMethod = new Menu();
			
			classRes = mm.getAnnotationAttributes(AdminAuth.class.getName());
			
			resMethod.setDisplay((Integer)classRes.get("display"));
			resMethod.setIcon((String)classRes.get("icon"));
			resMethod.setName((String)classRes.get("name"));
			resMethod.setOrderNum((Integer) classRes.get("orderNum"));
//			resMethod.setPsn(cName);
//			resMethod.setPid(menu.getId());
			resMethod.setPsn(psn);
			resMethod.setType((String) classRes.get("type"));
			String url = (String) classRes.get("url");
			if(url==null || "".equals(url.trim()) || "#".equals(url.trim())) {
				url = pUrl;
				Map<String, Object> meMapp = mm.getAnnotationAttributes(RequestMapping.class.getName());
				if(((String[])meMapp.get("value")).length<=0) {
					meMapp = mm.getAnnotationAttributes(GetMapping.class.getName());
				}
				if(((String[])meMapp.get("value")).length<=0) {
					meMapp = mm.getAnnotationAttributes(PostMapping.class.getName());
				}
				if(((String[])meMapp.get("value")).length<=0) {
					meMapp = mm.getAnnotationAttributes(DeleteMapping.class.getName());
				}
				String temp = ((String[]) meMapp.get("value"))[0];
				if(temp.indexOf("{")>=0) {temp = temp.substring(0, temp.lastIndexOf("{"));}
				if(temp.startsWith("/")) {temp = temp.substring(1, temp.length());}
				if(temp.endsWith("/")) {temp = temp.substring(0, temp.length()-1);}
				url = url + temp;
			}
			resMethod.setHref(url);
			resMethod.setSn(cName+"."+mm.getMethodName());
//			menuServiceImpl.addOrUpdate(resMethod);
			addOrUpdate(resMethod);
		}
	}

	public void addOrUpdate(Menu menu) {
		Menu m = menuService.findBySn(menu.getSn());
		try {
			String prSn = menu.getPsn();
			if(prSn!=null && !"".equals(prSn)) {
				boolean isEnglish = prSn.getBytes().length==prSn.length(); //无汉字
				if(isEnglish) {
					Menu pr = menuService.findBySn(menu.getPsn());
					if(pr!=null) {m.setPid(pr.getId());}
				} else {
					String pinyin = PinyinToolkit.cn2Spell(prSn, "_");
					Menu pr = menuService.findBySn(pinyin);
					boolean isAdd = false;
					if(pr==null) {pr = new Menu(); pr.setOrderNum(10); isAdd = true;}
					pr.setDisplay(1); pr.setName(prSn);
					pr.setSn(pinyin); pr.setPsn("root");
					pr.setType("1"); pr.setHref("#");
					if(isAdd) { menuService.save(pr); }
					//else {this.update(pr);} //这里不作修改，只通过系统后台修改
					menu.setPsn(pr.getSn()); menu.setPid(pr.getId());
				}
			}
		} catch (Exception e) {
		}
		if(m==null) {
			menuService.save(menu);
		} else {
			m.setDisplay(menu.getDisplay());
			m.setIcon(menu.getIcon());
			m.setName(menu.getName());
			//m.setOrderNum(menu.getOrderNum());
			m.setOrderNum(m.getOrderNum());
			m.setPsn(menu.getPsn());
			if(menu.getPid()!=null && menu.getPid()>0) { //
				m.setPid(menu.getPid());
			}
			m.setType(menu.getType());
			m.setHref(menu.getHref());
//            this.update(m);
			menuService.save(m);
		}
	}
}
