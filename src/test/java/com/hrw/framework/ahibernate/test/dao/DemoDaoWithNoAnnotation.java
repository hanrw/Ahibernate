
package com.hrw.framework.ahibernate.test.dao;

import android.database.sqlite.SQLiteDatabase;

import com.hrw.framework.ahibernate.dao.AhibernateDao;
import com.hrw.framework.ahibernate.test.domain.DemoWithNoAnnotation;

public class DemoDaoWithNoAnnotation extends AhibernateDao<DemoWithNoAnnotation> {

    public DemoDaoWithNoAnnotation(SQLiteDatabase db) {
        super(db);
    }

}
