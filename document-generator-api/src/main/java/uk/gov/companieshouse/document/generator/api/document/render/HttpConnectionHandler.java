package uk.gov.companieshouse.document.generator.api.document.render;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface HttpConnectionHandler {

    /**
     * Open a HttpUrlConnection for the request
     *
     * @param url
     * @return HttpUrlConnection for the given request
     * @throws IOException
     */
    HttpURLConnection openConnection(String url) throws IOException;
}
