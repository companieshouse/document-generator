package uk.gov.companieshouse.document.generator.company.report.mapping.model.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.corporateannotation.CorporateAnnotation;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.CurrentAppointments;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.Exemptions;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.Insolvency;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.KeyFilingDates;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.MortgageChargeDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames.PreviousNames;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.Pscs;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.RecentFilingHistory;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.CompanyRegisters;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.Statements;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.ukestablishment.UkEstablishment;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("company_report")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
public class CompanyReport {

    @JsonProperty("time_stamp_created")
    private String TimeStampCreated;

    @JsonProperty("company_registration_information")
    private RegistrationInformation registrationInformation;

    @JsonProperty("previous_names")
    private List<PreviousNames> previousNames;

    @JsonProperty("corporate_annotation")
    private List<CorporateAnnotation> corporateAnnotation;

    @JsonProperty("current_appointments")
    private CurrentAppointments currentAppointments;

    @JsonProperty("key_filing_dates")
    private KeyFilingDates keyFilingDates;

    @JsonProperty("recent_filing_history")
    private List<RecentFilingHistory> recentFilingHistory;

    @JsonProperty("pscs")
    private Pscs pscs;

    @JsonProperty("foreign_company_details")
    private ForeignCompanyDetails foreignCompanyDetails;

    @JsonProperty("uk_establishment")
    private List<UkEstablishment> ukEstablishment;

    @JsonProperty("psc_statements")
    private Statements statements;

    @JsonProperty("registers")
    private CompanyRegisters companyRegisters;

    @JsonProperty("insolvency")
    private Insolvency insolvency;

    @JsonProperty("exemptions")
    private Exemptions exemptions;

    @JsonProperty("mortgage_charge_details")
    private MortgageChargeDetails mortgageChargeDetails;

    public String getTimeStampCreated() {
        return TimeStampCreated;
    }

    public void setTimeStampCreated(String timeStampCreated) {
        TimeStampCreated = timeStampCreated;
    }

    public RegistrationInformation getRegistrationInformation() {
        return registrationInformation;
    }

    public void setRegistrationInformation(RegistrationInformation registrationInformation) {
        this.registrationInformation = registrationInformation;
    }

    public List<PreviousNames> getPreviousNames() {
        return previousNames;
    }

    public void setPreviousNames(List<PreviousNames> previousNames) {
        this.previousNames = previousNames;
    }

    public List<CorporateAnnotation> getCorporateAnnotation() {
        return corporateAnnotation;
    }

    public void setCorporateAnnotation(List<CorporateAnnotation> corporateAnnotation) {
        this.corporateAnnotation = corporateAnnotation;
    }

    public CurrentAppointments getCurrentAppointments() {
        return currentAppointments;
    }

    public void setCurrentAppointments(CurrentAppointments currentAppointments) {
        this.currentAppointments = currentAppointments;
    }

    public KeyFilingDates getKeyFilingDates() {
        return keyFilingDates;
    }

    public void setKeyFilingDates(KeyFilingDates keyFilingDates) {
        this.keyFilingDates = keyFilingDates;
    }

    public List<RecentFilingHistory> getRecentFilingHistory() {
        return recentFilingHistory;
    }

    public void setRecentFilingHistory(List<RecentFilingHistory> recentFilingHistory) {
        this.recentFilingHistory = recentFilingHistory;
    }

    public Pscs getPscs() {
        return pscs;
    }

    public void setPscs(Pscs pscs) {
        this.pscs = pscs;
    }

    public ForeignCompanyDetails getForeignCompanyDetails() {
        return foreignCompanyDetails;
    }

    public void setForeignCompanyDetails(ForeignCompanyDetails foreignCompanyDetails) {
        this.foreignCompanyDetails = foreignCompanyDetails;
    }

    public List<UkEstablishment> getUkEstablishment() {
        return ukEstablishment;
    }

    public void setUkEstablishment(List<UkEstablishment> ukEstablishment) {
        this.ukEstablishment = ukEstablishment;
    }

    public Statements getStatements() {
        return statements;
    }

    public void setStatements(Statements statements) {
        this.statements = statements;
    }

    public CompanyRegisters getCompanyRegisters() {
        return companyRegisters;
    }

    public void setCompanyRegisters(CompanyRegisters companyRegisters) {
        this.companyRegisters = companyRegisters;
    }
    public Insolvency getInsolvency() {
        return insolvency;
    }

    public void setInsolvency(
        Insolvency insolvency) {
        this.insolvency = insolvency;
    }

    public Exemptions getExemptions() {
        return exemptions;
    }

    public void setExemptions(Exemptions exemptions) {
        this.exemptions = exemptions;
    }

    public MortgageChargeDetails getMortgageChargeDetails() {
        return mortgageChargeDetails;
    }

    public void setMortgageChargeDetails(
        MortgageChargeDetails mortgageChargeDetails) {
        this.mortgageChargeDetails = mortgageChargeDetails;
    }
}
