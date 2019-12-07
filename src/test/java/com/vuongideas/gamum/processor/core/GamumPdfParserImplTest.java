package com.vuongideas.gamum.processor.core;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class GamumPdfParserImplTest {

    File dummy1;

    @Before
    public void setUp() throws Exception {
        dummy1 = ResourceUtils.getFile("classpath:dummy.pdf");
    }

    @Test
    public void extractText() throws IOException {
        GamumPdfParser parser = new GamumPdfParserImpl();
        String actual = parser.extractText(dummy1);
        assertTrue(actual.contains("Dummy PDF file"));
        assertEquals("Dummy PDF file", actual.trim());
    }
}