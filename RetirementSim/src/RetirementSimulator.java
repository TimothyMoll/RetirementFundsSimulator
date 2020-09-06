import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class takes in multiple user inputs for different financial categories and then uses
 *   these inputs to run a simulation  of how much their savings account is worth during these simulated years.
 *   The program  then  displays to  the user a list of their inputs and the max/mins of the simulated results
 *   along with a histogram of the accounts value.
 * 
 * @author Timothy 
 *
 */
public class RetirementSimulator {
	
/**Scanner to obtain the various numerical inputs from the user */
	private Scanner userInputs;
/** Input value from user that stores users initial savings account number*/
	private double  initialInvestment;
/**Input value from user that stores the users initial salary*/
	private double  initialSalary;
/** This is a value that changes through each simulated year, used to store the amount of money in the retirement account*/
	private double  totalInvestment;
/** This is a value that changes through each simulated year, used to keep track of the users salary each year with its increases/decreases*/
	private double  userSalary;
/** User input value used to hold the smallest amount of salary the user plans to put into the investment account*/
	private double  minSalarySaved;
/** User input value used to hold the largest amount of salary the user plans to put into the investment account*/
	private double  maxSalarySaved;
/** User input value used to hold the minimum amount the user wants/expects their investments to make */
	private double  minAnnualReturn;
/** User input value used to hold the maximum amount the user wants/expects their investments to make */
	private double  maxAnnualReturn;
/** User input value used to hold the minimum amount the user expects their salary raise to be */
	private double  minAnnualRaise;
/** User input value used to hold the maximum amount the user expects their salary raise to be */
	private double  maxAnnualRaise;
/** User input integer to tell how many years they have until they retire*/
	private int     yearsToRetirement;
/** Array used to store the amount the account is worth during each year, using the years as a counter on the size of the array to make*/
	private ArrayList<Double> investedArray;
/** The value of the smallest number, in the range given, the simulation gave for the users salary saved for a year*/
	private double minSimulatedSalarySaved;
/** The value of the largest number, in the range given, the simulation gave for the users salary saved for a year*/
	private double maxSimulatedSalarySaved;
/** The value of the total number of salary was saved in order to make a mean*/
	private double sumSimulatedSalarySaved;
/** The value of the smallest number, in the range given, the simulation gave for the users annual return on the investments for a year*/
	private double minSimulatedAnnualReturn;
/** The value of the largest number, in the range given, the simulation gave for the users annual return on the investments for a year*/
	private double maxSimulatedAnnualReturn;
/** The value of the total number the simulation gave for the users annual return on the investments*/
	private double sumSimulatedAnnualReturn;
/** The value of the smallest number, in the range given, the simulation gave for the users annual raise for a year*/
	private double minSimulatedAnnualRaise;
/** The value of the largest number, in the range given, the simulation gave for the users annual raise for a year*/
	private double maxSimulatedAnnualRaise;
/** The value of the total number the simulation gave for the users annual raise*/
	private double sumSimulatedAnnualRaise;	
/** The value of the largest number the simulation got for the users salary*/
	private double maxSimulatedSalary;
	
	
	/**
	 * The main function on this programs sole function is to call the other functions in order to get the users input, 
	 * run the simulation to get the results, and then to show the results and histogram to the user.
	 * @param args
	 */
 	public static void main(String[] args) {
		RetirementSimulator investment = new RetirementSimulator();
		investment.getInputs();
		investment.runSimulation();
		investment.printResults();
		investment.makeHistogram();
	}
	
 	/**
 	 * This methods purpose is to run other input methods in order to get all of the users input on 
 	 * different aspects of their financial (simulated) life
 	 */
	private void getInputs()
	{
		userInitialInvest();
		initialSalaryInput();
		minSavedInput();
		maxSavedInput();
		minAnnualReturnInput();
		maxAnnualReturnInput();
		minAnnualRaiseInput();
		maxAnnualRaiseInput();
		yearsToRetirementInput();
	}
	
