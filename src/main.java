import  java.util.ArrayList;
import  java.util.List;
import  java.util.Scanner;
import java.util.SortedMap;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;
    public Car(String carId, String brand, String model, double  basePricePerDay){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }
    public  boolean isAvailable(){
        return  isAvailable;
    }
    public void  rent(){
        isAvailable = false;
    }
    public void returnCar(){
        isAvailable = true;
    }
}
class Customer{
    private String customerName;
    private String customerId;

    public Customer(String customerName,String customerId){
        this.customerName = customerName;
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }
}
class Renatal{
    private Car car;
    private Customer customer;
    private int days;
    public Renatal(Car car,Customer customer,int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}
class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Renatal> renatals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        renatals = new ArrayList<>();
    }

    public void addCars(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car,Customer customer,int days) {
        if(car.isAvailable()){
            car.rent();
            renatals.add(new Renatal(car,customer,days));
        }
        else {
            System.out.println("Car is not Available for Rent!");
        }
    }
    public void returnCars(Car car){
        car.returnCar();
        Renatal renatalToRemove = null;
        for(Renatal renatal : renatals){
            if (renatal.getCar() == car){
                renatalToRemove =renatal;
                break;
            }
        }
        if (renatalToRemove != null){
            renatals.remove(renatalToRemove);
            System.out.println("Car returned Successfully");
        }
        else {
            System.out.println("Car was not returned");
        }
    }
    public  void  menu(){
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("==== Welcome to Car Rental Service ====");
            System.out.println("1. Rent a car");
            System.out.println("2.Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter Your Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1){
                System.out.println("\n== Rent a Car ==\n");
                System.out.println("Enter Your Name");
                String customerName = sc.nextLine();

                System.out.println("\nAvailable Cars: ");
                for (Car car : cars){
                    if (car.isAvailable()){
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }
                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = sc.nextLine();

                System.out.println("\nEnter the number of Days for rentals");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars){
                    if (car.getCarId().equals(carId) &&  car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getCustomerName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " +rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.println("\n Confirm Rental (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\n Car Rented Successfully.");
                    }else {
                        System.out.println("\n Rental Canceled");
                    }
                }
                else {
                    System.out.println("\n Invalid Car selection or Car not available for rent");
                }
            }
            else if (choice ==2){
                System.out.println("\n Rent a Car ==\n");
                System.out.print("Enter the car ID that you want to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for (Car car: cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null){
                    Customer customer = null;
                    for (Renatal renatal: renatals){
                        if (renatal.getCar() == carToReturn){
                            customer = renatal.getCustomer();
                            break;
                        }
                    }
                    if (customer != null){
                        returnCars(carToReturn);
                        System.out.println("Car returned Sucessflly by:" + customer.getCustomerName());
                    }else{
                        System.out.println("Car was not rented or rental information is missing!");
                    }
                }
                else {
                    System.out.println("Invalid car ID or car is not rented! ");
                }
            } else if (choice ==3) {
                break;
            }else {
                System.out.println("Invalid choice. Please Enter a valid Option");
            }
        }
        System.out.println("\n Thank You for using Car Rental Service! ");
    }
}
public class main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem =new CarRentalSystem();
        Car car1 =new Car("C001","Toyota","Glanza",70.0);
        Car car2 = new Car("C002","Hyundai","Aura",100);
        Car car3 = new Car("C003","Hyundai","Creta",10);
        Car car4 = new Car("C004","Hyundai","I10",1);
        rentalSystem.addCars(car1);
        rentalSystem.addCars(car2);
        rentalSystem.addCars(car3);
        rentalSystem.addCars(car4);


        rentalSystem.menu();



    }
}
