package co.pixelmc.pixelmcdonations.models;

public class Rank {

    private int rankId;
    private String rankName;
    private double donationThreshold;
    private int serverId;
    private String command;

    public Rank(int id, String name, double threshold, int serverId, String command){
        rankId = id;
        rankName = name;
        donationThreshold = threshold;
        this.serverId = serverId;
        this.command = command;
    }

    public double getDonationThreshold() {
        return donationThreshold;
    }

    public String getRankName() {
        return rankName;
    }

    public int getRankId() {
        return rankId;
    }

    public int getServerId() {
        return serverId;
    }

    public String getCommand() {
        return command;
    }
}
