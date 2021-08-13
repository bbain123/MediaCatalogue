//CS2210a
//Brendan Bain
//251086487

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextInterface {
	OrderedDictionary dictionary;
	
	public TextInterface(String inputFile) { //reads in the text file and stores in ordered dictionary
		try {
			dictionary = new OrderedDictionary();
		    BufferedReader inFile = new BufferedReader(new FileReader(inputFile)); 
		    String line = inFile.readLine();
		   
		    while (line != null) {
		    	String name = line;
		    	line = inFile.readLine();
		    	String content = line, type;
		    	
		    	if (content.endsWith(".wav")||content.endsWith(".mid"))
		    		type = "sound";
		    	else if (content.endsWith(".gif")||content.endsWith(".jpg"))
		    		type = "picture";
		    	else if (content.endsWith(".exe"))
		    		type = "program";
		    	else if (content.endsWith(".html"))
		    		type = "url";
		    	else
		    		type = "definition";

		    	dictionary.put(new DataItem(new Key(name,type), content)); //add to dictionary
		    	line = inFile.readLine(); 	
		    }
		    inFile.close();
		}
		catch (IOException e) { 
			System.out.println("Error opening file. " + e.getMessage());
			System.exit(0);
		}
		catch (Exception e) {
			System.out.println("Error in input file: "+e.getMessage());
			System.exit(0);
		}
	}
	
	
	public static void main(String[] args) {
			
		TextInterface text = new TextInterface(args[0]);    //store text file in ordered dictionary
			boolean dontExit = true;						//condition for text interface loop. Only exit when command "end" entered
			SoundPlayer p;
			PictureViewer v;
			ShowHTML b;
			RunCommand c;
			
			
			while(dontExit) {												//text interface loop		
				StringReader keyboard = new StringReader();
				String line = keyboard.read("Enter next command: ");
				String[] words = line.split(" ");
				
				try {
					//Command get: retrieves the second word(word[1]) from the ordered dictionary
					if(words[0].equals("get")) { 
						boolean foundOne = false, predecessor = false, successor = false;
						try {
							if (text.dictionary.get(new Key (words[1], "definition")) != null) {
								System.out.println(text.dictionary.get(new Key (words[1], "definition")).getContent());
								foundOne = true;
							}
							
							if (text.dictionary.get(new Key (words[1], "sound")) != null) {
								foundOne = true;
								p = new SoundPlayer();
								p.play(text.dictionary.get(new Key (words[1], "sound")).getContent());
							}
								
							if (text.dictionary.get(new Key (words[1], "picture")) != null) {
								foundOne = true;
								v = new PictureViewer();
								v.show(text.dictionary.get(new Key (words[1], "picture")).getContent());
							}
							
							if (text.dictionary.get(new Key (words[1], "url")) != null) {
								foundOne = true;
								b = new ShowHTML();
								b.show(text.dictionary.get(new Key (words[1], "url")).getContent());
							}
							
							if (text.dictionary.get(new Key (words[1], "program")) != null) {
								foundOne = true;
								c = new RunCommand();
								c.run(text.dictionary.get(new Key (words[1], "program")).getContent());
							}
							if (!foundOne) {
								System.out.println("The word " + words[1] + " is not in the ordered dictionary");
								
								if (text.dictionary.predecessor(new Key (words[1], "definition")) != null) {
									System.out.println("preceeding word: " + text.dictionary.predecessor(new Key (words[1], "definition")).getKey().getName());
									predecessor = true;
								}
							
								
								if (text.dictionary.successor(new Key (words[1], "definition")) != null) {
									System.out.println("following word: " + text.dictionary.successor(new Key (words[1], "definition")).getKey().getName());
									successor = true;
								}
							}
						}
						catch(MultimediaException e) {
							System.out.println("Error with fetching media: "+e.getMessage());
						}
						catch(NullPointerException e) {
							if (predecessor == false) {
								System.out.println("preceeding word: " );
								System.out.println("following word: " + text.dictionary.successor(new Key (words[1], "definition")).getKey().getName());
								successor = true;
							}
							
							if (successor == false) {
								System.out.println("following word: ");
							}	
						}	
					}
					
					
					//Command add: adds a new DataItem to the ordered dictionary. If its already in there, error message printed
					else if (words[0].equals("add")) {
						
						try {
							if (!words[2].equals("definition")) //if its a picture, url, sound, or program
								text.dictionary.put(new DataItem(new Key(words[1], words[2]), words[3]));
							else {									//if it is a definition
								int definitionLength = words.length;
								String definition = "";
								for (int i = 3; i < definitionLength; i++) {
									definition = definition.concat(words[i] + " ");
								}
								text.dictionary.put(new DataItem(new Key(words[1], words[2]), definition));
							}
								
						}
						catch (DictionaryException e) {
							System.out.println("A record with the given key (" + words[1] + ", " + words[2] + ") is already in the ordered dictionary");
						}
					}
					
					
					// Command end: ends the TextInterface
					else if (words[0].equals("end")) {
						break;
					}
					
					//Command list: prints all names that start with prefix (specified by words[1]/second word)
					else if (words[0].equals("list")) { 
						String prefix = words[1], nextWord, nextType;
						boolean beenFound = false;
						
						if (text.dictionary.get(new Key (words[1], "definition")) != null) {  //check if something IS prefix and print it 
							System.out.println(text.dictionary.get(new Key (words[1], "definition")).getKey().getName() + " ");
							beenFound = true;
						}
						
						if(text.dictionary.successor(new Key (words[1], "definition")) != null) { //if there is a successor
							nextWord = text.dictionary.successor(new Key (words[1], "definition")).getKey().getName();
						    nextType = text.dictionary.successor(new Key (words[1], "definition")).getKey().getKind();
						
						
							while(nextWord.startsWith(prefix)) {  							//if it starts with prefix, print it out and get next successor
								System.out.print(nextWord + " ");
								beenFound = true;
								if(text.dictionary.successor(new Key (nextWord, nextType)) != null) {
									DataItem nextItem = text.dictionary.successor(new Key(nextWord, nextType));
									nextWord = nextItem.getKey().getName();
									nextType = nextItem.getKey().getKind();
								}
								else {
									System.out.print("\n");
									break;
								}
							}
							if (beenFound)
								System.out.print("\n");
						}
						if (!beenFound) {
							System.out.println("No name attributes in the ordered dictionary start with prefix " + words[1]);
						}
					}
					
					//Command last: prints out the largest item in the ordered dictionary
					else if (words[0].equals("last")) {
						System.out.println(text.dictionary.largest().getKey().getName() + ", " + text.dictionary.largest().getKey().getKind() + ", " + text.dictionary.largest().getContent());
					}
					
					//Command first: prints out the smallest item in the ordered dictionary
					else if (words[0].equals("first")) {
						System.out.println(text.dictionary.smallest().getKey().getName() + ", " + text.dictionary.smallest().getKey().getKind() + ", " + text.dictionary.smallest().getContent());
					}
					
					//Command remove: removes item with key (w, k)/(words[1], words[2]) if its in the ordered dictionary
					else if (words[0].equals("remove")) {
						try {
							text.dictionary.remove(new Key(words[1], words[2]));
						}
						catch(DictionaryException e) {
							System.out.println("No record in the ordered dictionary has key (" + words[1] + ", " + words[2] + ")");
						}
					}
					
					else {
						System.out.println("Please type valid command: add, remove, get, first, last, list, end");
					}
				}
				catch(IndexOutOfBoundsException e) {
					System.out.println("Please enter all neccessary fields for that command");
				}

			}
	}

}
