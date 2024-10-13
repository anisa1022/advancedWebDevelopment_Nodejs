import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TargetCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String startDateStr = getDateInput(scanner, "Enter start date (YYYY-MM-DD): ");
        String endDateStr = getDateInput(scanner, "Enter end date (YYYY-MM-DD): ");

        System.out.print("Enter total annual target: ");
        double totalAnnualTarget = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter monthly target: ");
        double monthlyTarget = Double.parseDouble(scanner.nextLine());

        // Calculate and display results
        Result result = calculateTargets(startDateStr, endDateStr, monthlyTarget);
        displayResults(result);

        scanner.close();
    }

    private static String getDateInput(Scanner scanner, String prompt) {
        String dateInput;
        while (true) {
            System.out.print(prompt);
            dateInput = scanner.nextLine();
            if (isValidDate(dateInput)) {
                break;
            }
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
        return dateInput;
    }

    private static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static Result calculateTargets(String startDateStr, String endDateStr, double monthlyTarget) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        List<Double> monthlyTargets = new ArrayList<>();
        List<Integer> daysExcludingFridays = new ArrayList<>();
        List<Integer> daysWorkedExcludingFridays = new ArrayList<>();

        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            // Get the first and last day of the month
            LocalDate monthStart = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
            LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

            // Adjust monthStart and monthEnd to stay within the provided date range
            monthStart = monthStart.isBefore(startDate) ? startDate : monthStart;
            monthEnd = monthEnd.isAfter(endDate) ? endDate : monthEnd;

            // Calculate total days excluding Fridays for the entire month
            int totalDaysExcludingFridays = calculateTotalDaysExcludingFridays(monthStart.getYear(), monthStart.getMonthValue());
            daysExcludingFridays.add(totalDaysExcludingFridays);

            // Calculate working days in the range [monthStart, monthEnd]
            int workingDays = calculateWorkingDays(monthStart, monthEnd);
            daysWorkedExcludingFridays.add(workingDays);

            // Determine if it's a full month
            boolean isFullMonth = monthStart.getDayOfMonth() == 1 && 
                                  monthEnd.getDayOfMonth() == monthEnd.lengthOfMonth();

            // Calculate target for the month
            double targetForMonth;
            if (isFullMonth) {
                // Full month worked
                targetForMonth = monthlyTarget;
            } else {
                // Calculate daily rate based on total days excluding Fridays
                double dailyRate = totalDaysExcludingFridays > 0 ? monthlyTarget / totalDaysExcludingFridays : 0; // Avoid division by zero
                targetForMonth = dailyRate * workingDays; // Target based on actual working days
            }
            monthlyTargets.add(targetForMonth);

            // Move to the next month
            currentDate = monthEnd.plusDays(1); // Increment to the next month
        }

        double totalTarget = monthlyTargets.stream().mapToDouble(Double::doubleValue).sum();
        return new Result(daysExcludingFridays, daysWorkedExcludingFridays, monthlyTargets, totalTarget);
    }

    private static int calculateWorkingDays(LocalDate start, LocalDate end) {
        int workingDays = 0;
        LocalDate currentDay = start;

        while (!currentDay.isAfter(end)) {
            if (currentDay.getDayOfWeek().getValue() != 5) { // Exclude Fridays from worked days
                workingDays++;
            }
            currentDay = currentDay.plusDays(1);
        }
        return workingDays;
    }

    private static int calculateTotalDaysExcludingFridays(int year, int month) {
        int totalDaysExcludingFridays = 0;
        LocalDate monthStart = LocalDate.of(year, month, 1);
        LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

        LocalDate currentDay = monthStart;

        while (!currentDay.isAfter(monthEnd)) {
            if (currentDay.getDayOfWeek().getValue() != 5) { // Count only non-Fridays in the days of the entire month 
                totalDaysExcludingFridays++;
            }
            currentDay = currentDay.plusDays(1);
        }
        return totalDaysExcludingFridays;
    }

    private static void displayResults(Result result) {
        System.out.println("Days Excluding Fridays: " + result.daysExcludingFridays);
        System.out.println("Days Worked Excluding Fridays: " + result.daysWorkedExcludingFridays);
        System.out.println("Monthly Targets: " + result.monthlyTargets);
        System.out.printf("Total Target: %.2f%n", result.totalTarget);
    }

    // Helper class to hold results
    static class Result {
        List<Integer> daysExcludingFridays;
        List<Integer> daysWorkedExcludingFridays;
        List<Double> monthlyTargets;
        double totalTarget;

        public Result(List<Integer> daysExcludingFridays, List<Integer> daysWorkedExcludingFridays, List<Double> monthlyTargets, double totalTarget) {
            this.daysExcludingFridays = daysExcludingFridays;
            this.daysWorkedExcludingFridays = daysWorkedExcludingFridays;
            this.monthlyTargets = monthlyTargets;
            this.totalTarget = totalTarget;
        }
    }
}
