package co.pixelmc.pixelmcdonations.repositories;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.models.Player;
import co.pixelmc.pixelmcdonations.models.Rank;
import co.pixelmc.pixelmcdonations.models.config.DatabaseOptions;
import org.apache.commons.lang.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DonationRepository {
    private DatabaseOptions _databaseOptions;

    public DonationRepository(DatabaseOptions databaseOptions){
        _databaseOptions = databaseOptions;
    }

    public boolean addDonation(int playerId, double amount){
        String query = "INSERT INTO donations (PlayerID, Amount, DonationDate) VALUES (?, ?, ?)";

        try(
                Connection conn = DriverManager.getConnection(
                        _databaseOptions.getConnectionString(),
                        _databaseOptions.getUserName(),
                        _databaseOptions.getPassword()
                );
                PreparedStatement statement = conn.prepareStatement(query);
        ) {

            statement.setInt(1, playerId);
            statement.setDouble(2, amount);
            statement.setDate(3, new Date(new java.util.Date().getTime()));

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public double getDonationSum(Player player){
        String query = "SELECT SUM(donations.amount) AS amount\n" +
                "from players \n" +
                "INNER JOIN donations ON donations.PlayerID = players.PlayerID \n" +
                "where PlayerGUID = ?";

        try(
                Connection conn = DriverManager.getConnection(
                        _databaseOptions.getConnectionString(),
                        _databaseOptions.getUserName(),
                        _databaseOptions.getPassword()
                );
                PreparedStatement statement = conn.prepareStatement(query)
        ) {
            statement.setString(1, player.getPlayerUuid().toString());

            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Rank> claimAvailableRanks(Player player){
        int serverId = PixelMCDonations.getInstance().getServerId();
        double amountDonated = getDonationSum(player);

        List<Rank> ranks = new RankRepository(_databaseOptions)
                .getUnclaimedRanks(serverId, player.getPlayerId())
                .stream()
                .filter(x -> x.getServerId() == serverId)
                .filter(x -> x.getDonationThreshold() <= amountDonated)
                .toList();

        String query = "INSERT INTO playerserverranks (PlayerID, RankID, Claimed, ClaimDate) VALUES (?, ?, ?, ?)";

        for (Rank rank : ranks) {
            try(
                    Connection conn = DriverManager.getConnection(
                            _databaseOptions.getConnectionString(),
                            _databaseOptions.getUserName(),
                            _databaseOptions.getPassword()
                    );
                    PreparedStatement statement = conn.prepareStatement(query);
            ) {
                statement.setInt(1, player.getPlayerId());
                statement.setInt(2, rank.getRankId());
                statement.setBoolean(3, true);
                statement.setDate(4, new Date(new java.util.Date().getTime()));

                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ranks;
    }
}
