package uk.gov.companieshouse.document.generator.prosecution;

public enum ProsecutionType {

    ULTIMATUM("prosecution", "ultimatum.html", "ultimatum"), 
    SJPN("prosecution", "sjpn.html", "sjpn");

    private String assetId;
    private String template;
    private String resource;

    ProsecutionType(String assetId, String template, String resource) {
        this.assetId = assetId;
        this.template = template;
        this.resource = resource;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getTemplate() {
        return template;
    }

    public String getResource() {
        return resource;
    }

}
