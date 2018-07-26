package uk.gov.companieshouse.document.generator.interfaces.model;

public class DocumentInfo {
    
    String data;
    String templateId;
    String assetId;
    
    public DocumentInfo(){}
    
    public DocumentInfo(String data, String templateId, String assetId) {
        super();
        this.data = data;
        this.templateId = templateId;
        this.assetId = assetId;
    }
    
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getTemplateId() {
        return templateId;
    }
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public String getAssetId() {
        return assetId;
    }
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}
