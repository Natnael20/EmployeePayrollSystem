/**
 * 
 */
package employeePayrollSystem;
//importing packages
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;
/**
 * The application is about employee income system
 * It consists of different functionalities from naming to monthly salarly
 * Functionalities: 
 * 1. Add new employee  
 * 2. Record monthly salary  
 * 3. Calculate annual salary for an employee  
 * 4. Print all employees (sorted by name)  
 * 5. Print all employees (sorted by annual salary)  
 * q. End program  
 * @author XIX
 */
public class Main {

	//global variable
	private static final Scanner userInput = new Scanner(System.in);
	private static final Random random = new Random();
	
	//numerical constants
	public static final int MAX_AMOUNT = 20;
	public static final int ANNUAL_MONTH = 12;
	public static final int MIN_ID_SIZE = 1000;
	public static final int MAX_ID_SIZE = 9001;
	public static final int MAX_SALARY = 10000;
	
	//non-numerical constants
	public static final String EMPLOYEE_NAME = "> Enter Your Name: ";
	public static final String EMPLOYEE_ID = "> Enter employee ID: ";
	public static final String MONTHLY_SALARY = "> Enter monthly salary for employee: ";
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//storage for the user
		String[][] employeeName = new String[MAX_AMOUNT][MAX_AMOUNT];
		int[][] employeeId = new int[MAX_AMOUNT][MAX_AMOUNT];
		int[][] monthlySalary = new int[ANNUAL_MONTH][ANNUAL_MONTH];
		int[][] annualSalary = new int[MAX_AMOUNT][MAX_AMOUNT];
		int[] saveData = new int[MAX_AMOUNT];
		
		//program controller
		boolean programOn = true;

