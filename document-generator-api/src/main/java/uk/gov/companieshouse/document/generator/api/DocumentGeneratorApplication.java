package uk.gov.companieshouse.document.generator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.gov.companieshouse.document.generator.api.interceptor.LoggingInterceptor;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.exception.EnvironmentVariableException;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootApplication
public class DocumentGeneratorApplication implements WebMvcConfigurer {

    public static final String APPLICATION_NAME_SPACE = "document-generator-api";

    public static final String DOCUMENT_RENDER_SERVICE_HOST_ENV_VAR = "DOCUMENT_RENDER_SERVICE_HOST";

    public static final String DOCUMENT_BUCKET_NAME_ENV_VAR = "DOCUMENT_BUCKET_NAME";

    private static final String CHS_API_KEY = "CHS_API_KEY";

    private static final String API_URL = "API_URL";

    private static final Logger LOGGER = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    private static EnvironmentReader reader;

    public static void main(String[] args) {

        reader = new EnvironmentReaderImpl();

        checkEnvironmentParams();

        Integer port = Integer.getInteger("server.port");

        if (port == null) {
            LOGGER.error("Failed to start service, no port has been configured");
            System.exit(0);
        }

        SpringApplication.run(DocumentGeneratorApplication.class, args);
    }

    /**
     * Check all expected Environment variables are set
     */
    public static void checkEnvironmentParams() {

        List<String> environmentParams = new ArrayList<>();

        environmentParams.add(DOCUMENT_RENDER_SERVICE_HOST_ENV_VAR);
        environmentParams.add(DOCUMENT_BUCKET_NAME_ENV_VAR);
        environmentParams.add(CHS_API_KEY);
        environmentParams.add(API_URL);
        checkParams(environmentParams);
    }

    private static void checkParams(List<String> environmentParams) {

        AtomicBoolean environmentParamMissing = new AtomicBoolean(false);

        environmentParams.stream().forEach(param -> {

            try {
                String paramValue = reader.getMandatoryString(param);
                LOGGER.info("Environment variable " + param + " has value " + paramValue);
                } catch (EnvironmentVariableException e) {
                LOGGER.error("Environment variable " + param + " is not set", e);
                environmentParamMissing.set(true);
            }
        });

        if (environmentParamMissing.get() == true) {
            throw new RuntimeException("There are environment variables that " +
                "are not set, see logs for details - application will exit");
        }
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(loggingInterceptor)
                .excludePathPatterns("/healthcheck");
    }
}
