/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

import static java.lang.Math.abs;
import java.util.Random;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 *
 * @author Marcel Colón
 * @date 7/28/2021
 * @version 4.2
 */
public class AStar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Board declaration and random generation
        Node[][] enviro = new Node[15][15];
        enviro = boardGen();
        //Variable Section
        int menuChoice = 0;     //Used for user choices in main menu
        int subChoice;          //Used for user choices within a menus section
        int startRow = -1;      //Rolling starting Row position
        int startCol = -1;      //Rolling starting Column position
        int goalRow = -1;       //Rolling goal Row position
        int goalCol = -1;       //Rolling goal Column position
        char choice;            //Used for user choice for yes or no responses
        Scanner input = new Scanner(System.in); //Define scanner object for user inputs
        LinkedList<Node> solution = new LinkedList<Node>();//Linked List that serves as holder of solution path

        displayBoard(enviro, startRow, startCol, goalRow, goalCol); //Program starts by displaying the generated board
        while (menuChoice != 7) {                           //While loop for program menu system
            menu();                                         //Method call for menu display
            System.out.println("Please Select an Option");
            menuChoice = input.nextInt();                   //Take user input for menu option

            if (menuChoice < 1 || menuChoice > 7) {         //If user selects a number not within range of menu options
                System.out.println("Your selection is not a valid option. Please select between 1 and 7");
                //OPTION 1 (Display Board)
            } else if (menuChoice == 1) {                   //Otherwise, if they choose option 
                displayBoard(enviro, startRow, startCol, goalRow, goalCol);

                //OPTION 2 (Set Start Position)
            } else if (menuChoice == 2) {
                if (startRow > -1) {                        //If a position has already been set, offer to change current setting
                    System.out.println("A start position has already been set. Would you like to change the starting position?");
                    System.out.println("Input Y for yes or N for no");
                    choice = input.next().charAt(0);
                    if (choice == 'y' || choice == 'Y') {   //If yes, reset positions
                        startRow = -1;
                        startCol = -1;
                        System.out.println("Your start position has been reset");
                        System.out.println("");
                    } else {                                //Otherwise, send them back to menu
                        System.out.println("No changes have been made. Returning to main menu.");
                        System.out.println("");
                    }
                }
                while (startRow == -1) {                    //Loop user until a position is set
                    System.out.println("Please Choose a Row Index");
                    subChoice = input.nextInt();
                    if (subChoice < 0 || subChoice > 14) {  //Check input is in bounds
                        System.out.println("Your selection is not a valid Row index. Please try again.");
                    }//end if
                    else {
                        startRow = subChoice;
                    }
                }//End Row While
                while (startCol == -1) {
                    System.out.println("Please Choose a Column Index");
                    subChoice = input.nextInt();
                    if (subChoice < 0 || subChoice > 14) {  //Check bounds
                        System.out.println("Your selection is not a valid Column index. Please try again.");
                    } else {
                        startCol = subChoice;
                    }
                }//End Column While

                if (enviro[startRow][startCol].getType() == 1) {        //If the position given is not traversable
                    System.out.println("");
                    System.out.println("It appears that your starting position, Row: " + startRow + " Column: " + startCol + ", is not traversable. Your coords have been reset.");
                    startRow = -1;                                      //Reset positions
                    startCol = -1;
                    System.out.println("Please review the board again and pick a valid starting location.");
                    System.out.println("You may use option 1 in the main menu to view the current board.");
                    System.out.println("");
                } else if (startRow == goalRow && startCol == goalCol) {  //Check if their start matches goal
                    System.out.println("");
                    System.out.println("It appears that your starting position matches your current goal position. Your starting coords have been reset.");
                    startRow = -1;                                      //If so, reset
                    startCol = -1;
                    System.out.println("Please review the board again and pick a valid starting location.");
                    System.out.println("You may use option 1 in the main menu to view the current board.");
                    System.out.println("");

                } else {                                                //If there are no position conflicts
                    System.out.println("");
                    System.out.println("Your starting position has been set as Row: " + startRow + " Column: " + startCol);
                    System.out.println("You may view your set coords by selecting option 4 in the main menu");
                    System.out.println("");
                }

            }//End Option 2 
            //OPTION 3 (Set Goal Position)
            else if (menuChoice == 3) {                             //Same notes as previous option (Set Start)
                if (goalRow > -1) {
                    System.out.println("A goal position has already been set. Would you like to change the goal position?");
                    System.out.println("Input Y for yes or N for no");
                    choice = input.next().charAt(0);
                    if (choice == 'y' || choice == 'Y') {
                        goalRow = -1;
                        goalCol = -1;
                        System.out.println("Your goal position has been reset");
                        System.out.println("");
                    } else {
                        System.out.println("No changes have been made. Returning to main menu.");
                        System.out.println("");
                    }
                }
                while (goalRow == -1) {
                    System.out.println("Please Choose a Row Index");
                    subChoice = input.nextInt();
                    if (subChoice < 0 || subChoice > 14) {
                        System.out.println("Your selection is not a valid Row index. Please try again.");
                    }//end if
                    else {
                        goalRow = subChoice;
                    }
                }//End Row While
                while (goalCol == -1) {
                    System.out.println("Please Choose a Column Index");
                    subChoice = input.nextInt();
                    if (subChoice < 0 || subChoice > 14) {
                        System.out.println("Your selection is not a valid Column index. Please try again.");
                    } else {
                        goalCol = subChoice;
                    }
                }//End Column While

                if (enviro[goalRow][goalCol].getType() == 1) {
                    System.out.println("");
                    System.out.println("It appears that your goal position, Row: " + goalRow + " Column: " + goalCol + ", is not traversable. You coords have been reset.");
                    goalRow = -1;
                    goalCol = -1;
                    System.out.println("Please review the board again and pick a valid goal location.");
                    System.out.println("You may use option 1 in the main menu to view the current board.");
                    System.out.println("");
                } else if (goalRow == startRow && goalCol == startCol) {
                    System.out.println("");
                    System.out.println("It appears that your goal position matches your current start position. Your goal coords have been reset.");
                    goalRow = -1;
                    goalCol = -1;
                    System.out.println("Please review the board again and pick a valid starting location.");
                    System.out.println("You may use option 1 in the main menu to view the current board.");
                    System.out.println("");

                } else {
                    System.out.println("");
                    System.out.println("Your goal position has been set as Row: " + goalRow + " Column: " + goalCol);
                    System.out.println("You may view your set coords by selecting option 4 in the main menu");
                    System.out.println("");
                }
            } //OPTION 4 (View Set Positions)
            else if (menuChoice == 4) {
                if (startRow < 0) {                             //Check if start has not yet been set
                    System.out.println("");
                    System.out.println("A start position has not yet been set.");
                } else {                                        //Otherwise display set start position
                    System.out.println("");
                    System.out.println("Your start location is set at Row: " + startRow + " Column: " + startCol);
                }
                if (goalRow < 0) {                              //Check if goal has not yet been set
                    System.out.println("");
                    System.out.println("A goal position has not yet been set.");
                } else {                                        //Otherwise display set goal position
                    System.out.println("");
                    System.out.println("Your goal location is set at Row: " + goalRow + " Column: " + goalCol);
                    System.out.println("");
                }

            } //OPTION 5 (Find and Display the Solution)
            else if (menuChoice == 5) {
                if (startRow == -1 || startCol == -1 || goalRow == -1 || goalCol == -1) {   //Check that all positions have a valid index
                    System.out.println("You must have both Start and Goal positions set before you can find a solution");
                } else {
                    solution = aStar(enviro, enviro[startRow][startCol], enviro[goalRow][goalCol]);     //Setting solution list by running aStar search method which returns path to goal
                    if (solution.isEmpty()) {                                                           //If it returns empty then no path to goal was found
                        System.out.println("No path could be found.");
                    } else {                                                                            //If path found, display to user
                        displaySolution(enviro, solution, startRow, startCol, goalRow, goalCol);
                        System.out.println("S: Start Position, G: Goal Position, *: Path, x: Blocked");
                        flipList(solution);
                    }
                }

            } //OPTION 6 (Generate New Board)
            else if (menuChoice == 6) {
                enviro = boardGen();                                            //Create new board
                startRow = -1;                                                  //Reset all position indexes
                startCol = -1;
                goalRow = -1;
                goalCol = -1;
                System.out.println("A new board has been generated: ");
                displayBoard(enviro, startRow, startCol, goalRow, goalCol);     //Display New Board
                System.out.println("Start and Goal Positions have been reset.");
                System.out.println("");
                
            } //OPTION 7 (Close Program)
            else if (menuChoice == 7) {
                System.out.println("Program will now close.");
            }
        }//End While

    }//End Main

    /**
     * Description: Method that creates a 15x15 2d array of Node Objects where
     * each node creation has a 10% chance to be non-traversable
     *
     * @return 2d Array of nodes
     */
    public static Node[][] boardGen() {
        Random rand = new Random();                 //Create random object
        int diceResult;                             //Integer that holds the result of rolling a ten sided die
        Node[][] board = new Node[15][15];          //Create empty board

        for (int j = 0; j < 15; j++) {              //Row loop interative using j
            for (int k = 0; k < 15; k++) {          //Column loop interactive using k
                diceResult = rand.nextInt(10) + 1;  //Create a random # from 1 to 10
                if (diceResult == 1) {              //If the the number rolled is 1 (10% chance)
                    Node temp = new Node(j, k, 1);  //Create a non-traverseable object
                    board[j][k] = temp;
                } else {                            //Otherwise create a traverseable object
                    Node temp = new Node(j, k, 0);
                    board[j][k] = temp;
                }
            }
        }
        return board;
    }

    /**
     * Description: Method to print a board to terminal with start and goal
     * positions marked
     *
     * @param board Input of a 2d array of Node objects
     * @param startRow Starting row index
     * @param startCol Starting column index
     * @param goalRow Goal row index
     * @param goalCol Goal column index
     */
    public static void displayBoard(Node[][] board, int startRow, int startCol, int goalRow, int goalCol) {
        System.out.println("                  Column #         ");
        System.out.println(" 0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 ");   //Format display to show column indexes
        String dis;
        for (int j = 0; j < 15; j++) {
            for (int k = 0; k < 15; k++) {
                if (board[j][k].getType() == 1) {           //Display non-traverseable
                    dis = "x";
                } else if (j == startRow && k == startCol) { //Display start 
                    dis = "S";
                } else if (j == goalRow && k == goalCol) {  //Display goal
                    dis = "G";
                } else {                                    //Display open position
                    dis = " ";
                }
                System.out.print("[" + dis + "]");
            }//k Loop
            if (j == 5) {                                   //Format display to show row indexes
                System.out.println(" " + j + "  R");
            } else if (j == 6) {
                System.out.println(" " + j + "  o");
            } else if (j == 7) {
                System.out.println(" " + j + "  w");
            } else if (j == 8) {
                System.out.println(" " + j + "  #");
            } else {
                System.out.println(" " + j);
            }
        }//j loop
        System.out.println("");
    }

    /**
     * Description: Method to display solution and important info on path to
     * goal
     *
     * @param board 2d Array of objects representing current board
     * @param solution LinkedList that contains the ordered objects that path to
     * solution
     * @param startRow Integer of start row index
     * @param startCol Integer of start column index
     * @param goalRow Integer of goal row index
     * @param goalCol Integer of goal column index
     */
    public static void displaySolution(Node[][] board, LinkedList solution, int startRow, int startCol, int goalRow, int goalCol) {
        System.out.println("                  Column #         ");
        System.out.println("  0   1   2   3   4   5   6   7   8   9  10  11  12  13  14 ");
        String dis = " ";
        for (int j = 0; j < 15; j++) {
            for (int k = 0; k < 15; k++) {
                if (board[j][k].getType() == 1) {                           //If at this index the object is non-traverseable display x
                    dis = "x";
                    System.out.print("[" + dis + " ]");
                } else if (j == startRow && k == startCol) {                //If at this index we have the start position display S
                    dis = "S";
                    System.out.print("[" + dis + " ]");
                } else if (j == goalRow && k == goalCol) {                  //If at this index we have the goal position display G
                    dis = "G";
                    System.out.print("[" + dis + " ]");
                } else if (solution.contains(board[j][k])) {                //If at this index we have a object that is part of the path to goal display *
                    int temp = solution.lastIndexOf(board[j][k]);           //Varibale in place if I wish to show index of object (Not used in version 4.2)
                    System.out.print("[ *]");
                } else {
                    dis = " ";
                    System.out.print("[" + dis + " ]");
                }
            }//k Loop
            if (j == 5) {
                System.out.println(" " + j + "  R");
            } else if (j == 6) {
                System.out.println(" " + j + "  o");
            } else if (j == 7) {
                System.out.println(" " + j + "  w");
            } else if (j == 8) {
                System.out.println(" " + j + "  #");
            } else {
                System.out.println(" " + j);
            }
        }//j loop
        System.out.println("");
    }

    /**
     * Description: Method to print to terminal the menu options
     */
    public static void menu() {
        System.out.println("1.) Display Current Board");
        System.out.println("2.) Set Starting Position");
        System.out.println("3.) Set Goal Position");
        System.out.println("4.) Display Start and Goal Positions");
        System.out.println("5.) Find Solution");
        System.out.println("6.) Generate a New Board");
        System.out.println("7.) End Program");
    }

    /**
     * Description: Method to calculate the H value of all objects on the board
     *
     * @param state 2d Node Array of the current board
     * @param goalRow Integer of goal row index
     * @param goalCol Integer of goal column index
     * @return 2d Array of Nodes where each nodes H value has been set based on
     * the goal location
     */
    public static Node[][] setH(Node[][] state, int goalRow, int goalCol) {
        Node[][] enviroH = state;
        int tempH;
        for (int j = 0; j < 15; j++) {
            for (int k = 0; k < 15; k++) {
                tempH = (abs(j - goalRow) + abs(k - goalCol)) * 10;     //Calculate h relative to goal position (Manhattan Method)
                enviroH[j][k].setH(tempH);
            }
        }//End J 
        return enviroH;
    }

    /**
     * Description: Method that performs the A* Search Algorithm
     *
     * @param board 2d Array of node objects as input of the current board
     * @param start Object of start node
     * @param goal Object of goal node
     * @return LinkedList containing the path of nodes to traverse from start to
     * goal (Empty if no goal found)
     */
    public static LinkedList aStar(Node[][] board, Node start, Node goal) {
        Node current = start;
        PriorityQueue<Node> openList = new PriorityQueue<Node>();   //The Open list of discovered but unvisited nodes
        ArrayList<Node> closedList = new ArrayList<Node>();         //Closed list of visited nodes
        LinkedList<Node> path = new LinkedList<Node>();             //List of nodes that lead to goal

        start.setG(0);                                              //Set starting nodes movement cost
        start.setF();                                               //Set starting nodes f
        openList.add(start);                                        //Add start to openList

        while (!openList.isEmpty() || !current.equals(goal)) {      //While the list is not empty or goal isn't found
            current = openList.poll();      //STEP ONE              //Take node from the top (Node with best f value)

            if (current.equals(goal)) {                             //If that node is the goal, create the list of nodes that got us there using objects parent
                //STEP TWO
                while (current != start) {
                    path.add(current);
                    current = current.getParent();
                }
                path.add(start);                                    //Add start to list to have complete path of start to finish
                return path;                                        //Return the list and end the method
            } else {                                                //Otherwise...
                //Look at neighbors and generate their g values
                for (int j = -1; j <= 1; j++) {
                    int row = current.getRow() + j;
                    //if the row is out of bounds
                    if (row < 0 || row > 14) {
                        continue;
                    }//if row < 0
                    for (int k = -1; k <= 1; k++) {
                        int col = current.getCol() + k;
                        //if the column is out of bounds
                        if (col < 0 || col > 14) {
                            continue;
                        }
                        //Make a temporary node to process data and add to list
                        Node temp = board[row][col];
                        if (temp.equals(start)) {   //Ignore location if it is the starting node  
                            continue;
                        }
                        if (temp.equals(current)) { //Ignore location if it is the current node  
                            continue;
                        }
                        if (temp.getType() == 1) {  //Ignore location if it is not traverseable
                            continue;
                        }
                        if (closedList.contains(temp)) {    //Ignore location if it is in the closed list
                            continue;
                        }

                        //Find node in openlist, compare current g to g calculated by going through current node. Ignore node if g cost through current is higher
                        if (openList.contains(temp)) {
                            if (temp.getG() < temp.getG() + 10) {
                                continue;
                            }
                        }
                        //At this point, Moves that need to be ingnored have been ignored
                        int g = 10;                 //Set the cost to move in cardinal directions
                        if (current.getRow() != row && current.getCol() != col) {   //Check if we are looking in a diaginal location to current, if so set g cost to 14
                            g = 14;
                        }
                        temp.setG(current.getG() + g); //Add g to current g so we can maintain cost to move from start
                        temp.setF();                    //Run object method to set its F value
                        temp.setParent(current);        //Set the objects parent to the current node we are using to look around
                        openList.add(temp);             //Add the node to the openList
                    }//k for
                }//j for

            }//End else
            closedList.add(current);                //Once we have looked at all the neighbors, add the current node to the closed list and start the loop again
        }//End While Loop
        return path;                                //If we search through all moves and never find goal, return an empty list so we know goal can't be reached
    }

    /**
     * Description: A method to check if node is traversable (Currently not used
     * in version 4.2)
     *
     * @param check A node object
     * @return Boolean value if the object is traversable or not
     */
    public static boolean blocked(Node check) {
        return check.getType() == 1;
    }

    /**
     * Description: Method to print to terminal the nodes to goal in order from
     * start to finish
     *
     * @param path LinkedList containing the path from goal to start
     */
    public static void flipList(LinkedList<Node> path) {
        int size = path.size() - 1;
        int move = 1;
        for (int i = size; i > -1; i--) {
            System.out.print("Move " + move + ":" + path.get(i) + " ");
            move++;
        }
        System.out.println("");
        System.out.println("");

    }

}//End Class
