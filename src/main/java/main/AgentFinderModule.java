package main;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.name.Names;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import formatters.ConfigurationProvider;
import formatters.TextFormatter;
import formatters.TextFormatterFactory;
import formatters.impl.TextFormatterFactoryImpl;
import formatters.impl.TextFormatterImpl;
import freemarker.template.Configuration;
import globals.impl.GHashMapImpl;
import globals.interfaces.GHashMap;
import gui.FXPanels;
import gui.controller.MainPanelController;
import gui.controller.ProductInputController;
import gui.impl.FXPanelsImpl;
import gui.jpa.ProcessItem;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.BuilderFactory;
import jpa.converters.enums.ProcessType;
import kernel.actors.SQLServices;
import kernel.actors.impl.SQLServicesImpl;
import kernel.network.DBManager;
import kernel.network.impl.DBManagerImpl;
import kernel.actors.DepotManager;
import kernel.actors.impl.DepotManagerImpl;
import kernel.threads.ThreadManager;
import kernel.threads.impl.ThreadManagerImpl;
import kernel.threads.network.NetworkThread;
import kernel.threads.network.impl.NetworkThreadImpl;
import language.LangUtils;
import language.UTF8Control;
import language.impl.LangUtilsImpl;
import main.gui.ComboList;
import main.gui.impl.ComboListImpl;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by kadir.basol on 15.3.2016.
 */
public class AgentFinderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DBManager.class).to(DBManagerImpl.class);
        bind(DepotManager.class).to(DepotManagerImpl.class);
        bind(SQLServices.class).to(SQLServicesImpl.class);
        bind(BuilderFactory.class).to(JavaFXBuilderFactory.class);
        bind(ThreadManager.class).to(ThreadManagerImpl.class);
        bind(MainApp.class).to(MainAppImpl.class);
        bind(NetworkThread.class).to(NetworkThreadImpl.class);
        bind(ComboList.class).to(ComboListImpl.class);
        bind(GHashMap.class).to(GHashMapImpl.class);
/*
*             @Override
            public List<Locale> getCandidateLocales(String name,
                                                    Locale locale) {
                return Collections.singletonList(Locale.ROOT);
            }
        }
* */
        Locale locale = Locale.getDefault();

        final ResourceBundle bundle = ResourceBundle.getBundle("lang/Language", locale , new UTF8Control());
        bind(ResourceBundle.class).toInstance(bundle);

//        install(new FactoryModuleBuilder()
//                .implement(TextFormatter.class, TextFormatterImpl.class)
//                .build(TextFormatterFactory.class));

//        bind(String.class).annotatedWith(Names.named("connection")).toInstance("jdbc:jtds:sqlserver://localhost:1433/jdepo;instance=SQLEXPRESS;useUnicode=true;characterEncoding=UTF-8");
        bind(String.class).annotatedWith(Names.named("connection")).toInstance("jdbc:jtds:sqlserver://deposrv:1433/jdepo;");
//        bind(String.class).annotatedWith(Names.named("connection")).toInstance("jdbc:jtds:sqlserver://localhost:1433/jdepo;");

        bind(Configuration.class).toProvider( ConfigurationProvider.class ).in(Singleton.class);
        bind(TextFormatterFactory.class).to(TextFormatterFactoryImpl.class);
        bind(LangUtils.class).to(LangUtilsImpl.class);
        bind(ProcessItem.class);
//        bind(ProcessType.class).toInstance( ProcessType.INPUT );

//bind(AutoPlayerTask.class).in(Scopes.SINGLETON);

        // Binds our resource bundle that contains localized Strings
//        bind(ResourceBundle.class).annotatedWith(Names.named("i18n-resources"))
//                .toInstance(ResourceBundle.getBundle( "" ));
//
////        ClassLoader loader = new URLClassLoader(urls);
//        Locale locale = new Locale("en", "US");
//        final ResourceBundle labels = ResourceBundle.getBundle("i18n.MyBundle", locale );




//        bind(String.class).annotatedWith(Names.named("connectionNTLM")).toInstance("jdbc:sqlserver://deposrv:1433;databaseName=jdepo;integratedSecurity=true;");
//        bind(String.class).annotatedWith(Names.named("connectionNTLM")).toInstance("jdbc:sqlserver://KADIRBWIN81:1433;databaseName=jdepo;integratedSecurity=true;");
//        bind(String.class).annotatedWith(Names.named("connectionNTLM")).toInstance("jdbc:jtds:sqlserver://localhost:1433/jdepo;user=sa;password=protect32");
//        bind(String.class).annotatedWith(Names.named("connectionNTLM")).toInstance("jdbc:jtds:sqlserver://localhost:1433/jdepo;useNTLMv2=true;domain=SQLEXPRESS");


//        bind(String.class).annotatedWith(Names.named("connectionNTLM")).toInstance("jdbc:jtds:sqlserver://localhost:1433/jdepo;useNTLMv2=true");
        bind(String.class).annotatedWith(Names.named("connectionNTLM")).toInstance("jdbc:jtds:sqlserver://deposrv:1433/jdepo;useNTLMv2=true");
//        bind(String.class).annotatedWith(Names.named("connectionNTLM")).toInstance("jdbc:jtds:sqlserver://localhost:1433/jdepo;useNTLMv2=true;instance=SQLEXPRESS;useUnicode=true;characterEncoding=UTF-8");

//        bind(String.class).annotatedWith(Names.named("connectionNTLM")).toInstance("jdbc:jtds:sqlserver://deposrv:1433/jdepo;user=sa;password=aaaa");

        bind(BorderPane.class).annotatedWith(Names.named("MainBorder")).toInstance(new BorderPane());
        bind(FXPanels.class).to(FXPanelsImpl.class);
        
//        bindListener(HasInitMethod.INSTANCE, new TypeListener() {
//            public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
//                encounter.register(InitInvoker.INSTANCE);
//            }
//        });

/*        Properties configProps = Properties.load(getClass().getClassLoader().getResourceAsStream("myconfig.properties");
        Names.bindProperties(binder(), configProps);*/
    }

    static class HasInitMethod extends AbstractMatcher<TypeLiteral<?>> {
        public boolean matches(TypeLiteral<?> tpe) {
            try {
                Annotation[] annotations = tpe.getRawType().getAnnotations();
                return annotations != null && annotations.length > 0;
//                return tpe.getRawType().getMethod("init") != null;
            } catch (Exception e) {
                return false;
            }
        }

        public static final HasInitMethod INSTANCE = new HasInitMethod();
    }


    static class InitInvoker implements InjectionListener {
        public void afterInjection(Object injectee) {
            try {
                Annotation[] annotations = injectee.getClass().getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() == PostConstruct.class) {
//                        System.out.println(injectee);
                    }
                }
//                injectee.getClass().getMethod("init").invoke(injectee);
            } catch (Exception e) {
        /* do something to handle errors here */
            }
        }

        public static final InitInvoker INSTANCE = new InitInvoker();
    }

}
