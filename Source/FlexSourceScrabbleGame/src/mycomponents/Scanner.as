package mycomponents
{
	import mx.core.UIComponent;

	public class Scanner extends UIComponent
	{
		import mx.collections.ArrayCollection;
		import mx.events.FlexEvent;
		import mx.controls.Alert;
		import mycomponents.BindableImage;
		import mycomponents.HTTPmethods;
		import mycomponents.Player;
		import mycomponents.ArrayCollectionFilters;
		import mycomponents.BoardUtils;
		import mycomponents.ScanPosition;
		import flash.utils.getTimer;
		private var scanposition:ScanPosition = new ScanPosition;
		private var boardArray:ArrayCollection;
		private var wordlist:ArrayCollection;
		private var goodWords:ArrayCollection;
		private var htt:HTTPmethods;
		private var wordToPlay:ArrayCollection;
	   	private var boardutils:BoardUtils = new BoardUtils;
		private var stringUtil:MyStringUtil = new MyStringUtil;
		private var ScanPos:int =0;  // position on board being scanned for words
		public var Scanorientation:int =1; // scan accross or down 0 is vertical, 1 horizontal
		private var ScanRule:String =""; //keeps track of where at in scan
		private var ScanPosOffSet:int=0; // keeps number of letters prior to ScanPos if they exist,
		private var ScanRack:String // keeps track of current rack for to keep things simple between calls
		private var lastIndex:int = 32; // last square scanned on board , debug purposes
		public function Scanner()
		{
			super();
			this.scanposition.addEventListener("nextPos", ScanFullBoard);
		}
		public function setboardArray(board:ArrayCollection):void {
			this.boardArray = board;
			scanposition.setboardArray(this.boardArray);
		    boardutils.setBoardArray(board);
			
		}
		public function setWordList(word:ArrayCollection):void {
			this.wordlist = word;
			scanposition.setWordList(word);
		}
		public function setGoodWords(words:ArrayCollection):void{
			this.goodWords = words;
			scanposition.setGoodWords(words);
		}
		public function setHTTPmethods(htt:HTTPmethods):void{
			this.htt = htt;
			scanposition.setHTTPmethods(htt);
		//	this.htt.addEventListener("wordsfound", foundWordsRules);
		}
		public function setWordToPlay(obj:ArrayCollection):void {
			this.wordToPlay = obj;
			scanposition.setWordToPlay(obj);
		}
		
		public function setRack(rack:String):void {
			this.ScanRack = rack;
		}
	
		
		public function StartScan():void {
			this.ScanPos =0;
			this.Scanorientation =0;
			
			Scan();
			
		
		}
		private function Scan():void{
		    trace(ScanPos +  Scanorientation, "ScanPos + Scanorientation");
			if(ScanPos == 225 && Scanorientation ==0){
				Scanorientation =1; ScanPos = 0;
			}
			
			if(ScanPos < 225 && boardutils.isPosEmpty(ScanPos)){
				
			 scanposition.startBoardScan(ScanRack,ScanPos,Scanorientation);
			ScanPos++;
			}
			else if(ScanPos < 225 && !boardutils.isPosEmpty(ScanPos)){
			ScanPos++;
			Scan();
			}
			if(ScanPos ==225 && Scanorientation ==1){
				Alert.show((getTimer()/1000).toString());
			}	
			trace("SanPos + total time" ,ScanPos + " " + htt.getTotalTime()/1000);
		}
		
		private function ScanFullBoard(event:FlexEvent):void {
			Scan();
		   //	Alert.show(ScanPos.toString());
			}
		
		
	}
}