package at.fh.sve.util;

public class Constants {

    public static final class DB {
        public static final String PERSISTENCE_UNIT = "restapi-unit";
        public static final String DOMAIN_CLASS_PACKAGE = "at.fh.mus.parking.domain";

        public static final String DEFAULT_DATASTORE_PROVIDER = "mongodb";
        public static final String DEFAULT_DATABASE = "parkingDB";
        public static final String DEFAULT_DB_HOST = "localhost";
        public static final String DEFAULT_DB_PORT = "27017";

        public static final String CONFIG_PROP_DATASTORE_PROV = "DATASTORE_PROVIDER";
        public static final String CONFIG_PROP_DATABASE = "DATABASE";
        public static final String CONFIG_PROP_DB_HOST = "DB_HOST";
        public static final String CONFIG_PROP_DB_PORT = "DB_PORT";
    }

}
