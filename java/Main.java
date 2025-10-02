public class Main {
  private static <T> void printArray(T[] arr) {
    for(T elem : arr) {
      System.out.println(elem);
    }
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{1, 5};
    Integer[] b = {1, 2, 3};
    int[][] c1 = new int[][] {
      {1, 2},
      {3, 4}
    };
    int[][] c2 = new int[][] {
      {1, 2},
      {3},
      {4, 5, 6}
    };
    int[][] c3 = new int[3][];
    int[][] c4 = new int[3][4];

    printArray(a);
    printArray(b);
    printArray(c1);
    printArray(c2);
    printArray(c3);
  }
}