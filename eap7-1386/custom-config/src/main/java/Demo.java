import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.stream.Collectors;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println("test");
        Config config = ConfigProvider.getConfig();
        config.getOptionalValue("Test", String.class).orElse("test");

        ConfigProvider.getConfig().getConfigSources().forEach(configSource -> {
            System.out.println("=================================");
            System.out.println(configSource.getName());
            System.out.println(configSource.getOrdinal());
            System.out.println("Property names: " + configSource.getPropertyNames().stream().collect(Collectors.joining(", ")));
        });
        System.out.println("-------------------------------------");
    }
}
