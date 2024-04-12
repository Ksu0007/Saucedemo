package readProperties;

import core.BaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class PropertiesTest  extends BaseTest {
    @Test
    public void readPropertiesTest() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("application.properties"));
        String urlFromProperties = System.getProperty("url");
        System.out.println(urlFromProperties);
    }

    @Test
    public void readFromConf() {
        String urlFromConfig = ConfigProvider.URL;
        System.out.println(urlFromConfig);

    }
}
