package mycomponents
{
	public class Player
	{
		private var letters:String;
		private var score:int=0;
		public function Player()
		{
		}
		public function setLetters(letters:String):void{
			this.letters = letters;
		}
		public function getLetters():String{
			return this.letters;
		}
		public function addLetter(letter:String):void {
			this.letters = this.letters + letter;
		}
		public function getScore():int {
			return score;
		}
		public function setScore(score:int):void{
			this.score = score;
		}

	}
}