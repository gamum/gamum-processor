package com.vuongideas.gamum.processor.core;

import com.vuongideas.gamum.processor.model.ExtractedDocument;
import com.vuongideas.gamum.processor.model.fragment.ExtractedFragment;
import com.vuongideas.gamum.processor.model.fragment.ExtractedImageFragment;
import com.vuongideas.gamum.processor.model.fragment.ExtractedTextFragment;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageExtractor extends PDFStreamEngine {

    private List<ExtractedFragment> fragments = new ArrayList<>();
    private PDFont currentFont = null;

    @Override
    protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
        String operation = operator.getName();
        if ("Tj".equals(operation)) {
            System.out.println("show text");

            COSString text = (COSString) operands.get(0);

            if (currentFont != null) {
                StringBuilder builder = new StringBuilder();

                for (byte b : text.getBytes()) {
                    builder.append(currentFont.toUnicode(b));
                }
                fragments.add(ExtractedTextFragment.builder().text(builder.toString()).build());
            } else {
                // attempt to just get the text
                fragments.add(ExtractedTextFragment.builder().text(text.getString()).build());
            }

        } else if ("Tf".equals(operation)) {
            System.out.println("set font");

            // get the name of font
            currentFont = getResources().getFont((COSName) operands.get(0));

        } else if ("Do".equals(operation)) {
            COSName objectName = (COSName) operands.get(0);
            PDXObject xobject = getResources().getXObject(objectName);
            if( xobject instanceof PDImageXObject)
            {
                PDImageXObject image = (PDImageXObject)xobject;
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                // same image to local
                BufferedImage bImage = new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_ARGB);
                bImage = image.getImage();
                fragments.add(ExtractedImageFragment.builder().imageData(bImage).build());

            }
            else if(xobject instanceof PDFormXObject)
            {
                System.out.println("it's a form");
            }
        } else {
            super.processOperator(operator, operands);
        }

    }

    public ExtractedDocument buildDocument() {
        return ExtractedDocument.builder().fragments(fragments).build();
    }
}
