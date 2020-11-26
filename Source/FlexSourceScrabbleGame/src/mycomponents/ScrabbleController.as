package mycomponents
{
	/***
	 * Controller delegates tasks to ScrabbleRules that does HTTPmethods
	 * following rules.  Rules return word with position to laydown for 
	 * computer.  Controller  creates players, loads rack, lays down words, 
	 * keeps track of turns 
	 * 
	 * */ 
	import mx.controls.Text;
	import mx.events.FlexEvent;
	 [Bindable]
	public class ScrabbleController
	{
	import mx.controls.Image;
	import mx.controls.Alert;
	import mx.collections.ArrayCollection;
    import mycomponents.Rack;
    import mycomponents.LetterSack;
    import mycomponents.ScrabbleRules;
    import mycomponents.Player;
    import mycomponents.HTTPmethods;
    import mycomponents.scrabbleBoard;
    import mycomponents.DragableImage;
    import mycomponents.BoardUtils;
    import mx.controls.Text;

  
 
	private var rack:Rack;
	private var scrabbleboard:scrabbleBoard;
	private var lettersack:LetterSack = new LetterSack;
	private var rules:ScrabbleRules = new ScrabbleRules;
	private var Players:Array; 
	private var playingComputer:Boolean = true;
	private var wordlist:ArrayCollection;
	private var goodWords:ArrayCollection;
	private var playscore:Text;
	private var compscore:Text;
	private var gameOver:Boolean = false;

	/** word computer will play
	 * 
	 * **/
	private var wordToPlay:ArrayCollection = new ArrayCollection;
	private var htt:HTTPmethods = new HTTPmethods;
	private var boardUtil:BoardUtils = new BoardUtils;
	private var preturnString:String="";
	private var preturnString2:String="";
	private var blankTiles:ArrayCollection = new ArrayCollection;
	
	private var turnFlag:Boolean = false; // issue with board sent to server being at right angles.
	/**
	 * simple counter keeps track of whos turn it is
	 **/
	private var turn:int = 0;
	private var numPlayers:int;
	    
		public function ScrabbleController()
		{
		 rules.addEventListener("PlaceWord",placeWord);
		 rules.addEventListener("PlayerDone",wordPlayed);
		 rules.setBlankTiles(blankTiles);
		}
		public function setDisplayItems(compscore:Text , playscore:Text):void{
			this.playscore = playscore; this.compscore = compscore;
		}
		
	    public function testScrabbleRules():void{
	    	playWord();
	    }
	    
	    public function setPreturnString():void{
	    	this.preturnString = boardUtil.getBoardString(scrabbleboard.boardArray); 
	    	this.preturnString2 = boardUtil.getBoardString2(scrabbleboard.boardArray); 
	    }
	
		public function setWordList(wordlist:ArrayCollection):void {
			this.wordlist = wordlist;
			htt.setWordList(wordlist);
			rules.setHTTPmethods(htt);
			rules.setWordList(wordlist);
			rules.setWordToPlay(wordToPlay);
		}
		public function setGoodWords(words:ArrayCollection):void {
			this.goodWords = words;
			rules.setGoodWords(words);
		}
		public function fillRack(tempstring:String):void {
			  	var n:int = 17; 
				for(var i:int =0; i < tempstring.length ; i++){
					var image:DragableImage = new DragableImage;
				image.setValueFromChar(tempstring.charAt(i));
				image.x=n; image.width =41; image.height=41;
				image.oldx = n;
				image.src="rack"
				rack.addChild(image);
				n= n+ 41;
				}
								
			}
	
			
		public function setRack(rack:Rack):void {
			this.rack = rack;
		}
		public function setBoard(board:scrabbleBoard):void {
			this.scrabbleboard = board;
			rules.setboardArray(this.scrabbleboard.boardArray);
		}
		
		public function addPlayer():void {
			var player:Player = new Player();
			player.setLetters(lettersack.getLetters(7));
			Players.push(player);
			}
		public function Start(numPlayers:int):void{
			// clear out previous game
			this.gameOver = false;
			this.turnFlag = false;
			this.compscore.text = "";
			this.playscore.text = "";
		    Players =  new Array;
			wordlist.removeAll();
			wordToPlay.removeAll();
			blankTiles.removeAll();
			lettersack.fillSack();
			scrabbleboard.removeAllChildren();
			rack.removeAllChildren();
			for(var j:int =0; j< scrabbleboard.boardArray.length; j++){
				scrabbleboard.boardArray[j].value = 0;
				scrabbleboard.boardArray[j].tile = null;
			}
			preturnString="";
			preturnString2="";
			turn =0;
			this.numPlayers = numPlayers;
			for(var a:int = 0; a < numPlayers; a++) {
				addPlayer();
			} trace(Players.length + "Players Length");
			if(playingComputer){
				var computer:Player = Players[0];
				// computer finds best firstword and plays
			  rules.computerMove(computer,preturnString);
			}
		}
		
		public function nextTurn():void {  
			this.preturnString = boardUtil.getBoardString(scrabbleboard.boardArray); 
			this.preturnString2 = boardUtil.getBoardString2(scrabbleboard.boardArray); 
			if(turn < numPlayers - 1)turn ++;
			else turn = 0; 
			// computers turn
			if(turn == 0 && playingComputer){ 
			//	trace("playing computer", turn);
				var computer:Player = Players[0];
				 rules.computerMove(computer, preturnString);
			}
			//  players turn fill rack with letters from player 
			else {
				fillRack(Players[turn].getLetters());
			
			}
			 
		}
		
		public function playWord():void{  trace("playword 152");
			wordlist.removeAll(); wordlist.refresh();// was in HTTPmethods threw error sometimes
			rules.playerMove(Players[turn], preturnString2);
		}
		
		private function wordPlayed(event:FlexEvent):void{
			var player:Player = Players[turn];
			var newword:String ="";
			for(var i:int = 0; i <rack.numChildren; i++){
					var image:BindableImage = rack.getChildAt(i) as BindableImage;
					newword = newword + image.char;
				}
		if(wordlist[0].valid == "ok"){
			 player.setScore(player.getScore() + wordlist[0].value);
				var x:int = 7 - newword.length; //trace(newword);
				if(lettersack.getLength()!=0){ 
					var adword:String = lettersack.getLetters(x);
			// trace(adword);
			 player.setLetters(adword + newword);
			// trace(player.getLetters());
			 	}
			    else if(lettersack.getLength()==0 && newword.length ==0 ){ 
			   Alert.show("player ends game"); this.gameOver = true;
			   //TODO , later there could be more players loop through to get computers points for now
			   var comp:Player = Players[0] as Player;
			   var ciScore:int =0;
			     for(var ci:int =0; ci< comp.getLetters().length;ci++){
			     	var imga:BindableImage = new BindableImage;
			     	imga.setValueFromChar(comp.getLetters().charAt(ci));
			     	ciScore = ciScore + imga.value;
			     }
			    player.setScore(player.getScore() + wordlist[0].value + ciScore);
			    this.compscore.text = (Players[0].getScore() - ciScore).toString();
			    } 
			     else {
			   	player.setLetters(newword);
			  	Alert.show("Sack is empty, still has tiles " + newword);
			   	
			   }
				 
			}
			else {
				Alert.show(wordlist[0].valid);
					var z:int = player.getLetters().length - newword.length;
				for(var im:int = 0; im < z; im++){  
				var img:BindableImage = scrabbleboard.getChildAt(scrabbleboard.numChildren-1) as BindableImage;
	    		scrabbleboard.boardArray[img.pos].value = 0;
	    		scrabbleboard.boardArray[img.pos].tile = null;
	    		scrabbleboard.removeChildAt(scrabbleboard.numChildren-1); } 
			 } 
			 this.turnFlag = true;
			 rack.removeAllChildren();
			//Alert.show(boardUtil.getBoardString(scrabbleboard.boardArray));
			// this.preturnString = boardUtil.getBoardString(scrabbleboard.boardArray); 
			this.playscore.text = player.getScore().toString();	
			wordlist.removeAll();
		    if(!gameOver) nextTurn(); 
		    
		}
		
		/**
		 * places word on board for computer
		 * */
		public function placeWord(event:FlexEvent):void {
			    var player:Player = Players[turn];
		//wordlist.removeAll(); // was in HTTPmethods threw error sometimes
			if(wordToPlay[0].value=="NoWords"){
				Alert.show("No words for computer to play this turn");
				trace("Line 200 ScrabbleController " + player.getLetters() + lettersack.getLength().toString());
			}
			else{
				var pos:int;
				var orientation:int;
			  if(turnFlag){
					pos = boardUtil.getPos(wordToPlay[0].column, wordToPlay[0].row); 
					if(wordToPlay[0].orientation ==1){
						orientation = 0;
					}
					else {
						orientation = 1;
				    }
		      }
			  else { // first turn
			 		pos = boardUtil.getPos(wordToPlay[0].row, wordToPlay[0].column); 
					orientation = wordToPlay[0].orientation;
			  } 
			//   pos = boardUtil.getPos(wordToPlay[0].row, wordToPlay[0].column); 
			//var word:String = wordToPlay[0].value.toLowerCase(); 
			var word:String = wordToPlay[0].value; 
			// orientation  = wordToPlay[0].orientation;
			var score:int = wordToPlay[0].score;
			/* To Do  only add tile if no tile on space 
			*/
			for(var i:int =0; i <word.length; i++){ 
				var image:DragableImage = new DragableImage;
				// need to test if wild cards for blank tile routine
				    if(word.charAt(i).toLowerCase() == word.charAt(i)){
				    	image.char = word.charAt(i).toUpperCase();
				    }
				    else {
				    	image.char = word.charAt(i).toLowerCase();
				    }
					image.setValueFromChar(image.char); 
					
					image.x=  boardUtil.getXfromPos(pos); image.y =  boardUtil.getYfromPos(pos); image.width =41; image.height=41;
					image.pos = pos;
					image.src ="gameBoard"; 
					image.oldx =  boardUtil.getXfromPos(pos);
					image.oldy = image.y =  boardUtil.getYfromPos(pos);
					if(scrabbleboard.boardArray[pos].value ==0){
					scrabbleboard.addChild(image); }
					scrabbleboard.boardArray[pos].value = image.value; 
					scrabbleboard.boardArray[pos].tile =  image.char;
					if(orientation==1){
						pos++
					}
					else {
						pos = pos + 15;
					}
			}
			 
			  var x:int = 0; var afterrack:String ="";
			  if(wordToPlay[0].rackafter!= null){
			  	x = wordToPlay[0].rackafter.length; 
			  	afterrack = wordToPlay[0].rackafter;
			  }
			  if(lettersack.getLength()!=0){
			  	var letterFromSack:String = lettersack.getLetters(7-x).toUpperCase();
			 player.setLetters( letterFromSack + afterrack);
			   trace("letterFromSack + afterrack " + letterFromSack + afterrack);  
			 player.setScore(player.getScore() + score);
			 this.compscore.text = player.getScore().toString(); 
			 	}
			 else if(lettersack.getLength()==0 && wordToPlay[0].rackafter== null ){ 
			   Alert.show("end of game, computer finished"); trace("computer finished game");
			   this.gameOver = true;
			    //TODO , later there could be more players loop through to get Players[1] points for now
			   var comp:Player = Players[1] as Player;
			   var ciScore:int =0;
			     for(var ci:int =0; ci< comp.getLetters().length;ci++){
			     	var img:BindableImage = new BindableImage;
			     	img.setValueFromChar(comp.getLetters().charAt(ci));
			     	ciScore = ciScore + img.value;
			     }
			    player.setScore(player.getScore() + score + ciScore);
			    this.compscore.text = player.getScore().toString();
			    this.playscore.text = (Players[1].getScore() - ciScore).toString();
			   
			   
			  } 
			  
			 else {
			   	  player.setScore(player.getScore() + score);
			 this.compscore.text = player.getScore().toString(); 
			  player.setLetters(wordToPlay[0].rackafter);
			  	Alert.show("computer emptied sack, still has tiles " + wordToPlay[0].rackafter);
			   	
			   }
				//Alert.show(player.getLetters() + player.getScore().toString());
			}
			   
			   if(!gameOver){
				nextTurn(); }
			   
		}
		
	}
}