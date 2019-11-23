package com.company;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import com.google.gson.*;

public class Main {
    public static void main(String[] args) {
        String text = "";
        try (FileReader reader = new FileReader("АВТОМОБИЛИ.mkb")) {
            // читаем посимвольно
            int c;
            while ((c = reader.read()) != -1) {
                text = text.concat(String.valueOf((char) c));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String[] arr = text.split("\r\n\r\n");
        ArrayList blocksList = new ArrayList();
        for (String item : arr) {
            blocksList.add(item);
        }
        String[] questions = arr[1].trim().split("\r\n");
        ArrayList questionsList = new ArrayList();
        for (String item : questions) {
            questionsList.add(item);
        }
        questionsList.remove(0); // готовый список вопросов
        String[] hypotises = arr[2].trim().split("\r\n");
        ArrayList hypotisesList = new ArrayList();
        for (String item : hypotises) {
            hypotisesList.add(item);
        }
        ArrayList<ArrayList> hypotisesesList = new ArrayList<ArrayList>();
        for (String item : hypotises) {
            ArrayList tempList = new ArrayList();
            String[] temp = item.split(",");
            for (String temp_item : temp) {
                tempList.add(temp_item);
            }
            hypotisesesList.add(tempList);
        }
        Gson gson = new Gson();
        String JSON_FILE = "";
        JSON_FILE = JSON_FILE.concat("{\"questins\":");
        String questionsJson = gson.toJson(questionsList);
        JSON_FILE = JSON_FILE.concat(questionsJson + ",\n" +
                "\t\t\"hypothesis\":");

        ArrayList H_ARR = new ArrayList<hypotise>();
        hypotise h = new hypotise();
        for (ArrayList list : hypotisesesList) {
            String name = list.get(0).toString();
            double start = Double.parseDouble(list.get(1).toString());
            hypothesises[] hhArr = new hypothesises[questionsList.size()];
            int counter = 0;
            for (int i = 2; i < list.size(); i = i + 3) {
                hypothesises hh = new hypothesises(Double.parseDouble(list.get(i + 1).toString()), Double.parseDouble(list.get(i + 2).toString()));
                hhArr[counter] = hh;
                counter++;
            }
            h = new hypotise(start, name, hhArr);
            H_ARR.add(h);
        }
        JSON_FILE = JSON_FILE.concat(gson.toJson(H_ARR) + "}");

        try (FileWriter writer = new FileWriter("autos.json")) {
            writer.write(JSON_FILE);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.print(questionsJson);
    }
}

class hypotise {
    double statrWith = 0;
    String name = "";
    hypothesises[] hypothesiss = null;

    hypotise() {
    }

    hypotise(double statrWith, String name, hypothesises[] arr) {
        this.statrWith = statrWith;
        this.name = name;
        this.hypothesiss = arr;
    }
}

class hypothesises {
    double ifTrue;
    double ifFalse;

    hypothesises(double d1, double d2) {
        this.ifTrue = d1;
        this.ifFalse = d2;
    }
}