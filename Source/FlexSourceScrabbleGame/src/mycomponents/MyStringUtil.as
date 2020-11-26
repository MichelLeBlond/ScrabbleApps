package mycomponents
{
	public class MyStringUtil
	{
		public function MyStringUtil()
		{
		}
		/**
		 * returns string less first instance of char
		 * */
		public function removeChar(string:String, char:String):String{
			var index:int = string.indexOf(char);
				var fronts:String = string.substring(0, index);
				var tails:String = string.substring(index+1 , string.length);
			return fronts + tails;
		}
		/**
		 *  returns string remove string characters removed from target string
		 **/
		public function removeChars(target:String, remove:String):String{
			   
			for(var i:int = 0; i < remove.length; i++){
				target = removeChar(target, remove.charAt(i));
			}
			return target;
		}
		
		public function removeCharAt(pos:int, string:String):String {
               var front:String =  string.substring(0, pos); 
               var end:String = string.substring(pos + 1, string.length); 
               return front + end;
        }
		
		/**
		 * returns Array with indexes for char
		 * */
		public function returnIndexes(string:String,char:String):Array {
			var returnArray:Array = new Array;
			for(var i:int = 0; i < string.length; i++){
				if(string.charAt(i) == char) {
					returnArray.push(i);
				}
			}
			return returnArray;
		}
		/**
		 *  smallest regex must be of form .c++  or c++.  or c++.c++
		 * */
		public function returnMinRegexSize(regex:String):int{
			var returnSize:int =1;
			var cont:Boolean = false;
			if(regex.charAt(0) =="."){
				for(var i:int = 1; i < regex.length; i++){
					if(regex.charAt(i)==".") break;
					else returnSize++;
				}
			}
			else {
				for(var j:int = 2; j < regex.length; j++){
					if(regex.charAt(j)=="."){ cont = true; break;}
					else returnSize++;
				}
			}
			if(cont){
				for(var k:int = returnSize; k < regex.length; k++){
					if(regex.charAt(k)==".") break;
					else returnSize++;
				}
			}
					
			return returnSize;
			
		}

	}
}