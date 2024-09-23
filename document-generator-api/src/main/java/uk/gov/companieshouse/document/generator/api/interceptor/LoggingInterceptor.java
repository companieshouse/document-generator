package uk.gov.companieshouse.document.generator.api.interceptor;

import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.logging.util.RequestLogger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static uk.gov.companieshouse.document.generator.api.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@Component
public class LoggingInterceptor  implements AsyncHandlerInterceptor , RequestLogger {

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        logStartRequestProcessing(request, LOG);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        logEndRequestProcessing(request, response, LOG);
    }
}

