package mycomponents
{
	public class BoardUtils
	{
		import mx.collections.ArrayCollection;
	    import mycomponents.BindableImage;
	    import mx.controls.Alert;
	    import mycomponents.MyStringUtil;
	    private var boardArray:ArrayCollection;
	    private var stringUtil:MyStringUtil = new MyStringUtil;
	    private var crossWordsScore:int = 0;
	    

		public function BoardUtils()
		{
		}
		public function getCrossWordsScore():int{
			return this.crossWordsScore;
		}
		public function setCrossWordScore(score:int):void{
			this.crossWordsScore = score;
		}
		public function setBoardArray(board:ArrayCollection):void{
			this.boardArray = board;
		}
		/**
		 * String version of board to send to webserver.
		 * */
		public function getBoardString(board:ArrayCollection):String{
			var pos:String =""; //format as x@y@character,x@y@character 
			for(var i:int=0;i<board.length;i++){
			//if(board[i].value !=0){
				pos = pos + "," + board[i].row + "@" + board[i].column + "@" + board[i].tile;
				//}
			}
			pos =stringUtil.removeCharAt(0,pos);
			
			return pos;
		}
		
		public function getBoardString2(board:ArrayCollection):String{
			var pos:String =""; //format as x@y@character,x@y@character 
			for(var i:int=0;i<board.length;i++){
			//if(board[i].value !=0){
				pos = pos + "," + board[i].column + "@" + board[i].row + "@" + board[i].tile;
				//}
			}
			pos =stringUtil.removeCharAt(0,pos);
			
			return pos;
		}
		
		
		
		/** // 
		 * all scores
		 * returns int score value for string word starting at pos with orientation 
		 * 0 is vertical, 1 horizontal, bingo is if computer used up all 7 letters
		 **/
		public function boardScore(word:String, pos:int , orientation:int):int {
			var wordMultiplier:int =1;
			var multiplier:int =0;
			var score:int =0;
			var type:String;
			var image:BindableImage = new BindableImage;
			
			if(orientation==1){
				for(var i:int = 0; i< word.length; i++){
					
					type = boardArray[pos + i].type;
					multiplier = boardArray[pos +i].multiplier;
					
					 image.setValueFromChar(word.charAt(i));
					if(type =="letter" && isPosEmpty(pos + i)){
						score = score + image.value * multiplier;
					//	trace(pos + i, " pos + i from boardUtils.boarScore lettermuliplier empty");
					}
					else if(type =="word" && isPosEmpty(pos + i)) {
						score = score + image.value
						wordMultiplier = wordMultiplier * multiplier;
					
					}
					else {
						score = score + image.value;
					//	trace(pos + i, " pos + i from boardUtils.boarScore not empty");
					}
					if(pos == 224) break;
				}  
				
				
				
				
			}  //  vertical score
			else 
			{
				for(var j:int = 0; j< word.length; j++){
					 type= boardArray[pos + j * 15].type;
					 image.setValueFromChar(word.charAt(j));
					if(type =="letter"&& isPosEmpty(pos + j * 15)){
						multiplier = boardArray[pos + j * 15].multiplier;
						score = score + image.value * multiplier;
					}
					else if(type =="word" && isPosEmpty(pos + j * 15)) {
						score = score + image.value
						multiplier = boardArray[pos + j * 15].multiplier;
						wordMultiplier = wordMultiplier * multiplier;
					//	trace(pos + j * 15 +  " " +  multiplier + image.char);
					}
					else {
						score = score + image.value;
					}
				}
				
			}
			var bingo:Boolean =  isBingo(word, orientation ,pos);
			if(bingo){ score = score +50;}
			return score * wordMultiplier;
		}
		public function getXfromPos(pos:int):int{
			var x:int; 
			if(pos < 15){
				x = 16 + pos * 46;
			}
			else {
				x = 16 + (pos +1)%15 * 46 - 46;
				if(x==-30) x= 660;
			} // trace(x + "  x from getXfromPos BoardUtils");
			return x; 
		}
		public function getYfromPos(pos:int):int{
			var y:int= 15;
			if(pos > 14){
				y = 14 + (pos - pos%15)/15 * 46;
			}
			return y;
		} 
		
		public function getColumnFromPos(pos:int):int{
			return boardArray[pos].column;
		}
		
		public function getRowFromPos(pos:int):int {
			return boardArray[pos].row;
		}
		
		/**
		 * returns the index array on the board for a particular row, column spot
		 * */
		public function getPos(row:int, column:int):int {
			return	 row * 15 + column
			}
		
		public function isPosEmpty(pos:int):Boolean{
			if(boardArray[pos].value ==0){
			return true;
			}
			else {
			return false;
			}
		}
		
		public function isBingo(word:String, orientation:int, startpos:int):Boolean{
			var boolean:Boolean = false;
			var rackchars:int = 0;
			// down 
			trace ("from is bingo ", word);
			if(orientation ==0){
				for(var i:int =0; i < word.length; i++){
					//if(boardArray[startpos + i].value ==0) rackchars++;
					if(boardArray[startpos + i * 15].value ==0){ rackchars++;
					trace(boardArray[startpos + i * 15].value + " " +boardArray[startpos + i * 15].tile + " " + (startpos 
					+ i * 15 ) + " " + startpos);
					 }
				}
			}
			// across
			else {
				for(var j:int =0; j < word.length; j++){
				//if(boardArray[startpos + (j * 15)].value ==0) rackchars++;
				if(boardArray[startpos + j].value ==0){ rackchars++;
				trace(boardArray[startpos + j].value + " " +boardArray[startpos + j].tile + " " + startpos + j);
				 }
				}
			}
			if(rackchars ==7) boolean =true;
			return boolean;
		}
		/**
		 * returns number of characters prior to ScanPos 
		 * orientation 0 is vertical, 1 horizontal
		 **/
		public function getScanPosOffSet(pos:int, orientation:int):int{
			var posOffSet:int=0;
			if(orientation == 1){
				pos--;
				while(boardArray[pos].value !=0 && boardArray[pos].column >=1) {
					// var temp:int = pos;
					posOffSet++;
					pos--;
					if( boardArray[pos].column==15|| pos < 0) break;
					}
			}
			else {
				pos = pos -15;
				while(boardArray[pos].value !=0 && boardArray[pos].row >=1) {
					posOffSet++;
					pos = pos -15;
					if(pos < 0) break;
					} 
                  				
			}
			return posOffSet;
		}
		/**
		 * Returns a regex for starting pos 
		 * .. for empty, char value if something there
		 * */
		public function getRegexDown( pos:int, size:int):String{
			 var presentPattern:String = "";
			 var count:int = size;
			 var currentpos:int = pos;
			
			     	
			 while(count >0){
			 	if(getRowFromPos(currentpos) <=15){
			  		if(!isPosEmpty(currentpos)){
			 		presentPattern = presentPattern + boardArray[currentpos].tile;
			 		}
			 		else{
			 			presentPattern = presentPattern + ".";
			 			count--;
			 		}
			 		if( getRowFromPos(currentpos)==15) break;
			 	 currentpos = currentpos + 15;
			 	}
			 	else {
			 		count =0;
			 	}
			 }
			 // if all letters left in rack are used up and next spot on board has a tile
			 if(stringUtil.returnIndexes(presentPattern,".").length == size && getRowFromPos(currentpos)<15){
			 	  	if(!isPosEmpty(currentpos)){
			 			presentPattern = presentPattern + boardArray[currentpos].tile;
			 		}
			 } 
			 trace("FirstRegex ", presentPattern);
			 return presentPattern;
		}
		
		public function getRegexAccross(pos:int, size:int):String{
			 var presentPattern:String = "";
			 var count:int = size;
			 var currentpos:int = pos;
			 			
			 while(count > 0){ 
			 	if(getColumnFromPos(currentpos) <=15){ 
			  		if(!isPosEmpty(currentpos)){
			 		presentPattern = presentPattern + boardArray[currentpos].tile;
			 		}
			 		else{
			 			presentPattern = presentPattern + ".";
			 			count--;
			 		}
			 		if( getColumnFromPos(currentpos)==15) break;
			 	 currentpos++;
			 	}
			 	else {
			 		count =0;
			 	}
			 	
			 }
			 // if all letters left in rack are used up as .... s and next spot on board has a tile
			 if(stringUtil.returnIndexes(presentPattern,".").length == size && getColumnFromPos(currentpos)<15){
			 	  	if(!isPosEmpty(currentpos)){
			 			presentPattern = presentPattern + boardArray[currentpos].tile;
			 		}
			 } 
			 trace("FirstRegex ", presentPattern);
			 return presentPattern;
					 
		}
		
		/**
		 * Returns longest regex and smaller ones in comma separated string
		 *   adds xx. if pattern xx..x but not if patter xx.x
		 * 
		 * */
		public function getRegexSet(regex:String):String {
			var tempvar:String = regex;
			var returnvar:String=regex;
			for(var i:int =regex.length-1; i >1; i--){
				tempvar = stringUtil.removeCharAt(i,tempvar);
				if(regex.charAt(i)=="." && stringUtil.returnIndexes(tempvar,".").length>0
				&& tempvar.length > stringUtil.returnIndexes(tempvar,".").length){ // added last &&
					returnvar = returnvar + "," + tempvar;
				}
			}
			
			return returnvar;
		}
		
		/** for finding letters beside column, returns 
		 *  -1 if no match, 0 for first position, 1 for 
		 * 1 down etc. 
		 * */
		public function getNextToDownIndex(pos:int, size:int):int{
			var returnIndex:int =-1;
			 for(var i:int=0;i<size;i++){
			 	if(getColumnFromPos(pos)==1){
			 		if(boardArray[pos +1].value !=0){
			 			returnIndex =i;
			 			break;
			 		}
			 	}
			 	else if(getColumnFromPos(pos) ==15) {
			 		if(boardArray[pos -1].value !=0){
			 			returnIndex =i;
			 			break;
			 		}
			 	}
			 	else{
			 		if(boardArray[pos -1].value !=0 || boardArray[pos +1].value !=0){
			 			returnIndex =i;
			 			break;
			 		}
			 	}
			 	
			 	if(getRowFromPos(pos) == 15){
			 		break;
			 	}
			 	pos = pos +15;
			 	
			 }
			return returnIndex;
		}
		/** for finding letters beside row, returns 
		 *  -1 if no match, 0 for first position, 1 for 
		 * 1 down etc. 
		 * */
		public function getNextToAccrossIndex(pos:int, size:int):int{
			var returnIndex:int =-1;
			 for(var i:int=0;i<size;i++){
			 	if(getRowFromPos(pos)==1){
			 		if(boardArray[pos +15].value !=0){
			 			returnIndex =i;
			 			break;
			 		}
			 	}
			 	else if(getRowFromPos(pos) ==15) {
			 		if(boardArray[pos -15].value !=0){
			 			returnIndex =i;
			 			break;
			 		}
			 	}
			 	else{
			 		if(boardArray[pos -15].value !=0 || boardArray[pos +15].value !=0){
			 			returnIndex =i;
			 			break;
			 		}
			 	}
			 	
			 	if(getColumnFromPos(pos) == 15){
			 		break;
			 	}
			 	pos++;
			 	
			 }
			return returnIndex;
		}
		
		/** 
		 * orientation 0 is vertical, 1 horizontal
		 * */
			public function getCrossWords(word:String, pos:int, orientation:int):String {
				crossWordsScore = 0;
				var returnvar:String ="";
				var tempPos:int = pos;
				var columnIndex:int = boardArray[pos].column;
				var rowIndex:int = boardArray[pos].row;
				var tempwordOffset:int = 0;
				var tempLeft:String ="";
				var tempRight:String="";
				if(orientation ==0) {
					for(var i:int =0; i < word.length; i++){
						tempLeft = "";
						tempRight = "";
						tempPos = pos +(i * 15);
						//trace("tempPos", tempPos);  
						// move left from tempPos adding to word until space or edge is found
						for(var j:int=1;j< boardArray[tempPos].column; j++){
							//  trace("tempPos - j ", tempPos - j);
							 if(boardArray[tempPos - j].value ==0) {
							 	
							 	j = boardArray[tempPos].column;
							  } 
							else {
								if(boardArray[tempPos].value==0){
									tempwordOffset++;
								tempLeft = boardArray[tempPos - j].tile + tempLeft;
							//	trace("tempPos2", tempPos);
							//trace("leftPos tile",boardArray[tempPos - j].tile);
								 }
							} 
						}
						// move right from tempPos adding to word until space or edge is found
						for(var k:int=1;k<= 15 - columnIndex; k++){
						//	  trace("tempPos +k ", tempPos  +k);
							if(boardArray[tempPos + k].value ==0) {
								//k= boardArray[tempPos].column;
								k= 15 - columnIndex ;
							} 
							else { if(boardArray[tempPos].value==0){
							//	trace("tempPos3", tempPos);
							//	trace("Right pos tile",boardArray[tempPos + k].tile);
								tempRight = tempRight + boardArray[tempPos + k].tile; }
							}  
						}
						
						//Alert.show(tempWord);
						if((tempLeft + tempRight).length > 0) {
						returnvar = returnvar + "," + tempLeft + word.charAt(i) + tempRight;
						crossWordsScore = crossWordsScore + boardScore(tempLeft + word.charAt(i) + tempRight,tempPos-tempwordOffset, 1);
					//	trace("crossword and cumulative score " , crossWordsScore + word);
						}
					}
								
				}
				else {
					// TODO accross method
					for(var l:int =0; l < word.length; l++){
						tempLeft = "";
						tempRight = "";
						tempPos = pos +l;
						//trace("tempPos", tempPos);  
						// move up from tempPos adding to word until space or edge is found
						for(var m:int=1;m< boardArray[tempPos].row; m++){
							//  trace("tempPos - j ", tempPos - j);
							 if(boardArray[tempPos - m *15].value ==0) {
							 	
							 	m = boardArray[tempPos].row;
							  } 
							else {
								if(boardArray[tempPos].value==0){
									tempwordOffset++;
								tempLeft = boardArray[tempPos - m*15].tile + tempLeft;
							//	trace("tempPos2", tempPos);
							//trace("leftPos tile",boardArray[tempPos - j].tile);
								 }
							} 
						}
						// move down from tempPos adding to word until space or edge is found
						for(var n:int=1;n<= 15 - rowIndex; n++){
						//	  trace("tempPos +k ", tempPos  +k);
							if(boardArray[tempPos + n*15].value ==0) {
								//k= boardArray[tempPos].column;
								n= 15 - rowIndex ;
							} 
							else { if(boardArray[tempPos].value==0){
							//	trace("tempPos3", tempPos);
							//	trace("Right pos tile",boardArray[tempPos + k].tile);
								tempRight = tempRight + boardArray[tempPos + n*15].tile; }
							}  
						}
						
						//Alert.show(tempWord);
						if((tempLeft + tempRight).length > 0) {
						returnvar = returnvar + "," + tempLeft + word.charAt(l) + tempRight;
						crossWordsScore = crossWordsScore + boardScore(tempLeft + word.charAt(l) + tempRight,tempPos-tempwordOffset*15, 0);
					//	trace("crossword and cumulative score " , crossWordsScore + word);
						}
					}
					
					
					
				}
				returnvar = stringUtil.removeCharAt(0,returnvar); 
		//	trace("returnvar BoardUtils getCrossWords", returnvar + "  " + word);
				if(returnvar.length==0) returnvar ="good";
			//	Alert.show(returnvar);
				return returnvar;	
			}
		

	}
}