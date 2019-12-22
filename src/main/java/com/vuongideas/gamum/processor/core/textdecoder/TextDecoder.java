package com.vuongideas.gamum.processor.core.textdecoder;

import org.apache.pdfbox.cos.COSString;

import java.io.IOException;

public interface TextDecoder {
    String decode(COSString encoded) throws IOException;
}
