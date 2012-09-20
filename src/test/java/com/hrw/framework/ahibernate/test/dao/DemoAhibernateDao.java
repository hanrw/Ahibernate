
package com.hrw.framework.ahibernate.test.dao;

import org.hamcrest.Matcher;

import android.database.sqlite.SQLiteDatabase;

import com.hrw.framework.ahibernate.dao.AhibernateDao;
import com.hrw.framework.ahibernate.test.domain.Demo;

public class DemoAhibernateDao extends AhibernateDao<Demo> {

    public DemoAhibernateDao(SQLiteDatabase db) {
        super(db);
    }


}
