package uk.gov.companieshouse.document.generator.company.report.service.oracle.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uk.gov.companieshouse.api.company.Data;
import uk.gov.companieshouse.api.company.Links;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

import java.util.HashMap;
import java.util.Map;

//@Mapper(componentModel = "spring")
public interface CompanyProfileMapper {

    //@Mapping(source = "links", target = "links", qualifiedByName = "mapLinksToMap")
    CompanyProfileApi toCompanyProfileApi(Data companyProfile);


    //@Named("mapLinksToMap")
    default Map<String, String> mapLinksToMap(Links links) {
        if (links == null) {
            return null;
        }
        Map<String, String> linksMap = new HashMap<>();
        linksMap.put("personsWithSignificantControl", links.getPersonsWithSignificantControl());
        linksMap.put("personsWithSignificantControlStatements", links.getPersonsWithSignificantControlStatements());
        linksMap.put("registers", links.getRegisters());
        linksMap.put("self", links.getSelf());
        linksMap.put("charges", links.getCharges());
        linksMap.put("filingHistory", links.getFilingHistory());
        linksMap.put("insolvency", links.getInsolvency());
        linksMap.put("officers", links.getOfficers());
        linksMap.put("overseas", links.getOverseas());
        linksMap.put("ukEstablishments", links.getUkEstablishments());
        linksMap.put("exemptions", links.getExemptions());
        linksMap.put("personsWithSignificantControlStatement", links.getPersonsWithSignificantControlStatement());
        return linksMap;
    }
}