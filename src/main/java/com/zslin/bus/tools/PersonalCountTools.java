package com.zslin.bus.tools;

import com.zslin.bus.dao.IFamilyDao;
import com.zslin.bus.dao.IPersonalDao;
import com.zslin.bus.dto.PersonalCountDto;
import com.zslin.bus.threadlocal.RequestDto;
import com.zslin.bus.threadlocal.SystemThreadLocalHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员统计工具类
 * Created by zsl on 2019/4/30.
 */
@Component
public class PersonalCountTools {

    @Autowired
    private IFamilyDao familyDao;

    @Autowired
    private IPersonalDao personalDao;

    public List<PersonalCountDto> buildCountDto(Integer townId) {
        RequestDto requestDto = new RequestDto(townId, townId==1, true);
        SystemThreadLocalHolder.initRequestDto(requestDto);

        String kh = "卡户", yqh = "随迁户";
        List<PersonalCountDto> list = new ArrayList<>();
        list.add(new PersonalCountDto("全户统计", familyDao.findAllCount(), personalDao.findAllCount(), personalDao.findLdlCount(), personalDao.findJyCount()));
        list.add(new PersonalCountDto(kh+"统计", familyDao.findAllCount(kh), personalDao.findAllCount(kh), personalDao.findLdlCount(kh), personalDao.findJyCount(kh)));
        list.add(new PersonalCountDto(yqh+"统计", familyDao.findAllCount(yqh), personalDao.findAllCount(yqh), personalDao.findLdlCount(yqh), personalDao.findJyCount(yqh)));
        return list;
    }
}
