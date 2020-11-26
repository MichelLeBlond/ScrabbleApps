package mycomponents


{
	  [Event(name="wordsfound", type="mx.events.FlexEvent")]

		import flash.events.Event;
		import flash.external.ExternalInterface;

		import mx.collections.ArrayCollection;
		import mx.core.UIComponent;
		import mx.events.FlexEvent;
		import mx.rpc.events.ResultEvent;
		import mx.rpc.http.HTTPService;

	public class HTTPmethods extends  UIComponent
	{
		//private var webroot:String = "www.101-freethingstodo.com";
		// private var webroot:String = "michel.shuttle.net:8080/ScrabbleServlet/";
		// private var webroot:String = "104.199.127.172:8080/ScrabbleServlet/";
			private var webroota:String = ExternalInterface.call("window.location.href.toString");
		private var splitURL:Array = webroota.split('/');
		//private var webroot:String = splitURL[2]  + ":8080/ScrabbleServlet/"; 
             //   private var webroot:String = splitURL[2]  + "/ScrabbleServlet/"; most recent getPotentialWords
		private var webroot:String = splitURL[2] +"/";
		//private var webroot:String = "michel_leblond.s155.eatj.com/ScrabbleServlet/";
		private var AllWords:HTTPService = new HTTPService;
		private var wordlist:ArrayCollection;
		private var totalHTTPtime:Number =0;
		private var timex1:Number =0;
		//private var time1:Date;

		public function HTTPmethods()
		{



			AllWords.addEventListener(ResultEvent.RESULT,returnedWords);
		}
		public function getTotalTime():Number{
			return this.totalHTTPtime;
		}

		public function setWordList(ar:ArrayCollection):void{
			this.wordlist = ar;
		}

		public function findAllWords(board:String, rack:String):void{

		//AllWords.addEventListener(ResultEvent.RESULT,returnedWords);
		var newurl:String = "http://" + webroot +  "?board=" + board + "&rack=" + rack;
		AllWords.url = newurl; AllWords.send();
		var time1:Date =new Date();
		   timex1 = time1.getTime();
				}
		public function validateWord(board:String, word:String,	wordDirection:String, rack:String, x:int, y:int):void{
			var newurl:String = "http://" + webroot + "?board=" + board + "&rack=" + rack +"&word=" + word +
			"&wordDirection=" + wordDirection +"&x=" + x + "&y=" +y;
		//	trace(x + " x ");
		//	trace(y + " y ");
		//	trace(word + " word");
		//	trace(rack + " rack");
		//	trace(wordDirection + " Direction");
		//	trace(board + " board");
		AllWords.url = newurl; AllWords.send();
		var time1:Date =new Date();
		   timex1 = time1.getTime();
		}

		public function AreValidWords(search:String):void{

	//	AllWords.addEventListener(ResultEvent.RESULT,returnedWords);
		var newurl:String = "http://" + webroot + "/flex/AreWordsSQL.php" + "?data=" + search;
		AllWords.url = newurl; AllWords.send();
		var time1:Date=new Date();
		timex1 = time1.getTime();
		}
		public function getPotentialWords(data:String, rack:String):void{
		//	AllWords.addEventListener(ResultEvent.RESULT,returnedWords);
		//Alert.show("got to getPotentialwords" + data + "  " + rack);
			var newurl:String = "http://" + webroot + "/flex/ScanMathSQL2.php" + "?data=" + data +
			"&rack=" + rack;
			AllWords.url = newurl; AllWords.send();
			var time1:Date=new Date();
			timex1 = time1.getTime();
		}

		private function returnedWords(evt:ResultEvent):void
		{
			trace(wordlist.toString() + "  wordlist to string");
		//	wordlist.removeAll();
		 /**  for(var h:int=0; h < wordlist.length; h++){
				wordlist.removeItemAt(h);
			}  */
			var time2:Date = new Date();
			var tempdiff:int =0;

			tempdiff = time2.getTime()- timex1;



		totalHTTPtime = totalHTTPtime + tempdiff;
		 trace("Time for HTTP resuts to return", tempdiff);

				try {
		var tempCollection:ArrayCollection = AllWords.lastResult.words.word;
		for(var i:int =0; i < tempCollection.length; i ++){
			wordlist.addItem(tempCollection[i]); }
		dispatchEvent(new FlexEvent("wordsfound"));
			     	}
	   		// catchs cases where  one or no record is returned.
			   catch(err:Error){
					try {
					wordlist.addItem(AllWords.lastResult.words.word);
				dispatchEvent(new FlexEvent("wordsfound"));
				  	  }
				  	catch(err:Error){
				  		//Alert.show(err.toString());
				  		dispatchEvent(new FlexEvent("wordsfound"));
				  		trace("caught http error" ,err.toString());
				 	  }
				   	}
		}


	}
}