	/**
	 * This methods purpose is to actually do the calculations using the inputs from the user. It randomizes values inside the bounds given by the user and runs
	 * a loop with a length based on the years until desired retirement in order to keep changing the values each simulated year. Some values are saved each time
	 *  in order to print different results for the user to view using a different method.
	 */
	private void runSimulation(){
		double randInvestedSalary;
		double randInvestmentGainLoss;
		double salaryChange;
		this.investedArray = new ArrayList<Double>();
		this.investedArray.add(this.initialInvestment);
	
	 for(int yearsLeft  = 1; yearsLeft <= this.yearsToRetirement; yearsLeft++)
	 	{
		randInvestmentGainLoss   = randomInRange(this.minAnnualReturn,this.maxAnnualReturn);
		this.totalInvestment     = (randInvestmentGainLoss*this.totalInvestment)/100 + this.totalInvestment;
		
		randInvestedSalary       = randomInRange(this.minSalarySaved,this.maxSalarySaved);
	    this.totalInvestment     = (randInvestedSalary*this.userSalary)/100 + this.totalInvestment;
	    
		salaryChange             = randomInRange(this.minAnnualRaise, this.maxAnnualRaise);
		this.userSalary          = roundNumberDecimals((salaryChange*this.userSalary)/100 + this.userSalary);
				
		maxSalaryChanger(this.userSalary);
		minMaxChangeSalaryIncrease(salaryChange);
		minMaxChangeSalarySaved(randInvestedSalary);
		minMaxChangeAnnualReturn(randInvestmentGainLoss);
		this.sumSimulatedAnnualReturn  = this.sumSimulatedAnnualReturn   + randInvestmentGainLoss;
		this.sumSimulatedSalarySaved   = this.sumSimulatedSalarySaved    + randInvestedSalary;
		this.sumSimulatedAnnualRaise   = this.sumSimulatedAnnualRaise    + salaryChange;
		this.investedArray.add(roundNumberDecimals(this.totalInvestment));
		}
	}
	
	/**
	 * This methods purpose is to display two things in an easy to read manner for the user. First it displays 
	 * everything the user input to the simulation, 
	 * then this it displays different, random results the simulation produced in its loop within their upper and lower bounds.
	 */
	private void printResults(){
		System.out.println("");
		System.out.println("You entered:");
		System.out.println("Initial Investment: $"+this.initialInvestment);
		System.out.println("Initial Salary: $"+this.initialSalary);
		System.out.println("Yearly percentage of salary saved: "+this.minSalarySaved + " - "+ this.maxSalarySaved+"%");
		System.out.println("Range of yearly returns: "+this.minAnnualReturn + "% - "+ this.maxAnnualReturn+"%");
		System.out.println("Yearly salary increase range: "+this.minAnnualRaise + "% - "+ this.maxAnnualRaise+"%");
		System.out.println("Number of years until retirement: " + yearsToRetirement+" years");
		System.out.println("");
		System.out.println("The Simulation gave these results:");
		System.out.println("The range percent salary saved-- min: "+ this.minSimulatedSalarySaved+"%. max: "+this.maxSimulatedSalarySaved+"%. Average: "+roundNumberDecimals(this.sumSimulatedSalarySaved/this.yearsToRetirement)+"%");
		System.out.println("The percent of annual asset returns range-- Min: "+ this.minSimulatedAnnualReturn+"%.  Max: "+this.maxSimulatedAnnualReturn+"%. Average: "+roundNumberDecimals(this.sumSimulatedAnnualReturn/this.yearsToRetirement)+"%");
		System.out.println("Yearly percentage of salary increase-- min: "+ this.minSimulatedAnnualRaise+"% Max: "+ this.maxSimulatedAnnualRaise+"% Average: "+roundNumberDecimals(this.sumSimulatedAnnualRaise/this.yearsToRetirement)+"%");
		System.out.println("The maximum salary used in the simulation was: $"+ roundNumberDecimals(this.maxSimulatedSalary));
		System.out.println("");
	}
	
