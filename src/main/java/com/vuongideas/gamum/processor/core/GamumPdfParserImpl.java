package com.vuongideas.gamum.processor.core;

import com.vuongideas.gamum.processor.core.textdecoder.UnicodeTextDecoder;
import com.vuongideas.gamum.processor.model.ExtractedDocument;
import com.vuongideas.gamum.processor.model.PdfData;
import com.vuongideas.gamum.processor.model.fragment.ExtractedFragment;
import com.vuongideas.gamum.processor.model.fragment.ExtractedTextFragment;
import org.apache.fontbox.cmap.CMap;
import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Component
public class GamumPdfParserImpl implements GamumPdfParser {

    @Override
    public ExtractedDocument extractDocument(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);

        List<ExtractedFragment> fragments = new ArrayList<>();

        for (PDPage page : doc.getPages()) {
            PDFStreamParser parser = new PDFStreamParser(page);
            parser.parse();
            List<Object> tokens = parser.getTokens();
            for (Object token : tokens) {
                if (token instanceof COSString) {
                    handleString((COSString)token, fragments, page);
                } else if (token instanceof COSArray) {
                    handleArray((COSArray)token, fragments, page);
                }
            }
        }

        return ExtractedDocument.builder()
                .fragments(fragments)
                .build();
    }

    private void handleString(COSString stringToken, List<ExtractedFragment> fragments, PDPage page) throws IOException {
        fragments.add(ExtractedTextFragment.builder()
                .text(new UnicodeTextDecoder(page).decode(stringToken))
                .build());
    }

    private void handleArray(COSArray arrayToken, List<ExtractedFragment> fragments, PDPage page) throws IOException {
        for (Object token : arrayToken.toList()) {
            if (token instanceof COSString) {
                handleString((COSString)token, fragments, page);
            }
        }
    }
}
