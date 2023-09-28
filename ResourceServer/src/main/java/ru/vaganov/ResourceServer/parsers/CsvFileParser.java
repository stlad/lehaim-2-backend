package ru.vaganov.ResourceServer.parsers;

import java.io.*;


public class CsvFileParser<Target>{

    private String source;

    public CsvFileParser(String source) {
        this.source = source;
    }


    /**
     * @param parseLine (str) -> object. Как преобразовать строку в целевой объект
     * @param action (obj) -> void. Действие с объектом после его получение в результате парсинга строки
     */
    public void exec(lineParse<Target> parseLine, onEachAction<Target> action){
        try(InputStream inputStream = getClass().getResourceAsStream(source);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ){
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