	/**
	 * This method is used to obtain a users input, check if it is valid (shows error if the value is not valid), 
	 * and then if valid become the value of the users initial investment
	 * and total investment, because before the loop of the simulation (before the user simulates investments), the only money in the account
	 * is the money initially invested. 
	 */
	private void userInitialInvest(){
		double initialInvestment;
		do{
		System.out.println("Enter the amount in your retirement account as of now ");
		this.userInputs = new Scanner(System.in);
		initialInvestment = userInputs.nextDouble();
		initialInvestment = roundNumberDecimals(initialInvestment);
		if(initialInvestment<0.0){
			System.err.println("Account Balance Cannot Be Below $0.00");
		}
		}while(initialInvestment<0.0);
		
		totalInvestment         = initialInvestment;
		this.initialInvestment  = initialInvestment;
	}
 	
	/**
	 * This method is used to obtain a users input for their starting salary. If the value input is invalid, 
	 * an error is presented to the user and the user must retry a new value. 
	 * If the valid, the users initial salary is saved and is used as the max salary, because the max salary before the simulation runs must be the initial salary.
	 */
	private void initialSalaryInput(){
		double initialSalary;
		do{
			System.out.println("Enter the amount of your salary ");
			this.userInputs = new Scanner(System.in);
			initialSalary = userInputs.nextDouble();
			initialSalary = roundNumberDecimals(initialSalary);
			if(initialSalary<0.0){
				System.err.println("Salary Cannot Be Below $0.00");
			}
			}while(initialSalary<0.0);
		userSalary = initialSalary;
		this.maxSimulatedSalary = initialSalary;
		this.initialSalary = initialSalary;
	}
	
	/**
	 * This method is used to get the users input for their bound for the minimum amount of salary they are going to save in the simulation year. If the value is valid
	 * the value for minSalarySaved is changed to the input value from the user, if not the user is presented with an error and tries again until valid.
	 */
	private void minSavedInput(){
		double minSaved;
		do{
			System.out.println("Enter the LOWEST amount of your salary you plan to save as a percentage ");
			this.userInputs = new Scanner(System.in);
			minSaved = userInputs.nextDouble();
			minSaved = roundNumberDecimals(minSaved);
			if(minSaved<0.0){
				System.err.println("Salary saved Cannot Be Below 0.0% or above 100.0%");
			}
			}while(minSaved<0.0 || minSaved> 100.0);
		minSalarySaved = minSaved;
	}
	
	/**
	 * This method is used to get the users input for their bound for the maximum amount of salary they are going to save in the simulation year. If the value is valid
	 * the value for maxSalarySaved is changed to the input value from the user, if not the user is presented with an error and tries again until valid.
	 */
	private void maxSavedInput(){
		double maxSaved;
		do{
			System.out.println("Enter the MAX amount of your salary you plan to save as a percentage ");
			this.userInputs = new Scanner(System.in);
			maxSaved = userInputs.nextDouble();
			maxSaved = roundNumberDecimals(maxSaved);
			if(maxSaved < this.minSalarySaved || maxSaved> 100.0){
				System.err.println("Max Salary Saved Cannot Be Below The Lower Percentage or above 100% of your salary");
			}
			}while(maxSaved < this.minSalarySaved || maxSaved> 100.0);
		this.minSimulatedSalarySaved= maxSaved;
		maxSalarySaved = maxSaved;
	}
	
	/**
	 * This method is used to get the users input for their bound for the minimum amount of investment return they are going to get in the simulation. If the value is valid
	 * the value for minAnnualReturn is changed to the input value from the user, if not the user is presented with an error and tries again until valid.
	 */
	private void minAnnualReturnInput(){
		double minReturn;
		do{
			System.out.println("Enter the MIN amount your investments should make as a percentage");
			this.userInputs = new Scanner(System.in);
			minReturn = userInputs.nextDouble();
			minReturn = roundNumberDecimals(minReturn);
			if(minReturn < -100.00){
				System.err.println("Min returns cannot be below -100.0%");
			}
			}while(minReturn < -100.00);
		minAnnualReturn = minReturn;
	}
	
