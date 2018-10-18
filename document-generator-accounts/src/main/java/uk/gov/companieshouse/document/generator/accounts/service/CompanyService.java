package uk.gov.companieshouse.document.generator.accounts.service;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;

public interface CompanyService {

    CompanyProfileApi getCompanyProfile(String companyNumber) throws ServiceException;
}
