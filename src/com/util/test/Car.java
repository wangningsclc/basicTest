package com.util.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auth wn
 * @Date 2018/10/18
 */
public class Car {
    private String name;
    private List<Person> persons;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Car(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Car car1 = new Car("thunder");
        car1.setPersons(Arrays.asList(new Person("jim", BigDecimal.ONE, 12), new Person("jay", BigDecimal.ONE, 13)));
        Car car2 = new Car("nisang");
        car2.setPersons(Arrays.asList(new Person("lily", BigDecimal.ONE, 14), new Person("lucy", BigDecimal.ONE, 15)));
        List<Car> list = Arrays.asList(car1,car2);
        List<Car> l1 = list.stream().map(car -> {
            List<Person> persons = car.getPersons().stream().filter(
                    person ->
                            person.getAge() > 12
            ).collect(Collectors.toList());
            car.setPersons(persons);
            return car;
        }).collect(Collectors.toList());
        for(Car c: l1){
            System.out.println(c.getPersons());
        }
        List<Car> newList = new ArrayList<>();
        list.stream().forEach(car -> {
            List<Person> persons = car.getPersons().stream().filter(
                    person ->
                            person.getAge() > 12
            ).collect(Collectors.toList());
            car.setPersons(persons);
            newList.add(car);
        });
    }
}
