package com.vuongideas.gamum.processor.core;

import com.vuongideas.gamum.processor.model.PdfData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface GamumPdfParser {
    String extractText(File file) throws IOException;
    PdfData extractData(File file) throws IOException;
}
