package uk.gov.companieshouse.document.generator.company.report.handler;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.CompanyServiceOracle;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.FilingHistoryServiceOracle;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.OfficerDetailsServiceOracle;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Component
public class DissolvedCompanyReportDataHandler {

	private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

	private static final String COMPANY_REPORT = "company-report";

	@Autowired
	private FilingHistoryServiceOracle filingHistoryServiceOracle;

	@Autowired
	private OfficerDetailsServiceOracle officerDetailsServiceOracle;
	
	@Autowired
	private CompanyServiceOracle companyServiceOracle;

	@Autowired
	private CompanyReportMapper companyReportMapper;

	public DocumentInfoResponse getCompanyReport(String companyNumber, String requestId) throws HandlerException {

		ZonedDateTime timeStamp = ZonedDateTime.now();
		Map<String, Object> debugMap = getDebugMap(companyNumber);
		debugMap.put("request_id", requestId);

		LOG.info("Getting data for dissolved company report", debugMap);
		return createDocumentInfoResponse(companyNumber, requestId, timeStamp);
	}

	private DocumentInfoResponse createDocumentInfoResponse(String companyNumber, String requestId,
			ZonedDateTime timeStamp) throws HandlerException {

		DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();

		documentInfoResponse.setData(getCompanyReportData(companyNumber, requestId, timeStamp));
		documentInfoResponse.setAssetId(COMPANY_REPORT);
		documentInfoResponse.setPath(createPathString());
		documentInfoResponse.setTemplateName("company-report.html");
		documentInfoResponse.setDescriptionIdentifier(COMPANY_REPORT);

		return documentInfoResponse;
	}

	private String getCompanyReportData(String companyNumber, String requestId, ZonedDateTime timeStamp)
			throws HandlerException {
		CompanyReportApiData companyReportApiData = new CompanyReportApiData();

		CompanyProfileApi companyProfileApi = companyServiceOracle.getCompanyProfile(companyNumber);

		setCompanyReportData(companyNumber, requestId, companyReportApiData, companyProfileApi);

		return toJson(companyReportMapper.mapCompanyReport(companyReportApiData, requestId, companyNumber),
                companyNumber, requestId, timeStamp);
	}

	private void setCompanyReportData(String companyNumber, String requestId, CompanyReportApiData companyReportApiData,
			CompanyProfileApi companyProfileApi) {
	    companyReportApiData.setCompanyProfileApi(companyProfileApi);
		setFilingHistoryData(companyNumber, requestId, companyReportApiData, companyProfileApi);
		setOfficerDetailsData(companyNumber, requestId, companyReportApiData, companyProfileApi);
	}

	private void setFilingHistoryData(String companyNumber, String requestId, CompanyReportApiData companyReportApiData,
			CompanyProfileApi companyProfileApi) {
		FilingHistoryApi filingHistoryApi = filingHistoryServiceOracle.getFilingHistory(companyNumber);
		companyReportApiData.setFilingHistoryApi(filingHistoryApi);
	}

	private void setOfficerDetailsData(String companyNumber, String requestId, CompanyReportApiData companyReportApiData,
			CompanyProfileApi companyProfileApi) {
		OfficersApi officersApi = officerDetailsServiceOracle.getOfficerDetails(companyNumber);
		companyReportApiData.setOfficersApi(officersApi);
	}

	private String createPathString() {
		return String.format("/%s/%s", COMPANY_REPORT, getUniqueFileName());
	}

	private String getUniqueFileName() {
		UUID uuid = UUID.randomUUID();
		return "companyReport" + uuid.toString() + ".html";
	}

	private Map<String, Object> getDebugMap(String companyNumber) {
		Map<String, Object> debugMap = new HashMap<>();
		debugMap.put("company_number", companyNumber);
		return debugMap;
	}

	private String toJson(CompanyReport companyReport, String companyNumber, String requestId, ZonedDateTime timeStamp)
			throws HandlerException {

		String reportToJson;
		ObjectMapper mapper = new ObjectMapper();

		companyReport.setTimeStampCreated(timeStamp.format(DateTimeFormatter.ofPattern("d MMMM uuuu HH:mm:ss")));

		try {
			LOG.infoContext(requestId, "Attempting to convert company report to JSON", getDebugMap(companyNumber));
			reportToJson = mapper.writeValueAsString(companyReport);
		} catch (JsonProcessingException e) {
			throw new HandlerException(new StringBuilder(
					"Could not serialise Document data for the generation of the company report for company: ")
							.append(companyReport.getRegistrationInformation().getCompanyName()).append("-")
							.append(companyReport.getRegistrationInformation().getCompanyNumber()).toString());
		}

		return reportToJson;
	}
}
