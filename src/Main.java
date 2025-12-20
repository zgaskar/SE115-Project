// Main.java — Students version
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    public static int[][][] profits = new int[MONTHS][DAYS][COMMS];

    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        Scanner fileReader = null;

        for (int i = 0; i < MONTHS ; i++) {
            String fileName = "Data_Files/" + months[i] + ".txt";
            try{
                fileReader = new Scanner(Paths.get(fileName));
                fileReader.nextLine();

                while(fileReader.hasNextLine()){

                    String[] parts = fileReader.nextLine().split(",");

                    int daysValue = Integer.parseInt(parts[0].trim())-1;
                    String emtia = parts[1];
                    int commodityIndex = -1;
                    int profitValue = Integer.parseInt(parts[2].trim());

                    for (int j = 0; j < COMMS; j++) {
                        if(commodities[j].equals(emtia)){
                            commodityIndex = j;
                            break;
                        }

                    }
                    if(commodityIndex != -1){
                        profits[i][daysValue][commodityIndex] = profitValue;
                    }




                }
            } catch (Exception e) {
                System.out.println("File not found: " + fileName);
            } finally{
                if(fileReader != null){
                    fileReader.close();
                }

            }
            
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
       if(month<0 || month >=12){
           return "INVALID_MONTH";
       }

       int maxProfit = Integer.MIN_VALUE;
       String bestCommName = "";


           for (int c = 0; c < COMMS ; c++) {
               int currentCommTotal = 0;
               for (int d = 0; d < DAYS; d++) {
                   currentCommTotal += profits[month][d][c];

               }
               if(currentCommTotal > maxProfit){
                   maxProfit =  currentCommTotal;
                   bestCommName = commodities[c];
               }

           }

        return bestCommName + " " + maxProfit;
    }

    public static int totalProfitOnDay(int month, int day) {
        int dayIdx = day - 1;

        if(month<0 || month >= 12 || dayIdx<0 || dayIdx >= 28){
            return -99999;
        }

        int total = 0;
        for(int k = 0; k<COMMS; k++){
            total += profits[month][dayIdx][k];
        }
        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        System.out.println("Çalışma Yeri: " + System.getProperty("user.dir"));
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}