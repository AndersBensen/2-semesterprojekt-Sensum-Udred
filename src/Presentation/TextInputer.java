package Presentation;

import Acquaintance.ICase;
import Acquaintance.ICaseObject;
import Acquaintance.IDomainContact;
import Acquaintance.IPerson;
import Acquaintance.IVisualController;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public final class TextInputer implements IVisualController {

    private List<String> information;
    private final Scanner input;
    private final CommandConverter CC;
    private final IDomainContact IDC;

    public TextInputer(CommandConverter CC, IDomainContact IDC) {
        input = new Scanner(System.in, "ISO-8859-1");
        this.CC = CC;
        this.IDC = IDC;
    }

    public void start() {
        while (true) {
            information = new LinkedList();
            System.out.println("\nEnter a valid system command (login | caserequest | case | editcase | addemployee | deleteemployee | searchperson | logout):\n");
            String command = input.nextLine().toLowerCase();
            IPerson person;

            switch (command) {
                case "caserequest":
                    if (!IDC.authorizeCommand(command)) {
                        continue;
                    }

                    askQuestion("CPR number of the patient?");
                    person = IDC.getPerson(information.get(0));
                    if (person != null) {
                        information.add(person.getName());
                        information.add(Character.toString(person.getGender()));
                        information.add(person.getBirthDate());
                        information.add(person.getAddress());
                    } else {
                        askQuestion("Name of the patient?");
                        askQuestion("Gender of the patient?");
                        askQuestion("Birthdate of the patient?");
                        askQuestion("Address of the patient?");
                    }
                    askQuestion("Phone number of the patient?");
                    askQuestion("Email of the patient?");
                    askQuestion("Description of the case request");
                    askQuestion("Is message clear? (Y/N)");
                    askForFurtherInfo("What kind of care package is request?");
                    askQuestion("What kind of rehousing package is requested?");
                    askQuestion("Who is responsible for the case request?");
                    askQuestion("Is the citizen informed of the case request? (Y/N)");
                    CC.performCommand(command, information.toArray(new String[information.size()]));
                    break;
                case "case":
                    if (!IDC.authorizeCommand(command)) {
                        continue;
                    }

                    askQuestion("Which case request ID are you looking for?");
                    askQuestion("When is the next appointment?");
                    askQuestion("Enter type of guardianship (§5, §6, §7) - if any");
                    if (information.get(2).equalsIgnoreCase("5") || information.get(2).equalsIgnoreCase("6") || information.get(2).equalsIgnoreCase("7")) {
                        askQuestion("Enter the guardians contact information");
                        askQuestion("Descripe the correlation between guardian and citizen");
                    } else {
                        information.add("");
                        information.add("");
                    }
                    askQuestion("Is the citizen informed of his/her rights?");
                    askQuestion("Is the citizen informed electronically? (Y/N)");
                    askQuestion("Is it relevent for the citizen to give his/her consent? (Y/N)");
                    if (information.get(7).equalsIgnoreCase("Y")) {
                        askQuestion("Which type of consent?");
                    } else {
                        information.add("");
                    }
                    askForFurtherInfo("Who has provided information?");
                    askQuestion("Any special circumstances?");
                    askQuestion("Is the citizen from a different commune?");
                    askQuestion("What is the state of the case?");
                    CC.performCommand(command, information.toArray(new String[information.size()]));
                    break;
                case "editcase":
                    if (!IDC.authorizeCommand(command)) {
                        continue;
                    }

                    System.out.println("Which case ID are you looking for?");
                    int ID = input.nextInt();
                    ICase caseInfo = IDC.editCase(ID);
                    System.out.println("\n" + caseInfo);
                    reAddInfo(caseInfo);
                    int fieldNr = 1;
                    while (fieldNr != 0) {
                        System.out.println("\nWhich field should be edited? (1,2... or 0 to exit)");
                        fieldNr = input.nextInt();
                        if (fieldNr != 0) {
                            System.out.println("Type in your edit:");
                            input.nextLine(); //virker ikke uden denne ekstra input linje???
                            String edit = input.nextLine();
                            updateField(fieldNr, edit);
                        }
                    }
                    CC.performCommand(command, information.toArray(new String[information.size()]));
                    break;
                case "addemployee":
                    if (!IDC.authorizeCommand(command)) {
                        continue;
                    }

                    askQuestion("CPR number of the employee?");
                    person = IDC.getPerson(information.get(0));
                    if (person != null) {
                        information.add(person.getName());
                        information.add(Character.toString(person.getGender()));
                        information.add(person.getBirthDate());
                        information.add(person.getAddress());
                    } else {
                        askQuestion("Name of the employee?");
                        askQuestion("Gender of the employee?");
                        askQuestion("Birthdate of the employee?");
                        askQuestion("Address of the employee?");
                    }
                    askQuestion("Phone number of the employee?");
                    askQuestion("Email of the employee?");
                    askQuestion("Username of employee?");
                    askQuestion("Password of employee?");
                    askQuestion("Position number of Employee?");

                    CC.performCommand(command, information.toArray(new String[information.size()]));
                    break;
                case "deleteemployee":
                    if (!IDC.authorizeCommand(command)) {
                        continue;
                    }

                    askQuestion("ID of employee to be deleted?");

                    CC.performCommand(command, information.toArray(new String[information.size()]));
                    break;
                case "login":
                    IDC.resetTimer();
                    askQuestion("Input your username: ");
                    askQuestion("Input your password: ");
                    CC.performCommand(command, information.toArray(new String[information.size()]));
                    break;
                case "searchperson":
                    if (!IDC.authorizeCommand(command)) {
                        continue;
                    }
                    askQuestion("Please enter the CPR number of the requested person.");
                    for (ICaseObject string : IDC.getCaseObject(information.get(0))) {
                        System.out.println(string);
                    }
                    break;
                case "logout":
                    CC.performCommand(command, "");
                    break;
            }
        }
    }

    private void askQuestion(String question) {
        System.out.println(question);
        information.add(input.nextLine());
    }

    private void askForFurtherInfo(String question) {
        String answer = "";
        StringBuilder SB = new StringBuilder();
        while (!answer.equalsIgnoreCase("x")) {
            System.out.println(question + " (write x to exit)");
            answer = input.nextLine();
            if (!answer.equalsIgnoreCase("x")) {
                SB.append(answer);
                SB.append("#");
            }
        }
        SB.deleteCharAt(SB.length() - 1);
        information.add(SB.toString());
    }

    private String decodeArray(String[] sArray) {
        String decoded = "";
        for (String str : sArray) {
            decoded += str;
            decoded += "#";
        }
        return decoded;
    }

    private void updateField(int fieldNr, String updatedInfo) {

        information.set(fieldNr + 2, updatedInfo);
        System.out.println("Field number: " + fieldNr + " updated with: " + updatedInfo);
    }

    private void reAddInfo(ICase caseInfo) {
        information.add(Integer.toString(caseInfo.getID()));
        information.add(Integer.toString(caseInfo.getEmployeeID()));
        information.add(Integer.toString(caseInfo.getCaseRequest().getID()));
        information.add(caseInfo.getNextAppointment());
        information.add(caseInfo.getGuardianship());
        information.add(caseInfo.getPersonalHelper());
        information.add(caseInfo.getPersonalHelperPowerOfAttorney());
        information.add(caseInfo.getCitizenRights());
        information.add(Boolean.toString(caseInfo.isCitizenInformedElectronic()));
        information.add(Boolean.toString(caseInfo.hasConsent()));
        information.add(caseInfo.getConsentType());
        information.add(decodeArray(caseInfo.getCollectCitizenInfo()));
        information.add(caseInfo.getSpecialCircumstances());
        information.add(caseInfo.getDifferentCommune());
        information.add(caseInfo.getState());
        information.add(Long.toString(caseInfo.getDateCreated().getTime()));
    }

    @Override
    public void logout() {
        CC.performCommand("logout", "");
        System.out.println("User auto logged out");
    }

    @Override
    public void alert()
    {
        System.out.println("Alert Logout");
    }  
}
