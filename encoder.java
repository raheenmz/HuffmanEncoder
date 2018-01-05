import java.io.*;
import java.util.*;

class Node
{
	int value;
	int frequency;
	boolean isLeaf;
	Node left;
	Node right;
	
	public Node(int val,int freq)
	{
		this.value=val;
		this.frequency=freq;
		this.isLeaf=true;
		this.left=null;
		this.right=null;
	}
	
	public Node(Node n1,Node n2)
	{
		this.value=-1;
		this.frequency=n1.frequency+n2.frequency;
		this.isLeaf=false;
		this.left=n1;
		this.right=n2;
	}
}

class HTree
{
	Node root;
	
	public HTree(Node x)
	{
		this.root = x;
	}
}

class D4Heap 
{
	private Node[] heap;
	private int size;
	private int degree;
	
	
	public D4Heap() 
	{
		this.degree = 4;
		this.heap = new Node[100];
		this.size = 0;
		
	}
	
	public void printHeap() 
	{
		for(int i=0;i<size;i++)
		{
			System.out.print(heap[i].value+"->"+heap[i].frequency+"  ");
		}
		System.out.println() ;
	}
	
	public int size() 
	{
		return size;
	}
	
	public void insert(Node n) 
	{
		if (size==0) 
		{
			heap[0] = n ;
			size++;
			return;
		}

		if (size == heap.length) 
		{
			Node[] Heapx2 = new Node[size*2];
			for(int i=0; i<size; i++) 
			{
				Heapx2[i] = heap[i];
			}
			
			heap = Heapx2;
		}
		
		int i = size;
		
		for(; heap[(i-1)/degree].frequency > n.frequency; i=(i-1)/degree) 
		{
			if (i==0) 
				break;
			heap[i] = heap[(i-1)/degree];
		}
		
		heap[i] = n;
		size++;
	}
	
	public Node extractMin() 
	{
		if (size == 0) 
		{ 
			System.out.println("Heap id Empty !!!!");
		}
		
		Node min = heap[0];
		
		Node n1 = heap[size-1];
		
		int firstChild;
		
		int i=0;
		
		for(; (i*degree)+1 < size; i=firstChild) 
		{
			firstChild = (i*degree)+1;
			if (firstChild > size) 
				break; 
			
			int j=1, minChild = firstChild;
			for(; j<degree; j++) 
			{
				if (firstChild+j == size) 
					break;
				if(heap[minChild].frequency > heap[firstChild+j].frequency)
					minChild = firstChild+j;
			}
			
			firstChild = minChild;
			if (n1.frequency > heap[firstChild].frequency) 
			{
				heap[i] = heap[firstChild];
			} 
			else 
			{
				break;
			}
		}
		
		heap[i] = n1;
		size--;
		return min;
	}
}

public class encoder
{
	static HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
	static HashMap<Integer,String> codeTable = new HashMap<Integer,String> () ;

	public static void FreqGen(String file)
	{
		BufferedReader br = null;
		
		String line;
		int key,freq ;
		
		br = FileOper.initReadText(file) ;
		
		
		while((line=FileOper.readText(br)) != null)
		{
			key = Integer.parseInt(line) ;
			
			if (hm.containsKey(key) == true)
			{
				freq = hm.get(key) 		;
				hm.put(key, ++freq)		;
			}
			else
			{
				hm.put(key, 1)			;	
			}
		}
				
		FileOper.closeReadText(br);	
	}
	
	static String generateCode(BufferedWriter bw,Node n, String code) // creates codes from huffman tree
	{	
		String line ;
		
		if ( n.isLeaf == false)
		{
			code	=	generateCode(bw,n.left	,code+"0")	;
			code	=	generateCode(bw,n.right,code+"1")	;
		}
		else
		{
			line = Integer.toString(n.value)+" "+code+"\n" ;
			FileOper.writeText(bw, line)	;
			
			codeTable.put(n.value,code) ;
		}
		
		try
		{
			code = code.substring(0,code.length()-1) ;
		}
		catch(Exception E)
		{
			code = null ;
		}
		
		return code	;
	}
	
	static void encode (String file) // Encoded the data
	{
		BufferedReader br = null;
		FileOutputStream fos = null ;
		int key; String data="",code,line ;
		
		br = FileOper.initReadText(file) ;
		
		fos = FileOper.initWriteByte("./encoded.bin") ;

			while ((line=FileOper.readText(br)) != null && !line.isEmpty())
			{
				key = Integer.parseInt(line);
				code = codeTable.get(key) 	;
				
				if(((data+=code).length())%8 == 0)
				{
					FileOper.writeByte(fos,data);
					data = "" ;
				}
			
			}
			
			FileOper.closeWriteByte(fos);
	}
	
	public static void main(String args[])
	{
		D4Heap d = new D4Heap();
		
		FreqGen(args[0]);
		
		for (HashMap.Entry<Integer,Integer> entry : hm.entrySet())
		{
		    Node n = new Node(entry.getKey(),entry.getValue())			;						;
		    d.insert(n) ;
		   
		}
		
		while(d.size()>1){
			Node n1=d.extractMin();
			Node n2=d.extractMin();
			Node n3=new Node(n1,n2);
			d.insert(n3);
		}
		
		HTree h = new HTree(d.extractMin());
		BufferedWriter bw = FileOper.initWriteText("./code_table.txt");
		generateCode(bw,h.root,"");
		FileOper.closeWriteText(bw)	;
		encode(args[0]) ;
		
	}
}
