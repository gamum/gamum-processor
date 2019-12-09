package com.vuongideas.gamum.processor.core;

import com.vuongideas.gamum.processor.model.PdfData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class GamumPdfParserImplTest {

    GamumPdfParser parser;

    File dummy1;
    File greResearchValidityData;
    File pdfInfoAndTestFile;
    File pdfTestWithImage;

    @Before
    public void setUp() throws Exception {
        parser = new GamumPdfParserImpl();

        dummy1 = ResourceUtils.getFile("classpath:dummy.pdf");
        greResearchValidityData = ResourceUtils.getFile("classpath:gre_research_validity_data.pdf");
        pdfInfoAndTestFile = ResourceUtils.getFile("classpath:PDF_INFO_TEST_FILE.pdf");
        pdfTestWithImage = ResourceUtils.getFile("classpath:pdf-test.pdf");
    }

    @Test
    public void extractText() throws IOException {
        String actual = parser.extractText(dummy1);
        assertTrue(actual.contains("Dummy PDF file"));
        assertEquals("Dummy PDF file", actual.trim());
    }

    @Test
    public void extractDataDummy() throws IOException {
        PdfData data = parser.extractData(dummy1);
        assertTrue(data.getContents().contains("Dummy PDF file"));
    }

    @Test
    public void extractDataPdfTest() throws IOException {
        PdfData data = parser.extractData(pdfTestWithImage);
        assertNotNull(data);
    }
}