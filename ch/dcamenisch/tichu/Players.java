package ch.dcamenisch.tichu;

import ch.dcamenisch.tichu.Card.Color;
import ch.dcamenisch.tichu.Card.Value;

public class Players {
    private Player[] players;
    private int currentPlayer;

    public Players(int num) {
        this.players = new Player[num];

        for(int i = 0; i < num; i++) {
            players[i] = new PlayerTest("BOT " + i);
        }
    }

    public void determineStartingPlayer() {
        for(int i = 0; i < players.length; i++){
            if(players[i].hasCard(Value.MAHJONG, Color.SPECIAL)) {
                currentPlayer = i;
            }
        }
    }

    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }

    public Player[] getPlayers() {
        return players;
    }




}
