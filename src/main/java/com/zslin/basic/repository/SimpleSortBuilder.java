package com.zslin.basic.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2017/1/8 15:13.
 */
public class SimpleSortBuilder {

    /**
     * 调用的时候使用SimpleSortBuilder.generateSort("name","xh_d");表示先以name升序，之后以xh降序
     */
    public static Sort generateSort(String ...fields) {
        List<Order> orders = new ArrayList<>();
        for(String f:fields) {
            orders.add(generateOrder(f));
        }
        return new Sort(orders);
    }

    private static Order generateOrder(String f) {
        if(f==null || "".equals(f)) {return null;}
        Order order = null;
        String[] ff = f.split("_");
        if(ff.length>=2) {
            if(ff[1].equals("d")) {
                order = new Order(Direction.DESC,ff[0]);
            } else {
                order = new Order(Direction.ASC,ff[0]);
            }
            return order;
        }
        order = new Order(f);
        return order;
    }

}
