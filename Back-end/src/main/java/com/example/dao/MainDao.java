package com.example.dao;

import java.util.List;

public interface MainDao<V> {

    V save(V v);

    List<V> getAll();

}
