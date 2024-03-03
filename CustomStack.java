import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CustomStack<T> {
    private LinkedList<T> stack;

    public CustomStack() {
        this.stack = new LinkedList<>();
    }

    public void push(T value) {
        stack.push(value);
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return stack.removeFirst();
    }
    

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
}
