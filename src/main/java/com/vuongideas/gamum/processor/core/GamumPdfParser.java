package com.vuongideas.gamum.processor.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface GamumPdfParser {
    String extractText(File file) throws IOException;
}
