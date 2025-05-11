import java.util.Scanner;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            System.out.println("Initial balance cannot be negative. Setting to 0.");
            this.balance = 0;
        } else {
            this.balance = initialBalance;
        }
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance. Withdrawal failed.");
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }
        balance += amount;
        return true;
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void withdraw(double amount) {
        if (account.withdraw(amount)) {
            System.out.println("Withdrawal of $" + String.format("%.2f", amount) + " successful.");
        }
    }

    public void deposit(double amount) {
        if (account.deposit(amount)) {
            System.out.println("Deposit of $" + String.format("%.2f", amount) + " successful.");
        }
    }

    public void checkBalance() {
        System.out.println("Your current balance is: $" + String.format("%.2f", account.getBalance()));
    }
}

public class ATMApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM Machine!");
        System.out.print("Please enter your initial account balance: $");
        double initialBalance = 0;
        while (true) {
            try {
                initialBalance = Double.parseDouble(scanner.nextLine());
                if (initialBalance < 0) {
                    System.out.print("Initial balance cannot be negative. Please enter again: $");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: $");
            }
        }

        BankAccount account = new BankAccount(initialBalance);
        ATM atm = new ATM(account);

        boolean exit = false;

        while (!exit) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");

            System.out.print("Enter choice (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = readPositiveDouble(scanner);
                    atm.withdraw(withdrawAmount);
                    break;
                case "2":
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = readPositiveDouble(scanner);
                    atm.deposit(depositAmount);
                    break;
                case "3":
                    atm.checkBalance();
                    break;
                case "4":
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }

        scanner.close();
    }

    private static double readPositiveDouble(Scanner scanner) {
        double amount = -1;
        while (true) {
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount <= 0) {
                    System.out.print("Amount must be positive. Please enter again: $");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter again: $");
            }
        }
        return amount;
    }
}

