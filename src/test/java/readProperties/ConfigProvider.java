package readProperties;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ConfigProvider {
    Config config = readConfig();

    static Config readConfig(){
        return ConfigFactory.systemProperties().hasPath("testProfile")
                ? ConfigFactory.load(ConfigFactory.systemProperties().getString("testProfile"))
                : ConfigFactory.load("application.conf");
    }

    String URL = readConfig().getString("url");
    String STANDARD_LOGIN = readConfig().getString("userParams.standard.login");
    String STANDARD_PASSWORD = readConfig().getString("userParams.standard.password");
    String PROBLEM_USER_LOGIN = readConfig().getString("userParams.problem.login");
    String PROBLEM_USER_PASSWORD = readConfig().getString("userParams.problem.password");
    String USER_FIRST_NAME = readConfig().getString("userParams.standard.firstName");
    String USER_LAST_NAME = readConfig().getString("userParams.standard.lastName");
    String USER_ZIP = readConfig().getString("userParams.standard.zipCode");


}
