package com.vuongideas.gamum.processor.core;

import com.vuongideas.gamum.processor.model.ExtractedDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.stream.StreamSupport;

@Component
public class GamumPdfParserImpl implements GamumPdfParser {

    private PageExtractor extractor;

    @Autowired
    public GamumPdfParserImpl(PageExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public ExtractedDocument extractDocument(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);

        StreamSupport.stream(doc.getPages().spliterator(), false).forEach(p -> {
            try {
                extractor.processPage(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return extractor.buildDocument();
    }
}
