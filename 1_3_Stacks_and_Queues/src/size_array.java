/*
实现堆栈的pop和push的第二种方式——数组
过程中的主要步骤是数组的大小变换
为了减少数组元素复制的次数，使用直接double数组大小的方式，而pop缩小数组的时候是到了1/4占空时候才缩小

数组的优点是占用的内存少，平均分摊起来时间也少，但是在数组长度n扩大到2n时候要使用正比与2n的时间copy数组


 */


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Scanner;

public class size_array {
    private String[] stringStack;
    private int stackNum;


    public size_array(){
        stringStack = new String[1];
        stackNum = 0;
    }

    public String pop(){
        String temp = stringStack[--stackNum];
        stringStack[stackNum] = null;
        if(stackNum <= stringStack.length / 4){
            resize(stringStack.length / 2);
        }
        return temp;
    }

    public void push(String item){
        if (stringStack.length == stackNum) {
            if (stringStack.length ==0 ){
                stringStack = new String[1];
                stringStack[0] = item;
            }else {
                resize(stringStack.length * 2);
                stringStack[stackNum++] = item;
            }
        } else {
            stringStack[stackNum++] = item;
        }
    }

    private void resize(int capacity){
        String[] copy = new String[capacity];
        for (int i = 0; i < stackNum; i++) {
            copy[i] = stringStack[i];
        }
        stringStack = copy;
    }

    public int getStackNum(){
        return stackNum;
    }




    public static void main(String[] args){
        size_array stack = new size_array();
        StdOut.print("input something, mother fucker");
        while (!StdIn.isEmpty()) {
            String temp = StdIn.readString();
            if (temp.equals("-")) {
                StdOut.print(stack.pop());
                StdOut.print(stack.getStackNum());
            }else {
                stack.push(temp);
                StdOut.print(stack.getStackNum());
            }
        }

    }

}
