package com.vuongideas.gamum.processor.core;

import com.vuongideas.gamum.processor.model.ExtractedDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.stream.StreamSupport;

@Component
public class GamumPdfParserImpl implements GamumPdfParser {

    @Override
    public ExtractedDocument extractDocument(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);

        PageExtractor extractor = new PageExtractor();

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
