package com.example.mail.test1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <  >
 */
public class FGF {
    public void happyTime(double money, Consumer<Double> con) {
        con.accept(money);
    }
    @Test
    public void test() {
        happyTime(30, new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) {
                System.out.println("熬夜太累了，点个外卖，价格为：" + aDouble);
            }
        });
        System.out.println("+++++++++++++++++++++++++");

       //Lambda表达式写法
      //  happyTime(20, money -> System.out.println("熬夜太累了，吃口麻辣烫，价格为：" + money));
     happyTime(100,money -> System.out.println(" 冲个话费 价格为"+money) );



    }
    @Test
    public void test2(){
        List<String> list = Arrays.asList("景天","景泰","景坚","风景","景日","普京","北京","习大大","祁同伟");

        List<String> list5 = filterString(list, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("景");
            }
        });
        System.out.println(list5);
        System.out.println("拉姆大表达式++++++++++++++++++++++++++++++++++");
        //拉姆大表达式
        List<String> list7=filterString(list,s ->  s.contains("景") );

        System.out.println(list7);;
    }
    //指定规则 过滤
    public List<String> filterString(List<String> list, Predicate<String> predicate){

        List<String> fiterlist =new ArrayList<>();
        for (String s :list){
            if (predicate.test(s)){
                fiterlist.add(s);
            }
        }
        return fiterlist;

    }
}