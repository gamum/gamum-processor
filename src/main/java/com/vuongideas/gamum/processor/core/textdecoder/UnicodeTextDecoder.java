package com.vuongideas.gamum.processor.core.textdecoder;

import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class UnicodeTextDecoder implements TextDecoder {

    private PDPage page;

    public UnicodeTextDecoder(PDPage page) {
        this.page = page;
    }

    @Override
    public String decode(COSString encoded) throws IOException {
        // get fonts
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

        StringBuilder builder = new StringBuilder();

        for (byte b : encoded.getBytes()) {
            builder.append(font.toUnicode(b));
        }

        return builder.toString();

    }
}
