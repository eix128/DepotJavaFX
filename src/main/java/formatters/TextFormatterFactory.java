package formatters;

import formatters.TextFormatter;

import java.io.IOException;

/**
 * Created by kadir.basol on 6.4.2016.
 */

public interface TextFormatterFactory {
    public TextFormatter createTextureFormatter(String ftlFile) throws IOException;
}
