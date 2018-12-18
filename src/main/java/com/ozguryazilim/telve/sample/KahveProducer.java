package com.ozguryazilim.telve.sample;

import com.ozguryazilim.mutfak.kahve.annotations.Kahve;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

@ApplicationScoped
public class KahveProducer {
   
    @Resource(mappedName = "java:jboss/datasources/SimpleAppDS" )
    private DataSource dataSource;
    
    @Produces
    @Kahve
    public DataSource createDataSource() throws SQLException {
        return dataSource;
    }
}
