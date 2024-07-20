package co.pixelmc.pixelmcdonations.repositories;

import co.pixelmc.pixelmcdonations.models.Rank;
import co.pixelmc.pixelmcdonations.models.config.DatabaseOptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RankRepository {
    private DatabaseOptions _databaseOptions;

    public RankRepository(DatabaseOptions databaseOptions){
        _databaseOptions = databaseOptions;
    }

    public boolean addRank(String rankName, int serverId, double threshold, String command){
        String query = "INSERT INTO ranks (RankName, ServerId, DonationThreshold, Command) VALUES (?, ?, ?, ?)";

        try(
                Connection conn = DriverManager.getConnection(
                        _databaseOptions.getConnectionString(),
                        _databaseOptions.getUserName(),
                        _databaseOptions.getPassword()
                );
                PreparedStatement statement = conn.prepareStatement(query);
        ) {

            statement.setString(1, rankName);
            statement.setInt(2, serverId);
            statement.setDouble(3, threshold);
            statement.setString(4, command);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Rank> getRanks(){
        String query = "SELECT RankId, RankName, DonationThreshold, ServerId, Command FROM ranks";

        ArrayList<Rank> result = new ArrayList<>();

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
                Rank server = new Rank(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getString(5));
                result.add(server);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
