package cn.hll520.queryfilter.tools;

import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.term.ITermFilter;
import cn.hll520.queryfilter.term.ITermQuery;
import cn.hll520.queryfilter.term.ITermSort;
import cn.hll520.queryfilter.term.entiry.IFieldFilter;
import cn.hll520.queryfilter.term.entiry.IFieldSort;

import java.util.List;

/**
 * 描述： 字段转换工具
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午11:02
 */
public class FieldMapTools {

    /**
     * 全量条件转换
     *
     * @param fieldMap    转换接口
     * @param queryFilter 全量条件
     * @return 转换后的条件
     */
    public static ITermQuery map(IFieldMap fieldMap, ITermQuery queryFilter) {
        if (queryFilter != null) {
            map(fieldMap, (ITermFilter) queryFilter);
            map(fieldMap, (ITermSort) queryFilter);
        }
        return queryFilter;
    }

    /**
     * 转换过滤条件集
     *
     * @param fieldMap   转换接口
     * @param termFilter 过滤条件集
     * @return 转换后的过滤条件集
     */
    public static ITermFilter map(IFieldMap fieldMap, ITermFilter termFilter) {
        if (termFilter != null && termFilter.acquireTermFilters() != null) {
            List<IFieldFilter> fieldFilters = termFilter.acquireTermFilters();
            for (int i = fieldFilters.size() - 1; i >= 0; i--) {
                fieldFilters.set(i, map(fieldMap, fieldFilters.get(i)));
            }
        }
        return termFilter;
    }

    /**
     * 转换单个过滤条件
     *
     * @param fieldMap    字段转换接口
     * @param fieldFilter 过滤条件
     * @return 转换后的条件
     */
    public static IFieldFilter map(IFieldMap fieldMap, IFieldFilter fieldFilter) {
        if (fieldFilter != null && fieldMap != null) {
            fieldFilter.receiveFieldName(fieldMap.fieldMap(fieldFilter.acquireFieldName()));
        }
        return fieldFilter;
    }

    /**
     * 转换排序条件集
     *
     * @param fieldMap 转换方式
     * @param termSort 排序条件集
     * @return 转换后的排序条件集
     */
    public static ITermSort map(IFieldMap fieldMap, ITermSort termSort) {
        if (termSort != null && termSort.acquireTermSorts() != null) {
            List<IFieldSort> fieldSorts = termSort.acquireTermSorts();
            for (int i = fieldSorts.size() - 1; i >= 0; i--) {
                fieldSorts.set(i, map(fieldMap, fieldSorts.get(i)));
            }
        }
        return termSort;
    }

    /**
     * 转换单个排序条件
     *
     * @param fieldSort 排序条件
     * @param fieldMap  转换接口
     * @return 转换后的条件
     */
    public static IFieldSort map(IFieldMap fieldMap, IFieldSort fieldSort) {
        if (fieldSort != null && fieldMap != null) {
            fieldSort.receiveFieldName(fieldMap.fieldMap(fieldSort.acquireFieldName()));
        }
        return fieldSort;
    }
}
