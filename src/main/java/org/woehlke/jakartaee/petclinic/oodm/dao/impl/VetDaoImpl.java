package org.woehlke.jakartaee.petclinic.oodm.dao.impl;

import lombok.extern.log4j.Log4j2;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.woehlke.jakartaee.petclinic.oodm.dao.VetDao;
import org.woehlke.jakartaee.petclinic.oodm.entities.Vet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 02.01.14
 * Time: 08:30
 * To change this template use File | Settings | File Templates.
 */
@Log4j2
@Stateless
public class VetDaoImpl implements VetDao {

  private static final long serialVersionUID = -1003870150408928198L;

  @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
  private EntityManager entityManager;

  @Override
  public List<Vet> getAll() {
    String qlString = "select v from Vet v order by v.lastName,v.firstName";
    TypedQuery<Vet> q = entityManager.createQuery(qlString, Vet.class);
    List<Vet> list = q.getResultList();
    return list;
  }

  @Override
  public void delete(long id) {
    Vet vet = entityManager.find(Vet.class, id);
    entityManager.remove(vet);
  }

  @Override
  public Vet addNew(Vet vet) {
    vet.setUuid(UUID.randomUUID());
    log.debug("try to persist: " + vet.toString());
    entityManager.persist(vet);
    log.debug("persisted: " + vet.toString());
    return vet;
  }

  @Override
  public Vet findById(long id) {
    Vet vet = entityManager.find(Vet.class, id);
    return vet;
  }

  @Override
  public Vet update(Vet vet) {
    return entityManager.merge(vet);
  }

  @Override
  public List<Vet> search(String searchterm) {
    FullTextEntityManager fullTextEntityManager =
        org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
    QueryBuilder qb = fullTextEntityManager.getSearchFactory()
        .buildQueryBuilder().forEntity(Vet.class).get();
    org.apache.lucene.search.Query query = qb
        .keyword()
        .onFields("firstName", "lastName", "specialties.name")
        .matching(searchterm)
        .createQuery();
    // wrap Lucene query in a javax.persistence.Query
    javax.persistence.Query persistenceQuery =
        fullTextEntityManager.createFullTextQuery(query, Vet.class);
    // execute search
    @SuppressWarnings("unchecked")
    List<Vet> result = persistenceQuery.getResultList();
    return result;
  }

  @Override
  public void resetSearchIndex() {
    FullTextEntityManager fullTextEntityManager =
        org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
    String qlString = "select o from Vet o";
    TypedQuery<Vet> findAllActionItems = fullTextEntityManager.createQuery(
        qlString,
        Vet.class
    );
    for (Vet vet : findAllActionItems.getResultList()) {
      fullTextEntityManager.index(vet);
    }
    fullTextEntityManager.flushToIndexes();
    //fullTextEntityManager.clear();
  }


  @PostConstruct
  public void postConstruct() {
    log.debug("postConstruct");
  }

  @PreDestroy
  public void preDestroy() {
    log.debug("preDestroy");
  }

  @PrePassivate
  public void prePassivate() {
    log.debug("prePassivate");
  }

  @PostActivate
  public void postActivate() {
    log.debug("postActivate");
  }
}
