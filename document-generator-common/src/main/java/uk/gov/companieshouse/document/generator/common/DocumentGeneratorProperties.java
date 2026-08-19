package uk.gov.companieshouse.document.generator.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "document")
public class DocumentGeneratorProperties {

    @Valid
    private final Bucket bucket = new Bucket();
    @Valid
    private final Render render = new Render();

    public Bucket getBucket() {
        return bucket;
    }

    public Render getRender() {
        return render;
    }

    public static class Bucket {
        @NotEmpty
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Render {

        @Valid
        private final Service service = new Service();

        public Service getService() {
            return service;
        }

        public static class Service {

            @NotEmpty
            private String host;

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }
        }

    }

}
