package com.example.mail.factory;

/**
 * <  >
 */
public class CarFactory {

    public static void main(String[] args) {

        car dzcar = new DZfactory().getCAR();

        car bwmcar = new BMWfactory().getCAR();
        bwmcar.run();
        dzcar.run();

    }



}
//汽车接口
interface car{
    void run();

}

class DZ implements car{

    @Override
    public void run() {

        System.out.println("大众汽车在跑");
    }
}

class BWM implements car{

    @Override
    public void run() {
        System.out.println("宝马汽车在跑");
    }
}

//工厂接口
interface factory{
    car getCAR();

}

//专门产大众
class DZfactory implements factory{

    @Override
    public car getCAR() {
        return new DZ();
    }
}

//产宝马
class BMWfactory implements factory{



    @Override
    public car getCAR() {
        return new BWM();
    }
}