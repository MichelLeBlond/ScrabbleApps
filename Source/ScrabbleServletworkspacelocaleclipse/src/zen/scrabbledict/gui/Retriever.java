/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zen.scrabbledict.gui;
import zen.scrabbledict.data.*;
import java.util.TreeSet;
import java.util.Vector;




/**
 *
 * @author ubuntu6
 */
public class Retriever implements LogicListener, DictionaryListener{
    private static final long serialVersionUID = 1;
    private ResultsModel resultsModel = new ResultsModel();
    private TableSorter resultsSorter = new TableSorter(resultsModel);
    private String resultRack = null;
    private EnhancedBoard board = new EnhancedBoard();
    private Dictionary dictionary = new Dictionary(this);
    private boolean preview = false;
    private Logic logic;

  
    
    public Retriever(){
         dictionary = new Dictionary(this);
         // Try to load the preferred dictionary.
         String aba ="A";
        char xxx = aba.charAt(0);
         board.setLetter(7, 2, xxx);
        Thread t = new Thread(dictionary);
        t.start();
      
     
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Retriever myretriever = new Retriever();
    }

    public void processingComplete() {
        // Declare the results vector.
        Vector<WordMatch> results = new Vector<WordMatch>();

        // Must use ScoreComparator to sort the tree.
        TreeSet<WordMatch> matches = new TreeSet<WordMatch>(new ScoreComparator(ScoreComparisonType.RACK_USAGE));
        matches.addAll(logic.getResults());
        results.addAll(matches);

       
        if (matches.isEmpty()) {
             System.out.println("no matches nothing to report");
            resultsModel.clearResults();
        } else {
            resultsModel.setResults(results);
         System.out.println(matches.size());
         for(int i =0; i< matches.size(); i++) {
        //   System.out.println( resultsModel.getValueAt(i,0).toString() + " " + resultsModel.getValueAt(i,1).toString() + " " +   resultsModel.getValueAt(i,2).toString()
        //          + "  " + resultsModel.getValueAt(i,3).toString() + " " + resultsModel.getValueAt(i,4).toString() + " " + resultsModel.getValueAt(i,5).toString());
             System.out.println(resultsModel.getWordMatch(i).toString());
          
         }

        }
        
      
    }

    public void wordCountUpdated() {
          logic = new Logic("CASTING", board, dictionary, this);
       // Start the thread.
          logic.start();
        // Set the rack string. , this will be done by the servlet
            resultRack ="CASTING";
        
    }

    public void dictionaryCantLoad() {
      //  throw new UnsupportedOperationException("Not supported yet.");
    }

}
