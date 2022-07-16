package com.example.mail.test1;

import org.junit.Test;

import java.util.List;

/**
 * <  >
 */
public class StreamAPITest3 {

    @Test
    public void test3() {
        List<Employee> employees = EmployeeData.getEmployees();

//        allMatch(Predicate p)——检查是否匹配所有元素。
//          练习：是否所有的员工的年龄都大于18
        boolean allMatch = employees.stream().allMatch(e -> e.getSalary()>2000);
        System.out.println(allMatch);

        //        noneMatch(Predicate p)——检查是否没有匹配的元素。
//          练习：是否存在员工姓“马”
        boolean noneMatch = employees.stream().noneMatch(e -> e.getName().startsWith("马"));
        System.out.println(noneMatch);

        //      findFirst——返回第一个元素
        System.out.println(employees.stream().findFirst());
        //findAny——返回当前流中的任意元素
        System.out.println(employees.parallelStream().findAny());
        


    }
}
