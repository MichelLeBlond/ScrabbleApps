package mycomponents
{
	import mx.controls.Image;
     [Bindable]
	public class BindableImage extends Image
	{   
		
		public var value:int;
		public var char:String;
		public var oldx:int;
		public var oldy:int;
		public var src:String;
		/**
		 *  keep track of where tile came from 
		 */		
		public var pos:int
		public function BindableImage()
		{
			super();
		   	this.source ="assets/a.gif";
		   //	this.pos = 1000;
		   	this.src ="rack";
			
			
		}
		
		public function setValueFromChar(chr:String):void{
			if(chr =="a"|| chr =="e" || chr =="i"|| chr =="o" || chr =="u"|| chr =="n" || chr =="r"|| chr =="t" || chr =="l"|| chr =="s"){
				this.value = 1;
				
			}
			else if(chr =="d"|| chr =="g" ){
				this.value = 2;
			}
			else if(chr =="b"|| chr =="c" || chr =="m"|| chr =="p"){
				this.value = 3;
			}
			else if(chr =="f"|| chr =="h" || chr =="v"|| chr =="w" || chr =="y"){
				this.value = 4;
			}
			else if(chr =="j"|| chr =="x" ){
				this.value = 8;
			}
			else if(chr =="q"|| chr =="z" ){
				this.value = 10;
			}
			else if(chr =="k"){
				this.value = 5;
			}
			else if(chr=="." || chr.toUpperCase() == chr){
				this.value =-1;
			}
			this.char = chr;
			if(chr=="." || chr.toUpperCase() == chr){ 
				this.source = "assets/bl.gif"; this.toolTip = chr;
			}
			else {
				this.source = "assets/" + chr + ".gif";
			}
			
			
			
		}
		
	}
}