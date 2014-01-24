package ArgumentGenerator;

import java.util.ArrayList;

/**
 * Stack.java is a defined class to implement the stack function.
 * <p/>
 * User: Tshering Tobgay
 * Date: 1/23/14
 */
public class Stack<E> {
    private int size;
    private int index;
    private Object arrayElements;
    private Node top;

    public class Node {
        private ArrayList<E> name;
        private Node next;
    }

    /**
     * Default constructor
     */
    public Stack() {
        this.index = 0;
        this.size = 5;//default size
        arrayElements = new Object[size];
    }

    /**
     * The constructor defines the size of the stack
     */
    public Stack(int size) {
        this.index = 0;
        this.size = size;
        arrayElements = new Object[size];
    }

    public void push(ArrayList<E> name) {
        Node hold = top;
        top = new Node();
        top.name = name;
        top.next = hold;
        size++;
    }

    public ArrayList<E> pop() {
        if (isEmpty())
            throw new RuntimeException("Empty stack");
        ArrayList<E> hold = top.name;
        top = top.next;
        size--;
        return hold;
    }

    public Stack<E> addAll(Stack<E> copy) {
        Stack<E> hold = new Stack<E>();
        Stack<E> copiedStack = new Stack<E>();

        while (!copy.isEmpty()) {
            hold.push(copy.pop());
        }
        while (!hold.isEmpty()) {
            copiedStack.push(hold.pop());
        }

        return copiedStack;
    }

    public boolean isEmpty() {
        boolean hold = false;
        if (top == null)
            hold = true;
        return hold;
    }

    public int getSize() {
        return size;
    }
}

