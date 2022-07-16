package com.example.mail;

import java.nio.charset.StandardCharsets;

/**
 * <  >
 */
public class testS {

    public static void main(String[] args) {
       /* cal cal = new cal(){


            @Override
            public int add(int a, int b) {
                return a+b;

            }

        };*/

        String s="asdsadsadsadsad";
        String h="asd";

        int length = s.length();

        System.out.println(s.contains(h));
        System.out.println(s.endsWith("sad"));
        System.out.println(s.charAt(5));
        System.out.println(s.compareTo("sad"));
        System.out.println(s.startsWith("asd"));




        System.out.println(length);
        cal cal=(a,b)->{

            return a+b;
        };

        System.out.println(cal.add(7,6));
    }



    interface cal{

        int add(int a,int b);
    }
}
