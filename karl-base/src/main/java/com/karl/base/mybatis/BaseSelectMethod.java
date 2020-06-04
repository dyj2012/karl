package com.karl.base.mybatis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.karl.base.annotation.ForeignKey;
import com.karl.base.annotation.RelationField;
import com.karl.base.model.BaseEntity;
import com.karl.base.mybatis.vo.ForeignInfo;
import com.karl.base.mybatis.vo.ForeignTableInfo;
import com.karl.base.mybatis.vo.RelationInfo;
import com.karl.base.util.CamelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/6/4
 */
@Slf4j
public abstract class BaseSelectMethod extends AbstractMethod {

    @Override
    protected String sqlSelectColumns(TableInfo table, boolean queryWrapper) {
        return super.sqlSelectColumns(table, queryWrapper);
    }

    public static final Map<Class<?>, ForeignTableInfo> FOREIGN_TABLE_INFO_CACHE = new ConcurrentHashMap<>();

    protected String fixedWhereSql(Class<?> modelClass, String where) {
        return String.format("a.%s", where);
    }

    protected String fixedColumnSql(Class<?> modelClass, String columns) {
        ForeignTableInfo foreignTableInfo = FOREIGN_TABLE_INFO_CACHE.get(modelClass);
        Map<String, ForeignInfo> foreignTableInfoMap = foreignTableInfo.getForeignTableInfoMap();
        String columnSql = String.format("a.%s", columns.replace(",", ",a."));
        StringBuilder sb = new StringBuilder(columnSql);
        for (ForeignInfo foreignInfo : foreignTableInfoMap.values()) {
            List<RelationInfo> relationColumnList = foreignInfo.getRelationColumnList();
            for (RelationInfo r : relationColumnList) {
                sb.append(String.format(",%s.%s AS %s", foreignInfo.getAlias(), r.getRelationField(), r.getAlias()));
            }
        }
        for (Map.Entry<String, ForeignInfo> stringForeignInfoEntry : foreignTableInfoMap.entrySet()) {
            ForeignInfo foreignInfo = stringForeignInfoEntry.getValue();
            List<RelationInfo> relationColumnList = foreignInfo.getRelationColumnList();
            for (RelationInfo r : relationColumnList) {
                sb.append(String.format(",%s.%s AS %s", foreignInfo.getAlias(), r.getRelationField(), r.getAlias()));
            }
        }
        return sb.toString();
    }

    protected String fixedFromSql(Class<?> modelClass, String from) {
        ForeignTableInfo foreignTableInfo = FOREIGN_TABLE_INFO_CACHE.get(modelClass);
        Map<String, ForeignInfo> foreignTableInfoMap = foreignTableInfo.getForeignTableInfoMap();
        StringBuilder sb = new StringBuilder(from);
        sb.append(" a");
        for (Map.Entry<String, ForeignInfo> stringForeignInfoEntry : foreignTableInfoMap.entrySet()) {
            ForeignInfo foreignInfo = stringForeignInfoEntry.getValue();
            String foreignKey = stringForeignInfoEntry.getKey();
            List<RelationInfo> relationColumnList = foreignInfo.getRelationColumnList();
            if (relationColumnList.size() > 0) {
                sb.append(String.format(" LEFT JOIN %s %s ON a.%s = %s.%s ",
                        foreignInfo.getForeignTableName(),
                        foreignInfo.getAlias(),
                        foreignKey,
                        foreignInfo.getAlias(),
                        foreignInfo.getConditionField()
                ));
            }
        }
        return sb.toString();
    }

    protected boolean isForeignEntity(Class<?> cls) {
        return FOREIGN_TABLE_INFO_CACHE.computeIfAbsent(cls, clazz -> {
            ForeignTableInfo info = new ForeignTableInfo();
            List<Field> fields = new ArrayList<>();
            parseParentDeclaredFields(fields, cls);
            for (Field field : fields) {
                ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
                if (foreignKey != null) {
                    Class<? extends BaseEntity> value = foreignKey.value();
                    TableName tableName = value.getAnnotation(TableName.class);
                    if (tableName == null || StringUtils.isEmpty(tableName.value())) {
                        log.warn("Class:{}, Field:{}, 外键指定的Entity {} 没有表名", cls.getName(), field.getName(), value.getName());
                        continue;
                    }
                    info.setForeign(true);
                    ForeignInfo foreignInfo = new ForeignInfo();
                    foreignInfo.setAlias(String.format("B%s", info.getForeignTableInfoMap().size()));
                    foreignInfo.setForeignTableName(tableName.value());
                    foreignInfo.setConditionField(foreignKey.conditionField());
                    TableField tableField = field.getAnnotation(TableField.class);
                    if (tableField == null || StringUtils.isEmpty(tableField.value())) {
                        info.getForeignTableInfoMap().put(CamelUtils.toUnderline(field.getName()).toUpperCase(), foreignInfo);
                    } else {
                        info.getForeignTableInfoMap().put(tableField.value(), foreignInfo);
                    }

                }
            }
            if (info.isForeign()) {
                for (Field field : fields) {
                    RelationField relationField = field.getAnnotation(RelationField.class);
                    if (relationField != null) {
                        String foreignField = relationField.foreignField().toUpperCase();
                        ForeignInfo foreignInfo = info.getForeignTableInfoMap().get(foreignField);
                        if (foreignInfo != null) {
                            RelationInfo relationInfo = new RelationInfo();
                            relationInfo.setAlias(CamelUtils.toUnderline(field.getName()).toUpperCase());
                            relationInfo.setRelationField(relationField.refField());
                            foreignInfo.getRelationColumnList().add(relationInfo);
                        }
                    }
                }
            }
            return info;
        }).isForeign();
    }

    private void parseParentDeclaredFields(List<Field> fields, Class<?> cls) {
        if (cls.getSuperclass().equals(Object.class)) {
            return;
        }
        fields.addAll(Arrays.asList(cls.getDeclaredFields()));
    }

    protected String fixedSqlWhereByMap(TableInfo table) {
        if (table.isLogicDelete()) {
            // 逻辑删除
            String sqlScript = SqlScriptUtils.convertChoose("v == null", " a.${k} IS NULL ",
                    " a.${k} = #{v} ");
            sqlScript = SqlScriptUtils.convertForeach(sqlScript, "cm", "k", "v", "AND");
            sqlScript = SqlScriptUtils.convertIf(sqlScript, "cm != null and !cm.isEmpty", true);
            sqlScript += (NEWLINE + table.getLogicDeleteSql(true, true));
            sqlScript = SqlScriptUtils.convertWhere(sqlScript);
            return sqlScript;
        } else {
            String sqlScript = SqlScriptUtils.convertChoose("v == null", " a.${k} IS NULL ",
                    " a.${k} = #{v} ");
            sqlScript = SqlScriptUtils.convertForeach(sqlScript, COLUMN_MAP, "k", "v", "AND");
            sqlScript = SqlScriptUtils.convertWhere(sqlScript);
            sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null and !%s", COLUMN_MAP,
                    COLUMN_MAP_IS_EMPTY), true);
            return sqlScript;
        }
    }
}
