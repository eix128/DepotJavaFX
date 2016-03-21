package db;

import com.google.inject.Guice;
import com.google.inject.Injector;
import kernel.actors.DepotManager;
import kernel.events.ConnectionException;
import kernel.network.DBManager;
import main.AgentFinderModule;
import main.DepoMain;
import org.junit.Assert;
import org.junit.Test;
import ru.concerteza.util.jni.CtzJniUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by kadir.basol on 17.3.2016.
 */
public class DBTest {

    @Test
    public void denemeNTLM() throws ConnectionException {
        String canonicalPath = null;
        final Path path = FileSystems.getDefault().getPath(".");
        try {
            canonicalPath = path.toFile().getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        CtzJniUtils.loadJniLibsFromStandardPath(canonicalPath , "sqljdbc_auth");
        final Injector value = Guice.createInjector(new AgentFinderModule());
        final DBManager dbManager = value.getInstance(DBManager.class);
        final boolean b = dbManager.connectNTLM();
        Assert.assertEquals(b, true);
    }

    @Test
    public void denemePassword() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        final DBManager dbManager = value.getInstance(DBManager.class);
        final boolean sa = dbManager.connect("sa", "Depo@2015");
        Assert.assertEquals(sa, true);
    }


    @Test
    public void denemePassword2() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        final DBManager dbManager = value.getInstance(DBManager.class);
        final boolean sa = dbManager.connect("sa", "aaaa");
        Assert.assertEquals(sa, false);
    }


    @Test
    public void testDBTable() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        final DBManager dbManager = value.getInstance(DBManager.class);
        final DepotManager depotManager = value.getInstance(DepotManager.class);
        final boolean sa = dbManager.connectNTLM();
        final String userName = depotManager.getUserName();
        final boolean ozıdas = userName.contains("OZIDAS");
        Assert.assertEquals(ozıdas, true);
    }
//
}
