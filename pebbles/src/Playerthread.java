public class Playerthread extends Thread {
    @Override
    public void run() {//this will run each player
        int index = 0;
        Player thisPlayer = new Player(-1);
        for (Thread t : threadList) {//wtf
            if (Thread.currentThread() == t) {
                thisPlayer = playerList[index];
                thisPlayer.calculateTotalWeight();
                draw10(thisPlayer);
                break;
            }
            index++;
        }
        while (!hasWon) {
            if (thisPlayer.getTotalWeight() == 100){
                hasWon = true;
                System.out.println("player: "+ thisPlayer.playerID+" has won");
                for (Thread t : threadList) {//not sure what to do here we will discuss
                    t.stop();
                    //have each thread if one stopped if it did then stop all(maybe use a local varibale in the Game class (boolean)
                }
            }
            //player discards a pebble to a white bag
            //player chooses a black bag at random
            //player selects a pebble and if its empty then the player chooses another random back that's refilled
            //cycle repeats until a winner is announced
            drawAndDiscard(thisPlayer);
        }
    }
}
