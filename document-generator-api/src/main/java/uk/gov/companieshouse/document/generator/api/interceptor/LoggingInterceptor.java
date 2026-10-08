package uk.gov.companieshouse.document.generator.api.interceptor;

import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.companieshouse.api.util.security.Permission;
import uk.gov.companieshouse.api.util.security.TokenPermissions;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.logging.util.RequestLogger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static uk.gov.companieshouse.api.util.security.AuthorisationUtil.getTokenPermissions;
import static uk.gov.companieshouse.document.generator.api.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@Component
public class LoggingInterceptor  implements AsyncHandlerInterceptor , RequestLogger {

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        TokenPermissions tokenPermissions = getTokenPermissions(request)
                .orElseThrow(() -> new IllegalStateException("Token permissions not found in request"));

        boolean hasCompanyAccountRead = tokenPermissions.hasPermission(Permission.Key.COMPANY_ACCOUNTS, Permission.Value.READ);
        boolean hasCompanyAccountWrite = tokenPermissions.hasPermission(Permission.Key.COMPANY_ACCOUNTS, Permission.Value.UPDATE);

        if (hasCompanyAccountRead && hasCompanyAccountWrite) {
            LOG.info("Token has required permissions for filing abridged accounts");
            return true;
        } else {
            LOG.info("Token does not have required permissions READ and UPDATE for company accounts");
            throw new Exception("Token does not have required permissions READ and UPDATE for company accounts");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        logEndRequestProcessing(request, response, LOG);
    }
}

