package org.zslin.bus.service;

import com.zslin.RootApplication;
import com.zslin.bus.service.YqkfService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplication.class)
@ActiveProfiles("kh")
public class YqkfServieTest {
    @Resource
    private YqkfService yqkfService;

    @Test
    public void test01() {
        yqkfService.importYqkf();
    }
}
