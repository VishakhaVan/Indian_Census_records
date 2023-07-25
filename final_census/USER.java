package com.final_census;


import java.util.Locale;
import java.util.Scanner;
import java.util.regex.*;

class Node2 {  //Creating a new Singly linked List to add user data
   Node2 next;
    String Name, State, District, aadhar;
    char Gender;
    int age, Religion;
    long illiterate, literate;
    Node2(String Name, String State, String District, char Gender, String aadhar, int Religion, int age, long illiterate, long literate) {
        this.Name = Name;
        this.State = State;
        this.District = District;
        this.Gender = Gender;
        this.aadhar = aadhar;
        this.age = age;
        this.illiterate = illiterate;
        this.literate = literate;
        this.Religion = Religion;
    }
}
public class USER extends MLLW {  //class to carry out user/citizen's function
    Node2 head2;
    String aadhar, Name, State, District, UserName, Password, LUser, Lpass;
    String aadhar2, Name2, State2, District2;
    char Gender2;
    char Gender;
    int age, choice, ch, choice2, flag = 0, Religion, Religion2;
    long illiterate, literate;
    MLLW obj = new MLLW();
    Scanner in = new Scanner(System.in);  //using different Scanner's for nextLine() function to work properly
    Scanner aad = new Scanner(System.in);
    Scanner name = new Scanner(System.in);
    Scanner state = new Scanner(System.in);
    Scanner district = new Scanner(System.in);
    Scanner username = new Scanner(System.in);
    Scanner password = new Scanner(System.in);
    Scanner religion = new Scanner(System.in);

    public boolean isValidAadhaarNumber(String str) {  // method to check if it is a valid aadhar number or not
        String regex = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";

        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);

