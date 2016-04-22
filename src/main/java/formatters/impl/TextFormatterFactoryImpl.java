package formatters.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import formatters.TextFormatter;
import formatters.TextFormatterFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

/**
 * Created by kadir.basol on 6.4.2016.
 */
public class TextFormatterFactoryImpl implements TextFormatterFactory {

    private final Provider<Configuration> configuration;

//    private final Provider<Template>      templateProvider;

    @Inject
    public TextFormatterFactoryImpl(Provider<Configuration> cfg) {
        this.configuration = cfg;
    }

    @Override
    public TextFormatter createTextureFormatter(String ftlFile) throws IOException {
        final TextFormatterImpl textFormatter;
        final Configuration configuration = this.configuration.get();
        Template template = this.configuration.get().getTemplate(ftlFile);
        textFormatter = new TextFormatterImpl(configuration, ftlFile, template);
        return textFormatter;

    }
}
