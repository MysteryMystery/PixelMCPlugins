package co.pixelmc.pixelmcdonations.repositories;

import co.pixelmc.pixelmcdonations.models.Server;
import co.pixelmc.pixelmcdonations.models.config.DatabaseOptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerRepository {
    private DatabaseOptions _databaseOptions;

    public ServerRepository(DatabaseOptions databaseOptions){
        _databaseOptions = databaseOptions;
    }

    public List<Server> GetServers(){
        String query = "SELECT ServerId, ServerName, ServerDescription FROM servers";
        ArrayList<Server> result = new ArrayList<>();

        try(
            Connection conn = DriverManager.getConnection(
                    _databaseOptions.getConnectionString(),
                    _databaseOptions.getUserName(),
                    _databaseOptions.getPassword()
            );
            Statement statement = conn.createStatement()
        ) {
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                Server server = new Server(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                result.add(server);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean AddServer(String serverName, String serverDescription){
        String query = "INSERT INTO servers (ServerName, ServerDescription) VALUES (?, ?)";

        try(
            Connection conn = DriverManager.getConnection(
                _databaseOptions.getConnectionString(),
                _databaseOptions.getUserName(),
                _databaseOptions.getPassword()
            );
            PreparedStatement statement = conn.prepareStatement(query);
        ) {

            statement.setString(1, serverName);
            statement.setString(2, serverDescription);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
