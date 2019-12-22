package com.vuongideas.gamum.processor.model.fragment;

import lombok.Builder;
import lombok.Data;

import java.awt.image.BufferedImage;

@Data
@Builder
public class ExtractedImageFragment extends ExtractedFragment {
    private BufferedImage imageData;
}
