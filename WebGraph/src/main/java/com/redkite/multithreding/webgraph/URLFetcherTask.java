package com.redkite.multithreding.webgraph;

import com.redkite.multithreding.webgraph.utils.URLWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


public class URLFetcherTask implements Callable<FilePiece> {
    private final Logger log = LogManager.getLogger(URLFetcherTask.class);
    private String path = "D:\\sites";
    private URLWrapper site;

    public URLFetcherTask(Address address) {
        this.site = new URLWrapper(address.getAddress());
        new File(path).mkdir();
    }

    public URLFetcherTask(Address address, String path) {
        this.site = new URLWrapper(address.getAddress());
        this.path = path;
        new File(path).mkdir();
    }

    public List<String> saveAndFetchSite() throws IOException {
        List<String> res = new ArrayList<>();
        InputStream in = site.getStream();
        if(in == null)
            return res;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             BufferedWriter write = new BufferedWriter(new FileWriter(path + "\\" + site.getSiteName()))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                res.add(line);
              //  write.write(line);
            }
        } catch (IOException e) {
            log.error("Error during reading page from server: " + e.getMessage(), e);
        }
        return res;
    }

    @Override
    public FilePiece call() throws Exception {
        return new FilePiece(saveAndFetchSite(), site.getDomain());
    }
}
