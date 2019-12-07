package com.vuongideas.gamum.processor.core;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class GamumPdfParserImpl implements GamumPdfParser {

    @Override
    public String extractText(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);
        return new PDFTextStripper().getText(doc);
    }
}
