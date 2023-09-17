package com.freitasprojects.estock.controllers.interfaces;

import java.util.List;

public interface ControllerInterface <T> {
    public void create(T entity);
    public void update(T entity, Long id);
    public void delete(int id);
    public T findById(int id);
    public List<T> listAll();
}
