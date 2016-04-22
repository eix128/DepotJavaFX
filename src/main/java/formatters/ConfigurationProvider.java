package formatters;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Locale;

/**
 * Created by kadir.basol on 6.4.2016.
 */

public class ConfigurationProvider implements Provider<Configuration> {

    @Override
    public Configuration get() {
        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
//        configuration = new Configuration(Configuration.VERSION_2_3_23);
        // Specify the source where the template files come from. Here I set a
        // plain directory for it, but non-file-system sources are possible too:
//        try {
//            configuration.setDirectoryForTemplateLoading(FileSystems.getDefault().getPath(".").toFile());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        configuration.setDefaultEncoding("UTF-8");

//        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        FileTemplateLoader fileTemplateLoader = null;
        ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader( ConfigurationProvider.class , "formatters");

        configuration.setTemplateLoader(classTemplateLoader);

        configuration.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
        configuration.setClassForTemplateLoading(this.getClass(), "/");
        Locale trlocale= new Locale("tr", "TR");
        configuration.setEncoding( trlocale , "UTF-8" );

        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        configuration.setLogTemplateExceptions(false);

        return configuration;
    }
}
