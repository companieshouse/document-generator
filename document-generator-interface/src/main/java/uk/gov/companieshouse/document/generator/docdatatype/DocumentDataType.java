package uk.gov.companieshouse.document.generator.docdatatype;

public enum DocumentDataType {
    
    ACCOUNTS("/transactions/.*/accounts/.*");
    
    private final String value;
    
    DocumentDataType(String input) {
        this.value = input;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.value;
    }
    
    public DocumentDataType getDocumentDataType(String url) {
        //TODO implement parsing of the url to match the DocumentType enum regex.
        return null;
    }
}
