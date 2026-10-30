package com.ioStream;

import org.junit.Test;

import java.io.*;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/28 16:12
 */
public class FileCreate {
    public static void main(String[] args) {

    }
    public static void create01(){
        String filePath = "D:\\create01.txt";
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void create02(){

        File parentFile = new File("D:\\Text");
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        String fileName = "news02.txt";
        File file = new File(parentFile, fileName);
        System.out.println("文件名：" + file.getName());

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void readFile01() throws IOException {
        String filePath = "D:\\Text\\news02.txt";
        FileInputStream file = new FileInputStream(filePath);
        int read;
        while ((read = file.read()) != -1){
            System.out.print((char) read);
        }
        file.close();

    }
    @Test
    public void writeFile() throws IOException {
        String filePath = "D:\\Text\\a.txt";
        FileOutputStream file = null;

        file = new FileOutputStream(filePath,true);
        file.write("hello".getBytes(),0,3);
        file.close();
    }
    @Test
    public void copy() throws IOException {
        String sourceFilePath = "D:\\Text\\news02.txt";
        String targetFilePath = "E:\\b.txt";
        FileInputStream fileIn = new FileInputStream(sourceFilePath);
        FileOutputStream fileOut = new FileOutputStream(targetFilePath);
        byte[] buf = new byte[1024];
        int readLen = 0;
        while((readLen = fileIn.read(buf)) != -1){
            fileOut.write(buf,0,readLen);
        }
        fileIn.close();
        fileOut.close();

    }
    @Test
    public void bufferRead() throws IOException {
        String filePath = "D:\\Text\\news02.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null){
            System.out.println(line);

        }
        reader.close();
    }
    @Test
    public void bufferWrite() throws IOException {
        String filePath = "D:\\Text\\a.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
        writer.newLine();

        writer.write("hello");
        writer.close();
    }
    @Test
    public void copyImg() throws IOException {
        String sourceFilePath = "D:\\Text\\1.png";
        String targetFilePath = "E:\\a.png";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFilePath));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFilePath));
        byte[] buf = new byte[1024];
        int len;
        while((len = bis.read(buf)) != -1){
            bos.write(buf,0,len);
        }
        bis.close();
        bos.close();
    }
    @Test
    public void ObjectOutStream() throws IOException {
        String filePath = "D:\\Text\\data.dat";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        oos.writeObject("hello");
        oos.writeBoolean(true);
        oos.writeChar('a');
        oos.writeDouble(3.14);
        oos.close();
    }
    @Test
    public void ObjectInStream() throws IOException, ClassNotFoundException {
        String filePath = "D:\\Text\\data.dat";
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
        System.out.println(ois.readObject());
        System.out.println(ois.readBoolean());
        System.out.println(ois.readChar());
        System.out.println(ois.readDouble());
        ois.close();
    }
    @Test
    public void transform() throws IOException {
        String filePath = "D:\\Text\\news02.txt";
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath),"gbk");
    }

}
