package org.example.platzi.model;

public class Student {
    private String name;
    private String DNI;

    public Student(String name, String DNI) {
        this.name = name;
        this.DNI = DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", DNI='" + DNI + '\'' +
                '}';
    }
}
