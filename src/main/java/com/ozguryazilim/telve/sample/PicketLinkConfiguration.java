/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.sample;

import com.ozguryazilim.telve.idm.entities.ResourcePermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import org.picketlink.event.PartitionManagerCreateEvent;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.config.IdentityConfiguration;
import org.picketlink.idm.config.IdentityConfigurationBuilder;
import org.picketlink.idm.config.SecurityConfigurationException;
import com.ozguryazilim.telve.idm.entities.AccountTypeEntity;
import com.ozguryazilim.telve.idm.entities.AttributeTypeEntity;
import com.ozguryazilim.telve.idm.entities.GroupTypeEntity;
import com.ozguryazilim.telve.idm.entities.IdentityTypeEntity;
import com.ozguryazilim.telve.idm.entities.PartitionTypeEntity;
import com.ozguryazilim.telve.idm.entities.PasswordCredentialTypeEntity;
import com.ozguryazilim.telve.idm.entities.RelationshipIdentityTypeEntity;
import com.ozguryazilim.telve.idm.entities.RelationshipTypeEntity;
import com.ozguryazilim.telve.idm.entities.RoleTypeEntity;
import org.picketlink.idm.model.Relationship;
import org.picketlink.idm.model.basic.Realm;

/**
 * PicketLink ayarlarını yapar.
 *
 * @author Hakan Uygun
 */
@ApplicationScoped
public class PicketLinkConfiguration {

    private IdentityConfiguration identityConfig = null;

    @Produces
    public IdentityConfiguration createConfig() {
        if (identityConfig == null) {
            initConfig();
        }
        return identityConfig;
    }

    /**
     * This method uses the IdentityConfigurationBuilder to create an
     * IdentityConfiguration, which defines how PicketLink stores
     * identity-related data. In this particular example, a JPAIdentityStore is
     * configured to allow the identity data to be stored in a relational
     * database using JPA.AttributedTypeEntity.class,
     */
    @SuppressWarnings("unchecked")
    private void initConfig() {
        
        IdentityConfigurationBuilder builder = new IdentityConfigurationBuilder();

        builder
                .named("default")
                .stores()
                .jpa()
                .mappedEntity(
                        AccountTypeEntity.class,
                        RoleTypeEntity.class,
                        GroupTypeEntity.class,
                        IdentityTypeEntity.class,
                        RelationshipTypeEntity.class,
                        RelationshipIdentityTypeEntity.class,
                        PartitionTypeEntity.class,
                        PasswordCredentialTypeEntity.class,
                        AttributeTypeEntity.class,
                        ResourcePermission.class)
                .supportGlobalRelationship(Relationship.class)
                //.addContextInitializer(this.contextInitializer)
                // Specify that this identity store configuration supports all features
                .supportAllFeatures();
        identityConfig = builder.build();
    }

    public void configureDefaultPartition(@Observes PartitionManagerCreateEvent event) {
        
        PartitionManager partitionManager = event.getPartitionManager();
        createDefaultPartition(partitionManager);
    }

    private void createDefaultPartition(PartitionManager partitionManager) {
        
        Realm partition = partitionManager.getPartition(Realm.class, Realm.DEFAULT_REALM);
        if (partition == null) {
            try {
                partition = new Realm(Realm.DEFAULT_REALM);
                partitionManager.add(partition);
            } catch (Exception e) {
                throw new SecurityConfigurationException("Could not create default partition.", e);
            }
        }
    }
}
