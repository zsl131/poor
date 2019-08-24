package com.zslin.basic.repository;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2017/1/6 17:24.
 */
public class SimpleSpecification<T> implements Specification<T> {

    public static final String GRATE_EQUAL = "ge"; //大于等于
    public static final String GRATE_THEN = "gt"; //大于
    public static final String LESS_EQUAL = "le"; //小于等于
    public static final String LESS_THEN = "lt"; //小于
    public static final String LIKE_BEGIN = "likeb"; // like '%?'
    public static final String LIKE_END = "likee"; //like '?%'
    public static final String LIKE = "like"; //like '%?%'
    public static final String LIKE_BEGIN_END = "likebe"; //like '%?%'
    public static final String NOT_LIKE_BEGIN = "nlikeb"; //not like '%?'
    public static final String NOT_LIKE_END = "nlikee"; //not like '?%'
    public static final String NOT_LIKE = "nlike"; //not like '%?%'
    public static final String NOT_LIKE_BEGIN_END = "nlikebe"; // not like '%?%'
    public static final String EQUAL = "eq"; //equal =
    public static final String NOT_EQUAL = "ne"; // not equal   !=
    public static final String IS_NULL = "isnull"; //is null
    public static final String NOT_NULL = "notnull";
    public static final String IN = "in";

    /**
     * 查询的条件列表，是一组列表
     * */
    private List<SpecificationOperator> opers;

    public SimpleSpecification(List<SpecificationOperator> opers) {
        this.opers = opers;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        int index = 0;
        //通过resultPre来组合多个条件
        Predicate resultPre = null;
        for(SpecificationOperator op:opers) {
            if(index++==0) {
                resultPre = generatePredicate(root,criteriaBuilder,op);
                continue;
            }
            Predicate pre = generatePredicate(root,criteriaBuilder,op);
            if(pre==null) continue;
            Predicate subPredicate = null;
            if(op.getRelations()!=null && op.getRelations().size()>0) {
                subPredicate = subPredicate(pre, root, criteriaBuilder, op.getRelations());
            }
            if("and".equalsIgnoreCase(op.getJoin())) {
                if(subPredicate!=null) {
                    resultPre = criteriaBuilder.and(resultPre, subPredicate);
                } else {
                    resultPre = criteriaBuilder.and(resultPre, pre);
                }
            } else if("or".equalsIgnoreCase(op.getJoin())) {
                if(subPredicate!=null) {
                    resultPre = criteriaBuilder.or(resultPre, subPredicate);
                } else {
                    resultPre = criteriaBuilder.or(resultPre, pre);
                }
            }
        }
        return resultPre;
    }

    private Predicate subPredicate(Predicate predicate, Root<T> root, CriteriaBuilder criteriaBuilder, List<SpecificationOperator> operList) {
        int index = 0;
        //通过resultPre来组合多个条件
        Predicate resultPre = predicate;
        for(SpecificationOperator op : operList) {
            Predicate pre = generatePredicate(root,criteriaBuilder,op);
            if(pre==null) continue;
            Predicate subPredicate = null;
            if(op.getRelations()!=null && op.getRelations().size()>0) {
                subPredicate = subPredicate(pre, root, criteriaBuilder, op.getRelations());
            }
            if("and".equalsIgnoreCase(op.getJoin())) {
                if(subPredicate!=null) {
                    resultPre = resultPre==null?criteriaBuilder.and(subPredicate):criteriaBuilder.and(resultPre, subPredicate);
                } else {
                    resultPre = resultPre == null ? criteriaBuilder.and(pre) : criteriaBuilder.and(resultPre, pre);
                }
            } else if("or".equalsIgnoreCase(op.getJoin())) {
                if(subPredicate!=null) {
                    resultPre = resultPre == null ? criteriaBuilder.or(subPredicate) : criteriaBuilder.or(resultPre, subPredicate);
                } else {
                    resultPre = resultPre == null ? criteriaBuilder.or(pre) : criteriaBuilder.or(resultPre, pre);
                }
            }
        }
        return resultPre;
    }

    private boolean isNumber(Object val) {
        try {
            Double.valueOf(val.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Double getNumber(Object val) {
        try {
            Double d = Double.valueOf(val.toString());
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    private Predicate generatePredicate(Root<T> root,CriteriaBuilder criteriaBuilder, SpecificationOperator op) {

        /** 根据不同的操作符返回特定的查询*/
        if(EQUAL.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.equal(root.get(op.getKey()),op.getValue());
        } else if(GRATE_EQUAL.equalsIgnoreCase(op.getOper()) && isNumber(op.getValue())) {
            return criteriaBuilder.ge(root.get(op.getKey()), getNumber(op.getValue()));
        } else if(LESS_EQUAL.equalsIgnoreCase(op.getOper()) && isNumber(op.getValue())) {
            return criteriaBuilder.le(root.get(op.getKey()),getNumber(op.getValue()));
        } else if(GRATE_THEN.equalsIgnoreCase(op.getOper()) && isNumber(op.getValue())) {
            return criteriaBuilder.gt(root.get(op.getKey()),getNumber(op.getValue()));
        } else if(LESS_THEN.equalsIgnoreCase(op.getOper()) && isNumber(op.getValue())) {
            return criteriaBuilder.lt(root.get(op.getKey()),getNumber(op.getValue()));
        } else if(LIKE.equalsIgnoreCase(op.getOper()) || LIKE_BEGIN_END.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.like(root.get(op.getKey()),"%"+op.getValue()+"%");
        } else if(LIKE_BEGIN.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.like(root.get(op.getKey()),op.getValue()+"%");
        } else if(LIKE_END.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.like(root.get(op.getKey()),"%"+op.getValue());
        } else if(IS_NULL.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.isNull(root.get(op.getKey()));
        } else if(NOT_NULL.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.isNotNull(root.get(op.getKey()));
        } else if(NOT_EQUAL.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.notEqual(root.get(op.getKey()),op.getValue());
        } else if(NOT_LIKE.equalsIgnoreCase(op.getOper()) || NOT_LIKE_BEGIN_END.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.notLike(root.get(op.getKey()), "%"+op.getValue()+"%");
        } else if(NOT_LIKE_BEGIN.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.notLike(root.get(op.getKey()), op.getValue()+"%");
        } else if(NOT_LIKE_END.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.notLike(root.get(op.getKey()), "%"+op.getValue());
        } else if(IN.equalsIgnoreCase(op.getOper())) {
            return root.get(op.getKey()).in((Object[])op.getValue());
        }
        return null;
    }
}
