package com.redkite.multithreding.webgraph;

import java.util.List;

public class FilePiece {
    private final List<String> records;
    private final String domain;
    private final String currDir = "";

    public FilePiece(List<String> records, String domain) {
        this.records = records;
        this.domain = domain;
    }

    public List<String> getRecords() {
        return records;
    }

    public String getDomain() {
        return domain;
    }


}
