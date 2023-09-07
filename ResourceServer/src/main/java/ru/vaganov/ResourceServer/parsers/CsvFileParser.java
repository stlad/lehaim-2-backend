package ru.vaganov.ResourceServer.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class CsvFileParser<Target>{

    private File source;

    public CsvFileParser(File source) {
        this.source = source;
    }


    /**
     * @param parseLine (str) -> object. Как преобразовать строку в целевой объект
     * @param action (obj) -> void. Действие с объектом после его получение в результате парсинга строки
     */
    public void exec(lineParse<Target> parseLine, onEachAction<Target> action){
        try(BufferedReader reader = new BufferedReader(new FileReader(source))){
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                Target obj = parseLine.lineToObj(line);
                action.action(obj);
            }

        }catch (IOException e){}
    }

    public interface onEachAction<T>{
        void action(T obj);
    }

    public interface lineParse<T>{
        T lineToObj(String line);
    }
}
