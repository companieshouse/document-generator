package uk.gov.companieshouse.document.generator;

import java.util.Arrays;
import java.util.UUID;

public enum AccountType {

    ABRIDGED_ACCOUNTS("accounts", "abridged-accounts", "abridged-accounts", "abridged_accounts");

    private static final String HTML_EXTENSION = ".html";

    private String assetId;
    private String templateName;
    private String enumerationKey;
    private String resourceKey;

    AccountType(String assetId, String templateName, String enumerationKey, String resourceKey) {
        this.assetId = assetId;
        this.templateName = templateName;
        this.enumerationKey = enumerationKey;
        this.resourceKey = resourceKey;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        return templateName + uuid.toString() + HTML_EXTENSION;
    }

    public String getTemplateName() {
        return templateName + HTML_EXTENSION;
    }

    public String getEnumerationKey() {
        return enumerationKey;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public static AccountType getAccountType(String key) {
        return Arrays.stream(AccountType.values())
                .filter(e -> e.getResourceKey().equalsIgnoreCase(key))
                .findFirst()
                .orElse(null);
    }
}
