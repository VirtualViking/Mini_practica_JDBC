package utils;

import model.Toy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
  public static List<Toy> readToys(String filePath) {
    List<Toy> toys = new ArrayList<>();
    try {
      File file = new File(filePath);
      FileInputStream fileInputStream = new FileInputStream(file);
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      toys = (List<Toy>) objectInputStream.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return toys;

  }


  public static void writeToys(String filePath, List<Toy> toys) {
    try{
      File file = new File(filePath);
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(toys);

      objectOutputStream.close();
      fileOutputStream.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
