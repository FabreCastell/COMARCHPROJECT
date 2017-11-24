import java.io.*;


class Jtype {
    public int jtype(String a ,String b ) {
        return (Integer.parseInt(a) << 19) + (Integer.parseInt(b) << 16);
    }

}
class Rtype {
    public int rtype(String a ,String b , String c) {
        return (Integer.parseInt(a) << 19 ) + (Integer.parseInt(b) << 16 ) + (Integer.parseInt(c));
    }

}
class CheckLabel{
    public static int checkLabel(String c) {

        for (int i = 0; i < Assembler.count; i++) {
            String[] temp = Assembler.label[i].split("\\s+");
            //System.out.println(temp[0]);
            if (c.equals(temp[0]) && temp[1].equals(".fill")) {
                return killFill(temp[2]);


            } else if (c.equals(temp[0])) {
                //System.out.println(i);
                return i;
            }
        }
        throw new ArithmeticException("Label not found!");
    }

    public static int lwLabal(String c){
        for (int i = 0; i < Assembler.count; i++) {
            String[] temp = Assembler.label[i].split("\\s+");
            //System.out.println(i);
            if(c.equals(temp[0])){
                //System.out.println(i);
                return i;
            }
        }
        throw new ArithmeticException("Label not found!");
    }



    public static int killFill(String c){
        for(int i = 0; i< Assembler.count; i++) {
            String[] temp = Assembler.label[i].split("\\s+");
            if(c.equals(temp[0])){
                //System.out.println(i);
                return i;
            }
        }
        return 0;
    }
}

class Filltype {
    public int filltype(String a) {
        if(isInteger(a)){
            return (Integer.parseInt(a));
        }else{
            //System.out.println(a);
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
        }else {
            if (Assembler.op == 4) {
                return (Integer.parseInt(a) << 19) + (Integer.parseInt(b) << 16) + getMinus(CheckLabel.checkLabel(c) - Assembler.nowcount - 1);
            } else {
                //System.out.println(Proj.op);
                return (Integer.parseInt(a) << 19) + (Integer.parseInt(b) << 16) + CheckLabel.lwLabal(c);
            }
        }//itype lw & sw & no int
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
        String[] l = inst.split("\\s+");

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
            if(l[2] != null & l[3] != null ){
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
        String[] l = inst.split("\\s+");
        int out = 0;

        String in = l[1];

        if (in.equals("lw") | in.equals("sw") | in.equals("beq")){
            Itype it = new Itype();
            if(in.equals("lw")){
                Assembler.op = 2;
            }else if (in.equals("sw")){
                Assembler.op = 3;
            }else{
                Assembler.op = 4;
            }
            out = it.itype(l[2],l[3],l[4]);
        }else if (in.equals("add") | in.equals("nand")){
            Rtype rt = new Rtype();
            if(in.equals("add")){
                Assembler.op = 0;
            }else{
                Assembler.op = 1;
            }out = rt.rtype(l[2],l[3],l[4]);

        }else if (in.equals("jalr") ){
            Jtype jt = new Jtype();
            Assembler.op = 5;
            out = jt.jtype(l[2],l[3]);
        }else if (in.equals("halt") | in.equals("noop")){
            out = 0;
            if(in.equals("halt")){
                Assembler.op = 6;
            }else{
                Assembler.op = 7;
            }
        }else if (in.equals(".fill")){
            Filltype ft = new Filltype();
            return ft.filltype(l[2]);
        }
        return ((Assembler.op << 22) + out );

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

    public  int check(String line,int i) {
        //label
        //CheckLabel lab =new CheckLabel;
        //lab.checkLabel(l[0])

        //instruction
        CheckInstruction ins = new CheckInstruction();
        int sum = ins.checkInstruction(line,i);
        System.out.println(sum);
        return(sum);
    }
}

public class Assembler {
    static int count=0;
    static int nowcount=0;
    static String[] label = new String[count];
    static int op = 0;

    public static void main(String[] args) throws IOException{
        String path = "combination.txt";
        File file = new File(path);
        String out = "combinationOut.txt";
        File fileout = new File (out);
        int sum=0;

        BufferedWriter outFile = new BufferedWriter(new FileWriter(fileout));
        int num=0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                count++;
            }
            br.close();

            //System.out.println(count);
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
                cLabel = label[num].split("\\s+");

                for(int i=0; i<num; i++){
                    inLabel = label[i].split("\\s+");
                    if(inLabel[0].equals(cLabel[0]) && !cLabel[0].equals("")){
                        throw new ArithmeticException("Existed label!");
                    }
                }
                num++;
            }
            br.close();

//            for(int i=0; i<label.length; i++){
//                System.out.println(label[i]);
//            }

            br = new BufferedReader(new FileReader(file));
            int i=0;
            while ((line = br.readLine()) != null) {
                //String[] name = line.split("\\s+");
                Checkfield chf = new Checkfield();
                boolean x = chf.checkfield(line); //check field
                if(x == true){
                    Seperate sep = new Seperate();
                    sum = sep.check(line,i); //if correct field turn to checkInst
                    nowcount++;
                }else if(x == false){
                    break;
                    //have error exit
                }
                i++;
                outFile.write(String.valueOf(sum));//เขียนโค้ดที่แปลงมาได้ลง output file
                outFile.newLine();
            }
            outFile.flush();

            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}