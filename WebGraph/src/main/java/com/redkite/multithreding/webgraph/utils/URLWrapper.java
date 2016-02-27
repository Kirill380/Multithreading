package com.redkite.multithreding.webgraph.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//TODO make using optionals
public class URLWrapper {
    private static final Logger log = LogManager.getLogger(URLWrapper.class);
    private URL url;

    public URLWrapper(URL url) {
        this.url = url;
    }

    public URLWrapper(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            log.error("WTF?? - " + url);
        }
    }

    public URL unWrap() {
        return url;
    }

    public String getSiteName() {
        if(url != null)
            return url.getHost() + url.getPath().replace("/", "_") + ".html";
        else
            return "";
    }

    public String getDomain() {
        if(url != null)
            return  url.getProtocol() + "://" + url.getAuthority();
        else
            return "";
    }

    public InputStream getStream() {
        // if MalformedURLException was thrown
        if(url == null)
            return null;

        HttpURLConnection con = null;
        int redirect = 0;
        //TODO refactor if logic
        try {
            do {
                //check if url exist
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("HEAD");
                int code = con.getResponseCode();

                // return input stream if status ok
                if (code == HttpURLConnection.HTTP_OK) {
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    return con.getInputStream();

                // follow redirect if returned status == 3xx
                } else if (code == HttpURLConnection.HTTP_MOVED_PERM ||
                        code == HttpURLConnection.HTTP_MOVED_TEMP ||
                        code == HttpURLConnection.HTTP_SEE_OTHER
                        ) {
                        log.info("Redirected from" + url.toString() + "to" + con.getHeaderField("Location"));
                        redirect++;
                        url = new URL(con.getHeaderField("Location"));

                // return error if other code
                } else {

                    log.error("Connection failed. Error code: " + code);
                    return null;
                }

            } while (redirect > 0 && redirect < 30);

        } catch (IOException e) {
            log.error("Error during opening connection: " + e.getMessage(),e);
            return null;
        }

        log.error("Error Too many redirects");
        return null;
    }

}
