public class Garden {
    private String[][] garden;
    private int gardenRow;
    private int gardenColumn;
    public Garden(int row, int column){
        this.gardenRow = row;
        this.gardenColumn = column;
        // every element of this array is like #//#//#//#
        garden = new String[row][column];
        for (int rowCounter = 0 ; rowCounter < row ; rowCounter++){
            for (int columnCounter = 0 ; columnCounter < column ; columnCounter++){
             //   garden[rowCounter][columnCounter] = new String();
                garden[rowCounter][columnCounter] = "mt";
            }

        }
    }
    public String[][] getGarden(){
        return this.garden;
    }

    public int getGardenRow() {
        return gardenRow;
    }

    public int getGardenColumn() {
        return gardenColumn;
    }
}
