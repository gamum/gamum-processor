package com.vuongideas.gamum.processor.model;

import com.vuongideas.gamum.processor.model.fragment.ExtractedFragment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExtractedDocument {
    private List<ExtractedFragment> fragments;
}
