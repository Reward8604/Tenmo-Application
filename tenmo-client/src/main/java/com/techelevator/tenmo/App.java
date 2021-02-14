package com.techelevator.tenmo;

import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;
import java.util.Scanner;

import com.techelevator.tenmo.models.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.view.ConsoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private Scanner scanner = new Scanner(System.in);
	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private RestTemplate apiCall = new RestTemplate();

//    public AccountsServices accountsServices = new AccountsServices(API_BASE_URL);
    public TransferServices transferServices; // = new TransferServices(API_BASE_URL);
    
    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;

	}

	public void run() {
//		System.out.println("*********************");
//		System.out.println("* Welcome to TEnmo! *");
//		System.out.println("*********************");
		System.out.println
				("████████╗███████╗███╗░░██╗███╗░░░███╗░█████╗░\n" +
				 "╚══██╔══╝██╔════╝████╗░██║████╗░████║██╔══██╗\n" +
				 "░░░██║░░░█████╗░░██╔██╗██║██╔████╔██║██║░░██║\n" +
				 "░░░██║░░░██╔══╝░░██║╚████║██║╚██╔╝██║██║░░██║\n" +
				 "░░░██║░░░███████╗██║░╚███║██║░╚═╝░██║╚█████╔╝\n" +
				 "░░░╚═╝░░░╚══════╝╚═╝░░╚══╝╚═╝░░░░░╚═╝░╚════╝░");

		System.out.println
				 ("Ⓐ ⓉⒺⒶⓂ ⓂⒶⒿⓇ ⒶⓅⓅⓁⒾⒸⒶⓉⒾⓄⓃ");

		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		AccountsServices accountsServices = new AccountsServices(API_BASE_URL, currentUser);
		BigDecimal balance = accountsServices.getBalance();
		System.out.println("Your current balance is:");
		System.out.println(balance);
		
	}

	private void viewTransferHistory() {

	List<Transfers> transferHistory = transferServices.byId(currentUser.getUser().getId());
		for(Transfers transfersById: transferHistory)
		{
			System.out.println("Transfer ID = " + transfersById.getTransferId());
			System.out.println("Account From = " + transfersById.getAccountFrom());
			System.out.println("Transfer Status = " + transfersById.getTransferStatusDesc());
			System.out.println("Account To = " + transfersById.getAccountTo());
			System.out.println("Transfer Amount = " + transfersById.getTransferAmount());
			
		}
		
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {



//		----------------- START MAJR CODE --------------------------------

		//TransferServices transferServices = new TransferServices(API_BASE_URL, currentUser);
		List<User> users = transferServices.listUser();
		for(User user:users)
		{
			System.out.println(user.getId() + " " + user.getUsername());

		}


		System.out.println("Please enter the id of the user you want to send money to: ");
		String userChoice = scanner.nextLine();

		int receiversacctId = Integer.parseInt(userChoice);
		Transfers transfers = new Transfers();
		
		System.out.println("Enter amount to transfer: ");
		String userInput = scanner.nextLine();
		BigDecimal amtTransfrd = new BigDecimal(userInput);

		
		transfers.setAccountFrom(currentUser.getUser().getId());
		transfers.setAccountTo(receiversacctId);
		transfers.setTransferAmount(amtTransfrd);
		
		transferServices.sendBucks(transfers);
		

	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
				ApiServiceBase.setUser(currentUser);
				transferServices = new TransferServices(API_BASE_URL, currentUser);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
