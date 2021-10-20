import java.util.ArrayList;
import java.util.Random;


public class Player {
    int playerID;
    int[] pebbles = new int[10];
    int weight;
    Random rand = new Random();


    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int[] getPebbles() {
        return pebbles;
    }

    public void setPebbles(int[] pebbles) {
        this.pebbles = pebbles;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Player(int playerID) {
        this.playerID = playerID;
    }


    public int discardPebble(int replacementPebble) {
        int index = rand.nextInt(this.getPebbles().length);
        int pebbleWeight = this.getPebbles()[index];
        this.getPebbles()[index] = replacementPebble;
        return pebbleWeight;
    }


}
