package co.pixelmc.pixelmcdonations.models.config;

public class DatabaseOptions {
    private String _connectionString;
    private String _userName;
    private String _password;

    public DatabaseOptions(String connectionString, String username, String password){
        _connectionString = connectionString;
        _userName = username;
        _password = password;
    }

    public String getConnectionString(){
        return _connectionString;
    }

    public String getPassword() {
        return _password;
    }

    public String getUserName() {
        return _userName;
    }
}
