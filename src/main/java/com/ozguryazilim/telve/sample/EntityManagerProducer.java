/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ozguryazilim.telve.sample;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import org.picketlink.annotations.PicketLink;

/**
 * Entity Manager üreticisi
 * @author Hakan Uygun
 */
@ApplicationScoped
public class EntityManagerProducer {
    
    @PersistenceContext( type = PersistenceContextType.EXTENDED, unitName = "telve")
    private EntityManager em;

    @PersistenceContext(unitName = "picketlink")
    private EntityManager emp;

    @Produces
    public EntityManager getEntityManager() {
        return em;
    }

    /*
    public void close(@Disposes final EntityManager entityManager) {
        if( entityManager.isOpen() ){
            entityManager.close();
        }
    }*/
    
    @Produces
    @PicketLink
    private EntityManager getpicketLinkEntityManager() {
        return emp;
    }
}
