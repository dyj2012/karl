package com.karl.base.mybatis.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.karl.base.mybatis.BaseSelectMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/6/4
 */
public class SelectById extends BaseSelectMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_BY_ID;
        String column = sqlSelectColumns(tableInfo, false);
        String from = tableInfo.getTableName();
        String where = tableInfo.getKeyColumn();
        if (super.isForeignEntity(modelClass)) {
            column = super.fixedColumnSql(modelClass, column);
            from = super.fixedFromSql(modelClass, from);
            where = super.fixedWhereSql(modelClass, where);
        }
        SqlSource sqlSource = new RawSqlSource(configuration, String.format(sqlMethod.getSql(),
                column.toString(), from.toString(), where.toString(), tableInfo.getKeyProperty(),
                tableInfo.getLogicDeleteSql(true, true)), Object.class);
        return this.addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
    }
}
