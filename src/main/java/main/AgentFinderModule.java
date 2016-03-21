package main;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import kernel.network.DBManager;
import kernel.network.impl.DBManagerImpl;
import kernel.actors.DepotManager;
import kernel.actors.impl.DepotManagerImpl;

/**
 * Created by kadir.basol on 15.3.2016.
 */
public class AgentFinderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DBManager.class).to(DBManagerImpl.class);
        bind(DepotManager.class).to(DepotManagerImpl.class);
        bind(String.class).annotatedWith(Names.named("connection")).toInstance("jdbc:sqlserver://deposrv:1433;databaseName=jdepo;");
        bind(String.class).annotatedWith(Names.named("connectionNTLM")).toInstance("jdbc:sqlserver://deposrv:1433;databaseName=jdepo;integratedSecurity=true;");
//        Names.bindProperties(binder(),);

    }
}
