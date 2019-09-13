package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.filinghistory.AnnotationsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.Annotations;

import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToAnnotations {

    public abstract Annotations apiToAnnotations(AnnotationsApi annotationsApi);

    public abstract List<Annotations> apiToAnnotations(List<AnnotationsApi> annotationsApi);

    @AfterMapping
    protected void convertAnnotationsDescription(AnnotationsApi annotationsApi,
                                                 @MappingTarget Annotations annotations) {
        annotations.setDescription(setAnnotationsDescription(annotationsApi.getDescriptionValues()));
    }

    private String setAnnotationsDescription(Map<String, Object> descriptionValues) {

        return descriptionValues.get("description").toString();
    }
}
