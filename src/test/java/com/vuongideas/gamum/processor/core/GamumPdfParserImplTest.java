package com.vuongideas.gamum.processor.core;

import com.vuongideas.gamum.processor.model.ExtractedDocument;
import com.vuongideas.gamum.processor.model.PdfData;
import com.vuongideas.gamum.processor.model.fragment.ExtractedTextFragment;
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
    public void extractDataDummy() throws IOException {
        ExtractedDocument document = parser.extractDocument(dummy1);

        String text = document.getFragments().stream()
                .filter(f -> f instanceof ExtractedTextFragment)
                .map(f -> ((ExtractedTextFragment) f).getText())
                .reduce("", (a, b) -> a + b);

        System.out.println(text);

        assertTrue(text.contains("Dummy PDF file"));
    }

    @Test
    public void extractDataPdfTest() throws IOException {
        ExtractedDocument document = parser.extractDocument(pdfTestWithImage);
        assertNotNull(document);
    }
}