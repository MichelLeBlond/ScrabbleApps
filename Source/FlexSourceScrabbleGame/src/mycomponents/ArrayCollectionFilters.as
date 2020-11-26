package mycomponents
{
	public class ArrayCollectionFilters
	{
	
	import mx.collections.ArrayCollection;
	private var boardArray:ArrayCollection;
	private var row:int;
	private var column:int;
		public function ArrayCollectionFilters()
		{
		}
		public function setboardArray(board:ArrayCollection):void {
			this.boardArray = board;
		}
		public function filterbyrow(item:Object):Boolean{
					return item.row == this.row;
		}
		public function filterbycolumn(item:Object):Boolean{
				return item.column == this.column;
		}
		
		 public function sortOnScore(a:Object, b:Object):Number{
	   		if(a.score > b.score) {
	   			return 1;
	   		}
	   		else if(a.score <b.score) {
	   			return -1;
	   		}
	   		else {
	   			return 0;
	   		}
	   }
		

	}
}