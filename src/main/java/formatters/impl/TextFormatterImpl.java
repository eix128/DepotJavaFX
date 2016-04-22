package formatters.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;
import formatters.TextFormatter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.*;

/**
 * Created by kadir.basol on 6.4.2016.
 */
public class TextFormatterImpl implements TextFormatter {

    @Inject
    private ResourceBundle resourceBundle;

    private final Configuration configuration;
    private final Template template;
    private final String   ftlPath;

    @Inject
    public TextFormatterImpl(@Assisted Configuration configuration
            , String ftlPath, @Assisted Template template) {
        this.configuration = configuration;
        this.ftlPath = ftlPath;
        this.template = template;
    }

    @Inject
    public String process(HashMap<String, String> hashMap) {
        Writer writer = null;
        try {
            writer = new StringWriter();
            template.process(hashMap, writer);
            writer.flush();
            writer.close();
            return writer.toString();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
