package co.pixelmc.datastorage;

import com.electronwill.nightconfig.core.conversion.ConversionTable;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.FileConfigBuilder;

public class ConfigProvider {
    private final String BASE_PATH;

    public ConfigProvider(String basePath){
        if (!basePath.endsWith("/"))
            basePath += "/";
        BASE_PATH = basePath;
    }

    public FileConfig provide(String fileName, String defaultResourcePath){
        return provide(fileName, defaultResourcePath, null);
    }

    public FileConfig provide(String fileName, String defaultResourcePath, ConversionTable conversionTable){
        FileConfigBuilder configBuilder = FileConfig
                .builder(BASE_PATH + fileName);
        FileConfig config = configBuilder.defaultResource(defaultResourcePath)
                .autosave()
                .autoreload()
                .build();

        config.load();

        if (conversionTable != null)
            return conversionTable.wrapRead(config);
        return config;
    }

    public FileConfig provide(String existingFilename){
        FileConfig config = FileConfig
                .builder(BASE_PATH + existingFilename)
                .autosave()
                .autoreload()
                .build();

        config.load();
        return config;
    }
}