	/**
	 * This method is used to get the users input for their bound for the maximum amount of investment return they are going to get in the simulation. If the value is valid
	 * the value for maxAnnualReturn is changed to the input value from the user, if not the user is presented with an error and tries again until valid.
	 */
	private void maxAnnualReturnInput(){
		double maxReturn;
		do{
			System.out.println("Enter the MAX amount your investments should make as a percentage");
			this.userInputs = new Scanner(System.in);
			maxReturn = userInputs.nextDouble();
			maxReturn = roundNumberDecimals(maxReturn);
			if(maxReturn < this.minAnnualReturn){
				System.err.println("Max returns cannot be below the Minimum");
			}
			}while(maxReturn < this.minAnnualReturn);
		this.minSimulatedAnnualReturn = maxReturn;
		maxAnnualReturn = maxReturn;
	}
	
	/**
	 * This method is used to get the users input for their bound for the minimum amount of raise percentage they can get in the simulation. If the value is valid
	 * the value for minAnnualRaise is changed to the input value from the user, if not the user is presented with an error and tries again until valid.
	 */
	private void minAnnualRaiseInput(){
		double minRaise;
		do{
			System.out.println("Enter the MIN amount of your annual raise");
			this.userInputs = new Scanner(System.in);
			minRaise = userInputs.nextDouble();
			minRaise = roundNumberDecimals(minRaise);
			if(minRaise < -100.00){
				System.err.println("Min raises cannot be below -100.0%");
			}
			}while(minRaise < -100.00 );
		minAnnualRaise = minRaise;
	}
	
	/**
	 * This method is used to get the users input for their bound for the maximum amount of raise percentage they can get in the simulation. If the value is valid
	 * the value for maxAnnualRaise is changed to the input value from the user, if not the user is presented with an error and tries again until valid.
	 */
	private void maxAnnualRaiseInput(){
		double maxRaise;
		do{
			System.out.println("Enter the MAX amount of your annual raise");
			this.userInputs = new Scanner(System.in);
			maxRaise = userInputs.nextDouble();
			maxRaise = roundNumberDecimals(maxRaise);
			if(maxRaise < this.minAnnualRaise){
				System.err.println("Max raises cannot be below your minimum");
			}
			}while(maxRaise < this.minAnnualRaise);
		this.minSimulatedAnnualRaise = maxRaise;
		maxAnnualRaise = maxRaise;
	}
	
	/**
	 * This method is used to get the users input for their amount of years they have until they are going to retire. If the value is valid
	 * the value for yearsToRetirement is changed to the input value from the user, if not the user is presented with an error and tries again until valid.
	 */
	private void yearsToRetirementInput(){
		int retirementYears;
		do{
			System.out.println("Enter the amount of years until your retirement");
			this.userInputs = new Scanner(System.in);
			retirementYears = userInputs.nextInt();
			if(retirementYears < 0){
				System.err.println("I wish I could've retired already too, but the years until retirement must be a positive integer");
			}
			}while(retirementYears < 0);
		yearsToRetirement = retirementYears;
	}
	
	/**
	 * This program is passed two values and it creates a random number within the upper and lower bounds using the rand function of java
	 * @param lBound : the lowest number that the random number can be at
	 * @param uBound : the largest number that the value of the random number can be at
	 * @return a random value that is at the lower bound parameter or above while being at or below the upper bound parameter.
	 */
	private double randomInRange(double lBound, double uBound){
		double randomNum = 0;
		lBound = lBound * 100;
		uBound = uBound * 100;
		Random rand = new Random();
		randomNum = rand.nextInt(((int)uBound-(int)lBound)+1)+(int)lBound; 
		randomNum = randomNum / 100;
		return randomNum;
	}
	
	/**
	 * This method checks the current salary saved in the simulation to see if it is the new low or high salary saved for the simulation. 
	 * If it is shown to be the largest number in the salary saved category in the simulation, it becomes the new maxSimulatedSalarySaved, or vice-versa. 
	 * @param salarySaved this is the current year in the simulations salary saved, used here to check if its the new low or high of the simulation
	 */
	private void minMaxChangeSalarySaved(double salarySaved){
		if (salarySaved > this.maxSimulatedSalarySaved){
			this.maxSimulatedSalarySaved = salarySaved;
		}	 
		if (salarySaved < this.minSimulatedSalarySaved){
			this.minSimulatedSalarySaved = salarySaved;
		}	 
	}
	
