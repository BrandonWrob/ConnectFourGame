public class WinCondition {
    private String[][] replicaLabelStringArray;

    public WinCondition(int rows, int columns) {
        replicaLabelStringArray = new String[rows][columns];
    }

    public void updateLabelStringArray(String[][] labelStringArray) {
        for (int i = 0; i < labelStringArray.length; i++) {
            for (int j = 0; j < labelStringArray[i].length; j++) {
                replicaLabelStringArray[i][j] = labelStringArray[i][j];
            }
        }
    }

    public void printReplicaLabelStringArray() {
        for (int i = 0; i < replicaLabelStringArray.length; i++) {
            for (int j = 0; j < replicaLabelStringArray[i].length; j++) {
                System.out.print(replicaLabelStringArray[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
