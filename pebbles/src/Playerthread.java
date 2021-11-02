import java.util.Arrays;
import java.util.Objects;

//for data output
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Playerthread extends Thread {
    PebbleGame.Player ThisPlayer;
    boolean hasWon = false;

    public PebbleGame.Player getThisPlayer() {
        return this.ThisPlayer;
    }


    public Playerthread(PebbleGame.Player PlayerfromGame) { this.ThisPlayer = PlayerfromGame;
    }

    public void CreateFile() {
        try {
            File PlayerFile = new File("Player "+ this.getThisPlayer().getPlayerID()+".txt");
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    @Override
    public void run() {//this will run each player
        PebbleGame.Player thisThreadsPlayer = this.getThisPlayer();
        thisThreadsPlayer.calculateTotalWeight();
        thisThreadsPlayer.GenerateRandomChoice();//randomly the bag from which 10 pebbles will be chosen from
        PebbleGame.draw10(thisThreadsPlayer);//draws first 10 pebbeles
        FileWriter WriteToPlayerFile = null;
        try {
            WriteToPlayerFile = new FileWriter("Player "+thisThreadsPlayer.getPlayerID()+".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (hasWon) {
            if (thisThreadsPlayer.getTotalWeight() == 100){
                hasWon = true;
                System.out.println("player: "+ thisThreadsPlayer.getPlayerID()+" has won");
                break;
            }
            //player discards a pebble to a white bag
            //player chooses a black bag at random
            //player selects a pebble and if its empty then the player chooses another random back that's refilled
            //cycle repeats until a winner is announced

            //compare two arrays before and after to see what pebble has been discarded and drawn!
            int[] TempPebbleArray= thisThreadsPlayer.getPebbles();
            thisThreadsPlayer.GenerateRandomChoice();
            if(thisThreadsPlayer.getRandomBag()==0){//attempts to draw from bag X
                PebbleGame.drawAndDiscardFromBagX(thisThreadsPlayer,false);
            }else if(thisThreadsPlayer.getRandomBag()==1){//attempts to draw from bag Y
                PebbleGame.drawAndDiscardFromBagY(thisThreadsPlayer,false);
            }else{//attempts to draw from bag Z
                PebbleGame.drawAndDiscardFromBagZ(thisThreadsPlayer,false);
            }
            int NewPebble = 0;
            int OldPebble = 0;
            String LastBagDrawn;
            String LastBagDiscarded;
            for (int i = 0 ;i<=10;i++){
                if(TempPebbleArray[i]!=thisThreadsPlayer.getPebbles()[i]){
                    NewPebble = thisThreadsPlayer.getPebbles()[i];
                    OldPebble = TempPebbleArray[i];
                    break;//as there can only be one change in the array in a given iteration.
                }
            }
            LastBagDrawn = thisThreadsPlayer.getLastBagDrawn();
            if(Objects.equals(LastBagDrawn, "X")){
                LastBagDiscarded = "A";
            }else if(Objects.equals(LastBagDrawn, "Y")){
                LastBagDiscarded = "B";
            }else{//MUST BE Z THEN
                LastBagDiscarded = "C";
            }
            //output this into some texts files
            String String1=("player "+thisThreadsPlayer.getPlayerID()+" has drawn a " + NewPebble + "from bag " + LastBagDrawn);
            String String2=("player "+thisThreadsPlayer.getPlayerID()+" hand is "+ Arrays.toString(TempPebbleArray));
            String String3=("player "+thisThreadsPlayer.getPlayerID()+" has drawn a " + OldPebble + "from bag " + LastBagDiscarded);
            String String4=("player "+thisThreadsPlayer.getPlayerID()+" hand is "+ Arrays.toString(thisThreadsPlayer.getPebbles()));
            try {
                assert WriteToPlayerFile != null;
                WriteToPlayerFile.write(String1);
                WriteToPlayerFile.write(String2);
                WriteToPlayerFile.write(String3);
                WriteToPlayerFile.write(String4);
                WriteToPlayerFile.close();
            } catch (IOException e) {
                System.out.println("error");
                e.printStackTrace();
            }
        }
    }
}
