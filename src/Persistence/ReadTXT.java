package Persistence;

import Acquaintance.IReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class ReadTXT implements IReader {

    private final File cprFile = new File("CPRRegister.txt");
    private final File employeeFile = new File("Employees.txt");
    private final File caseFile = new File("Cases.txt");
    private final File caseRequestFile = new File("CaseRequests.txt");
    private final File currentIDsFile = new File("IDfile.txt");

    /**
     * Reads all the information about a specific case request.
     * @param id
     * @return String[] caseRequest
     */
    @Override
    public String[] getCaseRequest(int id) {
        String[] tokens = new String[15];
        String[] caseRequest = new String[15];
        String word;
        try (Scanner input = new Scanner(caseRequestFile)) {
            while (input.hasNextLine()) {
                word = input.nextLine();
                if (word.equals("")) {
                    continue;
                }

                tokens = word.split(";");
                if (Integer.parseInt(tokens[0]) == id) {
                    caseRequest = word.split(";");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Method getCaseRequest: FILE NOT FOUND EXCEPTION");
        } catch (NumberFormatException e) {
            System.out.println("Method getCaseRequest: NUMBER FORMAT EXCEPTION");
        }
        return caseRequest;
    }

    /**
     * Reads all the information about a specific case.
     *
     * @param id
     * @return String[] cases
     */
    @Override
    public String[] getCase(int id) {
        String[] tokens = new String[16];
        String[] cases = new String[16];
        String word;
        try (Scanner input = new Scanner(caseFile)) {
            while (input.hasNextLine()) {
                word = input.nextLine();
                if (word.equals("")) {
                    continue;
                }

                tokens = word.split(";");
                if (Integer.parseInt(tokens[0]) == id) {
                    cases = word.split(";");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Method getCase: FILE NOT FOUND EXCEPTION");
        } catch (NumberFormatException e) {
            System.out.println("Method getCase: NUMBER FORMAT EXCEPTION");
        }
        return cases;
    }

    /**
     * Reads all the information about an employee depending on the username 
     * and password that is given as an argument.
     * @param username
     * @param password
     * @return 
     */
    @Override
    public String[] login(String username, String password) {
        String[] tokens = new String[11];
        String[] employee = new String[11];
        String word;
        try (Scanner input = new Scanner(employeeFile)) {
            while (input.hasNextLine()) {
                word = input.nextLine();
                if (word.equals("")) {
                    continue;
                }

                tokens = word.split(";");
                if (tokens[8].equals(username) && tokens[9].equals(password)) {
                    employee = word.split(";");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Method login: FILE NOT FOUND EXCEPTION");
        }
        return employee;
    }

    /**
     * Reads all the information about a specific patient.
     * @param cpr
     * @return patient[]
     */
    @Override
    public String[] getPerson(String cpr) {
        String[] tokens = new String[7];
        String[] patient = new String[7];
        String word;
        try (Scanner input = new Scanner(cprFile)) {
            while (input.hasNextLine()) {
                word = input.nextLine();
                if (word.equals("")) {
                    continue;
                }

                tokens = word.split(";");
                if (tokens[0].equalsIgnoreCase(cpr)) {
                    patient = word.split(";");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Method getPerson: FILE NOT FOUND EXCEPTION");
        } catch (NumberFormatException e) {
            System.out.println("Method getPerson: NUMBER FORMAT EXCEPTION");
        }
        return patient;
    }

    /**
     * Reads all the information about a specific employee depending
     * on a specific id. 
     * @param id
     * @return String[] employee
     */
    @Override
    public String[] getEmployee(int id) {
        String[] tokens = new String[5];
        String[] employee = new String[11];
        String word;
        try (Scanner input = new Scanner(employeeFile)) {
            while (input.hasNextLine()) {
                word = input.nextLine();
                if (word.equals("")) {
                    continue;
                }

                tokens = word.split(";");
                if (Integer.parseInt(tokens[7]) == id) {
                    employee = word.split(";");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Method getEmployee: FILE NOT FOUND EXCEPTION");
        } catch (NumberFormatException e) {
            System.out.println("Method getEmployee: NUMBER FORMAT EXCEPTION");
        }
        return employee;
    }

    /**
     * Reads all the ID's from the id file, these ids are caseID, case
     * requestID, employeeID.
     * @return int[] ids
     */
    @Override
    public int[] getCurrentIDs() {
        int[] ids = new int[3];
        String[] s = new String[3];
        String word;
        try (Scanner input = new Scanner(currentIDsFile)) {
            while (input.hasNextLine()) {
                word = input.nextLine();
                s = word.split(";");
            }
            for (int i = 0; i < 2; i++) {
                ids[i] = Integer.parseInt(s[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Method getCurrentIDs: FILE NOT FOUND EXCEPTION");
        } catch (NumberFormatException e) {
            System.out.println("Method getCurrentIDs: NUMBER FORMAT EXCEPTION");
        }
        return ids;
    }

    /**
     * This method is only used in the database.
     * @param citizenCPR
     * @return 
     */
    @Override
    public List<String[]> getSimpleCaseRequests(String citizenCPR) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is only used in the database. 
     * @param citizenCPR
     * @return 
     */
    @Override
    public List<String[]> getSimpleCases(String citizenCPR) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
