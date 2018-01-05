import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;

 class DNode 
{
	
	public int value ;
	public int freq  ;
	
	public DNode left ;
	public DNode right;

	public DNode(int value, int freq)
	{
		this.value 	=	value	;
		this.freq  	=  	freq	;
		left		=  	null	;
		right		=  	null	;
		
	}
	
	public DNode(int freq, DNode left, DNode right)
	{
		value 		=	-100000	;
		this.freq  	=  	freq	;
		this.left	=	left	;
		this.right	=	right	;
	}
}


 class decoder 
{
	static void decode (DNode root, String file )
	{
		DataInputStream dis = null ;
		BufferedWriter bw = null ;
		String s="",bytes = null ;
		DNode curr ;
		int i ;
		
		dis = FileOper.initReadByte(file) ;
		bw = FileOper.initWriteText("./decoded.txt") ;
		
		while(true)
		{
			s = s + FileOper.readByte(dis, 2)	;
			curr = root	;
			i = 0 ;
			
			while (i<s.length())
			{
				if (s.charAt(i) == '0')
					curr = goLeft(curr) ;
				else
					curr = goRight(curr) ;
				
				if (curr.value != -100000)
				{
					FileOper.writeText(bw, Integer.toString(curr.value)+"\n");
				    s = s.substring(i+1) ;
				    if ((bytes = FileOper.readByte(dis,2)) != null)
				    	s = s + bytes	;
				    i = -1	;
				    curr = root 	;
				     ;
				}
				i++ ;

			}
			
			
			
			/*Could not reach code till end of two bytes then add to more bytes and continue. 
			 * if number of bytes was less than then possibility of additional bits added so skip */
			if (s.length() < 8)
				break ;
		}
		
		FileOper.closeReadByte(dis);
		FileOper.closeWriteText(bw);		
	}
	
	static DNode recreateHuffmanTree(String file)
	{
		int value ;
		String line,code ;
		DNode curr,next ;
		
		DNode root = new DNode (-100000,0);
		
		BufferedReader br = null ;
		
		br = FileOper.initReadText(file) ;
		
		while ((line=FileOper.readText(br)) != null && !line.isEmpty())
		{
			value = Integer.parseInt(line.substring(0,line.indexOf(' '))) ;
			code = line.substring(line.indexOf(' ')+1)						;
			curr = root ;
		    int i = 0 ;
		    
		    while (i<code.length())
		    {
		    	if(code.charAt(i) == '0')
		    	   next = addToLeft(curr) ;
		    	else
		    		next = addToRight(curr) ;
		    	curr = next ;
		    	i++ ;
		    }
		    curr.value = value ;
		}
		
		FileOper.closeReadText(br);
		
		return root ;
	}

	static DNode addToLeft(DNode curr)
	{
		DNode next ;
		
		if (curr.left != null)
		{
			next = curr.left ;
		}
		else
		{
			next = new DNode (-100000,0) ;
			curr.left = next 			;
		}
		
		return next	;
	}
	
	static DNode addToRight(DNode curr)
	{
		DNode next ;
		
		if (curr.right != null)
		{
			next = curr.right ;
		}
		else
		{
			next = new DNode (-100000,0) ;
			curr.right = next 			;
		}
		
		return next	;
	}
	
	static DNode goLeft (DNode curr)
	{
		return curr.left ;
	}
	
	static DNode goRight(DNode curr)
	{
		return curr.right ;
	}
	
	public static void main (String args[])
	{
		DNode root=null ;
		
		root = recreateHuffmanTree(args[1]) ;
		
		decode (root, args[0]) ;
		
	}
}
