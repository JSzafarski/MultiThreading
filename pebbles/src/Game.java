import java.util.*;
import java.io.File;
import java.io.IOException;

public class Game implements Runnable{ //NOT SURE LOL
    int numPlayers;//number of players
    Player[] playerList;
    LinkedList<Bag> discardQueue = new LinkedList<>();
    //instantiating black bags
    Bag bagX = new Bag("BLACK");
    Bag bagY = new Bag("BLACK");
    Bag bagZ = new Bag("BLACK");
    //instantiating white bags
    Bag bagA = new Bag("WHITE");
    Bag bagB = new Bag("WHITE");
    Bag bagC = new Bag("WHITE");

    //method for setting bag pairs of bags
    public void setBagPairs() {
        bagX.setBagPair(bagA);
        bagY.setBagPair(bagB);
        bagZ.setBagPair(bagC);
        bagA.setBagPair(bagX);
        bagB.setBagPair(bagY);
        bagC.setBagPair(bagZ);
    }

    public void draw_discard(Player thisPlayer) {//method that draws a pebble and then discards the pebble into the next bag in the discard queue will also refill a bag if found to be empty
        int num = rand.nextInt(3);
        int replacementpebble = -1;
        switch (num) {//num randomly generates a number to randomly enter a case which represent randomly picking a bag
            case 0 -> {
                replacementpebble = bagX.drawPebble();
                if (replacementpebble == -1) {//when bag is empty refill and can call this function recursively to continue the process of attempting to draw from a random bag
                    bagX.refillBag();
                    draw_discard(thisPlayer);
                } else {//the drawPebble method was successful and a pointer to a bag that is in line to be discarded into is added to the queue
                    discardQueue.add(bagX.getBagPair());
                }
            }
            case 1 -> {
                replacementpebble = bagY.drawPebble();
                if (replacementpebble == -1) {//when bag is empty refill and can call this function recursively to continue the process of attempting to draw from a random bag
                    bagY.refillBag();
                    draw_discard(thisPlayer);
                } else {//the drawPebble method was successful and a pointer to a bag that is in line to be discarded into is added to the queue
                    discardQueue.add(bagY.getBagPair());
                }
            }
            case 2 -> {
                replacementpebble = bagZ.drawPebble();
                if (replacementpebble == -1) {//when bag is empty refill and can call this function recursively to continue the process of attempting to draw from a random bag
                    bagZ.refillBag();
                    draw_discard(thisPlayer);
                } else {//the drawPebble method was successful and a pointer to a bag that is in line to be discarded into is added to the queue
                    discardQueue.add(bagZ.getBagPair());
                }
            }
        }
        //here call the discard pebble function on the next bag in line to be discarded into from the bag class, ensures each player holds no more than 10 pebbles
        discardQueue.removeFirst().discardPebble(thisPlayer, replacementpebble);
    }


