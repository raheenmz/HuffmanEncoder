

import java.io.FileReader		;
import java.io.FileWriter		;
import java.io.BufferedReader 	;
import java.io.BufferedWriter 	;
import java.io.DataInputStream;
import java.io.FileInputStream 	;
import java.io.FileOutputStream	;


public class FileOper 
{
	public static BufferedReader initReadText(String file)
	{
		FileReader fr	  = null	;
		BufferedReader br = null	;
		
		try
		{
			fr	= new FileReader(file)	;
			br 	= new BufferedReader(fr);
		}
		catch(Exception E)
		{
			System.out.println("Could Not Open Text File For Reading!!!");
		}
		
		return br	;
	}
	
	public static String readText(BufferedReader br)
	{
		String line ;
		
		try
		{
			if ((line = br.readLine().trim()) != null && !line.isEmpty())
				return line ;
			else
				return null	;
		}
		catch(Exception E)
		{
			return null ;
		}
		
	}
	
	public static void closeReadText ( BufferedReader br)
	{
		try
		{
			br.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not close Buffered Reader!!!");
		}
	}
	
	
	public static BufferedWriter initWriteText(String file) 
	{
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
		}
		catch(Exception E)
		{
			System.out.println("Could Not Open Text File To Write!!!");
		}
		
		return bw	;
	}
	
	public static void writeText(BufferedWriter bw, String line)
	{
		try
		{
			bw.write((line));
		}
		catch(Exception E)
		{
			System.out.println("Unable to Write in Text File!!!");
		}
	}
	
	public static void closeWriteText(BufferedWriter bw)
	{
		try
		{
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not close Buffered Writer!!!");
		}

	}
	
	public static FileOutputStream initWriteByte(String file)
	{
		FileOutputStream fos = null ;
		try
		{
			fos = new FileOutputStream(file) ;
		}
		catch(Exception E)
		{
			System.out.println("Could not open File to write bytes !!!");
		}
		return fos	;
	}
	public static void writeByte(FileOutputStream fos, String line)
	{
		for (int i = 0; i < line.length(); i += 8) 
	    {
	        String byteString = line.substring(i, i + 8); 
	        int parsedByte = 0xFF & Integer.parseInt(byteString, 2);
	        try 
	        {
	        	fos.write(parsedByte); 
	       
	        }
	        catch (Exception e)
	        {
	        	System.out.println("Could not write bytes !!!");	
	        }
	    }
	}
	
	public static void closeWriteByte(FileOutputStream fos)
	{
		try
		{
			fos.close();
		}
		catch(Exception E)
		{
			System.out.println("Unable to close byte file after writing !!!");
		}
	}
	
	public static DataInputStream initReadByte(String file)
	{
		FileInputStream fis = null ;
		DataInputStream dis = null ;
		
		try
		{
			fis = new FileInputStream(file) ;
			dis = new DataInputStream(fis)  ;
		}
		catch(Exception E)
		{
			System.out.println("Could not open File to read bytes !!!" + E.getMessage());
		}
		return dis	;
	}
	
	public static String readByte(DataInputStream dis, int size)
	{
		String code = "" ;
		int available = 0,length;
		
		try
		{
			available = dis.available() ;
		}
		catch(Exception e)
		{
			System.out.println("Error in finding out number of avaiable bytes") ;
		}
		
		if (available == 0)
		{
			return null ;
		}
		
		length = (available<size)?available:size ;
		
		byte b[] = new byte[length] ;
		
		try
		{
			dis.read(b) ;
		}
		catch(Exception E)
		{
			System.out.println("Error in reading Byte file!!!") ;
		}
		
		for(int i=0 ; i<length ; i++)
		{
			code += String.format("%8s", Integer.toBinaryString(b[i] & 0xFF)).replace(' ', '0') ;
		}
		
		//System.out.println("String::"+code) ;
		
		return code ;
	}
		 
	public static void closeReadByte(DataInputStream dis)
	{
		try
		{
			dis.close();
		}
		catch(Exception E)
		{
			System.out.println("Unable to close byte file after reading !!!");
		}
	}

}