	/**
	 * This method checks the current annual return in the simulation to see if it is the new low or high annual return for the simulation. 
	 * If it is shown to be the largest number in the annual return category in the simulation, it becomes the new maxSimulatedAnnualReturn, or vice-versa. 
	 * @param annualReturn this is the current year in the simulations annual return, used here to check if its the new low or high of the simulation
	 */
	private void minMaxChangeAnnualReturn(double annualReturn){
		if (annualReturn > this.maxSimulatedAnnualReturn){
			this.maxSimulatedAnnualReturn = annualReturn;
		}	 
		if (annualReturn < this.minSimulatedAnnualReturn){
			this.minSimulatedAnnualReturn = annualReturn;
		}	 
	}
	
	/**
	 * This method checks the current salary change in the simulation to see if it is the new low or high salary change for the simulation. 
	 * If it is shown to be the largest number in the salary change category in the simulation, it becomes the new maxSimulatedAnnualRaise, or vice-versa. 
	 * @param annualRaise this is the current year in the simulations salary change, used here to check if its the new low or high of the simulation
	 */
	private void minMaxChangeSalaryIncrease(double annualRaise){
		if (annualRaise > this.maxSimulatedAnnualRaise){
			this.maxSimulatedAnnualRaise = annualRaise;
		}	 
		if (annualRaise < this.minSimulatedAnnualRaise){
			this.minSimulatedAnnualRaise = annualRaise;
		}	 
	}
	
	/**
	 * This method checks the current salary in the simulation to see if it is the new high salary for the simulation. 
	 * If it is shown to be the largest number in the salary category in the simulation, it becomes the new maxSimulatedSalary.
	 * @param salary this is the current year in the simulations salary, used here to check if its the new high of the simulation
	 */
	private void maxSalaryChanger(double salary){
		if (salary > this.maxSimulatedSalary){
			this.maxSimulatedSalary = salary;
		}
	}
	
	/**
	 * This method prints out for the user a histogram that shows the user their accounts value as simulated as a year by year.
	 * The histogram also prints out the users yearly account value at the end of the string to show the numerical value of that year.
	 */
	private void makeHistogram(){
		System.out.println("The following histogram shows your account values through the years: ");
		System.out.println("");
		System.out.println("Year:");
		for(int counterOfYear = 0; counterOfYear<=this.yearsToRetirement; counterOfYear++ ){
		System.out.println(counterOfYear+": "+hashCount(this.investedArray.get(counterOfYear)/this.investedArray.get(maxOfArray())) +"($"+this.investedArray.get(counterOfYear)+")");
		}
	}
	
	/**
	 * This method takes a number from the makeHistogram method runs a loop to create a string of "#" symbols in a size accordance of the
	 * current value in the histogram compared to the largest account value in the simulation. With the most "#"s being 100 in the histogram.
	 * @param hashcount : a double that is used to be rounded and used as a counter
	 * @return a string that has the "#" symbol input into it the rounded integer number of times the makeHistogram method desires for that leg of the 
	 * histogram
	 */
	private String hashCount(double hashcount){
		hashcount = Math.round(hashcount*100);
		String hashes= "";
		for(int counter = 0; counter<hashcount; counter++){
			hashes= hashes +"#";
		}
		return hashes;
	}
	
   /**
	* This method runs through an array in order to locate the index of the largest value in the investeArray.
	* @return the index of the array that locates the largest value in the array
	*/
	private int maxOfArray(){
		int maxLoc = 0;
		for (int counter = 0; counter<this.investedArray.size(); counter++){
			if (this.investedArray.get(counter)>this.investedArray.get(maxLoc)){
				maxLoc=counter;}
		}
		return maxLoc;
	}

	/**
	 * This function takes in a double and rounds it off so it only has numbers into the hundredths place
	 * @param num : the input double that may contain way more decimal places than two
	 * @return a number that is rounded off to two decimal places. (ex. 2.43256 turns into 2.43)
	 */
	private static double roundNumberDecimals(double num){
		return num = Math.round(num * 100.0) /100.00;
	}
  	
	/**
	 * This method closes off the scanner from the user in order to avoid any issues of overuse.
	 */
	public void finalize(){
		this.userInputs.close();
	}	
}	