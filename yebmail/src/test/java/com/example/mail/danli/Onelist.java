package com.example.mail.danli;

import com.sun.org.apache.regexp.internal.RE;

/**
 * <  >
 */
public class Onelist {
    public static void main(String[] args) {
        Bank date1 = Bank.cxk();
        Bank bank2 = Bank.cxk();
        if (date1.equals(bank2)){
            System.out.println("true");
        }

    }

}
//饿汉方式
class Bank{


     private Bank(){

     }

     private static Bank foll =new Bank();

     public static Bank cxk(){

         if (foll==null){
             foll=new Bank();
         }
         return foll;
     }


}

class cat{
    private cat(){

    }

    private static cat a =new cat();

    public static cat fuck(){
        if (a==null){
            a=new cat();
        }
        return a ;
    }


}