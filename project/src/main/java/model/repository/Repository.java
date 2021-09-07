package model.repository;

import java.util.List;


public interface Repository<E> {

    List<E> getAll();

    E get(int id);

    void create();

    void save(E e);

    void update(E e);


}