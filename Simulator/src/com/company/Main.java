package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String s[] = {"8454151","9043971","655361"};
        String x="";
        int n[] = new int [3] ;

        for (int i = 0 ; i < n.length ;i ++){
            n[i]=Integer.parseInt(s[i]);
            System.out.println(n[i]);
            x=Integer.toBinaryString(n[i]);

            if(24-x.length() != 0 ) x = "0000" + x ;
            System.out.println(x);
            System.out.println(x.length() + "\n");
        }


    }
}
