/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classBooker.service;


import org.classBooker.service.exception.BadFormatFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.classBooker.dao.UserDAO;
import org.classBooker.dao.UserDAOImpl;
import org.classBooker.dao.exception.AlreadyExistingUserException;
import org.classBooker.entity.ProfessorPas;
import org.classBooker.entity.User;
import org.classBooker.service.exception.InexistentFileException;
import org.classBooker.service.exception.UnexpectedFormatFileException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Antares
 */
public class StaffMgrServiceImpl implements StaffMgrService{

    UserDAO u;
    
    public StaffMgrServiceImpl(){
        u =new UserDAOImpl();
    }
    
    @Override
    public void addUser(User user) {
        try {
            u.addUser(user);
        } catch (AlreadyExistingUserException ex) {
            System.out.println("El següent usuari no s'ha pogut afegir a la BD "
                    + user.toString());
        }
    }

    @Override
    public void addMassiveUser(String filename) 
            throws UnexpectedFormatFileException, InexistentFileException, BadFormatFileException{
        
        List<User> lUsers = parseFile(filename);
        
        for (User us : lUsers){
            addUser(us);
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
            throws UnexpectedFormatFileException, InexistentFileException, BadFormatFileException {
        
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

    private List<User> parseCsv(String filename) throws InexistentFileException, BadFormatFileException {
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
                if(strTok.countTokens()!=3){
                    throw new BadFormatFileException();
                }
                String nif = strTok.nextToken();
                if(nif.replaceAll(" ", "").isEmpty()){
                    
                }
                String name = strTok.nextToken();
                if(name.replaceAll(" ", "").isEmpty()){
                    
                }
                String mail = strTok.nextToken();
                if(mail.replaceAll(" ", "").isEmpty()){
                  
                }
                
                
                User us = new ProfessorPas(nif, mail, name);
                lUsers.add(us);
            }
        }
        catch(FileNotFoundException e){
                
        }
        catch(IOException e){
            
        }
        return lUsers;
    }

    private List<User> parseXml(String filename) throws InexistentFileException {
        List<User> lUsers = new ArrayList();
        File f = new File(filename);
        if(!f.exists()){
            throw new InexistentFileException();
        }
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbFactory.newDocumentBuilder();
            Document doc = db.parse(f);
            
            doc.getDocumentElement().normalize();
            
            NodeList nList = doc.getElementsByTagName("user");
            
            for(int temp = 0; temp<nList.getLength(); temp++){
                Node nNode = nList.item(temp);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    String nif = eElement.getElementsByTagName("nif").item(0).getTextContent();
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String email = eElement.getElementsByTagName("email").item(0).getTextContent();
                    User us = new ProfessorPas(nif, email, name);
                    lUsers.add(us);
                }
            }
        }
        catch(Exception e){
            
        }
        return lUsers;
    }
    
}
