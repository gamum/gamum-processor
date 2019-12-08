package com.vuongideas.gamum.processor.core;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class GamumPdfParserImplTest {

    File dummy1;
    File greResearchValidityData;
    File pdfInfoAndTestFile;

    @Before
    public void setUp() throws Exception {
        dummy1 = ResourceUtils.getFile("classpath:dummy.pdf");
        greResearchValidityData = ResourceUtils.getFile("classpath:gre_research_validity_data.pdf");
        pdfInfoAndTestFile = ResourceUtils.getFile("classpath:PDF_INFO_TEST_FILE.pdf");
    }

    @Test
    public void extractText() throws IOException {
        GamumPdfParser parser = new GamumPdfParserImpl();
        String actual = parser.extractText(dummy1);
        assertTrue(actual.contains("Dummy PDF file"));
        assertEquals("Dummy PDF file", actual.trim());
    }
}