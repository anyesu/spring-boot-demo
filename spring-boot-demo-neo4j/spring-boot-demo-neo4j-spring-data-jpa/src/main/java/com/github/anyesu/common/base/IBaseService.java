package com.github.anyesu.common.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author anyesu
 */
public interface IBaseService<T> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    <S extends T> S save(S entity);

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param s
     * @param depth
     * @param <S>
     * @return
     */
    <S extends T> S save(S s, int depth);

    /**
     * Saves all given entities.
     *
     * @param entities
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    <S extends T> Iterable<S> save(Iterable<S> entities);

    /**
     * Saves all given entities.
     *
     * @param entities
     * @param depth
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    <S extends T> Iterable<S> save(Iterable<S> entities, int depth);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T findOne(Long id);

    /**
     * Retrieves an entity by its id.
     *
     * @param id    must not be {@literal null}.
     * @param depth
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T findOne(Long id, int depth);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    boolean exists(Long id);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     * Returns all instances of the type.
     *
     * @param depth
     * @return all entities
     */
    Iterable<T> findAll(int depth);

    Iterable<T> findAll(Sort sort);

    Iterable<T> findAll(Sort sort, int depth);

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    Iterable<T> findAll(Iterable<Long> ids);

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @param depth
     * @return
     */
    Iterable<T> findAll(Iterable<Long> ids, int depth);

    Iterable<T> findAll(Iterable<Long> ids, Sort sort);

    Iterable<T> findAll(Iterable<Long> ids, Sort sort, int depth);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     * {@link Page#getTotalPages()} returns an estimation of the total number of pages and should not be relied upon for
     * accuracy.
     *
     * @param pageable
     * @return a page of entities
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     * {@link Page#getTotalPages()} returns an estimation of the total number of pages and should not be relied upon for
     * accuracy.
     *
     * @param pageable
     * @param depth
     * @return a page of entities
     */
    Page<T> findAll(Pageable pageable, int depth);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(Long id);

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    void delete(T entity);

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    void delete(Iterable<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();

}
