/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.UserDAOImpl;
import org.classBooker.dao.exception.AlreadyExistingUserException;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.User;
import org.classBooker.service.exception.InexistentFileException;
import org.classBooker.service.exception.UnexpectedFormatFileException;

/**
 *
 * @author Antares
 */
public class StaffMgrServiceImpl implements StaffMgrService{

    @Override
    public void addUser(User user) {

        UserDAO u =new UserDAOImpl();
        try {
            u.addUser(user);
        } catch (AlreadyExistingUserException ex) {
            System.out.println("El següent usuari no s'ha pogut afegir a la BD "
                    + user.toString());
        }
    }

    @Override
    public void addMassiveUser(String filename) 
            throws UnexpectedFormatFileException, InexistentFileException{
        
        List<User> lUsers = parseFile(filename);
        
        for (User u : lUsers){
            addUser(u);
        }
    }

    @Override
    public void deleteUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifyUserInformation(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<User> parseFile(String filename) 
            throws UnexpectedFormatFileException, InexistentFileException {
        
        List<User> lUsers = new ArrayList<>();
        if(isCSV(filename)){
            lUsers = parseCsv(filename);
        }            
        else if(isXml(filename)){
            lUsers = parseXml(filename);
        }
        else{
            throw new UnexpectedFormatFileException();
        }    
        return lUsers;
    }

    private boolean isCSV(String filename) {
        return filename.endsWith(".csv");
    }

    private boolean isXml(String filename) {
        return filename.endsWith(".xml");
    }

    private List<User> parseCsv(String filename) throws InexistentFileException {
        List<User> lUsers = new ArrayList();
        File f = new File(filename);
        if(!f.exists()){
            throw new InexistentFileException();
        }
        try{
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line=br.readLine())!=null){
                StringTokenizer strTok = new StringTokenizer(line,";");
                String nif = strTok.nextToken();
                String name = strTok.nextToken();
                String mail = strTok.nextToken();
                User u = new ProfessorPas(nif, mail, name);
                lUsers.add(u);
            }
        }
        catch(Exception e){
            
        }
        return lUsers;
    }

    private List<User> parseXml(String filename) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
