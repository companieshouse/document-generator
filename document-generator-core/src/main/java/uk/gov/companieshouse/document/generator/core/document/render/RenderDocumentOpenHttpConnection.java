package uk.gov.companieshouse.document.generator.core.document.render;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface RenderDocumentOpenHttpConnection {

    /**
     * Open a HttpUrlConnection for the request
     *
     * @param url
     * @return HttpUrlConnection for the given request
     * @throws IOException
     */
    HttpURLConnection openConnection(String url) throws IOException;
}
