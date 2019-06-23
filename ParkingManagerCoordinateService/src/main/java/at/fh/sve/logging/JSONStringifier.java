package at.fh.sve.logging;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONStringifier {

    private static ObjectWriter JSON_WRITER = new ObjectMapper().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL)
            .writerWithDefaultPrettyPrinter();

    private static final Logger LOG = LoggerFactory.getLogger(JSONStringifier.class);
    private JSONStringifier() {
    }

    public static String stringify(Object object) {
        try {
            return JSON_WRITER.writeValueAsString(object);
        } catch (IOException e) {
            if (!e.getMessage().contains("SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS")) {
                LOG.error("Fehler bei der Generierung des AuditString", e);
            }
            return null;
        }
    }
}
