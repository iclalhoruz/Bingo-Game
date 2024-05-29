package tombala;

public class LinkedList<T>{
    private Node <T> head;
    private Node <T> permHead;
    public int permNum;
    
    public int getPerm(){
        return permNum = (int)permHead.data;
    }
    
    public LinkedList(){
        head = null;
        permHead = null;
    }    
    
    public Node<T> MatrixToLinkedList(int[][] matrix){
        if(matrix == null || matrix.length == 0){
            return null;
        }
        
        Node<T>[][] node = new Node[matrix.length][matrix[0].length];
        
        // her eleman için node oluşturur
        for(int i = 0; i < matrix.length; i++){
            for(int ii = 0; ii < matrix[i].length; ii++){
                if(matrix[i][ii] != -1){
                    node[i][ii] = new Node(matrix[i][ii]);
                }                   
            }
        }
        
        //node'ları birbirine bağlar
        for(int i = 0; i < matrix.length - 1; i++){
            for(int ii = 0; ii < matrix[i].length; ii++){
                if(node[i + 1][ii] != null && node[i][ii] != null){
                    node[i][ii].down = node[i + 1][ii];
                }
            }            
        }
        for(int i = 0; i < matrix.length; i++){
            for(int ii = 0; ii < matrix[i].length - 1; ii++){
                if(node[i][ii + 1] != null && node[i][ii] != null ){
                    node[i][ii].right = node[i][ii + 1];
                }
            }
        }
        
        head = node[0][0];
        return head;
    }
    
    public Node<T> PermutationLinkedList(int[] matrix){
        if(matrix == null || matrix.length == 0){
            return null;
        }
        
        Node<T>[] node = new Node[matrix.length];
        
        //her eleman için node oluştur
        for(int i = 0; i < matrix.length; i++){
            node[i] = new Node(matrix[i]);
        }
        
        //node'ları bağla
        for(int i = 0; i < matrix.length; i++){
            if(node[i] != null && i + 1 != matrix.length){
                node[i].next = node[i + 1];
            }
        }
        permHead = node[0];
        permNum = (int) permHead.data;
        
        return permHead;
    }
    
    public int size(){
        int count = 0;
        Node<T> temp = head;
        
        while(temp != null){
            count++;
            temp = temp.next;
        }
        return count;
    }
    
    //next kullanarak print etme
    public void print(){
        Node<T> temp = permHead;
        
        while(temp != null){
            System.out.print(temp.data + "->");
            temp = temp.next;
        }
        System.out.println("Null");
        
    }
    
    //right ve down kullanarak print etme
    public void printLinkedList() {
        Node<T> temp = head;
        while (temp != null) {
            Node<T> current = temp;
            Node<T> bottom = temp.down;
            
            
            while (current != null) {
                System.out.print(current.data + "->");
                current = current.right;
            }
            System.out.println("null");
            
            while (bottom != null) {
            
                System.out.print("|" + "    ");
                bottom = bottom.right;
                
            }
            System.out.println();
            temp = temp.down;
        }
    }
    
    
    
    public boolean search(T search){
       Node<T> temp = head;

        while (temp != null) {
            Node<T> current = temp;

            while (current != null) {
                if (current.data.equals(search)) {
                    return true;
                }
                current = current.right;
            }

            temp = temp.down;
    }

    return false;
    }
    
    public void Replace(T search){
        Node<T> temp = head;

        while (temp != null) {
            Node<T> current = temp;

            while (current != null) {
                if (current.data.equals(search)) {
                    System.out.println("Data found and updated: " + current.data + " to -1");
                    current.data = (T) (Object) (-1);
                    return;
                }
                current = current.right;
            }

            temp = temp.down;
        }

        System.out.println("Data not found: " + search);
    }
    
    public boolean remove(){
        if(permHead == null){
            System.out.println("Empty List");
        }
        
        else{
            permHead = permHead.next;
            return true;
            
        }
        return false;
    }
    
    public int checkBingo(){
        Node<T> temp = head;
        int rowCount = 0;
        boolean isBingo = false;
        
        while(temp != null){
            Node<T> current = temp;
            
            //satırdaki değerleri kontrol et
            boolean isCinko = true;
            while(current != null){
                if(!current.data.equals((T)(Object)(-1))){
                    isCinko = false;
                    break;
                }
                
                current = current.right;
            }
            if(isCinko){
                rowCount++;
                isBingo = true;
            }
            temp = temp.down;
        }
        if(isBingo){
            if(rowCount == 3)
                System.out.println("Bingo!");
            else
                System.out.println(rowCount + ". Çinko");
            
            return rowCount;
        }
        return 0;
    }
}