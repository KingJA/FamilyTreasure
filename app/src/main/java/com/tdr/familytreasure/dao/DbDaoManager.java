package com.tdr.familytreasure.dao;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/5 9:56
 * 修改备注：
 */
public class DbDaoManager<T> implements DbDao<T>{

    @Override
    public T sleectFirst(Class<T> clazz, String key, String value) {
        return (T)DbDaoXutils3.getInstance().sleectFirst(clazz,  key,  value);
    }

    @Override
    public T sleectFirst(Class<T> clazz, String key1, String value1, String key2, String value2) {
        return (T)DbDaoXutils3.getInstance().sleectFirst( clazz,  key1,  value1,  key2,  value2);
    }

    @Override
    public List<T> sleectAll(Class<T> clazz, String key, String value) {
        return  (List<T>)DbDaoXutils3.getInstance().sleectAll( clazz,  key,  value);
    }

    @Override
    public List<T> sleectAllDb(Class<T> clazz) {
        return  (List<T>)DbDaoXutils3.getInstance().sleectAllDb( clazz);
    }

    @Override
    public void saveOrUpdate(T t) {
        DbDaoXutils3.getInstance().saveOrUpdate(t);
    }
}
