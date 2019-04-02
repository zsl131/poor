package com.zslin.bus.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsl on 2018/7/10.
 */
public class QueryListDto {
    private Integer page = 0;
    private Integer size = 15;
    private String [] sort ;
    private List<QueryListConditionDto> conditionDtoList;

    public QueryListDto() {}

    public QueryListDto(Integer page, Integer size, String sort, List<QueryListConditionDto> conditionDtoList) {
        this.page = page;
        this.size = size;
        /*if(sort==null || "".equals(sort.trim())) {
            sort = "id_d";
        }*/
        this.sort = buildSort(sort);
        this.conditionDtoList = conditionDtoList;
    }

    private String [] buildSort(String sort) {
        if(sort==null || "".equals(sort.trim())) {
            return new String[]{"id_d"};
        }
        String [] array = sort.replaceAll(" ", "").split(",");
        return array;
    }

    public QueryListDto(Integer page, Integer size, String sort, QueryListConditionDto ...conditionDtos) {
        this.page = page;
        this.size = size;
        /*if(sort==null || "".equals(sort.trim())) {
            sort = "id_d";
        }
        this.sort = sort;*/
        this.sort = buildSort(sort);
        this.conditionDtoList = new ArrayList<>();
        if(conditionDtos!=null && conditionDtos.length>0) {
            for(QueryListConditionDto dto : conditionDtos) {
                this.conditionDtoList.add(dto);
            }
        }
    }

    public String[] getSort() {
        return sort;
    }

    public void setSort(String[] sort) {
        this.sort = sort;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<QueryListConditionDto> getConditionDtoList() {
        return conditionDtoList;
    }

    public void setConditionDtoList(List<QueryListConditionDto> conditionDtoList) {
        this.conditionDtoList = conditionDtoList;
    }
}
