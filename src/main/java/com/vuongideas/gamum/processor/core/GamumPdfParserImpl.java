package com.vuongideas.gamum.processor.core;

import com.vuongideas.gamum.processor.model.PdfData;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

        StringBuilder textContents = new StringBuilder();

        for (PDPage page : doc.getPages()) {
            COSDictionary objects = page.getCOSObject();
            for (COSName key : objects.keySet()) {
                COSBase item = objects.getItem(key);
                if (item instanceof COSObject) {
                    COSObject obj = (COSObject)item;
                    obj.
                }
            }
        }

        return PdfData.builder()
                .contents(textContents.toString())
                .build();
    }
}
