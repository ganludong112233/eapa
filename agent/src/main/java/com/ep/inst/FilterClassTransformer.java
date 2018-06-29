package com.ep.inst;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import com.ep.config.Configuration;

import javassist.ClassPool;

/**
 * filtered class transformer
 * 
 * @author yi_liu
 * 
 */
public abstract class FilterClassTransformer implements ClassFileTransformer {

    static Map<String, Object> loadedUrls = new Hashtable<String, Object>();
    static Set<ClassLoader> loadedClassLoaders = new HashSet<ClassLoader>();
    public ClassPool pool = ClassPool.getDefault();
    public String[] defaultFilterUrls = new String[] {"sun/launcher", "sun/net", "sun/util",
            "sun/management", "sun/misc", "sun/reflect", "com/sun", "$Proxy", "java/lang",
            "com/sun/proxy", "CGLIB$$", "com/ep"};

    public abstract byte[] doTransform(ClassLoader loader, String className,
            Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {

        if (Configuration.isDebug()) {
            System.out.println("DEBUG:EAPA:" + "class:" + className + ":Loader:" + loader);
        }

        if (loader != null && !loadedClassLoaders.contains(loader)) {
            loadedClassLoaders.add(loader);

            if (loader != null && loader instanceof URLClassLoader) {
                URLClassLoader urlCl = (URLClassLoader) loader;
                while (urlCl != null) {
                    for (URL url : urlCl.getURLs()) {
                        if (!loadedUrls.containsKey(url.getPath())) {
                            try {
                                loadedUrls.put(url.getPath(), "");
                                pool.insertClassPath(url.getPath());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        // if the parent class loader is not URLClassloader, ignore the exception
                        urlCl = (URLClassLoader) urlCl.getParent();
                    } catch (Exception e) {}
                }
            }
        }

        for (String url : defaultFilterUrls) {
            if (className.contains(url)) {
                // avoid to re-transform the cglib proxy class
                return classfileBuffer;
            }
        }

        return doTransform(loader, className, classBeingRedefined, protectionDomain,
                classfileBuffer);
    }
}
