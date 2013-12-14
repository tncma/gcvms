package com.cma.gcvms.web.domain.support;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.primefaces.model.StreamedContent;

import com.cma.gcvms.web.util.DownloadUtil;

/**
 * StreamedContent that lazily loads the binary content.
 */
public abstract class ByteArrayStreamedContent implements StreamedContent {
    private String contentType = "application/download";
    private String name;
    private String contentEncoding;

    @Override
    public InputStream getStream() {
        DownloadUtil.forceResponseHeaderForDownload();
        return new ByteArrayInputStream(getByteArray());
    }

    /**
     * Lazily load the binary content.
     */
    protected abstract byte[] getByteArray();

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    @Override
    public String getContentEncoding() {
        return contentEncoding;
    }
}