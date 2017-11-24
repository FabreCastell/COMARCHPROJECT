package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String result ="";
        String[] memory = new String[65536];
        String file[] = new String[1];
        int register[]= new int [8];
        file = readFile("multi.txt",file);

        for (int i=0;i<file.length;i++){
            memory[i] = file[i];
        }

        //for(int i=0;i<memory.length;i++) System.out.println(memory[i]);
        String binaryRegister[] = new String[memory.length];
        int lengthOfBinary[] = new int [file.length] ;
        System.out.println("Example Run of Simulator\n");

        for (int i = 0 ; i < lengthOfBinary.length ;i ++){
            lengthOfBinary[i]=Integer.parseInt(memory[i]);
            System.out.println("memory["+i+"] = "+memory[i]);

            binaryRegister[i]=Integer.toBinaryString(lengthOfBinary[i]);
            while(25-binaryRegister[i].length() > 0 ) binaryRegister[i] = "0" + binaryRegister[i] ;

//            System.out.print(binaryRegister[i] + "("); // binary of machine code
//            System.out.println(binaryRegister[i].length()+")" + "\n"); // length of binary of machince code
        }

        int count=0 ;
        int instruc =1;
//        while(register[2] != 89){
        while (count < binaryRegister.length){
            printState(memory,register,count,file.length);
//            System.out.println(count + " " + register[1]);
            String temp = new String();
            String instruction = binaryRegister[count];
            count++;
            if(instruction.length() == 25){
                temp = instruction.substring(0, 3);
            }
//            System.out.println(instruction);
            if(temp.contains("000")){

                add(instruction,register);
            } else if(temp.contains("001")){

                nand(instruction,register);
            } else if(temp.contains("010")){

                lw(instruction,register,memory,count);
            } else if(temp.contains("011")){
                memory = sw(instruction,register,memory,count);

            } else if(temp.contains("100")){

                count = beq(instruction,register,count);

            } else if(temp.contains("101")){

                count = jalr(instruction,register,count);

            } else if(temp.contains("110")){
                System.out.println("machine halted");
                System.out.println("total of "+ instruc +" instructions executed\n" +
                        "final state of machine:\n");
                count++;
                printState(memory,register,count,file.length);
                break; // halt

            }
            //System.out.println(count);
            instruc++;
        }
        write("result.txt",result);
    }

    public static String[] readFile(String what,String [] memory){
        String value = "" ;
        ArrayList<String> tmp = new ArrayList<String>();
        try {
            // อ่านไฟล์ชื่อ textfile.txt
            FileInputStream fstream = new FileInputStream(what);

            // Get the object of DataInputStream
            DataInputStream instream = new DataInputStream(fstream);
            BufferedReader bf = new BufferedReader(new InputStreamReader(instream));
            String line;

            // อ่านไฟล์ทีละบรรทัด
            int i = 0;

            while ((line = bf.readLine()) != null) {
                //System.out.println(line);
                value = value + "\n" + line ;

                tmp.add(line) ;

                i++;
            }
            // ปิด input stream
            instream.close();


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        String[] arr = tmp.toArray(new String[tmp.size()]);

        return arr;

    }

    public static void write(String what,String x){
        try {
            // สร้าง object เพื่อใช้เขียนไฟล์ โดยระบุชื่อไฟล์ที่ต้องการสร้าง
            FileWriter writer = new FileWriter(what);

            // สร้าง object เพื่อใช้เขียนข้อมูลลงไปในไฟล์
            BufferedWriter out = new BufferedWriter(writer);

            // เขียนข้อมูลลงไปในไฟล์
            out.write(x);


            // ปิดการเขียนไฟล์
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


        register [dxrd] = ~(register[dxrs] & register[dxrt]);



    }
    public static void lw(String binaryRegister ,int[]register,String []memory,int count){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;
        String offset =  binaryRegister.substring(9, 25) ;

        int dxrs =(short) Integer.parseInt(rs, 2);
        int dxrt =(short) Integer.parseInt(rt, 2);
        int dxoffset = (short)Integer.parseInt(offset, 2);

//        System.out.println(dxrs + " dxrs");
//        System.out.println(dxrt + " dxrt");
//        System.out.println(dxoffset + " dxoffset");

        register[dxrt] = Integer.parseInt(memory[dxoffset+register[dxrs]]);

    }
    public static String [] sw(String binaryRegister ,int[]register,String []memory,int count){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;
        String offset =  binaryRegister.substring(9, 25) ;

        int dxrs = (short)Integer.parseInt(rs, 2);
        int dxrt = (short)Integer.parseInt(rt, 2);
        int dxoffset = (short)Integer.parseInt(offset, 2);
//        System.out.println(register[dxrs]+dxoffset + " eiei");

        memory[register[dxrs] + dxoffset] = Integer.toString(register[dxrt]);
        return  memory;

    }
    public static int beq(String binaryRegister ,int[]register,int count){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;
        String offset =  binaryRegister.substring(9, 25) ;

        //System.out.println(offset +" offset");

        int dxrs = Integer.parseInt(rs, 2);
        int dxrt = Integer.parseInt(rt, 2);
        int dxoffset = (short) Integer.parseInt(offset, 2);

        if(register[dxrs] == register[dxrt]) return count+dxoffset;
        else return count;
    }
    public static int jalr(String binaryRegister ,int[]register,int count ){
        String rs =  binaryRegister.substring(3, 6) ;
        String rt =  binaryRegister.substring(6, 9) ;

        int dxrs = Integer.parseInt(rs, 2);
        int dxrt = Integer.parseInt(rt, 2);
        register[dxrt] = count ;

        return register[dxrs];
    }
    public static void printState(String []memory,int []register,int count,int len){

        System.out.println("\n@@@\n" + "\tpc " + count +"\n\tmemory:");
        for (int i=0;i<len;i++) {
            System.out.println("\t\tmem[ "+i+" ] " + memory[i]);
        }
        System.out.println("\tregister:");
        for (int i=0;i<register.length;i++) {
            System.out.println("\t\treg[ "+i+" ] " + register[i]);
        }
        System.out.println("end state");
    }
}
