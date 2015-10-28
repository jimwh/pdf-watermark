package lab;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;

@Component
public class CustomerResourceLoader implements ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public InputStream getInputStream(String location) throws IOException {

        if(location==null) return null;
        if( !location.contains(ResourceLoader.CLASSPATH_URL_PREFIX) ) {
            location = ResourceLoader.CLASSPATH_URL_PREFIX + location;
        }
        Resource resource = resourceLoader.getResource(location);
            return resource!=null ? resource.getInputStream() : null;
        }
}
