package uk.gov.companieshouse.document.generator.company.report.utils;

import java.time.format.DateTimeFormatter;

public class TestUtils {

    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    public static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUUU);
    }

}
