import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import zen.scrabbledict.data.*;
import zen.scrabbledict.gui.*;

import java.util.TreeSet;
import java.util.Vector;
public class Runner implements LogicListener,DictionaryListener{
	private static final long serialVersionUID = 1L;
	private ResultsModel resultsModel;
	private String resultRack = null;
	private EnhancedBoard board;
	private  Dictionary dictionary = new Dictionary(this);
	private Logic logic;
	private String curDir;
	private int size;
	private boolean flag = true;
	private boolean dicFlag = false;
	private boolean logicFlag = false;
	private int me = 1;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Runner run = new Runner();
		

	}
	
	public Runner(){
		String curDir = System.getProperty("user.dir");
		 curDir = curDir + "\\src\\";
		 
	//	Dictionary dic = new Dictionary((DictionaryListener) this);
		dictionary.setCurDir(curDir);
		Thread t = new Thread(dictionary);		
		t.start();
		this.logicFlag = false;
		resultsModel = new ResultsModel();
		board = new EnhancedBoard();
		String scrabbleBoard = "";
		String resultRack = "abodgec";
		
		boolean waits = false;
		 while (!waits) {
			waits = this.dicFlag;
				 System.out.println(this.dicFlag);
					
		} 
		// System.out.println("got past waits");
		logic = new Logic(resultRack, board, dictionary, this);
		// Start the thread.
		logic.start();
		waits = false;
		while (!waits) {
			waits = this.logicFlag;
		System.out.println(logic.getState().toString() + "LOGIC");  
			
		} 
		showWords();
		
	}

	private void showWords() {
	
		
		System.out.println("<words>");
		if (flag) {
			for (int i = 0; i < size; i++) {
				System.out.println(resultsModel.getWordMatch(i).toString());

			}
		} else {
			System.out.println("<word><value>NoWords</value></word>");
		}
		System.out.println("</words>");
	}

	public synchronized void processingComplete() {
		// Declare the results vector.
		Vector<WordMatch> results = new Vector<WordMatch>();

		// Must use ScoreComparator to sort the tree.
		TreeSet<WordMatch> matches = new TreeSet<WordMatch>(
				new ScoreComparator(ScoreComparisonType.RACK_USAGE));
		matches.addAll(logic.getResults());
		results.addAll(matches);

		if (matches.isEmpty()) {
			System.out.println("no matches nothing to report");
			resultsModel.clearResults();
			flag = false;
		} else {
			flag = true;
			resultsModel.setResults(results);

		}

		this.size = matches.size();
		this.logicFlag = true;
	}

	public synchronized void wordCountUpdated() {
		System.out.println("wordcountupdate");
		this.dicFlag = true;

	}

	public void dictionaryCantLoad() {
		// throw new UnsupportedOperationException("Not supported yet.");
	
	}

	private void loadBoard(String scrabbleBoard) {
		String[] cells = null;
		cells = scrabbleBoard.split(",");
		for (int i = 0; i < cells.length; i++) {
			String cell = cells[i];
			String[] members = cell.split("@");
			int x = Integer.parseInt(members[0]);
			int y = Integer.parseInt(members[1]);
			char ch = members[2].charAt(0);
			if(!members[2].equals("null")){
			String st = Character.toString(members[2].charAt(0));
			String ST = st.toUpperCase();
			if(st == ST){
				board.setWild(x, y);
			}
		    board.setLetter(x, y, ST.charAt(0));
			}
		}

	}
	
	

}
