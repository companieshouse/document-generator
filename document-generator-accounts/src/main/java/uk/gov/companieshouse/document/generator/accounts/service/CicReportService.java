package uk.gov.companieshouse.document.generator.accounts.service;

import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReport;

public interface CicReportService {

    CicReport getCicReport(String cicReportLink, String requestId) throws ServiceException;
}
