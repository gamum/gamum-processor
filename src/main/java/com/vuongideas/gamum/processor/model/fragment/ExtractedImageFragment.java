package com.vuongideas.gamum.processor.model.fragment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExtractedImageFragment extends ExtractedFragment {
    private byte[] imageData;
}
