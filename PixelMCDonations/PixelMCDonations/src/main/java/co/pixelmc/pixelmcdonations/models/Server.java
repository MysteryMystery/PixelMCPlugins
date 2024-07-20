package co.pixelmc.pixelmcdonations.models;

public class Server {
    private int Id;
    private String ServerName;
    private String ServerDescription;

    public Server(int id, String serverName, String serverDescription){
        Id = id;
        ServerName = serverName;
        ServerDescription = serverDescription;
    }

    public String getServerDescription() {
        return ServerDescription;
    }

    public void setServerDescription(String serverDescription) {
        ServerDescription = serverDescription;
    }

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
