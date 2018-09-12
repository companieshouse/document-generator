package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

@Component
public class AccountsHandlerImpl implements AccountsHandler  {

    @Override
    public DocumentInfoResponse getAccountsData(String resourceLink) {
        return new DocumentInfoResponse();
    }
}
