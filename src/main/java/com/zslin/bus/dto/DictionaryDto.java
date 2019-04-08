package com.zslin.bus.dto;

import com.zslin.bus.model.Dictionary;

import java.util.List;

/**
 * Created by zsl on 2019/4/8.
 */
public class DictionaryDto {

    private Dictionary dic;

    private List<Dictionary> children;

    public DictionaryDto() {}

    public DictionaryDto(Dictionary dic, List<Dictionary> children) {
        this.dic = dic;
        this.children = children;
    }

    public Dictionary getDic() {
        return dic;
    }

    public void setDic(Dictionary dic) {
        this.dic = dic;
    }

    public List<Dictionary> getChildren() {
        return children;
    }

    public void setChildren(List<Dictionary> children) {
        this.children = children;
    }
}
