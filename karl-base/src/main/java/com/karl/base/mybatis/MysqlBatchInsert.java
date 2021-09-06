package com.karl.base.mybatis;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Objects;

import static java.util.stream.Collectors.joining;

/**
 * 插入多条数据
 *
 * @author karl
 * @since 2018-04-06
 */
@SuppressWarnings("all")
public class MysqlBatchInsert extends AbstractMethod {

    private static final String FOREACH_SCRIPT = "<foreach collection=\"list\" item=\"item\" separator=\",\">\n%s\n</foreach>";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        KeyGenerator keyGenerator = new NoKeyGenerator();
        SqlMethod sqlMethod = SqlMethod.INSERT_ONE;
        String columnStr = tableInfo.getKeyInsertSqlColumn(true) +
                tableInfo.getFieldList().stream()
                        .filter(a -> a.getInsertStrategy() != FieldStrategy.NEVER && a.getInsertStrategy() != FieldStrategy.IGNORED)
                        .map(TableFieldInfo::getInsertSqlColumn)
                        .filter(Objects::nonNull)
                        .collect(joining(NEWLINE));
        String columnScript = SqlScriptUtils.convertTrim(columnStr, LEFT_BRACKET, RIGHT_BRACKET, null, COMMA);
        String valueStr = tableInfo.getKeyInsertSqlProperty("item.", true) +
                tableInfo.getFieldList().stream()
                        .filter(a -> a.getInsertStrategy() != FieldStrategy.NEVER && a.getInsertStrategy() != FieldStrategy.IGNORED)
                        .map(i -> getInsertSqlPropertyMaybeIf(i, "item."))
                        .filter(Objects::nonNull)
                        .collect(joining(NEWLINE));
        String valuesScript = SqlScriptUtils.convertTrim(valueStr, LEFT_BRACKET, RIGHT_BRACKET, null, COMMA);
        String foreachValuesScript = String.format(FOREACH_SCRIPT, valuesScript);
        String keyProperty = null;
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (StringUtils.isNotBlank(tableInfo.getKeyProperty())) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                /** 自增主键 */
                keyGenerator = new Jdbc3KeyGenerator();
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            } else {
                if (null != tableInfo.getKeySequence()) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(getMethod(sqlMethod), tableInfo, builderAssistant);
                    keyProperty = tableInfo.getKeyProperty();
                    keyColumn = tableInfo.getKeyColumn();
                }
            }
        }
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), columnScript, foreachValuesScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, "mysqlBatchInsert", sqlSource, keyGenerator, keyProperty, keyColumn);
    }

    public String getInsertSqlPropertyMaybeIf(TableFieldInfo field, final String prefix) {
        String sqlScript = field.getInsertSqlProperty(prefix);
        if (field.isWithInsertFill()) {
            return sqlScript;
        }
        String prefixProperty = String.format("%s%s", prefix, field.getProperty());
        return convertChoose(sqlScript, field.isCharSequence(), prefixProperty, field.getInsertStrategy());
    }

    private String convertChoose(final String sqlScript, boolean isCharSequence, final String prefixProperty, final FieldStrategy fieldStrategy) {
        if (fieldStrategy == FieldStrategy.NOT_EMPTY && isCharSequence) {
            return SqlScriptUtils.convertChoose(String.format("%s != null and %s != ''", prefixProperty, prefixProperty),
                    sqlScript, "null,");
        }
        return SqlScriptUtils.convertChoose(String.format("%s != null", prefixProperty), sqlScript, "null,");
    }
}
