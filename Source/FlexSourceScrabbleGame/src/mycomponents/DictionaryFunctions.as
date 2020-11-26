package mycomponents
{
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.mxml.HTTPService;
	import mx.controls.Alert;
	public class DictionaryFunctions { 
	
		var webroot:String = "www.101-freethingstodo.com";
		var feedRequest:HTTPService = new HTTPService;
		var returnarray:Array;
		var returnarCollection:ArrayCollection;
		var recieved:Boolean = false;
		
		public function DictionaryFunctions()
		{
		}
		
		
		/**
		 * 
		 * @param word
		 * @return 
		 * 
		 */		
		public function isWord(word:String):Boolean{
			return false;
		}
		
		public function getWords(string:String):void {
			
			
			var newurl:String = "http://" + webroot + "/flex/CountOrLessSQL.php" + "?data=" + string;
					
					feedRequest.url = newurl;
					feedRequest.addEventListener(ResultEvent.RESULT, returner);
					feedRequest.send();
					/** delay wai
					while(!recieved){
						
					} ***/
					
					
					 
						
					
		}
		public function returner(result:Event):void{
				try {
			this.returnarCollection = feedRequest.lastResult.words.word;
		          	for(var j:int = 0; j < this.returnarCollection.length; j++){ 
		          		returnarray.push(this.returnarCollection.getItemAt(j).value.toString());
		          	}
			}
			
			catch(err:Error) {
				returnarray.push("nogood");				
			}
			this.recieved = true;
			
		}

	}
}