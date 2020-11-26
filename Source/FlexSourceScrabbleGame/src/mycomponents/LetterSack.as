package mycomponents
{
	public class LetterSack
	{
		import mx.controls.Alert;
		import mycomponents.MyStringUtil;
		private var allLetters:String ="aaaaaaaaabbccddddeeeeeeeeeeeeffggghhiiiiiiiiijkllllmmnnnnnnooooooooppqrrrrrr" + 
				"ssssttttttuuuuvvwwxyyz.."; 
	/**	private var allLetters:String ="aabccdeefghiillmnnoopr" + 
				"ssttuuwy"; */
		private var remainingLetters:String ="";
		private var stringutil:MyStringUtil = new MyStringUtil;
		public function LetterSack()
		{
			
			fillSack(); // start with full sack
		}
		public function getLetters(amount:int):String{
			var newstring:String="";
			//if(remainingLetters.length ==0) newstring = "endofsack";
			if(amount > remainingLetters.length) { amount = remainingLetters.length; }
		 		for(var i:int =0; i < amount ; i++){
				var tempindex:int = Math.round(Math.random() * (remainingLetters.length-1));
				newstring = newstring  + remainingLetters.charAt(tempindex);
				remainingLetters =stringutil.removeCharAt(tempindex, remainingLetters);
					}  trace("Remaining Letters Length " + remainingLetters.length);
					return newstring;	
			
		}
		public function fillSack():void{
			this.remainingLetters =this.allLetters;
			}
		public function addLetter(letter:String):void{
			this.remainingLetters = this.remainingLetters + letter;
		}
		
		public function getLength():int {
			return remainingLetters.length
		}
		

	}
}