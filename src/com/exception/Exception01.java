package com.exception;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/9/21 09:34
 */
public class Exception01 {
    public static void main(String[] args) {
        try {
            System.out.println(args.length);
            for (String arg : args){
                System.out.println(arg);
            }
            int a = Integer.parseInt(args[1]);
            int b = Integer.parseInt(args[0]);
            System.out.println("a / b = " + a / b);
        }catch (ArithmeticException e){
            System.out.println(e.getMessage());
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }

    }
}