        return m.matches();
    }
    void acceptAadhar() {   //accepting the aadhar number of the user
        System.out.println("Enter your aadhar Card number: ");
        aadhar = aad.nextLine();
        if (!isValidAadhaarNumber(aadhar)) {
            System.out.println("Please enter a valid aadhar number: ");
            acceptAadhar();
        }

    }
    void accept() {  //accepting all the other details of the user if a valid aadhar number is entered
        acceptAadhar();
        System.out.println("Enter your name:");
        Name = name.nextLine();
        State();
        System.out.println("Enter the State you live in: ");
        State = state.nextLine();
        district(State);


        if(flags==15) {
            flags = 0;
            System.out.println("Enter the District you live in: ");
            District = district.nextLine();
            Node ptr = head;
            while (ptr != null) {   //searching the state
                if (ptr.data.equalsIgnoreCase(State)) {
                    Node node = ptr.down;
                    while (node != null) {  //searching the district
                        if (node.data.equalsIgnoreCase(District)) {
                            flagd = 41;
                            break;
                        }
                        node = node.next;
                    }
                }
                ptr = ptr.next;
            }
            if (flagd == 41) {
                flagd=0;
                System.out.println("Enter your gender(Male:M/ Female:F/ Other:O): ");
                Gender = in.next().toLowerCase().charAt(0);
                do {
                    flag = 0;
                    System.out.println("1.Hindu\n2.Buddhist\n3.Muslim\n4.Jain\n5.Christian\n6.Sikh\n7.Other");
                    System.out.println("Enter the number according to your religion: ");
                    Religion = religion.nextInt();
                    if (Religion < 1 || Religion > 7) {
                        System.out.println("Please enter a valid religion");
                        flag = 1;
                    }
                } while (flag == 1);
                System.out.println("Enter your age: ");
                age = in.nextInt();

                if (age > 7) {
                    System.out.println("1.No education");
                    System.out.println("2.Below primary(below grade 5)");
                    System.out.println("3.Primary(grade 5-7)");
                    System.out.println("4.Middle School(grade 8-9)");
                    System.out.println("6.Lower Secondary(grade 10-11)");
                    System.out.println("7.Higher Secondary(grade 12)");
                    System.out.println("8.Beyond Secondary school");
                    System.out.println("Please enter your education level:");
                    choice = in.nextInt();
                    switch (choice) {
                        case 1 -> illiterate++;
                        case 2, 3, 4, 5, 6, 7, 8 -> literate++;
                        default -> System.out.println("Please enter a valid choice");
                    }
                }
                flag=18;
                increment(State, Gender, District, choice, Religion);  //calling the increment function
            }else{
                System.out.println("District not found");
            }
        }

    }
    void increment(String State, char gender, String District, int education, int Religion) {  //increasing the population if a new user enter their data in census
        long old_population = 0;
        Node found = null;
        Node temp = head;
        flag = 0;
        while (temp != null && flag == 0) {
            if (temp.data.equalsIgnoreCase(State)) {  //searching the state
                Node node = temp.down;
                if (node == null) {
                    return;
                }
                while (node != null && flag == 0) {  //searching the district
                    if (node.data.equalsIgnoreCase(District)) {
                        found = node;
                        old_population = node.Tpopulation;
                        temp.Tpopulation++;
                        node.Tpopulation++;
                        if (gender == 'f') {
                            found.Fpopulation = found.Fpopulation + 1; //INCR female pop in the district
                           temp.Fpopulation = temp.Fpopulation + 1; //INCR female pop in the state
                            if (education > 2) {    //incr literacy rate
                                double new_Fliterates = (found.FLiteracy * found.Fpopulation / 100) + 1;
                                found.FLiteracy = new_Fliterates / found.Fpopulation * 100;
                            }
                            switch (Religion) {  //incr religion rate acc.to their religion
                                case 1 -> {
                                    double new_Hpeeps = (found.HinduP22 * found.Tpopulation / 100) + 1;
                                    found.HinduP22 = new_Hpeeps / found.Tpopulation * 100;
                                }
                                case 2 -> {
                                    double new_Bpeeps = (found.BuddhistP22 * found.Tpopulation / 100) + 1;
                                    found.BuddhistP22 = new_Bpeeps / found.Tpopulation * 100;
                                }
                                case 3 -> {
                                    double new_Mpeeps = (found.MuslimP22 * found.Tpopulation / 100) + 1;
                                    found.MuslimP22 = new_Mpeeps / found.Tpopulation * 100;
                                }
                                case 4 -> {
                                    double new_Jpeeps = (found.JainP22 * found.Tpopulation / 100) + 1;
                                    found.JainP22 = new_Jpeeps / found.Tpopulation * 100;
                                }
                                case 5 -> {
                                    double new_Cpeeps = (found.ChristianP22 * found.Tpopulation / 100) + 1;
                                    found.ChristianP22 = new_Cpeeps / found.Tpopulation * 100;
                                }
                                case 6 -> {
                                    double new_Speeps = (found.SikhP22 * found.Tpopulation / 100) + 1;
                                    found.SikhP22 = new_Speeps / found.Tpopulation * 100;
                                }
                                case 7 -> {
                                    double new_Opeeps = (found.OtherP22 * found.Tpopulation / 100) + 1;
                                    found.OtherP22 = new_Opeeps / found.Tpopulation * 100;
                                }
                                default -> System.out.println("Invalid input!");
                            }
                        } else {
                            found.Mpopulation = found.Mpopulation+1; //INCR male pop in the district
                            temp.Mpopulation = temp.Mpopulation+1; //INCR male pop in the state
                            if (education > 2) {  //incr literacy rate
                                double new_Mliterates = (found.MLiteracy * found.Mpopulation / 100) + 1;
                                found.MLiteracy = new_Mliterates / found.Mpopulation * 100;
                            }
                            switch (Religion) { //incr religion rate acc.to their religion
                                case 1 -> {
                                    double new_Hpeeps = (found.HinduP22 * found.Tpopulation / 100) + 1;
                                    found.HinduP22 = new_Hpeeps / found.Tpopulation * 100;
                                }
                                case 2 -> {
                                    double new_Bpeeps = (found.BuddhistP22 * found.Tpopulation / 100) + 1;
                                    found.BuddhistP22 = new_Bpeeps / found.Tpopulation * 100;
                                }
                                case 3 -> {
                                    double new_Mpeeps = (found.MuslimP22 * found.Tpopulation / 100) + 1;
                                    found.MuslimP22 = new_Mpeeps / found.Tpopulation * 100;
                                }
                                case 4 -> {
                                    double new_Jpeeps = (found.JainP22 * found.Tpopulation / 100) + 1;
                                    found.JainP22 = new_Jpeeps / found.Tpopulation * 100;
                                }
                                case 5 -> {
                                    double new_Cpeeps = (found.ChristianP22 * found.Tpopulation / 100) + 1;
                                    found.ChristianP22 = new_Cpeeps / found.Tpopulation * 100;
                                }
                                case 6 -> {
                                    double new_Speeps = (found.SikhP22 * found.Tpopulation / 100) + 1;
                                    found.SikhP22 = new_Speeps / found.Tpopulation * 100;
                                }
                                case 7 -> {
                                    double new_Opeeps = (found.OtherP22 * found.Tpopulation / 100) + 1;
                                    found.OtherP22 = new_Opeeps / found.Tpopulation * 100;
                                }
                                default -> System.out.println("Invalid input!");
                            }
                        }
                        flag = 1;

                    } else {
                        node = node.next;
                    }
                }
            } else {
                temp = temp.next;
            }
        }


        System.out.println(" Old Total Population of this district: " + old_population);
        assert found != null;
        Node newNode = new Node(District, found.Mpopulation, found.Fpopulation, found.MLiteracy, found.FLiteracy, found.Mpopulation11, found.Fpopulation11, found.MLiteracy11, found.FLiteracy11, found.Mpopulation01, found.Fpopulation01, found.MLiteracy01, found.FLiteracy01, found.HinduP22, found.BuddhistP22, found.MuslimP22, found.JainP22, found.ChristianP22, found.SikhP22, found.OtherP22, found.HinduP11, found.BuddhistP11, found.MuslimP11, found.JainP11, found.ChristianP11, found.SikhP11, found.OtherP11, found.HinduP01, found.BuddhistP01, found.MuslimP01, found.JainP01, found.ChristianP01, found.SikhP01, found.OtherP01);
        if (found.next != null)
            found.next.prev = newNode; //connecting next to the new node
        System.out.println(" New Total Population of this district: " + newNode.Tpopulation);

    }
    void insertPerson() {  //inserting the person's data in the linked list
        accept();
            flag=0;
            Node2 newNode = new Node2(Name, State, District, Gender, aadhar, Religion, age, illiterate, literate);
            if (head2 == null) {
                head2 = newNode;
                return;
            }
            Node2 temp = head2;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = null;
            newNode = temp;
    }
    void update() {  //Updating the user's information if they want to
        System.out.println("Please enter your aadhar number: ");
        aadhar2 = aad.nextLine();
       Node2 temp = head2;
        if (head2 == null) {
            return;
        }
        while (temp != null) {  //searching the user using their aadhar number
            if (temp.aadhar.equals(aadhar2)) {
                System.out.println("Please enter what you want to update:");
                System.out.println("1.Name");
                System.out.println("2.State");
                System.out.println("3.District");
                System.out.println("4.Gender");
                System.out.println("5.Religion");
                choice2 = in.nextInt();
                switch (choice2) {
                    case 1 -> {
                        System.out.println("Enter your new name: ");  //updating their name
                        Name2 = name.nextLine();
                        temp.Name = Name2;
                    }
                    case 2 -> {
                        State();
                        System.out.println("Enter your new State: ");  //updating their State and hence district
                        State2 = state.nextLine();
                        temp.State = State2;
                        district(State2);
                        if(flags==15) {
                            flags=0;
                            System.out.println("Enter your new District: ");
                            District2 = district.nextLine();
                            temp.District = District2;
                        }
                    }
                    case 3 -> {
                        district(State);
                        System.out.println("Enter your new District: "); //updating their district
                        District2 = district.nextLine();
                        temp.District = District2;
                    }
                    case 4 -> {
                        System.out.println("Enter your new Gender: ");  ////updating their gender
                        Gender2 = in.next().charAt(0);
                        temp.Gender = Gender2;
                    }
                    case 5 -> {   //updating their religion
                        System.out.println("1.Hindu\n2.Buddhist\n3.Muslim\n4.Jain\n5.Christian\n6.Sikh\n7.Other");
                        System.out.println("Enter a new Religion: ");
                        Religion2 = religion.nextInt();
                        temp.Religion = Religion2;
                    }
                    default -> {
                        System.out.println("Please enter a valid choice: ");
                        update();
                    }
                }
                return;
            } else {
                temp = temp.next;
            }
        }
    }
    void user_display(){   //displaying a particular user's data
        System.out.println("Enter your aadhar number");
        String user_aadhar=aad.nextLine();
       Node2 ptr=head2;
        while(ptr!=null){
            if(ptr.aadhar.equals(user_aadhar)){
                flag=3;
                System.out.println("Name: "+ptr.Name);
                System.out.println("State: "+ptr.State);
                System.out.println("District: "+ptr.District);
                System.out.println("Gender: "+ptr.Gender);
                System.out.println("Age: "+ptr.age);
                switch(ptr.Religion){
                    case 1-> System.out.println("Religion: Hindu");
                    case 2-> System.out.println("Religion: Buddhist");
                    case 3-> System.out.println("Religion: Muslim");
                    case 4-> System.out.println("Religion: Jain");
                    case 5-> System.out.println("Religion: Christian");
                    case 6-> System.out.println("Religion: Sikh");
                    case 7-> System.out.println("Religion: Others/not stated");

                }
                return;
            }
            ptr=ptr.next;
        }
        if(flag!=3){
            System.out.println("Your data is not in this data");
        }

    }
    boolean LoginSignup() {    //login and sign up function for user
        System.out.println("******** Sign Up *******");
        System.out.println("Enter your username: ");
        UserName = username.nextLine();
        System.out.println("Enter your password: ");
        Password = password.nextLine();
        int attempts = 0;
        if ((UserName != null && Password != null) && !UserName.equals(Password)) {
            System.out.println("Successfully signed up! Please log in");
            while (attempts < 3) {
                System.out.println("Enter your username: ");
                LUser = username.nextLine();
                System.out.println("Enter your Password: ");
                Lpass = password.nextLine();
                if (LUser.equals(UserName) && Lpass.equals(Password)) {
                    System.out.println("Successfully logged in");
                    flag = 1;
                    return true;
                } else {
                    attempts++;
                    System.out.println("Incorrect Username or Password, retry");
                    if (attempts == 3) {
                        System.out.println("Too many attempts");
                    }
                }
            }
        } else {
            System.out.println("Username and password should not be empty or same");
            LoginSignup();
        }
        return flag == 1;
    }
    public void displayAllAttributesState() {  // displaying all the attributes(of a given state or district)

        System.out.println("Enter a choice:");
        System.out.println("1.State");
        System.out.println("2.District");
        int ch8 = in.nextInt();
        switch (ch8) {
            case 1 -> {  // displaying all the attributes of a state
                State();
                System.out.println("Enter state");
                String st = state.nextLine();
                Node ptr = head;
                while (ptr != null) {  //searching for the state
                    if (ptr.data.equalsIgnoreCase(st)) {
                        flags2 = 8;
                        float diff1TP = Math.abs(ptr.Tpopulation - ptr.Tpopulation11);
                        float diff2TP = Math.abs(ptr.Tpopulation11 - ptr.Tpopulation01);
                        float per22_11TP = (diff1TP * 100 / ptr.Tpopulation11);
                        float per11_01TP = (diff2TP * 100 / ptr.Tpopulation01);
                        float diff1MP = Math.abs(ptr.Mpopulation - ptr.Mpopulation11);
                        float diff2MP = Math.abs(ptr.Mpopulation11 - ptr.Mpopulation01);
                        float per22_11MP = (diff1MP * 100 / ptr.Mpopulation11);
                        float per11_01MP = (diff2MP * 100 / ptr.Mpopulation01);
                        float diff1FP = Math.abs(ptr.Fpopulation - ptr.Fpopulation11);
                        float diff2FP = Math.abs(ptr.Fpopulation11 - ptr.Fpopulation01);
                        float per22_11FP = (diff1FP * 100 / ptr.Fpopulation11);
                        float per11_01FP = (diff2FP * 100 / ptr.Fpopulation01);
                        double diff1AL = Math.abs(ptr.AvgLiteracy - ptr.AvgLiteracy11);
                        double diff2AL = Math.abs(ptr.AvgLiteracy11 - ptr.AvgLiteracy01);
                        double per22_11AL = (diff1AL * 100 / ptr.AvgLiteracy11);
                        double per11_01AL = (diff2AL * 100 / ptr.AvgLiteracy01);
                        double diff1ML = Math.abs(ptr.MLiteracy - ptr.MLiteracy11);
                        double diff2ML = Math.abs(ptr.MLiteracy11 - ptr.MLiteracy01);
                        double per22_11ML = (diff1ML * 100 / ptr.MLiteracy11);
                        double per11_01ML = (diff2ML * 100 / ptr.MLiteracy01);
                        double diff1FL = Math.abs(ptr.FLiteracy - ptr.FLiteracy11);
                        double diff2FL = Math.abs(ptr.FLiteracy11 - ptr.FLiteracy01);
                        double per22_11FL = (diff1FL * 100 / ptr.FLiteracy11);
                        double per11_01FL = (diff2FL * 100 / ptr.FLiteracy01);


                        System.out.println("Year\t\t\t\t\t\t2022\t\t\t\t2011\t\t\t\t2001\t\t\t\t% 2022-2011\t\t\t% 2011-2001");
                        System.out.println("Total population\t\t\t" + ptr.Tpopulation + "\t\t\t" + ptr.Tpopulation11 + "\t\t\t" + ptr.Tpopulation01 + "\t\t\t" + String.format("%.2f", per22_11TP) + "\t\t\t\t" + String.format("%.2f", per11_01TP));
                        System.out.println("Total Male population\t\t" + ptr.Mpopulation + "\t\t\t" + ptr.Mpopulation11 + "\t\t\t" + ptr.Mpopulation01 + "\t\t\t" + String.format("%.2f", per22_11MP) + "\t\t\t\t" + String.format("%.2f", per11_01MP));
                        System.out.println("Total Female population\t\t" + ptr.Fpopulation + "\t\t\t" + ptr.Fpopulation11 + "\t\t\t" + ptr.Fpopulation01 + "\t\t\t" + String.format("%.2f", per22_11FP) + "\t\t\t\t" + String.format("%.2f", per11_01FP));
                        System.out.println("Average Literacy\t\t\t" + String.format("%.2f", ptr.AvgLiteracy) + "\t\t\t\t" + String.format("%.2f", ptr.AvgLiteracy11) + "\t\t\t\t" + String.format("%.2f", ptr.AvgLiteracy01) + "\t\t\t\t" + String.format("%.2f", per22_11AL) + "\t\t\t\t" + String.format("%.2f", per11_01AL));
                        System.out.println("Male Literacy\t\t\t\t" + String.format("%.2f", ptr.MLiteracy) + "\t\t\t\t" + String.format("%.2f", ptr.MLiteracy11) + " \t\t\t\t" + String.format("%.2f", ptr.MLiteracy01) + "\t\t\t\t" + String.format("%.2f", per22_11ML) + "\t\t\t\t" + String.format("%.2f", per11_01ML));
                        System.out.println("Female Literacy\t\t\t\t" + String.format("%.2f", ptr.FLiteracy) + "\t\t\t\t" + String.format("%.2f", ptr.FLiteracy11) + "\t\t\t\t" + String.format("%.2f", ptr.FLiteracy01) + "\t\t\t\t" + String.format("%.2f", per22_11FL) + "\t\t\t\t" + String.format("%.2f", per11_01FL));
                        System.out.println("Sex ratio\t\t\t\t\t" + ptr.SexRatio + "\t\t\t\t\t" + ptr.SexRatio11 + "\t\t\t\t\t" + ptr.SexRatio01);
                        System.out.println("****** Religion Population ******");
                        System.out.println("Hindu\t\t\t\t\t\t" + String.format("%.2f", ptr.HinduP22) + "\t\t\t\t" + String.format("%.2f", ptr.HinduP11) + "\t\t\t\t" + String.format("%.2f", ptr.HinduP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.HinduP22 - ptr.HinduP11)) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.HinduP11 - ptr.HinduP01)));
                        System.out.println("Muslim\t\t\t\t\t\t" + String.format("%.2f", ptr.MuslimP22) + "\t\t\t\t" + String.format("%.2f", ptr.MuslimP11) + "\t\t\t\t" + String.format("%.2f", ptr.MuslimP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.MuslimP22 - ptr.MuslimP11)) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.MuslimP11 - ptr.MuslimP01)));
                        System.out.println("Buddhism\t\t\t\t\t" + String.format("%.2f", ptr.BuddhistP22) + "\t\t\t\t" + String.format("%.2f", ptr.BuddhistP11) + "\t\t\t\t" + String.format("%.2f", ptr.BuddhistP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.BuddhistP22 - ptr.BuddhistP11)) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.BuddhistP11 - ptr.BuddhistP01)));
                        System.out.println("Jain\t\t\t\t\t\t" + String.format("%.2f", ptr.JainP22) + "\t\t\t\t" + String.format("%.2f", ptr.JainP11) + "\t\t\t\t" + String.format("%.2f", ptr.JainP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.JainP22 - ptr.JainP11)) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.JainP11 - ptr.JainP01)));
                        System.out.println("Christian\t\t\t\t\t" + String.format("%.2f", ptr.ChristianP22) + "\t\t\t\t" + String.format("%.2f", ptr.ChristianP11) + "\t\t\t\t" + String.format("%.2f", ptr.ChristianP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.ChristianP22 - ptr.ChristianP11)) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.ChristianP11 - ptr.ChristianP01)));
                        System.out.println("Sikh\t\t\t\t\t\t" + String.format("%.2f", ptr.SikhP22) + "\t\t\t\t" + String.format("%.2f", ptr.SikhP11) + "\t\t\t\t" + String.format("%.2f", ptr.SikhP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.SikhP22 - ptr.SikhP11)) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.SikhP11 - ptr.SikhP01)));
                        System.out.println("Others/not stated\t\t\t" + String.format("%.2f", ptr.OtherP22) + "\t\t\t\t" + String.format("%.2f", ptr.OtherP11) + "\t\t\t\t" + String.format("%.2f", ptr.OtherP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.OtherP22 - ptr.OtherP11)) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.OtherP11 - ptr.OtherP01)));
                        return;
                    } else {
                        ptr = ptr.next;
                    }
                }
                if (flags2 != 8) {
                    System.out.println("State not found");
                }
            }
            case 2 -> {  //displaying all the attributes of a district
                State();
                System.out.println("Enter state");
                String st = state.nextLine();
                district(st);
                if (flags == 15) {
                    System.out.println("Enter district");
                    String ds = district.nextLine();
                    Node node = head;
                    while (node != null) {  //searching for the state
                        if (node.data.equalsIgnoreCase(st)) {
                            flags3 = 7;
                           Node ptr = node.down;
                            while (ptr != null) {  //searching for the district
                                if (ptr.data.equalsIgnoreCase(ds)) {
                                    flagd3 = 7;
                                    float diff1TP = Math.abs(ptr.Tpopulation - ptr.Tpopulation11);
                                    float diff2TP = Math.abs(ptr.Tpopulation11 - ptr.Tpopulation01);
                                    float per22_11TP = (diff1TP * 100 / ptr.Tpopulation11);
                                    float per11_01TP = (diff2TP * 100 / ptr.Tpopulation01);
                                    float diff1MP = Math.abs(ptr.Mpopulation - ptr.Mpopulation11);
                                    float diff2MP = Math.abs(ptr.Mpopulation11 - ptr.Mpopulation01);
                                    float per22_11MP = (diff1MP * 100 / ptr.Mpopulation11);
                                    float per11_01MP = (diff2MP * 100 / ptr.Mpopulation01);
                                    float diff1FP = Math.abs(ptr.Fpopulation - ptr.Fpopulation11);
                                    float diff2FP = Math.abs(ptr.Fpopulation11 - ptr.Fpopulation01);
                                    float per22_11FP = (diff1FP * 100 / ptr.Fpopulation11);
                                    float per11_01FP = (diff2FP * 100 / ptr.Fpopulation01);
                                    double diff1AL = Math.abs(ptr.AvgLiteracy - ptr.AvgLiteracy11);
                                    double diff2AL = Math.abs(ptr.AvgLiteracy11 - ptr.AvgLiteracy01);
                                    double per22_11AL = (diff1AL * 100 / ptr.AvgLiteracy11);
                                    double per11_01AL = (diff2AL * 100 / ptr.AvgLiteracy01);
                                    double diff1ML = Math.abs(ptr.MLiteracy - ptr.MLiteracy11);
                                    double diff2ML = Math.abs(ptr.MLiteracy11 - ptr.MLiteracy01);
                                    double per22_11ML = (diff1ML * 100 / ptr.MLiteracy11);
                                    double per11_01ML = (diff2ML * 100 / ptr.MLiteracy01);
                                    double diff1FL = Math.abs(ptr.FLiteracy - ptr.FLiteracy11);
                                    double diff2FL = Math.abs(ptr.FLiteracy11 - ptr.FLiteracy01);
                                    double per22_11FL = (diff1FL * 100 / ptr.FLiteracy11);
                                    double per11_01FL = (diff2FL * 100 / ptr.FLiteracy01);

                                    System.out.println("Year\t\t\t\t\t\t2022\t\t\t\t\t2011\t\t\t\t2001\t\t\t\t% 2022-2011\t\t\t\t% 2011-2001");
                                    System.out.println("Total population\t\t\t" + ptr.Tpopulation + " \t\t\t\t" + ptr.Tpopulation11 + "\t\t\t\t" + ptr.Tpopulation01 + "\t\t\t\t" + String.format("%.2f", per22_11TP) + "\t\t\t\t\t" + String.format("%.2f", per11_01TP));
                                    System.out.println("Total Male population\t\t" + ptr.Mpopulation + "\t\t\t\t\t" + ptr.Mpopulation11 + "\t\t\t\t" + ptr.Mpopulation01 + "\t\t\t\t" + String.format("%.2f", per22_11MP) + "\t\t\t\t\t" + String.format("%.2f", per11_01MP));
                                    System.out.println("Total Female population\t\t" + ptr.Fpopulation + "\t\t\t\t\t" + ptr.Fpopulation11 + "\t\t\t\t" + ptr.Fpopulation01 + "\t\t\t\t" + String.format("%.2f", per22_11FP) + "\t\t\t\t\t" + String.format("%.2f", per11_01FP));
                                    System.out.println("Average Literacy\t\t\t" + String.format("%.2f", ptr.AvgLiteracy) + "\t\t\t\t\t" + String.format("%.2f", ptr.AvgLiteracy11) + "\t\t\t\t" + String.format("%.2f", ptr.AvgLiteracy01) + "\t\t\t\t" + String.format("%.2f", per22_11AL) + "\t\t\t\t\t" + String.format("%.2f", per11_01AL));
                                    System.out.println("Male Literacy\t\t\t\t" + String.format("%.2f", ptr.MLiteracy) + "\t\t\t\t\t" + String.format("%.2f", ptr.MLiteracy11) + "\t\t\t\t" + String.format("%.2f", ptr.MLiteracy01) + "\t\t\t\t" + String.format("%.2f", per22_11ML) + "\t\t\t\t\t" + String.format("%.2f", per11_01ML));
                                    System.out.println("Female Literacy\t\t\t\t" + String.format("%.2f", ptr.FLiteracy) + "\t\t\t\t\t" + String.format("%.2f", ptr.FLiteracy11) + "\t\t\t\t" + String.format("%.2f", ptr.FLiteracy01) + "\t\t\t\t" + String.format("%.2f", per22_11FL) + "\t\t\t\t\t" + String.format("%.2f", per11_01FL));
                                    System.out.println("Sex ratio\t\t\t\t\t" + ptr.SexRatio + "\t\t\t\t\t\t" + ptr.SexRatio11 + "\t\t\t\t\t" + ptr.SexRatio01);
                                    System.out.println("****** Religion Population ******");
                                    System.out.println("Hindu\t\t\t\t\t\t" + String.format("%.2f", ptr.HinduP22) + "\t\t\t\t\t" + String.format("%.2f", ptr.HinduP11) + "\t\t\t\t" + String.format("%.2f", ptr.HinduP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.HinduP22 - ptr.HinduP11)) + "\t\t\t\t\t" + String.format("%.2f", Math.abs(ptr.HinduP11 - ptr.HinduP01)));
                                    System.out.println("Muslim\t\t\t\t\t\t" + String.format("%.2f", ptr.MuslimP22) + "\t\t\t\t\t" + String.format("%.2f", ptr.MuslimP11) + "\t\t\t\t" + String.format("%.2f", ptr.MuslimP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.MuslimP22 - ptr.MuslimP11)) + "\t\t\t\t\t" + String.format("%.2f", Math.abs(ptr.MuslimP11 - ptr.MuslimP01)));
                                    System.out.println("Buddhism\t\t\t\t\t" + String.format("%.2f", ptr.BuddhistP22) + "\t\t\t\t\t" + String.format("%.2f", ptr.BuddhistP11) + "\t\t\t\t" + String.format("%.2f", ptr.BuddhistP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.BuddhistP22 - ptr.BuddhistP11)) + "\t\t\t\t\t" + String.format("%.2f", Math.abs(ptr.BuddhistP11 - ptr.BuddhistP01)));
                                    System.out.println("Jain\t\t\t\t\t\t" + String.format("%.2f", ptr.JainP22) + "\t\t\t\t\t" + String.format("%.2f", ptr.JainP11) + "\t\t\t\t" + String.format("%.2f", ptr.JainP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.JainP22 - ptr.JainP11)) + "\t\t\t\t\t" + String.format("%.2f", Math.abs(ptr.JainP11 - ptr.JainP01)));
                                    System.out.println("Christian\t\t\t\t\t" + String.format("%.2f", ptr.ChristianP22) + "\t\t\t\t\t" + String.format("%.2f", ptr.ChristianP11) + "\t\t\t\t" + String.format("%.2f", ptr.ChristianP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.ChristianP22 - ptr.ChristianP11)) + "\t\t\t\t\t" + String.format("%.2f", Math.abs(ptr.ChristianP11 - ptr.ChristianP01)));
                                    System.out.println("Sikh\t\t\t\t\t\t" + String.format("%.2f", ptr.SikhP22) + "\t\t\t\t\t" + String.format("%.2f", ptr.SikhP11) + "\t\t\t\t" + String.format("%.2f", ptr.SikhP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.SikhP22 - ptr.SikhP11)) + "\t\t\t\t\t" + String.format("%.2f", Math.abs(ptr.SikhP11 - ptr.SikhP01)));
                                    System.out.println("Others/not stated\t\t\t" + String.format("%.2f", ptr.OtherP22) + "\t\t\t\t\t" + String.format("%.2f", ptr.OtherP11) + "\t\t\t\t" + String.format("%.2f", ptr.OtherP01) + "\t\t\t\t" + String.format("%.2f", Math.abs(ptr.OtherP22 - ptr.OtherP11)) + "\t\t\t\t\t" + String.format("%.2f", Math.abs(ptr.OtherP11 - ptr.OtherP01)));
                                    return;
                                } else {
                                    ptr = ptr.next;
                                }
                            }
                            if (flagd3 != 7) {
                                System.out.println("District not found in this particular state");
                                return;
                            }

                        } else {
                            node = node.next;
                        }
                    }
                }
            }
            default -> {
                System.out.println("Invalid input");
                displayAllAttributesState();
            }
        }
    }
    void displayIndia() {    //displaying all the attributes for India
        // Information();
        long TPopIndia = 0, MPopIndia = 0, FPopIndia = 0, TPopIndia11 = 0, MPopIndia11 = 0, FPopIndia11 = 0, TPopIndia01 = 0, MPopIndia01 = 0, FPopIndia01 = 0;
        double ALT = 0, MLT = 0, FLT = 0, ALT11 = 0, MLT11 = 0, FLT11 = 0, ALT01 = 0, MLT01 = 0, FLT01 = 0, ALIndia;
        double HPT = 0, MPT = 0, BPT = 0, JPT = 0, CPT = 0, SPT = 0, OPT = 0, HPT11 = 0, MPT11 = 0, BPT11 = 0, JPT11 = 0, CPT11 = 0, SPT11 = 0, OPT11 = 0, HPT01 = 0, MPT01 = 0, BPT01 = 0, JPT01 = 0, CPT01 = 0, SPT01 = 0, OPT01 = 0;
        Node ptr = head;

        while (ptr != null) {
            System.out.println("\nState: " + ptr.data);
            TPopIndia += ptr.Tpopulation;
            MPopIndia += ptr.Mpopulation;
            FPopIndia += ptr.Fpopulation;
            TPopIndia11 += ptr.Tpopulation11;
            MPopIndia11 += ptr.Mpopulation11;
            FPopIndia11 += ptr.Fpopulation11;
            TPopIndia01 += ptr.Tpopulation01;
            MPopIndia01 += ptr.Mpopulation01;
            FPopIndia01 += ptr.Fpopulation01;
            ALT += ptr.AvgLiteracy;
            MLT += ptr.MLiteracy;
            FLT += ptr.FLiteracy;
            ALT11 += ptr.AvgLiteracy11;
            MLT11 += ptr.MLiteracy11;
            FLT11 += ptr.FLiteracy11;
            ALT01 += ptr.AvgLiteracy01;
            MLT01 += ptr.MLiteracy01;
            FLT01 += ptr.FLiteracy01;
            HPT += ptr.HinduP22;
            BPT += ptr.BuddhistP22;
            MPT += ptr.MuslimP22;
            JPT += ptr.JainP22;
            CPT += ptr.ChristianP22;
            SPT += ptr.SikhP22;
            OPT += ptr.OtherP22;
            HPT11 += ptr.HinduP11;
            BPT11 += ptr.BuddhistP11;
            MPT11 += ptr.MuslimP11;
            JPT11 += ptr.JainP11;
            CPT11 += ptr.ChristianP11;
            SPT11 += ptr.SikhP11;
            OPT11 += ptr.OtherP11;
            HPT01 += ptr.HinduP01;
            BPT01 += ptr.BuddhistP01;
            MPT01 += ptr.MuslimP01;
            JPT01 += ptr.JainP01;
            CPT01 += ptr.ChristianP01;
            SPT01 += ptr.SikhP01;
            OPT01 += ptr.OtherP01;
            Node ptr2 = ptr.down;
            while (ptr2 != null) {
                System.out.println("  *" + "District: " + ptr2.data);
                ptr2 = ptr2.next;
            }
            ptr = ptr.next;
        }
        ALIndia = ALT / 7;
        double ALIndia11 = ALT11 / 7;
        double ALIndia01 = ALT01 / 7;
        double MLIndia = MLT / 7;
        double MLIndia11 = MLT11 / 7;
        double MLIndia01 = MLT01 / 7;
        double FLIndia = FLT / 7;
        double FLIndia11 = FLT11 / 7;
        double FLIndia01 = FLT01 / 7;
        double HPIndia = HPT / 7;
        double HPIndia11 = HPT11 / 7;
        double HPIndia01 = HPT01 / 7;
        double MPIndia = MPT / 7;
        double MPIndia11 = MPT11 / 7;
        double MPIndia01 = MPT01 / 7;
        double BPIndia = BPT / 7;
        double BPIndia11 = BPT11 / 7;
        double BPIndia01 = BPT01 / 7;
        double JPIndia = JPT / 7;
        double JPIndia11 = JPT11 / 7;
        double JPIndia01 = JPT01 / 7;
        double CPIndia = CPT / 7;
        double CPIndia11 = CPT11 / 7;
        double CPIndia01 = CPT01 / 7;
        double SPIndia = SPT / 7;
        double SPIndia11 = SPT11 / 7;
        double SPIndia01 = SPT01 / 7;
        double OPIndia = OPT / 7;
        double OPIndia11 = OPT11 / 7;
        double OPIndia01 = OPT01 / 7;

        System.out.println("\t\t\t\t\t ********** INDIA'S CENSUS **********");
        System.out.println("\nYear\t\t\t\t\t\t2022\t\t\t\t\t2011\t\t\t\t2001\n");
        System.out.println("Total population\t\t\t\t" + TPopIndia + " \t\t\t\t" + TPopIndia11 + "\t\t\t" + TPopIndia01);
        System.out.println("Total Male population\t\t\t\t" + MPopIndia + "\t\t\t\t" + MPopIndia11 + "\t\t\t" + MPopIndia01);
        System.out.println("Total Female population\t\t\t\t" + FPopIndia + "\t\t\t\t" + FPopIndia11 + "\t\t\t" + FPopIndia01+"\n");
        System.out.println("****** Literacy ******");
        System.out.println("Average Literacy\t\t\t\t" + String.format("%.2f",ALIndia) + "\t\t\t\t\t" + String.format("%.2f",ALIndia11) + "\t\t\t\t" + String.format("%.2f",ALIndia01));
        System.out.println("Male Literacy\t\t\t\t\t" + String.format("%.2f",MLIndia) + "\t\t\t\t\t" + String.format("%.2f",MLIndia11) + "\t\t\t\t" + String.format("%.2f",MLIndia01));
        System.out.println("Female Literacy\t\t\t\t\t" + String.format("%.2f",FLIndia) + "\t\t\t\t\t" + String.format("%.2f",FLIndia) + "\t\t\t\t" + String.format("%.2f",FLIndia)+"\n");
        System.out.println("****** Religion Population ******");
        System.out.println("Hindu\t\t\t\t\t\t" + String.format("%.2f", HPIndia) + "\t\t\t\t\t" + String.format("%.2f", HPIndia11) + "\t\t\t\t" + String.format("%.2f", HPIndia01));
        System.out.println("Muslim\t\t\t\t\t\t" + String.format("%.2f", MPIndia) + "\t\t\t\t\t" + String.format("%.2f", MPIndia11) + "\t\t\t\t" + String.format("%.2f", MPIndia01));
        System.out.println("Buddhism\t\t\t\t\t" + String.format("%.2f", BPIndia) + "\t\t\t\t\t" + String.format("%.2f", BPIndia11) + "\t\t\t\t" + String.format("%.2f", BPIndia01));
        System.out.println("Christian\t\t\t\t\t" + String.format("%.2f", CPIndia) + "\t\t\t\t\t" + String.format("%.2f", CPIndia11) + "\t\t\t\t" + String.format("%.2f", CPIndia01));
        System.out.println("Jain\t\t\t\t\t\t" + String.format("%.2f", JPIndia) + "\t\t\t\t\t" + String.format("%.2f", JPIndia11) + "\t\t\t\t" + String.format("%.2f", JPIndia01));
        System.out.println("Sikh\t\t\t\t\t\t" + String.format("%.2f", SPIndia) + "\t\t\t\t\t" + String.format("%.2f", SPIndia11) + "\t\t\t\t" + String.format("%.2f", SPIndia01));
        System.out.println("Others/not stated\t\t\t\t" + String.format("%.2f", OPIndia) + "\t\t\t\t\t" + String.format("%.2f", OPIndia11) + "\t\t\t\t" + String.format("%.2f", OPIndia01)+"\n");


    }
}