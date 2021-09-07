package dao;

import java.util.List;


public interface DAO<E> {

    List<E> getAll();

    E get(int id);

    void update(E e);

    void insert(E e);

}
