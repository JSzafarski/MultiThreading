import java.util.*;
import java.io.File;
import java.io.IOException;

public class Game {
    int numPlayers;//number of players
    ArrayList<Integer> pebbles = new ArrayList<Integer>();//don't we need 3 arrays for 3 csv files???
    Player[] playerList;
    LinkedList<Bag> discardQueue = new LinkedList<>();
    Random rand = new Random();
    //instantiating black bags
    Bag bagX = new Bag(pebbles, "BLACK");
    Bag bagY = new Bag(pebbles, "BLACK");
    Bag bagZ = new Bag(pebbles, "BLACK");
    //instantiating white bags
    Bag bagA = new Bag(pebbles, "WHITE");
    Bag bagB = new Bag(pebbles, "WHITE");
    Bag bagC = new Bag(pebbles, "WHITE");

    //method for setting bag pairs of bags
    public void setBagPairs() {
        bagX.setBagPair(bagA);
        bagY.setBagPair(bagB);
        bagZ.setBagPair(bagC);
        bagA.setBagPair(bagX);
        bagB.setBagPair(bagY);
        bagC.setBagPair(bagZ);
    }

    public void draw(Player thisPlayer) {
        int num = rand.nextInt(3);
        if (num == 0) {
            int replacementpebble = bagX.drawPebble();
            if (replacementpebble == -1) {
                bagX.refillBag();
                draw();
            } else {
                discardQueue.add(bagX.getBagPair());
            }
        } else if (num == 1)  {
            int replacementpebble = bagY.drawPebble();
            if (replacementpebble == -1) {
                bagY.refillBag();
                draw();
            } else {
                discardQueue.add(bagY.getBagPair());
            }
        } else if (num == 2) {
            int replacementpebble = bagZ.drawPebble();
            if (replacementpebble == -1) {
                bagZ.refillBag();
                draw();
            } else {
                discardQueue.add(bagZ.getBagPair());
            }
        }
        //here call the discard pebble function on from the bag class
        //take the top element of the linked list do that.function(thisPlayer, replacementPebble)
    }

    public void start_game() {

        setBagPairs();
        System.out.println("you will be asked to enter the number of players and then for the location");
        System.out.println("of three files in turn containing comma separated inger values for the pebble weights.");
        System.out.println("the integer must be strictly positive.");
        System.out.println("the game will then be simulated,and output written to file in the directory.");
        System.out.println("type: X to exit and start over.");
        System.out.println("");
        while (true) {//loops until use exits or enters correct information so the program can proceed
            System.out.println("Please enter the number of players:");
            numPlayers = Integer.parseInt(System.console().readLine());
            int totalPebbles = 0;
            try {
                if (numPlayers > 0) {
                    String file_input;
                    for (int i = 1; i <= 3; i++) {
                        System.out.println("Please enter location of bag number " + i + " to load:");//do we want to allow th user to enter same csv file 3 times?
                        file_input = System.console().readLine();//verify data type
                        if (Objects.equals(file_input, "X")) {
                            break;//exit the loop
                        } else { //check if the input is valid using try catch block
                            try{//reading a file
                                read_csv(file_input);
                                playerList = new Player[numPlayers];
                                for (int j = 1; j <= numPlayers; j++) {
                                    pebbles.addAll(pebbles);
                                    Player nextPlayer = new Player(j);
                                    playerList[j-1] = nextPlayer;
                                }
                                switch (i) {
                                    case 1 -> {
                                        if(pebbles.size() <10) {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagX.setPebbles(pebbles);
                                            pebbles.clear();
                                        }else{
                                            //say that the file i has got not enougth pebbles for the game to run
                                        }
                                    }
                                    case 2 -> {
                                        if(pebbles.size() <10) {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagY.setPebbles(pebbles);
                                            pebbles.clear();
                                        }else{

                                        }
                                    }
                                    case 3 -> {
                                        if(pebbles.size() <10) {
                                            totalPebbles = totalPebbles + pebbles.size();
                                            bagZ.setPebbles(pebbles);
                                            pebbles.clear();
                                        }else{

                                        }
                                    }
                                }
                            } catch (InvalidfileExeption e) {
                                throw new InvalidfileExeption(e);
                                //if the file read is invalid then repeat the for loop
                            }
                        }
                        // validate
                    }

                    if(totalPebbles<calculate_minPebbles(numPlayers)){
                        //throw error
                    }
                    //calculate the necessary amounts of pebbles for the players
                    if (!Objects.equals(file_input, "X")) {
                        //if X was pressed then the program should repeat everything as the user requested to change inputs
                        break;
                    }
                } else {
                    System.out.println("invalid input ,enter again!");//shows error
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("invalid input ,enter again!");//shows error
            }
        }

    }

    public void read_csv (String filename) throws IOException,InvalidfileExeption {//validate file name when calling the method
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
            }
        } catch (IOException e) {
            System.out.println("We Could not find the file and : " + e);
            throw new InvalidfileExeption("we could not find the file at this file path");
        }
    }

    public int calculate_minPebbles(int players){//simple lol
        return 11*players;
    }

    public int

    }
