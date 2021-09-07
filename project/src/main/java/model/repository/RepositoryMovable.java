package model.repository;

public interface RepositoryMovable<E> {

    void insert(E e);

    void update(E e);

    void save(E e);

    void delete(E e);

}
