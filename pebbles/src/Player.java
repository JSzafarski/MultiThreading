import java.util.Random;

public class Player implements Runnable {
    int  playerID;
    int[] pebbles = new int[10];
    Random rand = new Random();
    int Weight;

    public int getPlayerID() {
        return playerID;
    }

    public int[] getPebbles() {
        return pebbles;
    }

    public void setPebbles(int[] pebbles) {
        this.pebbles = pebbles;
    }//

    public void initialWeight(){
        int i = 1;
        while(i <=pebbles.length){
            Weight=Weight+pebbles[i];
            i++;
        }
    }

    public void UpdateWeight(int PebbleWeight ,boolean AddRemove){//true = add,false = remove
        if(!AddRemove){
            Weight -=PebbleWeight;
        }else{//ADD
            Weight +=PebbleWeight;
        }
    }

    public Player(int playerID) {
        this.playerID = playerID;
    }

    //takes a new pebble randomly adds it to the players hand in place of another pebble and returns the old pebble

    public int discardPebble(int replacementPebble) {
        int index = rand.nextInt(this.getPebbles().length);
        int pebbleWeight = this.getPebbles()[index];
        this.getPebbles()[index] = replacementPebble;
        return pebbleWeight;
    }
    //calculates the total weight of a player's hand
    public int calculateTotalWeight(){
        int totalWeight = 0;
        for (int i : this.getPebbles()) {
            totalWeight = totalWeight + i;
        }
        return totalWeight;
    }



    @Override
    public void run() {//this will run each player



    }


}
