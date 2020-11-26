package mycomponents
/** 
 * Rules for doing calculations 
 * 
 * */
{
	[Event(name="PlayerDone", type="mx.events.FlexEvent")]
	[Event(name="PlaceWord", type="mx.events.FlexEvent")]
	import flash.events.Event;
	
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;
    import mx.containers.TitleWindow;
	
	public class ScrabbleRules extends UIComponent
		
	{
		import mx.collections.ArrayCollection;
		import mx.events.FlexEvent;
		import mx.controls.Alert;
		import mycomponents.BindableImage;
		import mycomponents.HTTPmethods;
		import mycomponents.Player;
		import mycomponents.ArrayCollectionFilters;
		import mycomponents.BoardUtils;
		import mycomponents.BlankTileProxyWindow;
		import mx.controls.Text;
		import mx.managers.PopUpManager;
        import mx.containers.TitleWindow;
        import flash.geom.Point;
	    private var point1:Point = new Point();
	    private var blankTiles:ArrayCollection
		private var boardArray:ArrayCollection;
		private var wordlist:ArrayCollection;
		private var goodWords:ArrayCollection;
		private var login:BlankTileProxyWindow;
		private var htt:HTTPmethods;
		private var wordToPlay:ArrayCollection;
		private var filter:ArrayCollectionFilters = new ArrayCollectionFilters;
		private var boardutils:BoardUtils = new BoardUtils;
		private var stringUtil:MyStringUtil = new MyStringUtil;
		private var scanner:Scanner = new Scanner;
		public var Scanorientation:int = 1; // scan accross or down 0 is vertical, 1 horizontal
		public var ScanPos:int;  // position on board being scanned for words
		private var ScanRule:String =""; //keeps track of where at in scan
		private var ScanPosOffSet:int=0; // keeps number of letters prior to ScanPos if they exist,
		private var ScanRack:String // keeps track of current rack for to keep things simple between calls
		private var lastIndex:int = 32; // last square scanned on board , debug purposes
	
		/**
		 * keeps track of rule being run , some rules do multiple http calls
		 * */
		private var rule:String;
		public function ScrabbleRules()
		{
					
		}
		public function setBlankTiles(tiles:ArrayCollection):void{
			this.blankTiles = tiles;
		}
		public function setboardArray(board:ArrayCollection):void {
			this.boardArray = board;
			boardutils.setBoardArray(board);
			scanner.setboardArray(board);
		}
		public function setWordList(word:ArrayCollection):void {
			this.wordlist = word;
			scanner.setWordList(word);
		}
		public function setGoodWords(words:ArrayCollection):void{
			this.goodWords = words;
			scanner.setGoodWords(words);
		}
		public function setHTTPmethods(htt:HTTPmethods):void{
			this.htt = htt;
			this.htt.addEventListener("wordsfound", foundWordsRules);
			scanner.setHTTPmethods(htt);
		}
		public function setWordToPlay(obj:ArrayCollection):void {
			this.wordToPlay = obj;
			scanner.setWordToPlay(obj);
		}
		public function getboardArray():ArrayCollection {
			return this.boardArray;
		}
		
		
		public function computerMove(computer:Player, prereturnString:String):void {
			wordToPlay.removeAll();
			this.rule = "cfm";
			htt.findAllWords(prereturnString,computer.getLetters()); 
			}
		
		private function computerCompleteMove():void {
			var tempar:Array = new Array;
			for(var i:int =0; i< wordlist.length; i++){
				tempar.push(wordlist[i]);
			}
		tempar.sort(filter.sortOnScore);
			wordToPlay.addItem(tempar[tempar.length-1]); trace(" line 92 ScrabbleRules " + wordToPlay[0].value.toString());
			dispatchEvent(new FlexEvent("PlaceWord"));
		}
		/** 
		 * In servlet x is accross y is down
		 * */ 
		public function playerMove(player:Player, oldboard:String):void{
			var left:String="";
			var right:String="";
			var xToReturn:int = 0;
			var yToReturn:int = 0;
			var wordDirection:String ="right";
			var temparray:ArrayCollection = new ArrayCollection;
			for(var i:int=0; i<boardArray.length;i++){
				temparray.addItem(boardArray[i].column + "@"+ boardArray[i].row +"@"+ boardArray[i].tile);
			}
			var oldBoardArray:Array = oldboard.split(",");
			var oldBoardArrayCollection:ArrayCollection  = new ArrayCollection(oldBoardArray);
			var wordArray:Array = new Array;
		   for(var j:int=0;j<temparray.length;j++){
				if(!oldBoardArrayCollection.contains(temparray[j])){
					var obj:Object = new Object;
					var objarr:Array = temparray[j].split("@");
				    obj.x = objarr[0]; 
					obj.y = objarr[1]; 
					
					wordArray.push(obj);
				}
			} 
			   
			
			xToReturn = wordArray[0].x; yToReturn = wordArray[0].y; trace("xToReturn " + xToReturn); trace(yToReturn + " yToReturn");
			// three possiblities , single letter,  two or more down or to right
			if(wordArray.length>1){
				if(wordArray[0].x == wordArray[1].x){
					wordDirection = "down"; 
					left = topOfWord(wordArray); // build word up 
					if(yToReturn !=0) {yToReturn = yToReturn - left.length +1;} trace("Top of word " + left );
					right = bottomOfWord(wordArray);  trace("Bottom of word " + right );// build word down		
					if(left.length > 0 && right.length >0)	{
					right = stringUtil.removeCharAt(0,right); // keep from repeating first char
					}	
				}
				else {
					left = leftOfWord(wordArray);  trace(left , " left");// build word left 
				   if(xToReturn !=0){ xToReturn = xToReturn - left.length +1;} trace(" new xToReturn " + xToReturn);
					right = rightOfWord(wordArray); // build word right
					if(left.length > 0 && right.length >0)	{
					right = stringUtil.removeCharAt(0,right); // keep from repeating first char
					}	
					
				}
			}
			// single letter
		   else {
		   	var oneLeft:Boolean = false;
		   	var oneRight:Boolean =  false;
		   	var oneUp:Boolean = false;
		   	var oneDown:Boolean =  false;
		   	var tempY:int = wordArray[0].y; 
		   	var tempX:int = wordArray[0].x; 
		   	// boardArray[boardutils.getPos(xx + l,  wordArray[wordArray.length-1].y)].value ==0)
		   	 if(tempY > 0){
		   	 	if(boardArray[boardutils.getPos(tempY -1, tempX)].value!=0){
		   	 		oneUp = true;
		   	 	}		   	 
		   	 }
		   	  if(tempY < 15){
		   	 	if(boardArray[boardutils.getPos(tempY+1, tempX)].value!=0){
		   	 		oneDown = true;
		   	 	}		   	 
		   	 }
		   	 	 if(tempX > 0){ 
		   	 	if(boardArray[boardutils.getPos(tempY, tempX-1)].value!=0){
		   	 		oneLeft = true; trace("got to oneLeft");
		   	 	}		   	 
		   	 }
		   	  if(tempX < 15){
		   	 	if(boardArray[boardutils.getPos(tempY, tempX+1)].value!=0){
		   	 		oneRight = true; trace("got to oneRight");
		   	 	}		   	 
		   	 }
		   	 
		   	 if(oneLeft || oneRight){
		   	 		left = leftOfWord(wordArray); // build word left 
		   	 		 if(xToReturn !=0 && left.length >0){xToReturn = xToReturn - left.length + 1;}  trace ("got to single build right");
					right = rightOfWord(wordArray); // build word right
					if(left.length > 0 && right.length >0)	{
					right = stringUtil.removeCharAt(0,right); // keep from repeating first char
					}	
		   	 }
		   	 
		   	 else {
		   	 		left = topOfWord(wordArray); // build word up
		   	 		wordDirection = "down"; 
		   	 		if(yToReturn !=0 && left.length >0) {yToReturn = yToReturn - left.length +1; } trace("got single build down");
		   	 		right = bottomOfWord(wordArray); // build word down		
					if(left.length > 0 && right.length >0)	{
					right = stringUtil.removeCharAt(0,right); // keep from repeating first char
					}	
		   	 	
		   	 }
		   	 
		   	
		   }
			  /* TODO if player letter - rack = right + left  word was placed away from all others and not able to score 
			   send back to try again ? lose turn, not sure    */
			   this.rule = "plm"; trace (left + right + " word");
			   var rack:String = player.getLetters();
			// htt.validateWord(oldboard, (left + right).toUpperCase(), wordDirection , rack.toUpperCase(),  xToReturn, yToReturn)
			 htt.validateWord(oldboard, (left + right), wordDirection , rack.toUpperCase(),  xToReturn, yToReturn)	
				
		}
		private function playerCompleteMove():void{
				dispatchEvent(new FlexEvent("PlayerDone"));
		}
		
		/**
		 * Starts scanning board from top left to lower right corners 
		 * for valid words
		 **/
		public function startBoardScan(rack:String):void{ 
			this.rule ="non";
			scanner.setRack(rack);
			scanner.StartScan();
		
		}
				
				
		 private function  foundWordsRules(event:FlexEvent):void{
		 	if(rule =="cfm"){
		 		computerCompleteMove();
		 	}
		 	else if(rule=="plm"){
		 		playerCompleteMove();
		 	}
		 	
	   
	   }
	   
	   private function topOfWord(wordArray:Array):String{
	  	var top:String ="";
	   	var firstposY:int = wordArray[0].y; 
	   	var firstposX:int = wordArray[0].x;
	   	for(var k:int =0; k <= firstposY; k++) {
	   		var pos:int = boardutils.getPos(firstposY - k, firstposX);
	   		if(boardArray[pos].value ==0){
				k = firstposY + 1;
				}
			else {
				top = 	boardArray[pos].tile + top;
				if(boardArray[pos].value ==-1){ // blank tile
							//Alert.show(getBlankTileProxy(pos));		
				}
				} 
	   	}
	   		return top; 
	   	
	   }
	   
	   private function bottomOfWord(wordArray:Array):String{
	   var bottom:String= "";
	   var firstposY:int = wordArray[0].y; 
	   	var firstposX:int = wordArray[0].x;
	   	for(var k:int =0; k < 15 - firstposY; k++) {
	   		var pos:int = boardutils.getPos(firstposY + k, firstposX);
	   		if(boardArray[pos].value ==0){
				k = 15 - firstposY;
				}
			else {
				bottom = 	bottom + boardArray[pos].tile ;
				if(boardArray[pos].value ==-1){ // blank tile
							//Alert.show(getBlankTileProxy(pos));		
				}
				} 
	   	}
	   		return bottom; 
	   	
	   }
	   
	   private function leftOfWord(wordArray:Array):String{
	   	var left:String ="";
	   	var firstposY:int = wordArray[0].y; 
	   	var firstposX:int = wordArray[0].x;
	   	for(var k:int = 0; k <= firstposX; k++) {
	   		var pos:int = boardutils.getPos(firstposY, firstposX-k); 
	   		if(boardArray[pos].value !=0){
				left = 	boardArray[pos].tile + left;
				if(boardArray[pos].value ==-1){ // blank tile
							//Alert.show(getBlankTileProxy(pos));		
				}
			}
			else {
				k = firstposX +1;
				} 
	   	}
	   		return left; 
	   	
	   	
	   
	   }
	   
	   private function rightOfWord(wordArray:Array):String{
	   	var right:String =""; 
		var firstposY:int = wordArray[0].y; 
	   	var firstposX:int = wordArray[0].x;
	   	for(var k:int =0; k < 15 - firstposX; k++) {
	   		var pos:int = boardutils.getPos(firstposY, firstposX + k);
	   		if(boardArray[pos].value ==0){
				k = 15 - firstposX;
				}
			else {
				right = 	right + boardArray[pos].tile ;
				if(boardArray[pos].value ==-1){ // blank tile
							//Alert.show(getBlankTileProxy(pos));		
				}
				} 
	   	}
	   	return right;
	   }
	   /*
	   * Asks player for value of blank Tile, adds item to blankTiles  , returns value to calling function
	   **/
	private function getBlankTileProxy(pos:int):String{
	   	        login =BlankTileProxyWindow(PopUpManager.createPopUp( this, BlankTileProxyWindow , true));
				var returnedName:String ="";
				login.instruct.text ="Pick a letter for tile \n at "; // works
                // Calculate position of TitleWindow in Application's coordinates.
                // Position it 25 pixels down and to the right of the Button control.
                point1.x=0;
                point1.y=0;                
               // point1=myButton.localToGlobal(point1);
                login.x=point1.x+25;
                login.y=point1.y+25;
             
                // Pass a reference to the TextInput control
                // to the TitleWindow container so that the 
                // TitleWindow container can return data to the main application.
                login.loginName=returnedName;
	   	      return returnedName;
	   } 
	  
	}
}