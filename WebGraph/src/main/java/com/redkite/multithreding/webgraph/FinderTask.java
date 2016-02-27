package com.redkite.multithreding.webgraph;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FinderTask implements Callable<Set<Address>> {

    private Pattern findAnchor = Pattern.compile("<a.*href=\"(.+?)\".*?>");
    private FilePiece filePiece;

    public FinderTask(FilePiece filePiece) {
        this.filePiece = filePiece;
    }

    @Override
    public Set<Address> call() throws Exception {
        Set<Address> res = new HashSet<>();
        // search over piece of file
        for (String s : filePiece.getRecords()) {
            Matcher match = findAnchor.matcher(s);
            // search over line of file
            while (match.find()) {
                String address = match.group(1);
                // TODO refactor
                if (!address.contains("#") && !address.contains("javascript"))
                    if (!address.contains("://")) {
                        res.add(new Address(filePiece.getDomain() + address));
                    } else {
                        res.add(new Address(address));
                    }
            }
        }
        return res;
    }
}
