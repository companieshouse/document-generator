package uk.gov.companieshouse.document.generator.api.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseObject;

@Component
public class ApiResponseMapper {

    public ResponseEntity map(ResponseObject responseObject) {

        switch(responseObject.getStatus()) {
            case CREATED:
                return ResponseEntity.status(HttpStatus.CREATED).body(responseObject.getData());
            case NO_DATA_RETRIEVED:
            case NO_TYPE_FOUND:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            case NOT_RENDERED:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject.getData());
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
