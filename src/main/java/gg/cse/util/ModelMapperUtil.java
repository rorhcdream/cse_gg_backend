package gg.cse.util;

import org.modelmapper.ModelMapper;

public class ModelMapperUtil {
    private static ModelMapper modelMapper;

    public static ModelMapper get() {
        if (modelMapper == null)
            modelMapper = new ModelMapper();
        return modelMapper;
    }
}
