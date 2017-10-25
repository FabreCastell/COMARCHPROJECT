import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.lang.*;

class Jtype {
	public int jtype(String a ,String b ) {
		return (Integer.parseInt(a) << 19 ) + (Integer.parseInt(b) << 16 );
	}

}
class Rtype {
	public int rtype(String a ,String b , String c) {
		return (Integer.parseInt(a) << 19 ) + (Integer.parseInt(b) << 16 ) + (Integer.parseInt(c));
	}

}

class Itype {
	public int itype(String a , String b , String c) {
	return (Integer.parseInt(a) << 19 ) + (Integer.parseInt(b) << 16 ) + (Integer.parseInt(c));	
	}
}
class CheckLabel{
	public void checkLabel() {

		
	}
}
class CheckInst {
	public boolean checkInst(String inst) {
			//int index = i;
			String[] l = inst.split("\t");
			int out;
			String in = l[1];
			if (in.equals("lw") | in.equals("sw") | in.equals("beq")){
				if(l[2] != null & l[3] != null & l[4] != null){
					return true;
				}else{
					return false;
				}
				
			}else if (in.equals("add") | in.equals("nand")){
				if(l[2] != null & l[3] != null & l[4] != null){
					return true;
				}
			
			}else if (in.equals("jalr") ){
				if(l[2] != null & l[3] != null & l[3] == null){
					return true;
				}
				
			}else if (in.equals("halt") | in.equals("noop")){
				if(l[2] == null & l[3] == null & l[4] == null){
					return true;
				}			
			}		
		return false;
	}
	
}
class CheckInstruction {
	public int checkInstruction(String inst , int i) {
			int index = i;
			String[] l = inst.split("\t");
			int out = 0;
			int op = 0;
			String in = l[1];
			if (in.equals("lw") | in.equals("sw") | in.equals("beq")){
				Itype it = new Itype();
				out = it.itype(l[2],l[3],l[4]);
				if(in.equals("lw")){
					op = 2;
				}else if (in.equals("sw")){
					op = 3;
				}else{
					op = 4;
				}
			}else if (in.equals("add") | in.equals("nand")){
				Rtype rt = new Rtype();
				out = rt.rtype(l[2],l[3],l[4]);
				if(in.equals("add")){
					op = 0;
				}else{
					op = 1;
				}
			}else if (in.equals("jalr") ){
				Jtype jt = new Jtype();
				out = jt.jtype(l[2],l[3]);
				op = 5;
			}else if (in.equals("halt") | in.equals("noop")){
				out = 0;
				if(in.equals("halt")){
					op = 6;
				}else{
					op = 7;
				}
			}		
		return ((op << 22) + out );
	}
	
}
class Checkfield{
	public boolean checkfield(String line) {
		
		String[] field = line.split("\t");
		if (field[1] == null){
			System.out.println("no instruction");
			return false;
		}
		CheckInst checkin = new CheckInst();

		boolean x = checkin.checkInst(line);
		if (x == false){
			return false;
		}else{
			return true;
		}
		
	}
}

class Seperate {
    
    public  void check(String line,int i) {
		//label
		//CheckLabel lab =new CheckLabel;
		//lab.checkLabel(l[0])

		//instruction
		CheckInstruction ins = new CheckInstruction();    
		int sum = ins.checkInstruction(line,i);
		System.out.println(sum);
	}  	
}

public class Proj   {

	public static void main(String[] args) {
		
		String path = "D:\\project3\\test1.txt";
		File file = new File(path);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			int i=0;
			while ((line = br.readLine()) != null) {
				//String[] name = line.split("\t");
				Checkfield chf = new Checkfield(); 
				boolean x = chf.checkfield(line); //check field
				if(x == true){
					Seperate sep = new Seperate();
					sep.check(line,i); //if correct field turn to checkInst
				}else if(x == false){
					break;
					//have error exit
				}
				i++;
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	
}

