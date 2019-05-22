package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCompanyMapperTest {

    private ApiToCompanyMapper apiToCompanyMapper = new ApiToCompanyMapperImpl();

    private static final String JURISDICTION = "jurisdiction";

    private static final String COMPANY_NAME = "companyName";

    private static final String COMPANY_NUMBER = "companyNumber";

    private static final String PRIVATE_LIMITED_BY_GUARANTEE = "private-limited-guarant-nsc";

    private static final String PRIVATE_LIMITED_BY_GUARANTEE_EXEMPT = "private-limited-guarant-nsc-limited-exemption";

    private static final String PLC = "plc";

    @Test
    @DisplayName("Api to company mapper - PLC")
    void testApiToCompanyMapperPLCCompany() {

        Company company = apiToCompanyMapper.apiToCompany(createCompanyProfileWithType(PLC));

        assertNotNull(company);
        assertEquals(COMPANY_NAME, company.getCompanyName());
        assertEquals(COMPANY_NUMBER, company.getCompanyNumber());
        assertEquals(JURISDICTION, company.getJurisdiction());
        assertFalse(company.getIsLBG());
    }

    @Test
    @DisplayName("Api to company mapper - private limited by guarantee")
    void testApiToCompanyMapperPrivateLimitedByGuaranteeCompany() {

        Company company = apiToCompanyMapper.apiToCompany(createCompanyProfileWithType(PRIVATE_LIMITED_BY_GUARANTEE));

        assertNotNull(company);
        assertEquals(COMPANY_NAME, company.getCompanyName());
        assertEquals(COMPANY_NUMBER, company.getCompanyNumber());
        assertEquals(JURISDICTION, company.getJurisdiction());
        assertTrue(company.getIsLBG());
    }

    @Test
    @DisplayName("Api to company mapper - private limited by guarantee exempt")
    void testApiToCompanyMapperPrivateLimitedByGuaranteeExemptCompany() {

        Company company = apiToCompanyMapper.apiToCompany(createCompanyProfileWithType(PRIVATE_LIMITED_BY_GUARANTEE_EXEMPT));

        assertNotNull(company);
        assertEquals(COMPANY_NAME, company.getCompanyName());
        assertEquals(COMPANY_NUMBER, company.getCompanyNumber());
        assertEquals(JURISDICTION, company.getJurisdiction());
        assertTrue(company.getIsLBG());
    }

    private CompanyProfileApi createCompanyProfileWithType(String companyType) {

        CompanyProfileApi companyProfile = new CompanyProfileApi();
        companyProfile.setCompanyName(COMPANY_NAME);
        companyProfile.setCompanyNumber(COMPANY_NUMBER);
        companyProfile.setJurisdiction(JURISDICTION);
        companyProfile.setType(companyType);

        return companyProfile;
    }
}
