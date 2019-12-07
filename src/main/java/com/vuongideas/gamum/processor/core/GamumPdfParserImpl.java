package com.vuongideas.gamum.processor.core;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class GamumPdfParserImpl implements GamumPdfParser {

    @Override
    public String extractText(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);
        return new PDFTextStripper().getText(doc);
    }
}
