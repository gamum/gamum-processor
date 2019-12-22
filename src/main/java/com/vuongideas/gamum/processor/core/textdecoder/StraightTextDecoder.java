package com.vuongideas.gamum.processor.core.textdecoder;

import org.apache.pdfbox.cos.COSString;

import java.io.IOException;

public class StraightTextDecoder implements TextDecoder {
    @Override
    public String decode(COSString encoded) throws IOException {
        return encoded.getString();
    }
}
