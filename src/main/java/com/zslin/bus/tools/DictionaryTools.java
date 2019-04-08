package com.zslin.bus.tools;

import com.zslin.bus.dao.IDictionaryDao;
import com.zslin.bus.dto.DictionaryDto;
import com.zslin.bus.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsl on 2019/4/8.
 */
@Component
public class DictionaryTools {

    @Autowired
    private IDictionaryDao dictionaryDao;

    public List<DictionaryDto> buildDictionary() {
        List<DictionaryDto> result = new ArrayList<>();
        List<Dictionary> dicList = dictionaryDao.findParent();
        for(Dictionary d : dicList) {
            List<Dictionary> children = dictionaryDao.findByPid(d.getId());
            result.add(new DictionaryDto(d, children));
        }
        return result;
    }
}
