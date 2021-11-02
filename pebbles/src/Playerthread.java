import java.util.Arrays;
import java.util.Objects;

//for data output
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Playerthread extends Thread {
    Player ThisPlayer;

    public Playerthread(Player PlayerfromGame) {
        ThisPlayer = PlayerfromGame;
    }

    public void CreateFile() {
        try {
            File PlayerFile = new File("Player "+ThisPlayer.playerID+".txt");
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    @Override
    public void run() {//this will run each player
        ThisPlayer.calculateTotalWeight();
        Game.draw10(ThisPlayer);//draws first 10 pebbeles
        boolean hasWon = false;
        FileWriter WriteToPlayerFile = null;
        try {
            WriteToPlayerFile = new FileWriter("Player "+ThisPlayer.playerID+".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!hasWon) {
            String outputText;
            int[] TempPebbleArray = new int[10];
            if (ThisPlayer.getTotalWeight() == 100){
                hasWon = true;
                System.out.println("player: "+ ThisPlayer.playerID+" has won");
                //have each thread if one stopped it did then stop all(maybe use a local variable in the Game class (boolean)
            }
            //player discards a pebble to a white bag
            //player chooses a black bag at random
            //player selects a pebble and if its empty then the player chooses another random back that's refilled
            //cycle repeats until a winner is announced

            //compare two arrays before and after to see what pebble has been discarded and drawn!
            TempPebbleArray= ThisPlayer.getPebbles();
            Game.drawAndDiscard(ThisPlayer,false);//return the bag chosen and the pebble weight from the bag
            int NewPebble = 0;
            int OldPebble = 0;
            String LastBagDrawn;
            String LastBagDiscarded;
            for (int i = 0 ;i<=10;i++){
                if(TempPebbleArray[i]!=ThisPlayer.getPebbles()[i]){
                    NewPebble = ThisPlayer.getPebbles()[i];
                    OldPebble = TempPebbleArray[i];
                    break;//as there can only be one change in the array in a given iteration.
                }
            }
            LastBagDrawn = ThisPlayer.LastBagDrawn;
            if(Objects.equals(LastBagDrawn, "X")){
                LastBagDiscarded = "A";
            }else if(Objects.equals(LastBagDrawn, "Y")){
                LastBagDiscarded = "B";
            }else{//MUST BE Z THEN
                LastBagDiscarded = "C";
            }
            //output this into some texts files
            String String1=("player "+ThisPlayer.playerID+" has drawn a " + NewPebble + "from bag " + LastBagDrawn);
            String String2=("player "+ThisPlayer.playerID+" hand is "+ Arrays.toString(TempPebbleArray));
            String String3=("player "+ThisPlayer.playerID+" has drawn a " + OldPebble + "from bag " + LastBagDiscarded);
            String String4=("player "+ThisPlayer.playerID+" hand is "+ Arrays.toString(ThisPlayer.getPebbles()));
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
