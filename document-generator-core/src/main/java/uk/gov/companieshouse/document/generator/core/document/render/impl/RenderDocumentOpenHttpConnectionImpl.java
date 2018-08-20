package uk.gov.companieshouse.document.generator.core.document.render.impl;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.core.document.render.RenderDocumentOpenHttpConnection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@Service
public class RenderDocumentOpenHttpConnectionImpl implements RenderDocumentOpenHttpConnection {

    /**
     * Open a HttpUrlConnection for the request
     *
     * @param url
     * @return HttpUrlConnection for the given request
     * @throws IOException
     */
    @Override
    public HttpURLConnection openConnection(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();

        return (HttpURLConnection) connection;
    }
}
