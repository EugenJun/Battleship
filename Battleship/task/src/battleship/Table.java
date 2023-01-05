package battleship;

public class Table {
    char[][] battleCells = {
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
            {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
    };
    char[] verticalIndices = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    int[] horizontalIndices = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public char[] getVerticalIndices() {
        return this.verticalIndices;
    }

    public int[] getHorizontalIndices() {
        return this.horizontalIndices;
    }

    protected void drawTable() {

        char[] verticalIndices = getVerticalIndices();
        int[] horizontalIndices = getHorizontalIndices();

        System.out.print(" ");
        for (int horIndex : horizontalIndices) {
            System.out.print(" " + horIndex);
        }
        System.out.println();

        for (int i = 0; i < verticalIndices.length; i++) {
            System.out.print(verticalIndices[i] + " ");
            for (int j = 0; j < verticalIndices.length; j++) {
                System.out.print(battleCells[i][j] + " ");
            }
            System.out.println();
        }
    }

    protected void updateTable(int firstNumInd, int firstAlphaInd, int secondNumInd, int secondAlphaInd,
                               int minAlphaInd, int maxAlphaInd, int minNumInd, int maxNumInd) {

        if (firstNumInd == secondNumInd) {
            for (int i = firstNumInd - 1; i < firstNumInd; i++) {
                for (int j = minAlphaInd; j <= maxAlphaInd; ++j) {
                    this.setBattleCell(j, i, 'O');
                }
            }
        } else if (firstAlphaInd == secondAlphaInd) {
            for (int i = minNumInd - 1; i < maxNumInd; ++i) {
                this.setBattleCell(firstAlphaInd, i, 'O');
            }
        }
    }

    protected void updateTable(int numInd, int alphaInd, boolean isHit) {
        if (isHit) {
            setBattleCell(alphaInd, numInd, 'X');
        } else {
            setBattleCell(alphaInd, numInd, 'M');
        }
    }

    public char getBattleCell(int vertIndex, int horIndex) {
        return battleCells[vertIndex][horIndex];
    }

    public void setBattleCell(int vertIndex, int horIndex, char cellStatus) {
        battleCells[vertIndex][horIndex] = cellStatus;
    }
}