    public void start_game() {//if the undwser eneters e then the program must exit.
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        setBagPairs();
        System.out.println("you will be asked to enter the number of players and then for the location");
        System.out.println("of three files in turn containing comma separated inger values for the pebble weights.");
        System.out.println("the integer must be strictly positive.");
        System.out.println("the game will then be simulated,and output written to file in the directory.");
        System.out.println("type: X to exit and start over.");
        System.out.println("");
        while (true){//loops until use exits or enters correct information so the program can proceed
            System.out.println("Please enter the number of players:");
            numPlayers = Integer.parseInt(System.console().readLine());
            int totalPebbles = 0;
            try {
                if (numPlayers > 0) {
                    String file_input;
                    for (int i = 1; i <= 3; i++) {
                        System.out.println("Please enter location of bag number " + i + " to load:");
                        file_input = System.console().readLine();//verify data type
                        if (Objects.equals(file_input, "X")||(Objects.equals(file_input, "E"))){
                            if((Objects.equals(file_input, "E"))){
                                System.exit(0);
                            }
                            break;//exit the loop
                        } else { //check if the input is valid using try catch block
                            try{
                                pebbles.addAll(read_csv(file_input));//populates the pebbles temp array
                                if(pebbles.size()<10){//if some files contains less than 10 pebbles each then throw error as its below spec
                                    throw new InvalidfileExeption("file number" + i + " has to have at least 10 pebbles");
                                }else{
                                    switch (i) {//uses the for loop to identify which bag is being added to
                                        case 1 -> {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagX.setPebbles(pebbles);
                                            }
                                        case 2 -> {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagY.setPebbles(pebbles);
                                            }
                                        case 3 -> {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagZ.setPebbles(pebbles);
                                        }
                                        default -> {
                                            pebbles.clear();
                                        }
                                    }
                                }
                            } catch (InvalidfileExeption e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    if (totalPebbles < calculate_minPebbles(numPlayers)) {//not enough total pebbles to execute the game
                        System.out.println("there is not enough pebbles in total form each csv file,please increase that amount");
                    } else {
                        RunPlayers(numPlayers);//creates players for the game to execute
                        break;//all conditions have been met so the program my proceed
                    }
                } else {
                    System.out.println("invalid input ,enter again!");//shows error
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println(e.getMessage());//shows error
            }
        }
    }
    public ArrayList<Integer> read_csv (String filename) throws IOException,InvalidfileExeption {//validate file name when calling the method
        ArrayList<Integer> pebbles = new ArrayList<Integer>();
        String StringOfNumbers;
        Scanner scanner;
        int errorCount = 0;
        int temporaryint  = 0;
        String text = "";
        String ErrorString ="We have detected problems inside the file as follows: ";
        try {
            File file = new File(filename); // java.io.File(source file with text)
            scanner = new Scanner(file);    // java.util.Scanner
            while(scanner.hasNextLine()){//this loop is going to be used to read a text file (each line)
                StringOfNumbers = scanner.nextLine();
                int x = 0;
                StringBuilder CurrentString = new StringBuilder();
                while(x < StringOfNumbers.length() + 1) {//might need fixing
                    if(!String.valueOf(StringOfNumbers.charAt(x)).equals(",")){//fix
                        if(Character.isDigit(StringOfNumbers.charAt(x))){//check if it's a digit
                            CurrentString.append(StringOfNumbers.charAt(x));
                        }else{
                            errorCount++;
                            ErrorString=ErrorString.concat("type error on line: " + x +" ," );
                            //throw error as it's not a character
                        }
                    }else{
                        if(CurrentString.toString().equals("")){
                            ErrorString=ErrorString.concat("empty entry on line: " + x +" ," );
                            errorCount++;
                        }else{
                            temporaryint = Integer.parseInt(CurrentString.toString());
                            if(temporaryint>0){
                                pebbles.add(temporaryint);//adds the item to the list
                            }else{
                                ErrorString=ErrorString.concat("range error on line : " + x +" ," );
                                errorCount++;
                            }
                            CurrentString.setLength(0);
                        }
                        //save the number into an array and proceed gathering more numbers
                    }
                    CurrentString.append(StringOfNumbers.charAt(x));
                    x++;
                }
            }
            if(errorCount>0){
                //throw an exception
                throw new InvalidfileExeption(ErrorString.concat("total errors:" + errorCount));
            }else{
                return pebbles;
            }
        } catch (IOException e) {
            System.out.println("We Could not find the file and : " + e);
            throw new InvalidfileExeption("we could not find the file at this file path");
        }
    }

    public int calculate_minPebbles(int players){
        return 11*players;
    }

    public void RunPlayers(int numPlayers){
        //"int" defines the amount of players
        //player id starts from 1001 (we can change this idk not important tbh.
        for(int i = 1; i <= numPlayers; i++){
           playerList[i] = new Player(1000+i);
        }
    }


//    playerList = new Player[numPlayers];
//                                for (int j = 0; j <= numPlayers-1; j++) {//ensures that there is at least 11* the number of players of pebbles
//        //creates the next player
//        Player nextPlayer = new Player(1000+j);//make the id start from 1001 ? idk nto improtant
//        //adds the next player to the list of players
//        playerList[j] = nextPlayer;
//    }

}
