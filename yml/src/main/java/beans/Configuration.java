package beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * Created by crabime on 10/14/17.
 */
public final class Configuration {
    private Date released;
    private String version;
    private Connection connection;
    private List<String> protocols;
    private Map<String, String> users;

    public Date getReleased() {
        return released;
    }

    public void setReleased(Date released) {
        this.released = released;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<String> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<String> protocols) {
        this.protocols = protocols;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(format("Released: %s\n", released))
                .append(format("Version: %s\n", version))
                .append(format("Connection: %s\n", connection))
                .append(format("Protocols: %s\n", protocols))
                .append(format("Users: %s\n", users))
                .toString();
    }
}
