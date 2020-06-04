package com.karl.base.mybatis.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.karl.base.mybatis.BaseSelectMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/6/4
 */
public class SelectByMap extends BaseSelectMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_BY_MAP;

        String column = sqlSelectColumns(tableInfo, false);
        String from = tableInfo.getTableName();
        String where = tableInfo.getKeyColumn();
        if (super.isForeignEntity(modelClass)) {
            column = super.fixedColumnSql(modelClass, column);
            from = super.fixedFromSql(modelClass, from);
            where = super.fixedWhereSql(modelClass, where);
        }


        String sql = String.format(sqlMethod.getSql(), sqlSelectColumns(tableInfo, false),
                tableInfo.getTableName(), sqlWhereByMap(tableInfo));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Map.class);
        return this.addSelectMappedStatementForTable(mapperClass, getMethod(sqlMethod), sqlSource, tableInfo);
    }
}
