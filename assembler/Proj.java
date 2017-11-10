import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




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
class CheckLabel{
	public static int checkLabel(String c) {
		for(int i=0; i<Proj.count; i++){
			String[] temp = Proj.label[i].split("\t");
			if(c.equals(temp[0])){
				return i;
			}
		}
		throw new ArithmeticException("Label not found!");
	}
}

class Filltype {
	public int filltype(String a) {
		if(isInteger(a)){
			return (Integer.parseInt(a));
		}else{
			return  (CheckLabel.checkLabel(a));
		}
	}

	public boolean isInteger(String str) {
    	if(str == null || str.trim().isEmpty()) {
       		return false;
		}
		
		if(str.matches("(.*)[a-z](.*)")) {
			return false;
		}
    	return true;
	}	
}


class Itype {
	public int itype(String a , String b , String c) {
		if(isInteger(c)){
			return (Integer.parseInt(a) << 19 ) + (Integer.parseInt(b) << 16 ) + (Integer.parseInt(c));
		}else if(Proj.op == 4){
			return (Integer.parseInt(a) << 19 ) + (Integer.parseInt(b) << 16 ) + getMinus(CheckLabel.checkLabel(c)-Proj.nowcount-1);
		}else{
			return (Integer.parseInt(a) << 19 ) + (Integer.parseInt(b) << 16 ) + getMinus(CheckLabel.checkLabel(c)-Proj.nowcount);
		}
	}


	
	public int getMinus(int c){
		if(c<0){
			return c+65536;
		}else return c;
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
			 }else if (in.equals(".fill")){
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
			
			String in = l[1];
			
			if (in.equals("lw") | in.equals("sw") | in.equals("beq")){
				Itype it = new Itype();
				out = it.itype(l[2],l[3],l[4]);
				if(in.equals("lw")){
					Proj.op = 2;
				}else if (in.equals("sw")){
					Proj.op = 3;
				}else{
					Proj.op = 4;
				}
			}else if (in.equals("add") | in.equals("nand")){
				Rtype rt = new Rtype();
				out = rt.rtype(l[2],l[3],l[4]);
				if(in.equals("add")){
					Proj.op = 0;
				}else{
					Proj.op = 1;
				}
			}else if (in.equals("jalr") ){
				Jtype jt = new Jtype();
				out = jt.jtype(l[2],l[3]);
				Proj.op = 5;
			}else if (in.equals("halt") | in.equals("noop")){
				out = 0;
				if(in.equals("halt")){
					Proj.op = 6;
				}else{
					Proj.op = 7;
				}
			}else if (in.equals(".fill")){
				Filltype ft = new Filltype();
				return ft.filltype(l[2]);
				
			}
			return ((Proj.op << 22) + out );				
			
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
	static int count=0;
	static int nowcount=0;
	static String[] label = new String[count];
	static int op = 0;

	public static void main(String[] args) {
	

		String path = "test.txt";
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
			label = new String[count];
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
					nowcount++;
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