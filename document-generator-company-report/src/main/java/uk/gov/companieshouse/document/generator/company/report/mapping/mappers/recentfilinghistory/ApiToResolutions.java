package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.filinghistory.ResolutionsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.Resolutions;

import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToResolutions {

    public abstract Resolutions apiToResolutions(ResolutionsApi resolutionsApi);

    public abstract List<Resolutions> apiToResolutions(List<ResolutionsApi> resolutionsApi);

    @AfterMapping
    protected void convertResolutionsDescription(ResolutionsApi resolutionsApi,
                                                 @MappingTarget Resolutions resolutions) {
        resolutions.setDescription(setResolutionsDescription(
            resolutionsApi.getDescriptionValues()));
    }

    private String setResolutionsDescription(Map<String, Object> descriptionValues) {

        return descriptionValues.get("description").toString();
    }
}
