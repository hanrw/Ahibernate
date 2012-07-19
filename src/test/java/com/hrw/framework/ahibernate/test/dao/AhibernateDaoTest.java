
package com.hrw.framework.ahibernate.test.dao;

import org.junit.Test;

import android.database.sqlite.SQLiteDatabase;

import com.hrw.framework.ahibernate.dao.AhibernateDao;

public class AhibernateDaoTest {
//    @Test
    public void testInsert() {
        SQLiteDatabase db = SQLiteDatabase.create(null);
        AhibernateDao dao = new AhibernateDao(db);

    }
}
