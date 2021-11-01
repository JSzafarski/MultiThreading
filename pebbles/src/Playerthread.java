public class Playerthread extends Thread {
    Player ThisPlayer;
    public Playerthread(Player PlayerfromGame){
        ThisPlayer = PlayerfromGame;
    }
    @Override
    public void run() {//this will run each player
        Game.drawAndDiscard(ThisPlayer,true);//draws first 10 pebbeles
        ThisPlayer.calculateTotalWeight();
        Game.draw10(ThisPlayer);
        boolean hasWon = false;
        while (!hasWon) {
            if (ThisPlayer.getTotalWeight() == 100){
                hasWon = true;
                System.out.println("player: "+ ThisPlayer.playerID+" has won");
                //have each thread if one stopped if it did then stop all(maybe use a local varibale in the Game class (boolean)
            }
            //player discards a pebble to a white bag
            //player chooses a black bag at random
            //player selects a pebble and if its empty then the player chooses another random back that's refilled
            //cycle repeats until a winner is announced
            Game.drawAndDiscard(ThisPlayer,false);
        }
    }
}
