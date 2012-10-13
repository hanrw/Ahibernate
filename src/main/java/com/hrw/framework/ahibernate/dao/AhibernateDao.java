
package com.hrw.framework.ahibernate.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.hrw.framework.ahibernate.builder.EntityBuilder;
import com.hrw.framework.ahibernate.cfg.Configuration;
import com.hrw.framework.ahibernate.exceptions.MappingException;
import com.hrw.framework.ahibernate.mapping.Column;
import com.hrw.framework.ahibernate.mapping.Table;
import com.hrw.framework.ahibernate.sql.Delete;
import com.hrw.framework.ahibernate.sql.Insert;
import com.hrw.framework.ahibernate.sql.Operate;
import com.hrw.framework.ahibernate.sql.Select;
import com.hrw.framework.ahibernate.sql.SelectNew;
import com.hrw.framework.ahibernate.sql.Update;
import com.hrw.framework.ahibernate.table.TableUtils;

public class AhibernateDao<T> {
    private static String EMPTY_SQL = "DELETE FROM ";

    private SQLiteDatabase db;

    private Logger log = Logger.getLogger(Configuration.class);

    private String TAG = "AhibernateDao";

    private Configuration cfg;

    public AhibernateDao(SQLiteDatabase db) {
        this.db = db;
        this.cfg = Configuration.getInstance().configure();
    }

    public Configuration getConfiguration() {
        return cfg;
    }

    @SuppressWarnings("unchecked")
    public Class<T> getGenricTypeClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public int insert(T entity) {
        if (null == cfg.getEntityPersister(entity.getClass().getName())) {
            throw new MappingException("Unknown entity: " + entity.getClass().getName());
        }
        Table table = cfg.getTable(entity.getClass().getName());
        Insert insert = new Insert().setTableName(table.getName());

        for (Column col : table.getColumns().values()) {
            insert.addColumn(col.getName(), getColumnValueByColumnName(entity, col));
        }
        String sql = insert.toStatementString();
        log.info("insert entity " + entity.getClass().getName() + ":" + sql);
        SQLiteStatement stmt = null;
        try {
            stmt = db.compileStatement(sql);
            return (int) stmt.executeInsert();
        } catch (android.database.SQLException e) {
            return -1;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public Object get(Serializable id) {
        Class<T> entityClass = getGenricTypeClass();
        if (null == cfg.getEntityPersister(entityClass.getName())) {
            throw new MappingException("Unknown entity: " + entityClass.getName());
        }
        Table table = cfg.getTable(entityClass.getName());
        SelectNew selectNew = new SelectNew();
        selectNew.setSelectClause("*");
        selectNew.setFromClause(table.getName());
        selectNew.setWhereClause(table.getIdentifierName() + " = " + id);
        String sql = selectNew.toStatementString();
        log.info("get entity " + entityClass.getName() + ":" + sql);
        Cursor cursor = db.rawQuery(sql, null);
        EntityBuilder<T> builder = new EntityBuilder<T>(entityClass, cursor);
        List<T> queryList = builder.buildQueryList();
        cursor.close();
        return queryList.size() == 0 ? null : queryList.get(0);
    }

    public Object getColumnValueByColumnName(T entity, Column colmun) {
        Field field;
        try {
            field = entity.getClass().getDeclaredField(colmun.getFieldName());
            field.setAccessible(true);
            return field.get(entity);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public List<T> queryList(Class clazz, Map<String, String> where) {
        String sql = new Select(clazz, where).toStatementString();
        Log.d(TAG, "query sql:" + sql);
        Cursor cursor = db.rawQuery(sql, null);
        EntityBuilder<T> builder = new EntityBuilder<T>(clazz, cursor);
        List<T> queryList = builder.buildQueryList();
        cursor.close();
        return queryList;
    }

    /**
     * query by entity if entity is null query for all.
     * 
     * @param entity
     * @return List<T>
     */
    public List<T> queryList(T entity) {
        if (entity != null && null == cfg.getEntityPersister(entity.getClass().getName())) {
            throw new MappingException("Unknown entity: " + entity.getClass().getName());
        }
        try {
            entity = entity != null ? entity : (T) getGenricTypeClass().newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        String sql = new Select(entity).toStatementString();
        Log.d(TAG, "query sql:" + sql);
        Cursor cursor = db.rawQuery(sql, null);
        EntityBuilder<T> builder = new EntityBuilder<T>(entity.getClass(), cursor);
        List<T> queryList = builder.buildQueryList();
        cursor.close();
        return queryList;
    }

    public void update(T entity, Map<String, String> where) {
        String sql = new Update(entity, where).toStatementString();
        Log.d(TAG, "update sql:" + sql);
        SQLiteStatement stmt = null;
        try {
            stmt = db.compileStatement(sql);
            stmt.execute();
        } catch (android.database.SQLException e) {
            Log.e(TAG, e.getMessage() + " sql:" + sql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void update(T entity) {
        String sql = new Update(entity).toStatementString();
        Log.d(TAG, "update sql:" + sql);
        SQLiteStatement stmt = null;
        try {
            stmt = db.compileStatement(sql);
            stmt.execute();
        } catch (android.database.SQLException e) {
            Log.e(TAG, e.getMessage() + " sql:" + sql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void delete(Class clazz, Map<String, String> where) {
        String sql = null;
        if (null == where) {
            sql = new Delete(clazz).toStatementString();
        } else {
            sql = new Delete(clazz, where).toStatementString();
        }
        Log.d(TAG, "delete sql:" + sql);
        SQLiteStatement stmt = null;
        try {
            stmt = db.compileStatement(sql);
            stmt.execute();
        } catch (android.database.SQLException e) {
            Log.e(TAG, e.getMessage() + " sql:" + sql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void truncate(Class clazz) {
        String sql = EMPTY_SQL + TableUtils.getTableName(clazz);
        Log.d(TAG, "truncate sql:" + sql);
        SQLiteStatement stmt = null;
        try {
            stmt = db.compileStatement(sql);
            stmt.execute();
        } catch (android.database.SQLException e) {
            Log.e(TAG, e.getMessage() + " sql:" + sql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void delete(T entity) {
        String sql = null;
        sql = new Delete(entity).toStatementString();
        Log.d(TAG, "delete sql:" + sql);
        SQLiteStatement stmt = null;
        try {
            stmt = db.compileStatement(sql);
            stmt.execute();
        } catch (android.database.SQLException e) {
            Log.e(TAG, e.getMessage() + " sql:" + sql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void truncate(Class clazz, Map<String, String> where) {

        StringBuffer sql = new StringBuffer(EMPTY_SQL + TableUtils.getTableName(clazz));
        Log.d(TAG, "truncate sql:" + sql);
        if (where != null) {
            sql.append(" WHERE ");
            Iterator iter = where.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry e = (Map.Entry) iter.next();
                sql.append(e.getKey())
                        .append(" = ")
                        .append(Operate.isNumeric(e.getValue().toString()) ? e.getValue() : "'"
                                + e.getValue() + "'");
                if (iter.hasNext()) {
                    sql.append(" AND ");
                }
            }
        }
        SQLiteStatement stmt = null;
        try {
            stmt = db.compileStatement(sql.toString());
            stmt.execute();
        } catch (android.database.SQLException e) {
            Log.e(TAG, e.getMessage() + " sql:" + sql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

    }

    public SQLiteDatabase getSQLiteDatabase() {
        return db;
    }
}
