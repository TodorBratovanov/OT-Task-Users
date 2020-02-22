package com.example.ot.otusers.model;

import lombok.Getter;

@Getter
public enum RdfFormat {

    TTL("ttl", "TTL", ".ttl", "text/turtle"),
    N_TRIPLES("ntriples", "N-TRIPLES", ".nt", "application/n-triples");

    private String name;
    private String format;
    private String fileExtension;
    private String mediaType;

    RdfFormat(String name, String format, String fileExtension, String mediaType) {
        this.name = name;
        this.format = format;
        this.fileExtension = fileExtension;
        this.mediaType = mediaType;
    }

    public static RdfFormat getByName(String name) {
        for (RdfFormat value : values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unsupported RdfFormat: " + name);
    }
}
