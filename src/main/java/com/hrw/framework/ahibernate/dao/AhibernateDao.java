
package com.hrw.framework.ahibernate.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.hrw.framework.ahibernate.builder.EntityBuilder;
import com.hrw.framework.ahibernate.sql.Delete;
import com.hrw.framework.ahibernate.sql.Insert;
import com.hrw.framework.ahibernate.sql.Operate;
import com.hrw.framework.ahibernate.sql.Select;
import com.hrw.framework.ahibernate.sql.Update;
import com.hrw.framework.ahibernate.table.TableUtils;

public class AhibernateDao<T> {
    private static String EMPTY_SQL = "DELETE FROM ";

    private SQLiteDatabase db;

    private String TAG = "AhibernateDao";

    public AhibernateDao(SQLiteDatabase db) {
        this.db = db;
    }

    public int insert(T entity) {
        String sql;
        sql = new Insert(entity).toStatementString();
        Log.d(TAG, "insert sql:" + sql);
        SQLiteStatement stmt = null;
        try {
            stmt = db.compileStatement(sql);
            long rowId = stmt.executeInsert();
            return 1;
        } catch (android.database.SQLException e) {
            Log.e(TAG, "inserting to database failed: " + sql, e);
            return -1;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
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

    public List<T> queryList(T entity) {
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
}
