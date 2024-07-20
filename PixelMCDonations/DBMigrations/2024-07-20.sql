-- Table for storing player information
CREATE TABLE Players (
    PlayerID INT NOT NULL AUTO_INCREMENT,

    PlayerGUID CHAR(16),

    PRIMARY KEY (PlayerID)
);

-- Table for storing donation information
CREATE TABLE Donations (
    DonationID INT NOT NULL AUTO_INCREMENT,
    PlayerID INT,
    Amount DECIMAL(10, 2) NOT NULL,
    DonationDate DATETIME NOT NULL,

    PRIMARY key (DonationID),
    FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID)
);

-- Table for storing server information
CREATE TABLE Servers (
    ServerID INT NOT NULL AUTO_INCREMENT,
    ServerName VARCHAR(255) NOT NULL,
    ServerDescription TEXT,

    PRIMARY KEY (ServerID)
);

-- Table for storing perks information
CREATE TABLE Perks (
    PerkID INT NOT NULL AUTO_INCREMENT,
    ServerID INT,
    PerkDescription TEXT,
    DonationAmount DECIMAL(10, 2),

    PRIMARY KEY (PerkID),
    FOREIGN KEY (ServerID) REFERENCES Servers(ServerID)
);

-- Junction table to manage perks claimed by players on specific servers
CREATE TABLE PlayerServerPerks (
    PlayerID INT,
    ServerID INT,
    PerkID INT,
    Claimed BIT DEFAULT 0,
    ClaimDate DATETIME,

    FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID),
    FOREIGN KEY (ServerID) REFERENCES Servers(ServerID),
    FOREIGN KEY (PerkID) REFERENCES Perks(PerkID),
    PRIMARY KEY (PlayerID, ServerID, PerkID)
);