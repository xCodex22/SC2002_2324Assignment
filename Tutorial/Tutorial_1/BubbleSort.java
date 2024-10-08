package Tutorial.Tutorial_1;

import java.util.Scanner;

public class BubbleSort {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[100];
        int n;
        int i;

        
        
        System.out.println("Enter number of Integer elements to be sorted: ");
        n = scanner.nextInt();

        for (i = 0; i < n; i++) {
            System.out.println("Enter integer value for element no." + i + " : ");
            array[i] = scanner.nextInt();
        }
        
        bubbleSort(array, n);
        
        System.out.println("Final sorted array is: ");

        for (int j = 0; j < n; j++) {
            System.out.print(array[j] + " ");
        }
        scanner.close();
    }
    
    public static void bubbleSort(int[] a, int n) {
        int i, j, t;
        for(i = n - 2; i>=0; i--) {
            for(j = 0; j <= i; j++) {
                if(a[j] > a[j+1]) {
                    t = a[j];
                    a[j] = a[j+1];
                    a[j+1] = t;
                }
            }
        }
    }   
}