package com.example.mail.test1;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <  >
 */
public class StreamAPITest2 {
    @Test
    public void test() {
        List<Employee> list = EmployeeData.getEmployees();
        //        filter(Predicate p)——接收 Lambda ， 从流中排除某些元素。
        Stream<Employee> stream = list.stream();
        //练习：查询员工表中薪资大于7000的员工信息
        stream.filter(e -> e.getSalary() > 7000).forEach(System.out::println);
        //添加元素
        System.out.println("+++++++++++++++++++++++");
        list.add(new Employee(1013, "李飞", 42, 8500));
        list.add(new Employee(1013, "李飞", 41, 8200));
        list.add(new Employee(1013, "李飞", 28, 6000));
        list.add(new Employee(1013, "李飞", 39, 7800));
        list.add(new Employee(1013, "李飞", 41, 8200));
        //查重
        list.stream().distinct().forEach(System.out::println);//
        System.out.println("+++++++++++++++++++++++");
        //截断
        List<Integer> collect = list.stream().limit(4)
                .map(Employee::getAge).collect(Collectors.toList());

        System.out.println(collect);


    }

    @Test
    public void test2() {
       // map(Function f)——接收一个函数作为参数，将元素转换成其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素。
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        list.stream().map(str -> str.length()).forEach(System.out::println);

//        练习1：获取员工姓名长度大于3的员工的姓名。
        List<Employee> employees = EmployeeData.getEmployees();
        Stream<String> namesStream = employees.stream().map(Employee::getName);
        namesStream.filter(name -> name.length() > 3).forEach(System.out::println);
        System.out.println();


    }
    @Test
    public void test3() {

//        sorted()——自然排序
//        List<Integer> list = Arrays.asList(25, 45, 36, 12, 85, 64, 72, -95, 4);
//        list.stream().sorted().forEach(System.out::println);
        List<Employee> employees = EmployeeData.getEmployees();
        employees.stream().sorted( (e1,e2) -> {

            int ageValue = Integer.compare(e1.getAge(),e2.getAge());
            if(ageValue != 0){
                return ageValue;
            }else{
                return -Double.compare(e1.getSalary(),e2.getSalary());
            }

        }).forEach(System.out::println);
    }


}
