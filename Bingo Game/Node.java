package tombala;

public class Node<T> {
    T data;
    Node<T> right;
    Node<T> down;
    Node<T> next;
    
    public Node(T data){
        this.data = data;
        this.right = null;
        this.down = null;
        this.next = null;
    }
}
