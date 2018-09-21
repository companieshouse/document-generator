package uk.gov.companieshouse.document.generator.api.utility;

public enum DocumentType {

    ACCOUNTS("/transactions\\/.*\\/(?:company-)?accounts\\/.*");

    private String pattern;

    public String getPattern() {
        return pattern;
    }

    DocumentType(String pattern) {
        this.pattern = pattern;
    }
}