		while(programOn) {
			//prompting the user to choose
			menu();
			String option = userInput.nextLine();
			switch(option) {
				case "1" : 
					addEmployee(employeeName, employeeId, saveData);
					break;
				case "2" :
					monthlySalary(employeeId, saveData, monthlySalary);
					break;
				case "3" :
					annualSalary(employeeId, saveData, monthlySalary, annualSalary);
				break;
				case "4" :
					displayEmployeeByName(employeeName, employeeId, saveData, 
							monthlySalary, annualSalary);
					break;
				case "5" :
					displayEmployeeByAnnual(employeeName, employeeId, saveData, 
							monthlySalary, annualSalary);
					break;
				case "q" :
					break;
				default: 
					System.out.println("Invalid input");
			}
			
		}
	}
	
	//writing the functionality for the management system
	//beginning with menu
	/**
	 * different functionality the user to choose from
	 */
	public static void menu() {
		System.out.println("----------------------------------");
		System.out.println("# Employee Payroll System");
		System.out.println("----------------------------------");
		System.out.println("1. Add new employee");
		System.out.println("2. Record monthly salary");
		System.out.println("3. Calculate annual salary for an employee");
		System.out.println("4. Print all employees (sorted by name)");
		System.out.println("5. Print all employees (sorted by annual salary)");
		System.out.println("q. End program");
		System.out.print("> Enter your option: ");
		
	}
	
	/**
	 * The method will handle new employee or storage for new person
	 * Validation/requirement:
	 * 1. Ensure the employee's name is not empty or null.
	 * 2. Check that the employee ID is unique (no duplicates).
	 * 3. Handle edge cases for overly long names (e.g., limit to 50 characters).
	 * @param employee
	 * @param id
	 * @param saveData
	 */
	public static void addEmployee(String[][] employee, int[][] id, int[] saveData) {
		//declare local variables
		String employeeName = "";
		int uniqueId;
        boolean idIsUnique;

        System.out.print(EMPLOYEE_NAME);
		employeeName = userInput.nextLine();
		
		//validation
		//Ensure the employee's name is not empty or null
		if(employeeName.isEmpty() || employeeName == null) {
			System.out.println("Employee cannot be empty or null!");
			return;
		}

		
		//generate a unique id
        do {
            uniqueId = random.nextInt(MAX_ID_SIZE - MIN_ID_SIZE + 1) + MIN_ID_SIZE;
            idIsUnique = true;
            for (int i = 0; i < saveData.length; i++) {
                //making sure the id is unique
            	if (id[0][i] == uniqueId) {
                    idIsUnique = false;
                    break;
                }
            }
        } while (!idIsUnique); //id is not unique
        
        //saving data
        int indexSaving = saveData[0]++;
        employee[0][indexSaving] = employeeName;
        id[0][indexSaving] = uniqueId;
        
        System.out.println("Employee ID is: " + uniqueId);
	}
	
	/**
	 * Record monthly salary for the employee
	 * validation/requirement
	 * 1. Ensure the salary is a positive number.
	 * 2. Reject unrealistic values (e.g., salaries in the billions).
	 * 3. Check if the employee ID exists before recording the salary.
	 * @param id
	 * @param saveData
	 * @param monthlySalary
	 */
	public static void monthlySalary(int[][] id, int[] saveData, int[][] monthlySalary) {
	    // Local variables
	    int employeeId = 0;
	    int salary = 0;
	    boolean idExist = false;

	    while (true) { // Loop to allow retrying on invalid ID
	        System.out.print(EMPLOYEE_ID);
	        employeeId = userInput.nextInt();

	        // Validation: Ensure the ID exists in the 2D array `id`
	        for (int i = 0; i < saveData.length; i++) {
	            if (id[0][i] == employeeId) {
	                idExist = true;
	                break;  // Exit loop if ID is found
	            }
	        }

	        if (!idExist) {
	            System.out.println("ID doesn't exist! Try again or type -1 to return to the menu.");
	            if (employeeId == -1) {
	                return; // Exit to main menu
	            }
	        } else {
	            break; // Valid ID found, exit the loop
	        }
	    }

	    // Find the employee's index for storing salary
	    int saveIndex = -1;
	    for (int i = 0; i < saveData.length; i++) {
	        if (id[0][i] == employeeId) {
	            saveIndex = i;  // Assign index of the employee
	            break;  // Exit loop once employee index is found
	        }
	    }
	    
	    // Creating a space between employee and salary input
	    userInput.nextLine();

	    // Ask for monthly salary input (same for all 12 months)
	    System.out.print(MONTHLY_SALARY);
	    salary = userInput.nextInt();

	    // Salary validation: Ensure salary is positive
	    while (salary < 0) {
	        System.out.println("Salary cannot be negative!");
	        System.out.print(MONTHLY_SALARY);
	        salary = userInput.nextInt();
	    }

	    // Validate if salary is realistic
	    while (salary > MAX_SALARY) {
	        System.out.println("Provide a realistic salary! Maximum is " + MAX_SALARY);
	        System.out.print(MONTHLY_SALARY);
	        salary = userInput.nextInt();
	    }

	    userInput.nextLine();

	    // Set the same salary for all 12 months for this employee
	    for (int month = 0; month < ANNUAL_MONTH; month++) {
	        monthlySalary[saveIndex][month] = salary;
	    }

	    System.out.println("Monthly salary recorded successfully for Employee ID: " + employeeId);
	}


	/**
	 * calculate annual salary
	 * valiation/requirment
	 * 1. Check if the entered ID exists in the system.
	 * 2. Display an error if the ID is not found.
	 * 3. Ensure the employee has a recorded monthly salary.
	 * 4. Display an error if no salary data is available for the employee.
	 * @param id
	 * @param saveData
	 * @param monthlySalary
	 * @param annualSalary
	 */
	public static void annualSalary(int[][] id, int[] saveData, int[][] monthlySalary, int[][] annualSalary) {
		//local variable
		int employeeId = 0;
		boolean idExist = false;
				
		System.out.print(EMPLOYEE_ID);
		employeeId = userInput.nextInt();
				
		//validation
		//ensure the id exist in the 2array id
		for(int i=0; i < saveData.length; i++) {
			if(id[0][i] == employeeId) {
				idExist = true; //ID is found in array
			}
		}
				
		if(!idExist) {
			System.out.println("ID doesn't exist!");
			return; //Id doesn't found
		}
				
		//creating a space between employee and salary
		userInput.nextLine();
		
		// Track the employee's index based on their ID
	    int employeeIndex = -1;
	    for (int i = 0; i < saveData.length; i++) {
	        if (id[0][i] == employeeId) {
	            employeeIndex = i;
	            break;
	        }
	    }

	    // Calculate the annual salary (sum of monthly salaries)
	    int sumAnnualSalary = 0;
	    for (int i = 0; i < ANNUAL_MONTH; i++) {
	        sumAnnualSalary += monthlySalary[employeeIndex][i];
	    }
	    
	    // Store the calculated annual salary for this employee
	    annualSalary[0][employeeIndex] = sumAnnualSalary;
		
	    System.out.println("Annual Salary: " + sumAnnualSalary + "kr!");
	}
	/**
	 * display history by sorted name
	 * requirement/validation
	 * call a method called sortByName
	 * @param employee
	 * @param id
	 * @param saveData
	 * @param monthlySalary
	 * @param annualSalary
	 */
	public static void displayEmployeeByName(String[][] employee, int[][] id, int[] saveData, 
			int[][] monthlySalary, int[][] annualSalary) {

        System.out.printf("%-20s %-15s %-15s %-15s%n", "Id", "Employee", "Monthly Salary", "Annual Salary");
        
        sortByName(employee, id, monthlySalary, annualSalary, saveData);
        
        // Loop through the employees based on saveData
        for (int i = 0; i < saveData[0]; i++) {
            // Calculate the monthly salary (assuming all months have the same salary for simplicity)

            // Display the employee data
            System.out.printf("%-20d %-15s %-15d %-15d%n", 
                    id[0][i], 
                    employee[0][i], 
                    monthlySalary[i][0], 
                    annualSalary[0][i]);
        }
	}
	
	/**
	 * Display history by sorted annual salary
	 * @param employee
	 * @param id
	 * @param saveData
	 * @param monthlySalary
	 * @param annualSalary
	 */
	public static void displayEmployeeByAnnual(String[][] employee, int[][] id, int[] saveData, 
			int[][] monthlySalary, int[][] annualSalary) {
		
		System.out.printf("%-20s %-15s %-15s %-15s%n", "id", "employee", "monthlySalary", "annualSalary");
        
		sortByAnnualSalary(employee, id, monthlySalary, annualSalary, saveData);
        
		// Loop through the employees based on saveData
        for (int i = 0; i < saveData[0]; i++) {
            // Display the employee data
            System.out.printf("%-20d %-15s %-15d %-15d%n", 
                    id[0][i], 
                    employee[0][i], 
                    monthlySalary[i][0], 
                    annualSalary[0][i]);
        }
	}
	
	public static void sortByName(String[][] employeeName, int[][] employeeId, int[][] monthlySalary, int[][] annualSalary, int[] saveData) {
	    // Bubble sort for sorting employeeName alphabetically
	    for (int i = 0; i < saveData[0] - 1; i++) {
	        for (int j = 0; j < saveData[0] - 1 - i; j++) {
	            if (employeeName[0][j].compareTo(employeeName[0][j + 1]) > 0) {
	                // Swap employee names
	                String tempName = employeeName[0][j];
	                employeeName[0][j] = employeeName[0][j + 1];
	                employeeName[0][j + 1] = tempName;

	                // Swap employee IDs to keep them in sync with the employee names
	                int tempId = employeeId[0][j];
	                employeeId[0][j] = employeeId[0][j + 1];
	                employeeId[0][j + 1] = tempId;

	                // Swap monthly salaries to keep them in sync with the employee names
	                int tempSalary;
	                for (int month = 0; month < ANNUAL_MONTH; month++) {
	                    tempSalary = monthlySalary[j][month];
	                    monthlySalary[j][month] = monthlySalary[j + 1][month];
	                    monthlySalary[j + 1][month] = tempSalary;
	                }

	                // Swap annual salary to keep it in sync with the employee names
	                int tempAnnualSalary = annualSalary[0][j];
	                annualSalary[0][j] = annualSalary[0][j + 1];
	                annualSalary[0][j + 1] = tempAnnualSalary;
	            }
	        }
	    }
	}
	
	public static void sortByAnnualSalary(String[][] employeeName, int[][] employeeId, int[][] monthlySalary, int[][] annualSalary, int[] saveData) {
	    // Bubble sort for sorting employees by their annual salary in ascending order
	    for (int i = 0; i < saveData[0] - 1; i++) {
	        for (int j = 0; j < saveData[0] - 1 - i; j++) {
	            if (annualSalary[0][j] > annualSalary[0][j + 1]) {
	                // Swap employee names to keep them in sync with annual salary
	                String tempName = employeeName[0][j];
	                employeeName[0][j] = employeeName[0][j + 1];
	                employeeName[0][j + 1] = tempName;

	                // Swap employee IDs to keep them in sync with annual salary
	                int tempId = employeeId[0][j];
	                employeeId[0][j] = employeeId[0][j + 1];
	                employeeId[0][j + 1] = tempId;

	                // Swap monthly salaries to keep them in sync with annual salary
	                int tempSalary;
	                for (int month = 0; month < ANNUAL_MONTH; month++) {
	                    tempSalary = monthlySalary[j][month];
	                    monthlySalary[j][month] = monthlySalary[j + 1][month];
	                    monthlySalary[j + 1][month] = tempSalary;
	                }

	                // Swap annual salary to keep it in sync
	                int tempAnnualSalary = annualSalary[0][j];
	                annualSalary[0][j] = annualSalary[0][j + 1];
	                annualSalary[0][j + 1] = tempAnnualSalary;
	            }
	        }
	    }
	}
}
