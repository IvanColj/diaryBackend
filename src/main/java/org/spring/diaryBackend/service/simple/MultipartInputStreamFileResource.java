package org.spring.diaryBackend.service.simple;

import org.springframework.core.io.AbstractResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Вспомогательный класс для загрузки файлов через RestTemplate
 */
public class MultipartInputStreamFileResource extends AbstractResource {

    private final InputStream inputStream;
    private final String filename;

    public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        this.inputStream = inputStream;
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public long contentLength() throws IOException {
        return -1; // Неизвестная длина - Spring сам определит
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Multipart file resource for: " + filename;
    }
}