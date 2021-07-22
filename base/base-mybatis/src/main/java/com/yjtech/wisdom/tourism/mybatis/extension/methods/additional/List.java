package com.yjtech.wisdom.tourism.mybatis.extension.methods.additional;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author songjun
 * @since 2021/7/20 10:01
 */
public class List extends AbstractMethod {
    private static final String WRAPPER_ENTITY_DOT = "params" + DOT;

    private static final String WRAPPER_ENTITY = "params";

    /**
     * 下面这几个不用管，我们用到的不是querywrapper,不会用这个几个字段，这里放这里可以不用改他的源码
     */
    String WRAPPER = "params";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_LIST;
        String sql = String.format(sqlMethod.getSql(), sqlSelectColumns(tableInfo, true), tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo), EMPTY);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, "list", sqlSource, tableInfo);
    }

    @Override
    protected String sqlSelectColumns(TableInfo table, boolean queryWrapper) {
        /* 假设存在 resultMap 映射返回 */
        String selectColumns = ASTERISK;
        if (table.getResultMap() == null || (table.getResultMap() != null && table.isInitResultMap())) {
            /* 普通查询 */
            selectColumns = table.getAllSqlSelect();
        }
        if (!queryWrapper) {
            return selectColumns;
        }
        return selectColumns;
    }

    @Override
    protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
        if (table.isLogicDelete()) {
            String sqlScript = table.getAllSqlWhere(true, true, WRAPPER_ENTITY_DOT);
            sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER_ENTITY),
                    true);
            sqlScript += (NEWLINE + table.getLogicDeleteSql(true, false) + NEWLINE);
            sqlScript = SqlScriptUtils.convertChoose(String.format("%s != null", WRAPPER), sqlScript,
                    table.getLogicDeleteSql(false, false));
            sqlScript = SqlScriptUtils.convertWhere(sqlScript);
            return newLine ? NEWLINE + sqlScript : sqlScript;
        } else {
            String sqlScript = table.getAllSqlWhere(false, true, WRAPPER_ENTITY_DOT);
            sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER_ENTITY), true);
            sqlScript += NEWLINE;
            sqlScript = SqlScriptUtils.convertWhere(sqlScript) + NEWLINE;
            sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER), true);
            return newLine ? NEWLINE + sqlScript : sqlScript;
        }
    }
}
