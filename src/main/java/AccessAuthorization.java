import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.util.*;

public class AccessAuthorization {

    // files used for RBAC: who has which roles, and the authorizations for each role
    private final static String ROLES_FILE_PATH = "roles.txt";
    private final static String ACCESS_FILE_PATH = "role_authorization.txt";
    private final static String LIST_FILE_PATH = "user_authorization.txt";

    private String status;

    public static Map<String,List<String>> roleFunctions;
    public static Map<String,List<String>> userFunctions;
    public static Map<String,List<String>> roleUsers;

    //function that return true if the user username can execute the function, false otherwise.
    public static boolean verifyAccess(String username, String function) {
        //used for RBAC
        
        //variables that will contain the files' content in a dictionnary
        //the files are parsed each time a function is called to insure that the latest information has been taken into account.
        roleFunctions = new HashMap<String,List<String>>();
        roleUsers = new HashMap<String,List<String>>();
        
        //String that contains the user role associated to the user
        String userRole = findUserRole(username, roleUsers);
        
        //List that contains all the functions the role can access
        List<String> functionAccess = findFunctionsForRole(userRole, roleFunctions);

        //checking if the user has access to the given function... return Bool
        if (functionAccess.contains(function)) {
            System.out.println("User "+username+" ("+userRole+") tried to access the "+function+" function: access granted");
            return true;
        }
        else {
            System.out.println("User "+username+" ("+userRole+") tried to access the "+function+" function: access denied");
            return false;
        }
    }
    
    public static boolean verifyList(String username, String function) {
        userFunctions = new HashMap<String,List<String>>();
        List<String> userAccess = findFunctionsForUser(username, userFunctions);
        if (userAccess.contains(function)) {
            System.out.println("User "+username+" tried to access the "+function+" function: access granted");
            return true;
        }
        else {
            System.out.println("User "+username+" tried to access the "+function+" function: access denied");
            return false;
        }

    }

    // returns the first key where user is in the list; Note: with that configuration, one user can only have one role. Could be extended
    // fixed
    public static String findUserRole(String username, Map roleUsersdict) {
        
        Map<String, String[]> dictRoleUsers = parseFile(ROLES_FILE_PATH, roleUsersdict);
        
        String userRole = new String();
        
        Set<String> keysInDict = dictRoleUsers.keySet();
        for (String key: keysInDict) {
            String[] usersArray = dictRoleUsers.get(key);
            List<String> usersList = Arrays.asList(usersArray);
            if (usersList.contains(username)){
                userRole = key; // 
            }
        }
    return userRole;
    }

    // will parse the RBAC files and return a dictionnary { key: [value1, value2], ...} where key is
    // the role (String) and value1, ... is either a username or a function (both strings)
    // fixed
    public static Map parseFile(String fileToParse, Map nameDict) {
        
        // parses the file of the following : each entry in a new line
        //key,value1;value2;value3; returns the associated Map {key1: [value1,value2,...]...}

        try {
            File file = new File(fileToParse);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split(","); // list of element on the line
                // int len = splitLine[1].length(); // lenght of the second string (table - like string)
                String[] splitTable = splitLine[1].split(";"); //removes the brackets from the string, then splits 
                nameDict.put(splitLine[0], splitTable);
                }
            br.close();
        } catch (Exception e) {
            System.out.println("Problem while accessing the credentials file:\n" + e.getMessage());
            e.printStackTrace();
        }
        
        return nameDict;
        
    }

    
    public static List<String> findFunctionsForRole(String role, Map roleFunction) {
        //List Access: role is actually the username
        //RBAC: role is a role
        //returns a list the functions that the given role can access
        
        Map<String, String[]> rightsMap = parseFile(ACCESS_FILE_PATH, roleFunction);
        String[] arrayFunctions = rightsMap.get(role);
        List<String> functionList = Arrays.asList(arrayFunctions);
        return functionList;
    } 
    
    public static List<String> findFunctionsForUser(String username, Map userFunctions) {
        //List Access: role is actually the username
        //RBAC: role is a role
        //returns a list the functions that the given role can access
        
        Map<String, String[]> rightsMap = parseFile(LIST_FILE_PATH, userFunctions);
        String[] arrayFunctions = rightsMap.get(username);
        List<String> functionList = Arrays.asList(arrayFunctions);
        return functionList;
    } 
    
}
