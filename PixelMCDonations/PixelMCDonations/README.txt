#PixelMC Donations

##Roadmap 
- refactor repositories
- implement perks
- implement dependency injection
- implement subcommands

##setup / commands

All commands start with `pixelmcdonations`

|command   |description|
|----------|--------|
|`server add <name> <description>` | add a server. The Id will then need to be regsitered in the config.yml |
|`server list` | lists the servers |
|`rank add <rank name> <server id> <threshold amount> <command to run>` | registers a new rank|
|`rank list` | lists all ranks |
|`donation add <uuid> <amount>` | adds a donation value to a player
|`claim` | claims the ranks for the user |
