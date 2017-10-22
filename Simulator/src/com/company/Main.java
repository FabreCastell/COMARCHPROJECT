package com.company;
import java.io.*;
public class Main {

    public static void main(String[] args) {
	// write your code here
        String memory[] = new String [10] ;
        String register[]=new String [8];
        readFile("MachineCode.txt",memory);


        String binaryRegister[] = new String[memory.length];
        int lengthOfBinary[] = new int [memory.length] ;

        for (int i = 0 ; i < lengthOfBinary.length ;i ++){
            lengthOfBinary[i]=Integer.parseInt(memory[i]);
            System.out.println(lengthOfBinary[i]);

            if(lengthOfBinary[i] >= 0)binaryRegister[i]=Integer.toBinaryString(lengthOfBinary[i]);
            else binaryRegister[i] = memory[i];

            while(25-binaryRegister[i].length() != 0 ) binaryRegister[i] = "0" + binaryRegister[i] ;

           // System.out.print(binaryRegister[i] + "("); // binary of machine code
           // System.out.println(binaryRegister[i].length()+")" + "\n"); // length of binary of machince code
        }

        for(int i =0;i<memory.length;i++){

            String temp = binaryRegister[i].substring(0, 3);

            if(temp.contains("000")){
                add();
            } else if(temp.contains("001")){

                nand();
            } else if(temp.contains("010")){

                lw();
            } else if(temp.contains("011")){
                sw();

            } else if(temp.contains("100")){

                beq();
            } else if(temp.contains("101")){

                jalr();
            } else if(temp.contains("110")){
                halt(); //end program

            } else if(temp.contains("111")){

                //do nothing
            }
        }
    }

    public static String readFile(String what,String [] n){
        String value = "" ;
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
                n[i] = line ;
                i++;
            }
            // ปิด input stream
            instream.close();


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return value;

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

    public static void add(){}
    public static void nand(){}
    public static void lw(){

    }
    public static void sw(){}
    public static void beq(){}
    public static void jalr(){}
    public static void halt(){}

}
