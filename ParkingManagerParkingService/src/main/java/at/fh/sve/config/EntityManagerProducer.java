package at.fh.sve.config;


import at.fh.sve.domain.BaseEntity;
import at.fh.sve.util.Constants;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.ogm.datastore.mongodb.MongoDBProperties;
import org.hibernate.ogm.jpa.HibernateOgmPersistence;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityManagerProducer {

    @Inject
    @ConfigProperty(name = Constants.DB.CONFIG_PROP_DATASTORE_PROV, defaultValue = Constants.DB.DEFAULT_DATASTORE_PROVIDER)
    private String DATASTORE_PROVIDER;

    @Inject
    @ConfigProperty(name = Constants.DB.CONFIG_PROP_DATABASE, defaultValue = Constants.DB.DEFAULT_DATABASE)
    private String DATABASE;

    @Inject
    @ConfigProperty(name = Constants.DB.CONFIG_PROP_DB_HOST, defaultValue = Constants.DB.DEFAULT_DB_HOST)
    private String DB_HOST;

    @Inject
    @ConfigProperty(name = Constants.DB.CONFIG_PROP_DB_PORT, defaultValue = Constants.DB.DEFAULT_DB_PORT)
    private String DB_PORT;

    private Properties props;

    @PostConstruct
    public void init() {
        props = new Properties() {{
            put(MongoDBProperties.DATASTORE_PROVIDER, DATASTORE_PROVIDER);
            put(MongoDBProperties.DATABASE, DATABASE);
            put(MongoDBProperties.HOST, DB_HOST);
            put(MongoDBProperties.PORT, DB_PORT);
            put(MongoDBProperties.CREATE_DATABASE, "true");
            put(MongoDBProperties.USERNAME, "admin");
            put(MongoDBProperties.PASSWORD, "sve");
        }};
    }

    @Produces
    public EntityManager createEntityManager() {
        List<String> entities = scanForEntities();
        PersistenceUnitInfo pInfo = new MongoPersistenceUnitInfo(Constants.DB.PERSISTENCE_UNIT, scanForEntities(), props);
        EntityManagerFactory emf = new HibernateOgmPersistence().createContainerEntityManagerFactory(pInfo, new HashMap<>());
        return emf.createEntityManager();
    }

    public void close(EntityManager entityManager) {
        entityManager.close();
    }

    private List<String> scanForEntities() {
        Reflections reflections = new Reflections(Constants.DB.DOMAIN_CLASS_PACKAGE);
        Set<Class<? extends BaseEntity>> entities = reflections.getSubTypesOf(BaseEntity.class);
        entities.add(BaseEntity.class);

        entities.forEach(e -> System.out.println(e.getName()));

        return entities
                .stream()
                .map(Class::getName)
                .collect(Collectors.toList());
    }
}
