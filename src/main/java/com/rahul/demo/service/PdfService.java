package com.rahul.demo.service;

import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfService {

    public String extractText(MultipartFile file){
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded PDF file is empty");
        }
        String contentType = file.getContentType();
        if (contentType != null && !contentType.equalsIgnoreCase("application/pdf")) {
            throw new IllegalArgumentException("Expected a PDF file, but got content type: " + contentType);
        }

        try (
            InputStream is = file.getInputStream();
            PDDocument document = PDDocument.load(is)
        ) {
            if (document.isEncrypted()) {
                throw new IllegalStateException("Encrypted PDF documents are not supported");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);

        } catch (IllegalArgumentException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract PDF (invalid/corrupt PDF?): " + e.getMessage(), e);
        }
    }

}
