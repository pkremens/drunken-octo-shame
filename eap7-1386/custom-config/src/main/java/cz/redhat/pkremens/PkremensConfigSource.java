package cz.redhat.pkremens;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://ftp.fau.de/eclipse/microprofile/microprofile-config-1.3/microprofile-config-spec.pdf
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class PkremensConfigSource implements ConfigSource {
    @Override
    public int getOrdinal() {
        return 112;
    }

    @Override
    public Set<String> getPropertyNames() {
        return new HashSet<>();
    }

    @Override
    public Map<String, String> getProperties() {
        return new HashMap<>();
    }

    @Override
    public String getValue(String key) {
        return null;
    }

    @Override
    public String getName() {
        return "pkremensConfig";
    }
}
