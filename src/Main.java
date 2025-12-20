// Main.java — Students version
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    public static int[][][] profits = new int[MONTHS][DAYS][COMMS];


    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        Scanner fileReader = null;

        for (int i = 0; i < MONTHS; i++) {
            String fileName = "Data_Files/" + months[i] + ".txt";
            try {
                fileReader = new Scanner(Paths.get(fileName));
                fileReader.nextLine();

                while (fileReader.hasNextLine()) {

                    String[] parts = fileReader.nextLine().split(",");

                    int daysValue = Integer.parseInt(parts[0].trim()) - 1;
                    String emtia = parts[1];
                    int commodityIndex = -1;
                    int profitValue = Integer.parseInt(parts[2].trim());

                    for (int j = 0; j < COMMS; j++) {
                        if (commodities[j].equals(emtia)) {
                            commodityIndex = j;
                            break;
                        }

                    }
                    if (commodityIndex != -1) {
                        profits[i][daysValue][commodityIndex] = profitValue;
                    }
                }
            } catch (Exception e) {
                System.out.println("File not found: " + fileName);
            } finally {
                if (fileReader != null) {
                    fileReader.close();
                }

            }

        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= 12) {
            return "INVALID_MONTH";
        }

        int maxProfit = Integer.MIN_VALUE;
        String bestCommName = "";
        for (int c = 0; c < COMMS; c++) {
            int currentCommTotal = 0;
            for (int d = 0; d < DAYS; d++) {
                currentCommTotal += profits[month][d][c];

            }
            if (currentCommTotal > maxProfit) {
                maxProfit = currentCommTotal;
                bestCommName = commodities[c];
            }

        }

        return bestCommName + " " + maxProfit;
    }

    public static int totalProfitOnDay(int month, int day) {
        int dayIdx = day - 1;

        if (month < 0 || month >= 12 || dayIdx < 0 || dayIdx >= 28) {
            return -99999;
        }


        int total = 0;
        for (int k = 0; k < COMMS; k++) {
            total += profits[month][dayIdx][k];
        }
        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int commIdx = -1;

        for (int j = 0; j < COMMS; j++) {
            if (commodities[j].equals(commodity)) {
                commIdx = j;
                break;
            }
        }
        if (commIdx == -1 || from > to || from < 1 || to > 28) {
            return -99999;
        }
        int totalProfit = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d <= to - 1; d++) {
                totalProfit += profits[m][d][commIdx];
            }
        }
        return totalProfit;
    }

    public static int bestDayOfMonth(int month) {

        if (month >= MONTHS || month < 0) {
            return -1;
        }
        int maxProfit = Integer.MIN_VALUE;
        int bestDayIdx = -1;

        for (int d = 0; d < DAYS; d++) {
            int dailyTotal = 0;
            for (int c = 0; c < COMMS; c++) {
                dailyTotal += profits[month][d][c];
            }
            if (dailyTotal > maxProfit) {
                maxProfit = dailyTotal;
                bestDayIdx = d;
            }
        }
        return bestDayIdx + 1;
    }

    public static String bestMonthForCommodity(String comm) {
        int commIdx = -1;

        for (int j = 0; j < COMMS; j++) {
            if (commodities[j].equals(comm)) {
                commIdx = j;
                break;
            }
        }
        if (commIdx == -1) {
            return "INVALID_COMMODITY";
        }

        int maxProfit = Integer.MIN_VALUE;
        int bestMonthIdx = -1;

        for (int m = 0; m < MONTHS; m++) {
            int currentMonthTotal = 0;
            for (int d = 0; d < DAYS; d++) {
                currentMonthTotal += profits[m][d][commIdx];
            }
            if (currentMonthTotal > maxProfit) {
                maxProfit = currentMonthTotal;
                bestMonthIdx = m;
            }
        }

        if (bestMonthIdx != -1) {
            return months[bestMonthIdx];
        }

        return "NO_DATA";
    }

    public static int consecutiveLossDays(String comm) {
        int commIdx = -1;

        for (int j = 0; j < COMMS; j++) {
            if (commodities[j].equals(comm)) {
                commIdx = j;
                break;
            }
        }
        if (commIdx == -1) {
            return -1;
        }

        int maxStreak = 0;
        int currentStreak = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                int profit = profits[m][d][commIdx];

                if (profit < 0) {
                    currentStreak++;
                } else {
                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                    currentStreak = 0;
                }

            }

        }
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }

        return maxStreak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int commIdx = -1;

        for (int j = 0; j < COMMS; j++) {
            if (commodities[j].equals(comm)) {
                commIdx = j;
                break;
            }
        }
        if (commIdx == -1) {
            return -1;
        }

        int count = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][commIdx] > threshold) {
                    count++;
                }
            }
        }

        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) {
            return -99999;
        }

        int maxSwing = 0;

        for (int d = 0; d < DAYS; d++) {
            int dailyMax = Integer.MIN_VALUE;
            int dailyMin = Integer.MAX_VALUE;

            for (int c = 0; c < COMMS; c++) {
               int profit = profits[month][d][c];

                if (profit > dailyMax) {
                    dailyMax = profit;
                }
                if (profit < dailyMin) {
                    dailyMin = profit;
                }
            }

            int currentSwing = dailyMax - dailyMin;

            if (currentSwing > maxSwing) {
                maxSwing = currentSwing;
            }
        }
        return maxSwing;
    }
    
    public static String compareTwoCommodities(String c1, String c2) {
        int idx1 = -1;
        int idx2 = -1;

        for (int j = 0; j < COMMS; j++) {
            if (commodities[j].equals(c1)) {
                idx1 = j;
            }
            if (commodities[j].equals(c2)) {
                idx2 = j;
            }
        }
        if (idx1 == -1 || idx2 == -1) {
            return "INVALID_COMMODITY";
        }

        int total1 = 0;
        int total2 = 0;
        String winner = "";

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                total1 += profits[m][d][idx1];
                total2 += profits[m][d][idx2];
            }

        }

        int diff = Math.abs(total1 - total2);

        if (total1 > total2) {
            return c1 + " is better by " + diff;
        } else if (total2 > total1) {
            return c2 + " is better by " + diff;
        } else {
          return "EQUAL";
        }
    }
    
    public static String bestWeekOfMonth(int month) {
        if(month<0 || month>= MONTHS){
                return "INVALID_MOTH";
        }

        int maxProfit = Integer.MIN_VALUE;
        int bestStartIdx = -1;
        for (int d = 0; d < DAYS ; d+=7) {
            int currentWeekProfit = 0;
            for (int k = 0; k < 7 ; k++) {
            for (int c = 0; c < COMMS; c++) {
                int profit = profits[month][d][c];
                currentWeekProfit += profit;
                }
            }
            if(currentWeekProfit > maxProfit){
                maxProfit = currentWeekProfit;
                bestStartIdx = d;
            }
        }
        int week = (bestStartIdx / 7) + 1;

        return "Week " + week;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");

    }
}