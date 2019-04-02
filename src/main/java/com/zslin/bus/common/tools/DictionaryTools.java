package com.zslin.bus.common.tools;
import com.zslin.bus.basic.dto.DictionaryDto;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsl on 2018/8/7.
 * 数据字典处理工具类
 */
public class DictionaryTools<E> {

    public List<DictionaryDto> buildDictionaryDtoList(List<E> objectList) {
        List<DictionaryDto> result = new ArrayList<>();
        for(Object obj : objectList) {
            JSONObject jsonObj = new JSONObject(obj);
            result.add(new DictionaryDto(jsonObj.getInt("id"), jsonObj.getString("name")));
        }
        return result;
    }
}
