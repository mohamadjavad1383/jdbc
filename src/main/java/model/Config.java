package model;

public class Config {
    private static Config instance;

    private Config() {
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }
    public String classForName() {
        return "org.postgresql.Driver";
    }

    public String getUsername() {
        return "postgres";
    }
    public String getPassword() {
        return "1274335299";
    }
    public String getUrl() {
        return "jdbc:postgresql://localhost:5432/university";
    }
}
