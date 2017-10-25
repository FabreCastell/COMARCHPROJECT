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
public class CheckLabel{
	public int checkLabel(String c) {
		for(int i=0; i<Proj.count; i++){
			String temp = Proj.label[i].split("\t");
			if(c.equals(temp[0]) && temp[1].equals(".fill")){
				return Integer.parseInt(temp[2]);
			}
		}
		throw new ArithmeticException("Label not found!");
	}
}

class Itype {
	public int itype(String a , String b , String c) {
		
		if(isInteger(c)){
			return (Integer.parseInt(a) << 19 ) + (Integer.parseInt(b) << 16 ) + (Integer.parseInt(c));
		}else
			return (Integer.parseInt(a) << 19 ) + (Integer.parseInt(b) << 16 ) + (CheckLabel.checkLabel(c));
		}
	

	public boolean isInteger(String str) {
    	if(str == null || str.trim().isEmpty()) {
       		return false;
    	}
    	for (int i = 0; i < str.length(); i++) {
        	if(!Character.isDigit(str.charAt(i))) {
            	return false;
        	} 
    	}
    	return true;
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
					return true;			
			}
			throw new ArithmeticException("Instruction doesn't exist!");
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
		if(line.equals("")){
			System.out.println("no instruction");
			return true;
		}
		// String[] field = line.split("\t");
		// if (field[1] == null){
		// 	System.out.println("no instruction");
		// 	return false;
		// }
		CheckInst checkin = new CheckInst();

		boolean x = checkin.checkInst(line);
		return x;
		
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
	int count=0;
	String[] label = new String[count];

	public static void main(String[] args) {
		
		String path = "test1.txt";
		File file = new File(path);
		
		
		
		int num=0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				count++;
			}
			br.close();
		
		
			System.out.println(count);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String[] inLabel;
			String[] cLabel;
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				label[num] = line;
				for(int i=0; i<num; i++){
					inLabel = label[i].split("\t");
					cLabel = label[num].split("\t");
					if(inLabel[0].equals(cLabel[0]) && !cLabel[0].equals("")){
						throw new ArithmeticException("Existed label!");
					}
				}
				num++;
			}
			br.close();
			
			for(int i=0; i<label.length; i++){
				System.out.println(label[i]);
			}

		
			br = new BufferedReader(new FileReader(file));
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

