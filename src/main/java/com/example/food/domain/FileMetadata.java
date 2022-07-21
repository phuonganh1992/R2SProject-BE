package com.example.food.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class FileMetadata implements Serializable {
    private String bucket;
    private String key;
    private String name;
    private String mime;
    private String hash;
    private String etag;
    private Long size;
    private String extension;
    private String url;
    private Boolean publicAccess = false;
}
