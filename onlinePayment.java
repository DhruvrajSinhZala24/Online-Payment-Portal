import java.io.*;
import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.sql.Date;

class OnlinePayment {
    static String DB_URL = "jdbc:mysql://localhost:3306/payment_app";
    static String DB_USER = "root";
    static String DB_PASSWORD = "";
    static Scanner sc = new Scanner(System.in);
    String userId;
    TransactionList th = new TransactionList();
    BinarySearchTree bst = new BinarySearchTree();

    public static void main(String[] args) throws Exception {
        System.out.println("=========================================================");
        System.out.println("         Welcome to the Online Payment System          ");
        System.out.println("=========================================================");
        System.out.println("  Manage your balance, make transactions, and track     ");
        System.out.println("                  your expenses.  ");
        System.out.println("=========================================================");
        OnlinePayment op = new OnlinePayment();
        op.UserDetails();
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option:- ");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    op.createUser();
                    break;
                case 2:
                    isLoggedIn = op.login();
                    if(isLoggedIn){
                        boolean b = true;
                        while (b) {
                            System.out.println("1. Add Balance");
                            System.out.println("2. Transfer Money");
                            System.out.println("3. Check Balance");
                            System.out.println("4. Pay Money");
                            System.out.println("5. View Expenses");
                            System.out.println("6. View Transaction History");
                            System.out.println("7. Search Transactions");
                            System.out.println("8. Delete Transactions");
                            System.out.println("9. Minimum Amount From Transactions");
                            System.out.println("10. Maximum Amount From Transactions");
                            System.out.println("11. Exit");
                            System.out.print("Choose an option:- ");
                            int opt = sc.nextInt();
                
                            switch (opt) {
                                case 1:
                                    op.addBalance();
                                    break;
                                case 2:
                                    op.transferMoney();
                                    break;
                                case 3:
                                    op.checkBalance();
                                    break;
                                case 4:
                                    op.payMoney();
                                    break;
                                case 5:
                                    op.viewExpenses();
                                    break;
                                case 6:
                                    op.viewTransactionHistory();
                                    break;
                                case 7:
                                    op.searchTransactions();
                                    break;
                                case 8:
                                    op.deleteTransactions();
                                    break;
                                case 9:
                                    op.minTransaction();
                                    break;
                                case 10:
                                    op.maxTransaction();
                                    break;   
                                case 11:
                                    b = false;
                                    break;
                                default:
                                    System.out.println("Invalid option, please try again.");
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    void createUser() throws Exception {
        System.out.print("Enter first name:- ");
        String firstName = sc.next();
        System.out.print("Enter last name:- ");
        String lastName = sc.next();
        boolean d = true;
        int age = 0;
        while (d) {
            System.out.print("Enter age:- ");
            age = sc.nextInt();
            if (age >= 18) {
                d = false;
            } else {
                System.out.println("USER MUST BE 18 YEARS OLD OR MORE");
            }
        }
    
        sc.nextLine();
        System.out.print("Enter phone number:- ");
        String phoneNumber = sc.nextLine();
        while (phoneNumber.length() != 10) {
            System.out.println("Invalid phone number.");
            System.out.print("Please enter a 10-digit phone number:- ");
            phoneNumber = sc.nextLine();
        }
        while (!isUniquePhoneNumber(phoneNumber)) {
            System.out.println("Phone number already exists or is invalid.");
            System.out.print("Please enter a different 10-digit phone number:- ");
            phoneNumber = sc.nextLine();
            while (phoneNumber.length() != 10) {
                System.out.println("Invalid phone number.");
                System.out.print("Please enter a 10-digit phone number:- ");
                phoneNumber = sc.nextLine();
            }
        }
    
        System.out.print("Enter aadhar number:- ");
        String aadharNumber = sc.nextLine();
        while (aadharNumber.length() != 12) {
            System.out.println("Invalid Aadhar number.");
            System.out.print("Please enter a 12-digit Aadhar number:- ");
            aadharNumber = sc.nextLine();
        }
        while (!isUniqueAadharNumber(aadharNumber)) {
            System.out.println("Aadhar number already exists or is invalid.");
            System.out.print("Please enter a different 12-digit Aadhar number:- ");
            aadharNumber = sc.nextLine();
            while (aadharNumber.length() != 12) {
                System.out.println("Invalid Aadhar number.");
                System.out.print("Please enter a 12-digit Aadhar number:- ");
                aadharNumber = sc.nextLine();
            }
        }
    
        String dob = "";
        boolean validDob = false;
        while (!validDob) {
            System.out.print("Enter date of birth (YYYY-MM-DD):- ");
            dob = sc.nextLine();
            if (dob.matches("\\d{4}-\\d{2}-\\d{2}")) {
                LocalDate dobDate = LocalDate.parse(dob);
                LocalDate currentDate = LocalDate.now();
                if (dobDate.isAfter(currentDate)) {
                    System.out.println("Date of birth cannot be in the future.");
                } else if (currentDate.getYear() - dobDate.getYear() != age) {
                    System.out.println("Date of birth does not match the entered age.");
                } else {
                    validDob = true;
                }
            } else {
                System.out.println("Invalid date of birth format.");
            }
        }
    
        System.out.print("Enter bank name:- ");
        String bankName = sc.nextLine();
        System.out.print("Enter branch name:- ");
        String branchName = sc.nextLine();
        System.out.print("Enter 11-digit account number:- ");
        String accountNumber = sc.nextLine();
        while (accountNumber.length() != 11) {
            System.out.println("Invalid account number format.");
            System.out.print("Please enter exactly 11 digits:- ");
            accountNumber = sc.nextLine();
        }
        while (!isUniqueAccountNumber(accountNumber)) {
            System.out.println("Account number already exists or is invalid.");
            System.out.print("Please enter a different 11-digit account number:- ");
            accountNumber = sc.nextLine();
            while (accountNumber.length() != 11) {
                System.out.println("Invalid account number format.");
                System.out.print("Please enter exactly 11 digits:- ");
                accountNumber = sc.nextLine();
            }
        }
    
        System.out.print("Enter password:- ");
        String password = sc.nextLine();
        boolean c = true;
        double balance=0.0;
        while(c){
            System.out.print("Enter Initial Balance:- ");
            balance = sc.nextDouble();
            if(balance<=0.0){
              System.out.println("Balance must be greater then zero!");
            }
            else{
                c=false;
            }
        }
        
    
        String bankDetails = "Bank Name: " + bankName + ", Account Number: " + accountNumber + ", Branch: " + branchName;
    
        userId = firstName.toLowerCase() + dob.substring(0, 4);
    
        String sql = "INSERT INTO users (userId, firstName, lastName, age, phoneNumber, aadharNumber, dob, bankDetails, balance, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userId);
        pstmt.setString(2, firstName);
        pstmt.setString(3, lastName);
        pstmt.setInt(4, age);
        pstmt.setString(5, phoneNumber);
        pstmt.setString(6, aadharNumber);
        pstmt.setDate(7, Date.valueOf(dob));
        pstmt.setString(8, bankDetails);
        pstmt.setDouble(9, balance);
        pstmt.setString(10,password);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        logUserDetails(firstName, lastName, age, phoneNumber, aadharNumber, dob, bankDetails, password);
        System.out.println("User account created successfully. Your User ID is: " + userId);
    }
    
    boolean isUniquePhoneNumber(String phoneNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE phoneNumber = ?";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, phoneNumber);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        boolean isUnique = rs.getInt(1) == 0;
        rs.close();
        pstmt.close();
        conn.close();
        return isUnique;
    }
    
    boolean isUniqueAadharNumber(String aadharNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE aadharNumber = ?";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, aadharNumber);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        boolean isUnique = (rs.getInt(1) == 0);
        rs.close();
        pstmt.close();
        conn.close();
        return isUnique;
    }
    
    boolean isUniqueAccountNumber(String accountNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE bankDetails LIKE ?"; 
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + accountNumber + "%");
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        boolean isUnique = rs.getInt(1) == 0;
        rs.close();
        pstmt.close();
        conn.close();
        return isUnique;
    }
    
    boolean login() throws Exception {
        sc.nextLine();
        System.out.print("Enter User ID:- ");
        String userIdInput = sc.nextLine();

        String sql = "SELECT password FROM users WHERE userId = ?";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userIdInput);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String storedPassword = rs.getString("password");

            System.out.print("Enter Password:- ");
            String passwordInput = sc.nextLine();

            if (storedPassword.equals(passwordInput)) {
                System.out.println("Login successful.");
                userId = userIdInput;
                rs.close();
                pstmt.close();
                conn.close();
                return true;
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("User ID not found.");
        }

        rs.close();
        pstmt.close();
        conn.close();
        return false;
    }

    void addBalance() throws Exception {
        System.out.print("Enter amount to add:- ");
        double amount = sc.nextDouble();
        sc.nextLine();
    
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
    
        String sql = "UPDATE users SET balance = balance + ? WHERE userId = ?";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
    
        pstmt.setDouble(1, amount);
        pstmt.setString(2, userId);
        pstmt.executeUpdate();
    
        pstmt.close();
        conn.close();
    
        String transactionDetail = "Added balance:- " + amount;
        logTransaction(transactionDetail);
        th.add(transactionDetail);
        bst.insert(amount);
        System.out.println("Balance added successfully.");
    }
    
    void transferMoney() throws Exception {
        sc.nextLine(); 
        System.out.print("Enter recipient's User ID:- ");
        String recipientId = sc.nextLine();
    
        if (!userExists(recipientId)) {
            System.out.println("No such user ID found in the database.");
            return;
        }
    
        System.out.print("Enter amount to transfer:- ");
        double amount = sc.nextDouble();
        sc.nextLine(); 
    
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
    
        while (!verifyPassword()) {
            System.out.println("Incorrect password. Please try again.");
        }
    
        double currentBalance = getCurrentBalance();
        if (amount > currentBalance) {
            System.out.println("Insufficient balance.");
            return;
        }
    
        String deductBalance = "UPDATE users SET balance = balance - ? WHERE userId = ?";
        String addBalance = "UPDATE users SET balance = balance + ? WHERE userId = ?";
        String transaction = "INSERT INTO transactions (userId, transactionType, amount, description, date) VALUES (?, ?, ?, ?, ?)";
        String expenseSql = "INSERT INTO expenses (userId, amount, description, date) VALUES (?, ?, ?, ?)";
    
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        conn.setAutoCommit(false);
    
        PreparedStatement pstmtDeduct = conn.prepareStatement(deductBalance);
        pstmtDeduct.setDouble(1, amount);
        pstmtDeduct.setString(2, userId);
        pstmtDeduct.executeUpdate();
    
        PreparedStatement pstmtAdd = conn.prepareStatement(addBalance);
        pstmtAdd.setDouble(1, amount);
        pstmtAdd.setString(2, recipientId);
        pstmtAdd.executeUpdate();
    
        PreparedStatement pstmtTransaction = conn.prepareStatement(transaction);
        pstmtTransaction.setString(1, userId);
        pstmtTransaction.setString(2, "Transfer");
        pstmtTransaction.setDouble(3, amount);
        pstmtTransaction.setString(4, "Transferred to " + recipientId);
        pstmtTransaction.setDate(5, new Date(System.currentTimeMillis()));
        pstmtTransaction.executeUpdate();
    
        PreparedStatement pstmtExpense = conn.prepareStatement(expenseSql);
        pstmtExpense.setString(1, userId);
        pstmtExpense.setDouble(2, amount);
        pstmtExpense.setString(3, "Transferred to " + recipientId);
        pstmtExpense.setDate(4, new Date(System.currentTimeMillis()));
        pstmtExpense.executeUpdate();
    
        conn.commit();
        pstmtDeduct.close();
        pstmtAdd.close();
        pstmtTransaction.close();
        pstmtExpense.close();
        conn.close();
    
        String transactionDetail = "Transferred " + amount + " to " + recipientId;
        logTransaction(transactionDetail);
        th.add(transactionDetail);
        bst.insert(amount);
    
        logTransactionForRecipient(recipientId, "Received " + amount + " from " + userId);
        System.out.println("Money transferred successfully.");
    }
    
    private boolean userExists(String userId) throws Exception {
        String sql = "SELECT COUNT(*) FROM users WHERE userId = ?";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        boolean exists = rs.getInt(1) > 0;
        rs.close();
        pstmt.close();
        conn.close();
        return exists;
    }
    
    void payMoney() throws Exception {
        sc.nextLine(); 
        System.out.print("Enter recipient's/vendor's User ID:- ");
        String recipientId = sc.nextLine();
        if (userExists(recipientId)) {
            System.out.println("User ID found in the database.");
            transferMoney();
            return;
        }
        System.out.print("Enter description (reason or recipient's name):- ");
        String description = sc.nextLine();
        System.out.print("Enter amount to pay:- ");
        double amount = sc.nextDouble();
        sc.nextLine(); 
    
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
    
        while (!verifyPassword()) {
            System.out.println("Incorrect password. Please try again.");
        }
    
        double currentBalance = getCurrentBalance();
        if (amount > currentBalance) {
            System.out.println("Insufficient balance.");
            return;
        }
    
        String deductBalance = "UPDATE users SET balance = balance - ? WHERE userId = ?";
        String transaction = "INSERT INTO transactions (userId, transactionType, amount, description, date) VALUES (?, ?, ?, ?, ?)";
        String expenseSql = "INSERT INTO expenses (userId, amount, description, date) VALUES (?, ?, ?, ?)";
    
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        conn.setAutoCommit(false);
    
        PreparedStatement pstmtDeduct = conn.prepareStatement(deductBalance);
        pstmtDeduct.setDouble(1, amount);
        pstmtDeduct.setString(2, userId);
        pstmtDeduct.executeUpdate();
    
        PreparedStatement pstmtTransaction = conn.prepareStatement(transaction);
        pstmtTransaction.setString(1, userId);
        pstmtTransaction.setString(2, "Payment");
        pstmtTransaction.setDouble(3, amount);
        pstmtTransaction.setString(4, description);
        pstmtTransaction.setDate(5, new Date(System.currentTimeMillis()));
        pstmtTransaction.executeUpdate();
    
        PreparedStatement pstmtExpense = conn.prepareStatement(expenseSql);
        pstmtExpense.setString(1, userId);
        pstmtExpense.setDouble(2, amount);
        pstmtExpense.setString(3, description);
        pstmtExpense.setDate(4, new Date(System.currentTimeMillis()));
        pstmtExpense.executeUpdate();
    
        conn.commit();
        pstmtDeduct.close();
        pstmtTransaction.close();
        pstmtExpense.close();
        conn.close();
    
        String transactionDetail = "Paid " + amount + " to " + description;
        logTransaction(transactionDetail);
        th.add(transactionDetail);
        bst.insert(amount);
    
        logTransactionForRecipient(recipientId, "Received payment of " + amount + " from " + userId);
        System.out.println("Payment made successfully.");
    }
    
    void logTransaction(String transaction) throws Exception {
        FileWriter fw = new FileWriter("transactions.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(transaction);
        bw.newLine();
        bw.close();
    }

    void logTransactionForRecipient(String recipientId, String transaction) throws Exception {
        FileWriter fw = new FileWriter(recipientId + "transactions.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(transaction);
        bw.newLine();
        bw.close();
    }

    double getCurrentBalance() throws Exception {
        String sql = "SELECT balance FROM users WHERE userId = ?";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();
        double balance = 0.0;
        if (rs.next()) {
            balance = rs.getDouble("balance");
        }
        rs.close();
        pstmt.close();
        conn.close();
        return balance;
    }
    boolean verifyPassword() throws Exception {
        System.out.print("Enter your password:- ");
        String inputPassword = sc.nextLine();
        String sql = "SELECT password FROM users WHERE userId = ?";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();
        boolean isValid = false;
        if (rs.next()) {
            String storedPassword = rs.getString("password");
            isValid = storedPassword.equals(inputPassword);
        }
        rs.close();
        pstmt.close();
        conn.close();
        return isValid;
    }
    

    void checkBalance() throws Exception {
        double balance = getCurrentBalance();
        System.out.println("Your current balance is:- " + balance);
    }

    void logUserDetails(String firstName, String lastName, int age, String phoneNumber, String aadharNumber, String dob, String bankDetails,String password) throws Exception {
        FileWriter fw = new FileWriter("userDetails.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("User ID:- " + userId);
        bw.newLine();
        bw.write("First Name:- " + firstName);
        bw.newLine();
        bw.write("Last Name:- " + lastName);
        bw.newLine();
        bw.write("Age:- " + age);
        bw.newLine();
        bw.write("Phone Number:- " + phoneNumber);
        bw.newLine();
        bw.write("Aadhar Number:- " + aadharNumber);
        bw.newLine();
        bw.write("Date of Birth:- " + dob);
        bw.newLine();
        bw.write("Bank Details:- " + bankDetails);
        bw.newLine();
        bw.write("Password:- " + password);
        bw.newLine();
        bw.write("`````````````````````````````````````````````````````````````````````");
        bw.newLine();
        bw.close();
    }
    void UserDetails() throws Exception {
        String sql = "SELECT * FROM users";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        FileWriter fw = new FileWriter("userDetails.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);

        while (rs.next()) {
            String userId = rs.getString("userId");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            int age = rs.getInt("age");
            String phoneNumber = rs.getString("phoneNumber");
            String aadharNumber = rs.getString("aadharNumber");
            Date dob = rs.getDate("dob");
            String bankDetails = rs.getString("bankDetails");
            Double balance1 = rs.getDouble("balance");
            String password = rs.getString("password");

            bw.write("User ID:- " + userId);
            bw.newLine();
            bw.write("First Name:- " + firstName);
            bw.newLine();
            bw.write("Last Name:- " + lastName);
            bw.newLine();
            bw.write("Age:- " + age);
            bw.newLine();
            bw.write("Phone Number:- " + phoneNumber);
            bw.newLine();
            bw.write("Aadhar Number:- " + aadharNumber);
            bw.newLine();
            bw.write("Date of Birth:- " + dob);
            bw.newLine();
            bw.write("Bank Details:- " + bankDetails);
            bw.newLine();
            bw.write("Balance:- " + balance1);
            bw.newLine();
            bw.write("Password:- " + password);
            bw.newLine();
            bw.write("`````````````````````````````````````````````````````````````````````");
            bw.newLine();
        }

        rs.close();
        stmt.close();
        conn.close();
        bw.close();
    }

    void viewExpenses() throws Exception {
        String sql = "SELECT * FROM expenses WHERE userId = ?";
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();
    
        System.out.println("---------------------------------------------------------");
        System.out.println("                     Expenses                           ");
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-15s %-25s %-10s%n", "Amount", "Description", "Date");
        System.out.println("---------------------------------------------------------");
    
        boolean hasExpenses = false;
        while (rs.next()) {
            double amount = rs.getDouble("amount");
            String description = rs.getString("description");
            Date date = rs.getDate("date");
            System.out.printf("%-15.2f %-25s %-10s%n", amount, description, date);
            hasExpenses = true;
        }
    
        if (!hasExpenses) {
            System.out.println("No expenses found.");
        }
    
        System.out.println("---------------------------------------------------------");
        rs.close();
        pstmt.close();
        conn.close();
    }
    

    void viewTransactionHistory() {
        if (th.isEmpty()) {
            System.out.println("No transactions to show.");
            return;
        }

        System.out.println("Transaction History:- ");
        th.print();
    }

    void searchTransactions() {
        System.out.print("Enter amount to search:- ");
        double searchAmount = sc.nextDouble();

        if (searchAmount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
        List<String> results = th.searchByAmount(searchAmount);
        if (results.isEmpty()) {
            System.out.println("No transactions found with amount " + searchAmount);
        } 
        else {
            System.out.println("Transactions with amount " + searchAmount + ":- ");
            for (String transaction : results) {
                System.out.println(transaction);
            }
        }
    }
    void deleteTransactions() {
        System.out.print("Enter amount to delete:- ");
        double deleteAmount = sc.nextDouble();
    
        if (deleteAmount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
        th.deleteByAmount(deleteAmount);
        bst.delete(deleteAmount);
        System.out.println("Transactions with amount " + deleteAmount + " deleted successfully.");
    }
    void minTransaction(){
        double min = bst.min(bst.root);
        System.out.println("Minimum Amount From Transaction:- " + min);
    }
    void maxTransaction(){
        double max = bst.max(bst.root);
        System.out.println("Maximum Amount From Transaction:- " + max);
    }
}

class TransactionList {
    Node head;

    class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
        }
    }
    void add(String data) {
        Node n = new Node(data);
        if (head == null) {
            head = n;
        } 
        else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = n;
        }
    }
    void deleteByAmount(double amount) {
        if (head == null) {
            System.out.println("No transactions to delete.");
            return;
        }

        while (head != null && (head.data.contains("Added balance: " + amount) ||  
                                 head.data.contains("Transferred " + amount) ||
                                 head.data.contains("Paid " + amount))) {
            head = head.next;
        }

        Node current = head;
        while (current != null && current.next != null) {
            if (current.next.data.contains("Added balance: " + amount) ||  
                current.next.data.contains("Transferred " + amount) ||
                current.next.data.contains("Paid " + amount)) {
                current.next = current.next.next;
            } 
            else {
                current = current.next;
            }
        }
    }
    void print() {
        Node temp = head;
        if (head == null) {
            System.out.println("No transactions to display.");
        }
        else{
            while (temp != null) {
                System.out.println(temp.data);
                temp = temp.next;
            }
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public List<String> searchByAmount(double amount) {
        List<String> results = new ArrayList<>();
        Node current = head;
        while (current != null) {
            if (current.data.contains("Added balance:- " + amount) ||  
                current.data.contains("Transferred " + amount) ||
                current.data.contains("Paid " + amount)) {
                results.add(current.data);
            }
            current = current.next;
        }
        return results;
    }
}
class BinarySearchTree 
{
    class Node 
    {
        double data;
        Node left, right;
        Node(double data) {
        this.data = data;
        }
    }

    Node root=null;
    void insert(double key)
    {
        root = insertRec(root,key);
    }

    Node insertRec(Node t, double key)
    {
        if(t == null)
        {
            t = new Node(key);
        }
        else if(key < t.data)
        {
            t.left = insertRec(t.left, key);
        }
        else
        {
            t.right = insertRec(t.right, key);
        }
        return t;
    }
    void delete(double key)
    {
        root = deleteRec(root,key);
    }

    Node deleteRec(Node t, double key)
    {
        if(t == null)
        {
            System.out.println("Value not found");
        }
        else if(key < t.data)
        {
            t.left = deleteRec(t.left, key);
        }
        else if(key > t.data)
        {
            t.right = deleteRec(t.right, key);
        }
        else
        {
            if(t.left == null)
            {
                return t.right;
            }
            else if (t.right == null)
            {
                return t.left;
            }
            else
            {
                t.data = min(t.right);
                t.right = deleteRec(t.right, t.data);
            }
        }
        return t;
    }
    double min(Node t) 
    {
        while (t.left != null) 
        {
            t=t.left;
        }
        return t.data;
    }
    double max(Node t) 
    {
        while (t.right!= null) 
        {
            t=t.right;
        }
        return t.data;
    }
}