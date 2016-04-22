package language;

import com.google.inject.Guice;
import com.google.inject.Injector;
import formatters.TextFormatter;
import formatters.TextFormatterFactory;
import globals.GlobalDatas;
import main.AgentFinderModule;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by kadir.basol on 6.4.2016.
 */
public class LanguagesTest {
    @Test
    public void doit() {
        final Locale locale = new Locale("en", "US");
        ResourceBundle bundle1 = ResourceBundle.getBundle("lang/Language",locale,new ResourceBundle.Control() {
            @Override
            public List<Locale> getCandidateLocales(String baseName, Locale locale) {
                return Arrays.asList(
                        locale,
                        // no Locale.CHINESE here
                        Locale.ROOT);
            }
        });
        final String string = bundle1.getString("companyType.Supplier");
//        System.out.println(string);
    }
//    http://stackoverflow.com/questions/32697430/command-for-thermal-printer-in-java
    @Test
    public void ftlTest() throws IOException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final TextFormatterFactory valueInstance = value.getInstance(TextFormatterFactory.class);
        final TextFormatter textureFormatter = valueInstance.createTextureFormatter("/lang/hello.ftl");
        final HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("msg","Kadir");
        final TextFormatterFactory valueInstance2 = value.getInstance(TextFormatterFactory.class);

        final String process = textureFormatter.process(objectObjectHashMap);
//        System.out.println(process);
    }
}
