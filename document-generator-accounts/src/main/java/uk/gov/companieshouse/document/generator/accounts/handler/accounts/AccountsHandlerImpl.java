package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfo;

@Component
public class AccountsHandlerImpl implements AccountsHandler  {

    @Override
    public DocumentInfo getAccountsData(String resourceLink) {
        return new DocumentInfo();
    }
}
