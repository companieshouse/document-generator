package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.exemptions.ExemptionItemsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items.ExemptionItems;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToExemptionItems {

    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    public abstract ExemptionItems apiToExemptionItems(ExemptionItemsApi exemptionItemsApi);

    public abstract List<ExemptionItems> apiToExemptionItems(List<ExemptionItemsApi> exemptionItemsApi);

    @AfterMapping
    protected void formatExemptFromDate(ExemptionItemsApi exemptionItemsApi,
                                        @MappingTarget ExemptionItems exemptionItems) {
        if (exemptionItemsApi !=null && exemptionItemsApi.getExemptFrom() != null) {
            LocalDate exemptFromDate = exemptionItemsApi.getExemptFrom();
            exemptionItems.setExemptFrom(exemptFromDate.format(getDateFormatter()));
        }
    }

    private DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUUU);
    }
}
