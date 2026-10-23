package uk.gov.companieshouse.document.generator.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * <p>Useful to prevent deployments where the Spring Context is broken.</p>
 * <p>But beware of properties/environment variables being missing from:</p>
 * <ul>
 *     <li>this test, in test/resources and the surefire configuration</li>
 *     <li>CHS Docker compose for this project</li>
 *     <li>AWS, in the ecs-service-configs repository</li>
 * </ul>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SpringContextSmokeTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }
}
