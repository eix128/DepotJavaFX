package kernel.network.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import globals.GlobalDatas;
import globals.interfaces.PostInit;
import gui.FXPanels;
import jpa.DepotsEntity;
import kernel.events.ConnectionException;
import kernel.network.DBManager;
import org.pacesys.reflect.Reflect;
import utils.guava.LazyCache;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Created by kadir.basol on 16.3.2016.
 */
@Singleton
public class DBManagerImpl implements DBManager {

    private EntityManager entityManager;
    private EntityManagerFactory mainData;

    private Properties currentProperties;

    @Inject
    @Named("connectionNTLM")
    private String service;

    @Inject
    @Named("connection")
    private String connection;

    private CriteriaBuilder criteriaBuilder;

    @Inject
    private Injector injector;


    @Inject
    private FXPanels fxPanels;


    private ArrayList<LazyCache<?>> refreshList;

    @Inject
    public DBManagerImpl() {
//        stringMap.put("javax.persistence.jdbc.user",jdbcUrl);
//        stringMap.put("javax.persistence.jdbc.password",jdbcUrl);
        refreshList = new ArrayList<LazyCache<?>>();
    }


    @Override
    public boolean connect(@NotNull String userName, @NotNull String password) throws ConnectionException {
        try {
            Properties stringMap = new Properties();
            stringMap.put("javax.persistence.jdbc.url", connection);
            stringMap.put("javax.persistence.jdbc.user", userName);
            stringMap.put("javax.persistence.jdbc.password", password);
            mainData = Persistence.createEntityManagerFactory("MainData", stringMap);
            entityManager = mainData.createEntityManager();
            currentProperties = stringMap;

            this.criteriaBuilder = mainData.getCriteriaBuilder();


            Set<ManagedType<?>> managedTypes = mainData.getMetamodel().getManagedTypes();

            managedTypes.forEach(managedType -> {
                        Class<?> javaType = managedType.getJavaType();
                        try {
                            Class<?> aClass = javaType;
                            Method[] methods = aClass.getMethods();
                            for (Method method : methods) {
                                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                                for (Annotation declaredAnnotation : declaredAnnotations) {
                                    if (declaredAnnotation.annotationType() == PostInit.class) {
                                        method.invoke(null);
                                    }
                                }
                            }

                        } catch (Exception e) {
//                                e.printStackTrace();
                        }
//                    List<Method> methods = Reflect.on(javaType).methods().annotatedWith(PostConstruct.class);
//                    for (Method method : methods) {
//                        try {
//                            method.invoke(javaType,null);
//                        } catch (Throwable e) {
//                            e.printStackTrace();
//                        }
//                    }
                    }
            );
            fxPanels.onNetworkAccess();
        } catch (Throwable e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean updateOrCreateObject(Object object, String namedQuery) {
        return false;
    }

    public static List<String> getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();
        List<String> parameterNames = new ArrayList<>();

        for (Parameter parameter : parameters) {
            if (!parameter.isNamePresent()) {
                throw new IllegalArgumentException("Parameter names are not present!");
            }

            String parameterName = parameter.getName();
            parameterNames.add(parameterName);
        }

        return parameterNames;
    }

    @Override
    public boolean connectNTLM() throws ConnectionException {
//        final String value = connectionNTLM.value();
        Properties stringMap = new Properties();
//        try {
//            stringMap.put("javax.persistence.jdbc.url", service);
//            mainData = Persistence.createEntityManagerFactory("MainData", stringMap);
//            entityManager = mainData.createEntityManager();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        stringMap.put("javax.persistence.jdbc.url", service);
//        try {
//            mainData = Persistence.createEntityManagerFactory("MainData", stringMap);
//            entityManager = mainData.createEntityManager();
//            this.criteriaBuilder = mainData.getCriteriaBuilder();
//            currentProperties = stringMap;
//        } catch (Exception e) {
//            System.out.println(e);
//            e.printStackTrace();
//        }

        mainData = Persistence.createEntityManagerFactory("MainData", stringMap);
        entityManager = mainData.createEntityManager();
        this.criteriaBuilder = mainData.getCriteriaBuilder();
        currentProperties = stringMap;

        Set<ManagedType<?>> managedTypes = mainData.getMetamodel().getManagedTypes();
        final Method[] lastMethod = new Method[1];
        managedTypes.forEach(managedType -> {
                    Class<?> javaType = managedType.getJavaType();
                    try {
                        Class<?> aClass = javaType;
                        Method[] methods = aClass.getMethods();
                        for (Method method : methods) {
                            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                            for (Annotation declaredAnnotation : declaredAnnotations) {
                                if (declaredAnnotation.annotationType() == PostInit.class) {
                                    lastMethod[0] = method;
                                    if (method != null)
                                        method.invoke(null);
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (lastMethod[0] != null) {
//                            System.out.println(lastMethod[0].getDeclaringClass());
                        }
                        e.printStackTrace();
                    }
//                    List<Method> methods = Reflect.on(javaType).methods().annotatedWith(PostConstruct.class);
//                    for (Method method : methods) {
//                        try {
//                            method.invoke(javaType,null);
//                        } catch (Throwable e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
        );


//        List<Method> methods = Reflect.on(someClass).methods().annotatedWith(PostConstruct.class);
//        for (Method m : methods) {
//            m.invoke(null);
//        }
        fxPanels.onNetworkAccess();
        return true;
    }

    @Override
    public boolean disconnect() {
        try {
            if (entityManager != null) {
                try {
                    entityManager.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mainData != null) {
                mainData.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    @Override
    public EntityManager get() {
        if (entityManager != null && entityManager.isOpen())
            return entityManager;
        else
            return mainData.createEntityManager();
    }

    @Override
    public EntityManagerFactory getEMF() {
        return mainData;
    }

    @Override
    public EntityManager getNew() {
        return mainData.createEntityManager();
    }

    @Override
    public final void refreshDB() {
        for (LazyCache<?> lazyCache : refreshList) {
            lazyCache.invalidate();
            lazyCache.get();
        }
    }

    @Override
    public void addRefresh(LazyCache<?> lazyCache) {
        if (lazyCache != null)
            refreshList.add(lazyCache);
    }
}
