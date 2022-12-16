import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hotel {
    Scanner scanner = new Scanner(System.in);
    private ArrayList<Room> roomList;
    private ArrayList<Booking> bookingList;

    // Initialize the hotel with 3 rooms
    public Hotel() {
        this.roomList = new ArrayList<Room>();
        this.roomList.add(new PremiumRoom(101));
        this.roomList.add(new DeluxeRoom(102));
        this.roomList.add(new GrandSuiteRoom(103));
        this.bookingList = new ArrayList<Booking>();
    }

    // Search for a room by room number. Return empty room if not found.
    public Room searchRoom(int roomNumber) {
        for (Room room : roomList) {
            if (room.getNumber() == roomNumber) {
                return room;
            }
        }
        return new PremiumRoom(); //null room
    }

    // Get list of empty rooms.
    public ArrayList<Room> getEmptyRoomList() {
        ArrayList<Room> emptyRoomList = new ArrayList<Room>();
        for (Room room : roomList) {
            if (!room.isReserved()) {
                emptyRoomList.add(room);
            }
        }
        return emptyRoomList;
    }

    // Print list of empty rooms.
    public void printEmptyRoom() {
        String roomType = "";
        for (Room room : roomList) {
            if (!room.isReserved()) {
                if (room instanceof PremiumRoom)
                    roomType = "Premium Room";
                else if (room instanceof DeluxeRoom)
                    roomType = "Deluxe Room";
                else if (room instanceof GrandSuiteRoom)
                    roomType = "Grand Suite Room";
                System.out.println("Room number " + room.getNumber() + " : " + roomType);
            }
        }
    }

    // Print each rooms' description.
    public void printRoomDescription() {
        System.out.println("- " + (new PremiumRoom()).getDescription());
        System.out.println("- " + (new DeluxeRoom()).getDescription());
        System.out.println("- " + (new GrandSuiteRoom()).getDescription());
    }

    // Add a booking to the booking list.
    public void addBooking(Booking booking) {
        this.bookingList.add(booking);
    }

    // Remove a booking from the booking list.
    public void removeBooking(Booking booking) {
        this.bookingList.remove(booking);
    }

    // Get all guests' info from user. 
    private ArrayList<Guest> getGuestInformation(int guestCount) {
        ArrayList<Guest> guestList = new ArrayList<Guest>();

        for (int i = 0; i < guestCount; i++) {
            System.out.printf("Guest no.%d\n", i+1);
            System.out.print("Please input your full name: ");
            String inputFullName = scanner.nextLine();
            System.out.print("Please input your phone: ");
            String inputPhone = scanner.nextLine();
            System.out.print("Please input your email: ");
            String inputEmail = scanner.nextLine();
            Guest newGuest = new Guest(inputFullName, inputPhone, inputEmail);
            guestList.add(newGuest);
        }
        return guestList;
    }

    // Get date in correct format.
    private Date getDate() {
        String dateString;
        Date date = new Date();
        boolean done = false;
        while (!done) {
            dateString = scanner.nextLine();
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date = format.parse(dateString);
                done = true;
            } catch (Exception e) {
                System.out.println("Invalid date: " + dateString);
            }
        }
        return date;
    }

    // Get time in correct format.
    private Date getTime() {
        String timeString;
        Date time = new Date();
        boolean done = false;
        while (!done) {
            timeString = scanner.nextLine();
            try {
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                time = format.parse(timeString);
                done = true;
            }
            catch (Exception e) {
                System.out.println("Invalid time: " + timeString);
            }
        }
        return time;
    }

    // Validate checkin and checkout date info.
    private boolean validateDate(Date dateCheckIn, Date dateCheckOut) {
        Date currentDate = new Date();
        if (dateCheckIn.compareTo(currentDate) < 0) {
            return false;
        }
        if (dateCheckOut.compareTo(dateCheckIn) < 0) {
            return false;
        }
        return true;
    }

    // Get date info from user.
    private ArrayList<Date> getDateInformation() {
        Date dateCheckIn = new Date();
        Date dateCheckOut = new Date();
        while (true) {
            System.out.print("Please input check-in date (dd/MM/yyyy): ");
            dateCheckIn = getDate();
            System.out.print("Please input check-out date (dd/MM/yyyy): ");
            dateCheckOut = getDate();
            System.out.println(dateCheckIn);
            System.out.println(dateCheckOut);
            if (validateDate(dateCheckIn, dateCheckOut)) {
                break;
            }
        }
        ArrayList<Date> dateInfo = new ArrayList<Date>();
        dateInfo.add(dateCheckIn);
        dateInfo.add(dateCheckOut);
        return dateInfo;
    }

    // Get selected room info from user.
    private Room getRoomInformation() {
        ArrayList<Room> emptyRoomList = getEmptyRoomList();
        System.out.printf("There are %d rooms available:\n", emptyRoomList.size());
        for (Room room : emptyRoomList) {
            System.out.println("Room Number " + room.getNumber());
        }
        while (true) {
            System.out.print("Please choose your room number: ");
            int roomNumber = scanner.nextInt();
            Room room = searchRoom(roomNumber);
            if (emptyRoomList.contains(room)) {
                return room;
            }
            else {
                System.out.println("Invalid room number");
            }
        }
    }

    // Get y/n (confirm) from user.
    private char getYN() {
        char choice = scanner.next().charAt(0);
        while (choice != 'y' && choice != 'n') {
            System.out.println("Please enter 'y' or 'n' (y = yes | n = no)");
            choice = Character.toLowerCase(scanner.next().charAt(0));
        }
        return choice;
    }

    // Get additional bed info from user.
    public boolean getAdditionalBedInfo() {
        System.out.print("Do you want an additional single-sized bed (y/n): ");
        char choice = getYN();
        if (choice == 'y') return true;
        else return false;
    }

    // Get additional food info from user.
    public boolean getAdditionalFoodInfo() {
        System.out.print("Do you want FoodPlus (y/n): ");
        char choice = getYN();
        if (choice == 'y') return true;
        else return false;
    }

    // Get additional cleaning service info from user.
    public boolean getAdditionalCleaningInfo() {
        System.out.print("Do you want CleaningPlus (y/n): ");
        char choice = getYN();
        if (choice == 'y') return true;
        else return false;
    }

    //// NEW added method : using to get payment information
    // Get payment method from user.
    public String getPaymentMethod() {
        System.out.print("Please select your payment method (1 = Cash | 2 = Credit/Debit Card): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        while (choice != 1 && choice != 2) {
            System.out.println("Please enter '1' or '2' (1 = Cash | 2 = Credit/Debit Card)");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        if(choice == 1) return "Cash";
        else return "Credit/Debit Card";
    }

    // Get all booking info from user.
    public Booking getBookingInformation() {
        System.out.print("How many guest(s) are you booking for: ");
        int guestCount = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Guest> guestList = getGuestInformation(guestCount);
        ArrayList<Date> dateInfo = getDateInformation();
        Room room = getRoomInformation();

        // Check room type and ask for additional amenities if needed.
        if (room instanceof DeluxeRoom) {
            if (getAdditionalBedInfo()) ((DeluxeRoom)room).addBed();
        }
        else if (room instanceof GrandSuiteRoom) {
            if (getAdditionalBedInfo()) ((GrandSuiteRoom)room).addBed();
            if (getAdditionalFoodInfo()) {
                ArrayList<Amenity> amenities = ((GrandSuiteRoom)room).getAmenityList();
                for (Amenity amenity : amenities) {
                    if (amenity instanceof Food) {
                        amenities.set(amenities.indexOf(amenity), new FoodPlus());
                    }
                }
            }
            if (getAdditionalCleaningInfo()) {
                ArrayList<Amenity> amenities = ((GrandSuiteRoom)room).getAmenityList();
                for (Amenity amenity : amenities) {
                    if (amenity instanceof CleaningService) {
                        amenities.set(amenities.indexOf(amenity), new CleaningServicePlus());
                    }
                }
            }
            scanner.nextLine();
            System.out.println("What time do you want to get cleaning service? (hh:mm:ss)");
            Date time = getTime();
            System.out.println(time);
            for (Amenity amenity : ((GrandSuiteRoom)room).getAmenityList()) {
                if (amenity instanceof CleaningService) {
                    ((CleaningService)amenity).setTime(time);
                }
                if (amenity instanceof CleaningServicePlus) {
                    ((CleaningServicePlus)amenity).setTime(time);
                }
            }
        }
        // Calculating total price and get payment method.
        float totalPrice = room.getTotalPrice();
        System.out.println("Total price will be: " + totalPrice);
        String paymentMethod = getPaymentMethod();
        Transaction transaction = new Transaction(totalPrice, paymentMethod);
        return new Booking(guestList, dateInfo.get(0), dateInfo.get(1), room, transaction);
    }

    // Menu interface for application.
    private void showMenu() {
        System.out.println("============================================================");
        System.out.println("================== Welcome to Laork Hotel ==================");
        System.out.println("============================================================");
        System.out.println("===    What you want to do today?                        ===");
        System.out.println("===    1. See list of available rooms                    ===");
        System.out.println("===    2. See description of each room type              ===");
        System.out.println("===    3. Book a room                                    ===");
        System.out.println("===    4. Unbook a room                                  ===");
        System.out.println("============================================================");
    }

    // Get menu number from user.
    private int getMenu() {
        while(true) {
            showMenu();
            System.out.print("Please input menu number: ");
            //int menu = Integer.parseInt(scanner.nextLine());
            int menu = scanner.nextInt();
            //System.out.println(menu);
            if (menu >= 1 && menu <= 4) {
                return menu;
            }
        }
    }

    // Hotel booking application.
    public void startService() {
        while(true) {
            int menu = getMenu();
            if (menu == 1)
                printEmptyRoom();
            else if (menu == 2)
                printRoomDescription();
            else if (menu == 3) {
                Booking booking = getBookingInformation();
                booking.print();
                Transaction transaction = booking.getTransaction();
                if (transaction.confirm()) {
                    addBooking(booking);
                    booking.getRoom().reserve();
                }
                System.out.println(bookingList);
            }
            else if (menu == 4) {
                Booking targetBooking = null;
                System.out.print("What is your room number: ");
                int roomNo = scanner.nextInt();
                for (Booking booking : bookingList) {
                    if (roomNo == booking.getRoom().getNumber()) {
                        targetBooking = booking;
                    }
                }
                if (targetBooking != null) {
                    targetBooking.getRoom().unReserve();
                    removeBooking(targetBooking);
                }
                else {
                    System.out.println("Wrong room number");
                }
            }
        }
    }
}
