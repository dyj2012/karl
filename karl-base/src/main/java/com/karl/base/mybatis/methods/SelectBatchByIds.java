package com.karl.base.mybatis.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.karl.base.mybatis.BaseSelectMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/6/4
 */
public class SelectBatchByIds extends BaseSelectMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_BATCH_BY_IDS;
        String column = sqlSelectColumns(tableInfo, false);
        String from = tableInfo.getTableName();
        String where = tableInfo.getKeyColumn();
        if (super.isForeignEntity(modelClass)) {
            column = super.fixedColumnSql(modelClass, column);
            from = super.fixedFromSql(modelClass, from);
            where = super.fixedWhereSql(modelClass, where);
        }
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(sqlMethod.getSql(),
                column, from, where,
                SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA),
                tableInfo.getLogicDeleteSql(true, true)), Object.class);
        return addSelectMappedStatementForTable(mapperClass, getMethod(sqlMethod), sqlSource, tableInfo);
    }
}
