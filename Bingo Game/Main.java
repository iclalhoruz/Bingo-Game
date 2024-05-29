package tombala;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static int zinc1;
    public static int zinc2;
    
    public static void main(String[] args) {
        LinkedList<Integer> list1 = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();
        LinkedList<Integer> permList = new LinkedList<>();
        
        /*
        int[][] card1 = {{5, -1, 22, -1, 45, -1, 60, 73, -1},
            {-1, 10, -1, 31, 47, 58, 68, -1, -1},
            {-1, 17, 26, 38, -1, -1, -1, 79, 86}};
        
        int[][] card2 = {{-1, 17, -1, 34, -1, 51, 60, -1, 80},
            {4, -1, 27, -1, 45, -1, -1, 74, 86},
            {-1, -1, 29, 38, 49, -1, 65, 77, -1}};
        */
 
        //kart oluşturma
        int[][] card1 = generateCard();
        int[][] card2 = generateCard();
        
        int[][] lastCard1 = lastMatrix(card1);
        int[][] lastCard2 = lastMatrix(card2);
        
        while(lastCard1 == lastCard2){
            card2 = generateCard();
            lastCard2 = lastMatrix(card2);
        }
        
        //permutation oluşturma
        int[] randomPermutation = generatePermutation(90);
        //int[] randomPermutation = {10, 4, 34, 58, 31, 45, 68, 60, 47, 80, 5, 26, 22, 38, 73, 77, 51, 17, 86, 79};
        permList.PermutationLinkedList(randomPermutation);
        System.out.println();
        permList.print();
        
        //kartları linked listten yazdırma
        list1.MatrixToLinkedList(lastCard1);
        System.out.println();
        list1.printLinkedList();
        
        list2.MatrixToLinkedList(lastCard2);
        System.out.println();
        list2.printLinkedList();
        
        Bingo_GUI.main(card1, card2, list1, list2, permList);
    }
    
    
    public static void bingoControl(LinkedList<Integer> list1, LinkedList<Integer> list2, LinkedList<Integer> permList){
        
            int permNum = permList.getPerm();
            
            list1.Replace(permNum);
            list2.Replace(permNum);
            zinc1 = list1.checkBingo();
            zinc2 = list2.checkBingo();
            
            list1.printLinkedList();
            list2.printLinkedList();
            
            permList.remove();
            permList.print();
            
            Bingo_GUI.cardPanel.repaint();
        
    }
    
    private static int[][] generateCard(){
        int[][] card = new int[3][9];
        
        Random random = new Random();
        
        // random matrix oluşturma
        for(int col = 0; col < card[0].length; col++){
            int start = (col == card[0].length - 1) ? 81 : (10 * col);
            int end = (col == card[0].length - 1) ? 91 : (10 * (col + 1));
            
            for(int row = 0; row < card.length; row++){
                int randNum;
                boolean isUnique;
                
                //sütunlardaki sayıların aynı olup olmadığını kontrol etme
                do{
                    randNum = random.nextInt(end - start) + start;
                    isUnique = true;
                    
                    if(randNum == 0){
                        isUnique = false;
                        continue;
                    }
                    
                    for(int i = 0; i < row; i++){
                        if(card[i][col] == randNum){
                            isUnique = false;
                            break;
                        }
                    }
                    
                }while(!isUnique);
                
                card[row][col] = randNum;
            }
        }
        
        //her satırdan rastgele 4 indexe -1 yazdırma.
        for(int row = 0; row < card.length; row++){
            int count = 0;
            while(count < 4){
                int randİndex = random.nextInt(9);
                
                if(card[row][randİndex] != -1){
                    card[row][randİndex] = -1;
                    count++;
                }
            }
            
        }
        
        //kartı ekrana yazdırma
        for (int[] row : card) {
            System.out.print("[");
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.print("]");
            System.out.println();
        }
        System.out.println();
        
        return card;
    }

    private static int[][] lastMatrix(int[][] matrix) {
        if(matrix == null || matrix.length == 0){
            return null;
        }
        
        ArrayList<ArrayList<Integer>> newMatrix = new ArrayList<>();
        
        for(int i = 0; i < matrix.length; i++){
            ArrayList<Integer> row = new ArrayList<>();
            
            for(int ii = 0; ii < matrix[i].length; ii++){
                if(matrix[i][ii] != -1){
                    row.add(matrix[i][ii]);
                }
            }
            newMatrix.add(row);
        }
        
        int numRows = newMatrix.size();
        int numCols = newMatrix.get(0).size();
        int[][] finalMatrix = new int[numRows][numCols];
        
        for (int i = 0; i < numRows; i++) {
            ArrayList<Integer> row = newMatrix.get(i);
            for (int j = 0; j < numCols; j++) {
                finalMatrix[i][j] = row.get(j);
            }
        }
        
        for (int[] row : finalMatrix) {
            System.out.print("[");
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.print("]");
            System.out.println();
        }
        System.out.println();
        
        return finalMatrix;
    }

    private static int[] generatePermutation(int n) {
        int[] permutation = new int[n];
        for(int i = 0; i < n; i++){
            permutation[i] = i + 1;
        }
        
        shuffle(permutation);
        return permutation;
    }

    private static void shuffle(int[] permutation) {
        Random random = new Random();
        for(int i = permutation.length - 1; i > 0; i--){
            int index = random.nextInt(i + 1);
            int temp = permutation[index];
            permutation[index] = permutation[i];
            permutation[i] = temp;
        }
    }

}
    