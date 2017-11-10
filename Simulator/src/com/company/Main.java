package com.company;
import java.io.*;
public class Main {

    public static void main(String[] args) {
	// write your code here
        String memory[] = new String [10] ;
        int register[]= new int [8];
        readFile("MachineCode.txt",memory);


        String binaryRegister[] = new String[memory.length];
        int lengthOfBinary[] = new int [memory.length] ;
        System.out.println("Example Run of Simulator\n");
        for (int i = 0 ; i < lengthOfBinary.length ;i ++){
            lengthOfBinary[i]=Integer.parseInt(memory[i]);
            System.out.println("memory["+i+"] = "+memory[i]);

            if(lengthOfBinary[i] >= 10){
                binaryRegister[i]=Integer.toBinaryString(lengthOfBinary[i]);
                while(25-binaryRegister[i].length() != 0 ) binaryRegister[i] = "0" + binaryRegister[i] ;
            }
            else binaryRegister[i] = memory[i];

            //System.out.print(binaryRegister[i] + "("); // binary of machine code
            //System.out.println(binaryRegister[i].length()+")" + "\n"); // length of binary of machince code
        }
        int count=0 ;
        int instruc =1;
        while (count < binaryRegister.length){
            printState(memory,register,count);
            String temp = new String();
            if(binaryRegister[count].length() == 25){
                temp = binaryRegister[count].substring(0, 3);
            }

            if(temp.contains("000")){
                add(binaryRegister[count],register);
            } else if(temp.contains("001")){

                nand(binaryRegister[count],register);
            } else if(temp.contains("010")){

                lw(binaryRegister[count],register,memory,count);
            } else if(temp.contains("011")){
                sw(binaryRegister[count],register);

            } else if(temp.contains("100")){

                count = beq(binaryRegister[count],register,count);

            } else if(temp.contains("101")){

                jalr(binaryRegister[count],register,count);

            } else if(temp.contains("110")){
                System.out.println("machine halted");
                System.out.println("total of "+ instruc +" instructions executed\n" +
                        "final state of machine:\n");
                count++;
                printState(memory,register,count);
                break; // halt

            }
            //System.out.println(count);
            count++;
            instruc++;
        }
    }

    public static String readFile(String what,String [] n){
        String value = "" ;
        try {
            
            FileInputStream fstream = new FileInputStream(what);

            // Get the object of DataInputStream
            DataInputStream instream = new DataInputStream(fstream);
            BufferedReader bf = new BufferedReader(new InputStreamReader(instream));
            String line;

            
            int i = 0;
            while ((line = bf.readLine()) != null) {
                //System.out.println(line);
                value = value + "\n" + line ;
                n[i] = line ;
                i++;
            }
            
            instream.close();


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return value;

    }

    public static void write(String what,String x){
        try {
            
            FileWriter writer = new FileWriter(what);

            
            BufferedWriter out = new BufferedWriter(writer);

            
            out.write(x);


           
            out.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void add(String binaryRegister ,int[]register){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;
        String rd =  binaryRegister.substring(22, 25) ;

        int dxrs = Integer.parseInt(rs, 2);
        int dxrt = Integer.parseInt(rt, 2);
        int dxrd = Integer.parseInt(rd, 2);

        register[dxrd] = register[dxrt] + register[dxrs] ;

    }
    public static void nand(String binaryRegister ,int[]register){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;
        String rd =  binaryRegister.substring(22, 25) ;

        int dxrs = Integer.parseInt(rs, 2);
        int dxrt = Integer.parseInt(rt, 2);
        int dxrd = Integer.parseInt(rd, 2);


    }
    public static void lw(String binaryRegister ,int[]register,String []memory,int count){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;
        String offset =  binaryRegister.substring(9, 25) ;

        int dxrs = Integer.parseInt(rs, 2);
        int dxrt = Integer.parseInt(rt, 2);
        int dxoffset = Integer.parseInt(offset, 2);
        /*System.out.println(dxrs + " dxrs");
        System.out.println(dxrt + " dxrt");
        System.out.println(dxoffset + " dxoffset");*/
        register[dxrt] = Integer.parseInt(memory[count + dxoffset+dxrs*4]);

    }
    public static void sw(String binaryRegister ,int[]register){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;
        String offset =  binaryRegister.substring(9, 25) ;

        int dxrs = Integer.parseInt(rs, 2);
        int dxrt = Integer.parseInt(rt, 2);
        int dxoffset = Integer.parseInt(offset, 2);
    }
    public static int beq(String binaryRegister ,int[]register,int count){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;
        String offset =  binaryRegister.substring(9, 25) ;

        //System.out.println(offset +" offset");

        int dxrs = Integer.parseInt(rs, 2);
        int dxrt = Integer.parseInt(rt, 2);
        short dxoffset = (short) Integer.parseInt(offset, 2);

        if(register[dxrs] == register[dxrt]) return count+dxoffset;
        else return count;
    }
    public static int jalr(String binaryRegister ,int[]register,int count ){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;

        int dxrs = Integer.parseInt(rs, 2);
        int dxrt = Integer.parseInt(rt, 2);

        return dxrs;
    }
    public static void printState(String []memory,int []register,int count){

        System.out.println("\n@@@\n" + "\tpc " + count +"\n\tmemory:");
        for (int i=0;i<memory.length;i++) {
            System.out.println("\t\tmem[ "+i+" ] " + memory[i]);
        }
        System.out.println("\tregister:");
        for (int i=0;i<register.length;i++) {
            System.out.println("\t\treg[ "+i+" ] " + register[i]);
        }
        System.out.println("end state");
    }
}
