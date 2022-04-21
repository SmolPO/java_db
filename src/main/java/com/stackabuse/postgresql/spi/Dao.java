/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackabuse.postgresql.spi;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T, I> {
    Optional get(int id);
    Collection getAll();
    Optional save(T t);
    void update(T t);
    void delete(T t);
}
