package com.vuongideas.gamum.processor.core;

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

        StringBuilder contentsBuilder = new StringBuilder();

        List<ExtractedFragment> fragments = new ArrayList<>();

        for (PDPage page : doc.getPages()) {

            PDFont font = StreamSupport.stream(page.getResources().getFontNames().spliterator(), false)
                    .map(fn -> {
                        try {
                            return page.getResources().getFont(fn);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElseThrow(IOException::new);

            PDFStreamParser parser = new PDFStreamParser(page);
            parser.parse();
            List<Object> tokens = parser.getTokens();
            for (Object token : tokens) {
                if (token instanceof COSString) {
                    for (byte b : ((COSString)token).getBytes()) {
                        contentsBuilder.append(font.toUnicode(b));
                    }
                    fragments.add(ExtractedTextFragment.builder()
                            .text(contentsBuilder.toString())
                            .build());
                    contentsBuilder.setLength(0);
                }
            }
        }

        return ExtractedDocument.builder()
                .fragments(fragments)
                .build();
    }
}
