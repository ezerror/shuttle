package com.example.shuttledemo.entity;

/**
 * 示例参数实体类
 *
 * @author sy
 * @date 2021/11/4
 **/
public class TestEntity {
    private String name;

    private String age;

    public TestEntity(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "TestEntity{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
