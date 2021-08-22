package gg.cse.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

public class ModelMapperUtil {
    private static ModelMapper modelMapper;

    public static ModelMapper get() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                    .setFieldMatchingEnabled(true);
        }
        return modelMapper;
    }
}
