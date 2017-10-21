package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String s[] = {"8454151","9043971","655361"};
        String x[] = new String[s.length];
        int n[] = new int [s.length] ;

        for (int i = 0 ; i < n.length ;i ++){
            n[i]=Integer.parseInt(s[i]);
            System.out.println(n[i]);

            x[i]=Integer.toBinaryString(n[i]);

            while(25-x[i].length() != 0 ) x[i] = "0" + x[i] ;

            System.out.print(x[i] + "(");
            System.out.println(x[i].length()+")" + "\n");
        }

        String temp = x[0].substring(0, 3);
        System.out.println(temp);
        System.out.println(temp.contains("010"));
    }
}
