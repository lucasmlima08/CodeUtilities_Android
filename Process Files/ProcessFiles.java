package com.myllenno.lucas.habidar.Functions;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/*
 * @Author: Lucas Myllenno S M Lima.
 * @Date: 05/07/2015
 */

public class ProcessFiles {

    public void createFile(Context context, String fileName) throws Exception {
        File file = preparedFile(context, fileName);
        if(!file.exists())
            file.createNewFile();
    }

    public void removeFile(Context context, String fileName) throws Exception {
        File file = preparedFile(context, fileName);
        if (file.exists())
            file.delete();
    }

    private File preparedFile(Context context, String fileName) throws Exception {
        String filePath = context.getFilesDir().getPath().toString() + fileName;
        File file = new File(filePath);
        return file;
    }

    private BufferedReader preparedFileRead(Context context, String fileName) throws Exception {
        File file = preparedFile(context, fileName);
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader;
    }

    private Writer preparedFileWrite(Context context, String fileName) throws Exception {
        File file = preparedFile(context, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        Writer writer = new BufferedWriter(outputStreamWriter);
        return writer;
    }

    public ArrayList<String> readFile(Context context, String fileName) throws Exception {
        BufferedReader bufferedReader = preparedFileRead(context, fileName);
        ArrayList<String> read = new ArrayList<String>();
        String line = bufferedReader.readLine();
        while (line != null){
            read.add(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return read;
    }

    public void writeFile(Context context, String fileName, ArrayList<String> write) throws Exception{
        Writer writer = preparedFileWrite(context, fileName);
        String stringAux = "";
        for (int i=0; i < write.size(); i++){
            stringAux += write.get(i);
            stringAux += "\n";
        }
        writer.write(stringAux);
        writer.close();
    }
}