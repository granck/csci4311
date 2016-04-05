/**
 * Garrick Ranck
 * CSCI4311
 * Programming Assignment 1
 * SpanningTree.java
 *
 * Calculates the spanning tree algorithm for a set of switches
 * and connections
 **/

import java.io.*;

public class SpanningTree{

	public static void main(String[] args){

		String fileName = "input.txt"; //name of file to read
		String line = null; //reference to each line to be read
		String[] lineArray = new String[3]; //stores all lines read from file

		try{

			//opens file
			FileReader fileReader = new FileReader(fileName); 
			
			//places file into buffer to read
			BufferedReader bufferReader = new BufferedReader(fileReader);
			
			//continues to read lines and place them into arrays until no more lines
			for(int y = 0; (line = bufferReader.readLine()) != null; y++){
				lineArray[y] = line;
				//System.out.println(lineArray[y]);
			}
			
			//closes buffer
			bufferReader.close();

		}//end try block
		catch(FileNotFoundException ex){
			System.out.println("No file named " + fileName);
		}
		catch(IOException ex){
			//ex.printStackTrace();
			System.out.println("Can't read file?");
		}

		String[] line1 = lineArray[0].split("\\s+");
		String[] line2 = lineArray[1].split("\\s+");
		String[] line3 = lineArray[2].split("\\s+");
		
		//for(int x = 0; x < line
		//System.out.println(line1);
		//System.out.println(line2);
		//System.out.println(line3);

		ConnectionNode[] test = new ConnectionNode[line2.length];
		for(int x = 2; x < line2.length; x++){

			test[x - 2] = new ConnectionNode(line2[x]);
			//test[1] = new ConnectionNode(line2[3]);
			System.out.print("nodeA: " + test[x - 2].getFirstNode());
			System.out.println("\tnodeB: " + test[x - 2].getSecondNode());
			//System.out.println("Second nodeA: " + test[1].getFirstNode());
			//System.out.println("Second nodeB: " + test[1].getSecondNode());

		}//end for
		
		//Todo list
		// 1 - Determine if connections need to be made
		// 	by way of random assigning
		// 2 - Determine a way to represent a connection between
		// 	two nodes
		// 3 - Figure out all connections made and determine
		// 	if a node is not used (that is, are all nodes used
		// 	in at least one connection)
		// 4 - Determine what nodes any current node is connected to
		// 5 - Develop loop that will iterate through nodes and determine
		// 	quickest path to true root node
	}//end main method
	
	//takes a String representing a connection between two points and 
	//returns a String array 
	


	
	//represents a connection between two nodes
	private static class ConnectionNode{
		char firstNode;
		char secondNode; //contains 
		
		public ConnectionNode(){
		}
		public ConnectionNode(String connection){
			String[] tempConnection = connection.split("-");	
			firstNode = tempConnection[0].charAt(0);
			secondNode = tempConnection[1].charAt(0);
		}

		private char getFirstNode(){
			return firstNode;
		}
		
		private char getSecondNode(){
			return secondNode;
		}

	}//end private inner class ConnectionNode
}//end class SpanningTree


