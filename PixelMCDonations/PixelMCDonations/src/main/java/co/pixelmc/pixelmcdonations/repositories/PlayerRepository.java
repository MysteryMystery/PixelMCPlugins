package co.pixelmc.pixelmcdonations.repositories;

import co.pixelmc.pixelmcdonations.models.Player;
import co.pixelmc.pixelmcdonations.models.Rank;
import co.pixelmc.pixelmcdonations.models.config.DatabaseOptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerRepository {
    private DatabaseOptions _databaseOptions;

    public PlayerRepository(DatabaseOptions databaseOptions){
        _databaseOptions = databaseOptions;
    }

    public boolean addPlayer(UUID uuid){
        String query = "INSERT INTO players (PlayerGuid) VALUES (?)";

        try(
                Connection conn = DriverManager.getConnection(
                        _databaseOptions.getConnectionString(),
                        _databaseOptions.getUserName(),
                        _databaseOptions.getPassword()
                );
                PreparedStatement statement = conn.prepareStatement(query);
        ) {

            statement.setString(1, uuid.toString());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Player> getPlayer(UUID uuid){
        String query = "SELECT PlayerId, PlayerGUID from players where PlayerGUID = ?";
        ArrayList<Player> result = new ArrayList<>();

        try(
                Connection conn = DriverManager.getConnection(
                        _databaseOptions.getConnectionString(),
                        _databaseOptions.getUserName(),
                        _databaseOptions.getPassword()
                );
                PreparedStatement statement = conn.prepareStatement(query)
        ) {
            statement.setString(1, uuid.toString());

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Player player = new Player(
                        rs.getInt(1),
                        UUID.fromString(rs.getString(2))
                );
                result.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
