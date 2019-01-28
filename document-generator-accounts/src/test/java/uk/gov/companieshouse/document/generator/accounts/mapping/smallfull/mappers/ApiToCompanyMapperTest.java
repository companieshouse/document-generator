package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCompanyMapperTest {

    private static final String JURISDICTION = "jurisdiction";

    private static final String COMPANY_NAME = "companyName";

    private static final String COMPANY_NUMBER = "companyNumber";

    @Test
    @DisplayName("tests company values map to company IXBRL model")
    void testApiToCompanyMaps() {

        Company company = ApiToCompanyMapper.INSTANCE.apiToCompany(createCompanyProfile());

        assertNotNull(company);
        assertEquals(COMPANY_NAME, company.getCompanyName());
        assertEquals(COMPANY_NUMBER, company.getCompanyNumber());
        assertEquals(JURISDICTION, company.getJurisdiction());
    }

    private CompanyProfileApi createCompanyProfile() {

        CompanyProfileApi companyProfile = new CompanyProfileApi();
        companyProfile.setCompanyName(COMPANY_NAME);
        companyProfile.setCompanyNumber(COMPANY_NUMBER);
        companyProfile.setJurisdiction(JURISDICTION);

        return companyProfile;
    }
}
