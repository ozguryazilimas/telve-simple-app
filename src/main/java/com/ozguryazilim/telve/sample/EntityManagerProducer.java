package com.ozguryazilim.telve.sample;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Entity Manager üreticisi
 * @author Hakan Uygun
 */
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext( type = PersistenceContextType.EXTENDED, unitName = "telve")
    private EntityManager em;

    @Produces
    public EntityManager getEntityManager() {
        return em;
    }

}
