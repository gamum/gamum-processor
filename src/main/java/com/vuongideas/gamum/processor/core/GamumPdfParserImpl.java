package com.vuongideas.gamum.processor.core;

import com.vuongideas.gamum.processor.model.PdfData;
import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class GamumPdfParserImpl implements GamumPdfParser {

    @Override
    public String extractText(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);
        return new PDFTextStripper().getText(doc);
    }

    @Override
    public PdfData extractData(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);

        StringBuilder contentsBuilder = new StringBuilder();

        for (PDPage page : doc.getPages()) {
            PDFStreamParser parser = new PDFStreamParser(page);
            parser.parse();
            List<Object> tokens = parser.getTokens();
            for (Object token : tokens) {
                if (token instanceof COSString) {
                    contentsBuilder.append(((COSString)token).getString());
                }
            }
        }

        return PdfData.builder()
                .contents(contentsBuilder.toString())
                .build();
    }
}
