package at.fh.sve.config;

import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.ReaderListener;
import io.swagger.models.Contact;
import io.swagger.models.Swagger;

@SwaggerDefinition
public class SwaggerConfig implements ReaderListener {
    @Override
    public void beforeScan(Reader reader, Swagger swagger) {

    }

    @Override
    public void afterScan(Reader reader, Swagger swagger) {
        swagger.getInfo().setVersion("1.0");
        swagger.getInfo().setTitle("SVE_Project");
        swagger.getInfo().setContact(new Contact().name("Christoph Ruhsam").email("ruhsamchristoph@gmail.com"));
    }
}
