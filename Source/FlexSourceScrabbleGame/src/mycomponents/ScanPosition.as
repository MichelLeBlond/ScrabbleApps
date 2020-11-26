package mycomponents
{
	[Event(name="nextPos", type="mx.events.FlexEvent")]
	import mx.core.UIComponent;
    import flash.events.Event;
   	import mx.events.FlexEvent;
	public class ScanPosition extends UIComponent
	{
		import mx.collections.ArrayCollection;
		import mx.events.FlexEvent;
		import mx.controls.Alert;
		import mycomponents.BindableImage;
		import mycomponents.HTTPmethods;
		import mycomponents.Player;
		import mycomponents.ArrayCollectionFilters;
		import mycomponents.BoardUtils;
		private var boardArray:ArrayCollection;
		private var wordlist:ArrayCollection;
		private var goodWords:ArrayCollection;
		private var htt:HTTPmethods;
		private var wordToPlay:ArrayCollection;
	   	private var boardutils:BoardUtils = new BoardUtils;
		private var stringUtil:MyStringUtil = new MyStringUtil;
		public var Scanorientation:int; // scan accross or down 0 is vertical, 1 horizontal
		private var ScanRule:String =""; //keeps track of where at in scan
		private var ScanPosOffSet:int=0; // keeps number of letters prior to ScanPos if they exist,
		private var ScanRack:String // keeps track of current rack for to keep things simple between calls
		private var lastIndex:int = 32; // last square scanned on board , debug purposes
		private var ScanPos:int;  // position on board being scanned for words
		/**
		 * keeps track of rule being run , some rules do multiple http calls
		 * */
		private var rule:String;
		public function ScanPosition()
		{
			super();
		}
		public function setboardArray(board:ArrayCollection):void {
			this.boardArray = board;
			boardutils.setBoardArray(board);
		}
		public function setWordList(word:ArrayCollection):void {
			this.wordlist = word;
		}
		public function setGoodWords(words:ArrayCollection):void{
			this.goodWords = words;
		}
		public function setHTTPmethods(htt:HTTPmethods):void{
			this.htt = htt;
			this.htt.addEventListener("wordsfound", foundWordsRules);
		}
		public function setWordToPlay(obj:ArrayCollection):void {
			this.wordToPlay = obj;
		}
		public function setRack(rack:String):void {
			this.ScanRack = rack;
		}
		/**
		 *  send http for validity of cross words
		 * */
		 
		 	/**
		 * Starts scanning board from top left to lower right corners 
		 * for valid words
		 **/
		public function startBoardScan(rack:String,ScanPos:int ,Scanorientation:int):void{
			/** does downward scans first to build regexs.
			 * needs to check above if pos y is > 1
			 * 
			 * scan down same column first then do side rules if Regex empty
			 * 
			 * 
			 **/ 
			 ScanPosOffSet = 0; // important keeps board offsetting on new scans later turns
			  this.ScanRack = rack;
			 wordlist.removeAll(); 
			 var startPos:int = ScanPos;
			 var regexes:String ="";
			 var firstRegex:String="";
			 var sidePos:int;
			 var maxLength:int
			this.Scanorientation = Scanorientation;
			this.ScanPos = ScanPos;
		
			 // scan down
			 if(Scanorientation == 0){
			
	
		 	  // grab longest possible regex add to  ScanRegexs  loop through remove last character tile 2 left
			 	// adding to Scan Regexs each time.
			 	 if(boardutils.getRowFromPos(startPos) >1){
			  		if(!boardutils.isPosEmpty(startPos -15)) {
			  		startPos =startPos - boardutils.getScanPosOffSet(startPos,0) * 15;
			  		ScanPosOffSet = boardutils.getScanPosOffSet(ScanPos,0);
			  		}
			  	 }
			  	firstRegex = boardutils.getRegexDown(startPos,rack.length);
			    // only add regex if has atleast one letter from board to play to.
			     if(stringUtil.returnIndexes(firstRegex,".").length < firstRegex.length){
			      regexes = boardutils.getRegexSet(firstRegex);
			      } 
			     
			     // letters beside rule
			     if(regexes.length ==0){
			     	firstRegex ="";
			        sidePos = boardutils.getNextToDownIndex(startPos,rack.length);
			        maxLength = rack.length;
			     	 if(16 - boardutils.getRowFromPos(startPos)< rack.length){
			     	maxLength = 16 - boardutils.getRowFromPos(startPos);
			    	 }  
			       	 if(sidePos!=-1){
			     	// need regexes for maxLenght
			     		for(var i:int =0; i< maxLength ; i++){
			     		firstRegex =  firstRegex + ".";
			     		}
			     		regexes = firstRegex;
			    		while(firstRegex.length >sidePos +1) {
			     		firstRegex = stringUtil.removeCharAt(firstRegex.length -1 , firstRegex);
			     		regexes = regexes + "," + firstRegex;
			     		} 
			     	 }
			     }   
			 }
			 
				// across scan		 
			 else { 
			 		 if(boardutils.getColumnFromPos(startPos) >1){ 
			  			if(!boardutils.isPosEmpty(startPos -1)) { 
			  			startPos =startPos - boardutils.getScanPosOffSet(startPos,1); 
			  			ScanPosOffSet = boardutils.getScanPosOffSet(ScanPos,1); 
			  			}
			  	 	}
			  	 	firstRegex  = boardutils.getRegexAccross(startPos,rack.length);
			  	 	
			   		 // only add regex if has atleast one letter from board to play to.
			     	if(stringUtil.returnIndexes(firstRegex,".").length < firstRegex.length){
			     		 regexes = boardutils.getRegexSet(firstRegex);
			      	} 
			      	 // letters beside rule
			     if(regexes.length ==0){
			     	firstRegex ="";
			        sidePos = boardutils.getNextToAccrossIndex(startPos,rack.length); trace(sidePos," sidePos");
			        maxLength = rack.length;
			     	 if(16 - boardutils.getColumnFromPos(startPos)< rack.length){
			     	maxLength = 16 - boardutils.getColumnFromPos(startPos);
			    	 }  
			       	 if(sidePos!=-1){
			     	// need regexes for maxLenght
			     		for(var j:int =0; j< maxLength ; j++){
			     		firstRegex =  firstRegex + ".";
			     		}
			     		regexes = firstRegex;
			    	 	while(firstRegex.length >sidePos +1) {
			     		firstRegex = stringUtil.removeCharAt(firstRegex.length -1 , firstRegex);
			     		regexes = regexes + "," + firstRegex;
			     		} 
			     	 }
			     }   
			 	
			 	
			 }
			 
			trace(regexes,"regexes");
			//Alert.show("?data=" + regexes + "&rack=" +rack);
			 // if is a regex ie words  do this otherwise move to next ScanPos
		/** if(regexes.length ==0 && ScanPos < lastIndex){
				ScanPos++;
				trace("scanPoss from phase1", ScanPos);
				startBoardScan(ScanRack, ScanPos, Scanorientation);
			}
			else { */
			 this.rule = "goToSecondScan";
			htt.getPotentialWords(regexes,rack);
			 /** } */
		
		}
		 
		private function phaseTwoScan():void{
			// gets cross words, sends to http, sets rule
			 if(wordlist[0]!= "NoWords"){
			 	var offSetadjust:int;
			 	if(Scanorientation ==0){
			 		offSetadjust = ScanPosOffSet *15;
			  	}
			  	else offSetadjust = ScanPosOffSet;
		 var toSend:String = boardutils.getCrossWords(wordlist[0],ScanPos - offSetadjust, Scanorientation);
			 this.rule = "goToThirdScan"; 
			 htt.AreValidWords(toSend);
			   }
			  else {
			  ScanPosOffSet =0;
			  dispatchEvent(new FlexEvent("nextPos"));
			  	// dispatch event to Scanner to move to next pos
			  }
			
		}
		
		private function phaseThreeScan():void {
		var word:String = wordlist.removeItemAt(0).toString();
		var areGood:String = wordlist.removeItemAt(wordlist.length -1).toString();
	    
		if(areGood =="yes"){
			var tempStart:int;
		
			if(Scanorientation == 0){
				tempStart = ScanPos - 15 * ScanPosOffSet; 
			}
			else {
				tempStart = ScanPos - ScanPosOffSet;
			}
			
			addWordToGoodWords(tempStart, word, Scanorientation , boardutils.getCrossWordsScore());
		//	trace(boardutils.boardScore(word,tempStart,Scanorientation) + " " + word , " Word");
			boardutils.setCrossWordScore(0);
		}
		
		
		if(wordlist.length > 0){
			phaseTwoScan();
		}
		else {
			 ScanPosOffSet =0;
			 dispatchEvent(new FlexEvent("nextPos")); 	// dispatch event to Scanner to move to next pos
		 }
		
		}
		
		// TODO addWordToGoodWords needs fixing
		
	    private function addWordToGoodWords(startpos:int, myword:String, orientation:int, crossWordScore:int):void{
				var score:int = 0;
				var word:Object = new Object;
				word.word = myword;
				word.orientation = orientation;
				word.pos = startpos;
			    word.score = boardutils.boardScore(myword,startpos,orientation) + crossWordScore;
			 	goodWords.addItem(word);
		}
		
		 private function  foundWordsRules(event:FlexEvent):void{
		 	
		 	if(rule=="goToSecondScan"){
		 		phaseTwoScan();
		 	}
		 	else if(rule=="goToThirdScan"){
		 		phaseThreeScan();
		 	}
	   
	   }
		
		
		
	}
}