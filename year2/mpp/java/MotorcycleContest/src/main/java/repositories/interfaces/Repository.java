package repositories.interfaces;

import domain.entities.Entity;

import java.sql.SQLException;
import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {
    Optional<E> findOne(ID id);

    Iterable<E> findAll();

    E save(E entity);

    E remove(ID id);

    Integer size();
}
