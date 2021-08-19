package uk.gov.companieshouse.document.generator.api.document;

public enum DocumentType {

    ACCOUNTS("/transactions\\/.*\\/(?:company-)?accounts\\/.*"),
    COMPANY_REPORT("/(?:dissolved-)?company-number/.*"),
    PROSECUTION("/internal/company/.*/prosecution-cases/.*/defendants/.*");

    private String pattern;

    public String getPattern() {
        return pattern;
    }

    DocumentType(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
