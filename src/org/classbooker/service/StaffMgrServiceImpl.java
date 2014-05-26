/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.classbooker.dao.UserDAO;
import org.classbooker.dao.UserDAOImpl;
import org.classbooker.dao.exception.AlreadyExistingUserException;
import org.classbooker.entity.ProfessorPas;
import org.classbooker.entity.User;
import org.classbooker.service.exception.InexistentFileException;
import org.classbooker.service.exception.UnexpectedFormatFileException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Antares
 */
public class StaffMgrServiceImpl implements StaffMgrService{
    
    private UserDAO u;
    private static final Logger LOGGER = Logger.getLogger(ReservationMgrServiceImpl.class);
    private static final int X = 3;
    
    public StaffMgrServiceImpl(){
        u =new UserDAOImpl();
        
    }
    public UserDAO getUserDAO(){
        return this.u;
    }
    public void setUserDao(UserDAO u){
        this.u=u;
    }
    
    @Override
    public void addUser(User user) throws AlreadyExistingUserException {
        u.addUser(user);
    }

    @Override
    public void addMassiveUser(String filename) 
            throws Exception{
        
        List<User> lUsers = parseFile(filename);
        
        for (User us : lUsers){
            try{
                addUser(us);
            }catch(AlreadyExistingUserException e){
                LOGGER.log(Level.INFO, "The file contains Repeated Users ",e);
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        //To change body of generated methods, choose Tools | Templates.
        throw new UnsupportedOperationException("The operation deleteUser is not supported yet."); 
    }

    @Override
    public void modifyUserInformation(User user) {
        //To change body of generated methods, choose Tools | Templates.
        throw new UnsupportedOperationException("The operation modifyUserInformation is not supported yet."); 
    }

    private List<User> parseFile(String filename) throws InexistentFileException, UnexpectedFormatFileException{
        
        List<User> lUsers = new ArrayList<>();
        if(isCSV(filename)){
            lUsers = parseCsv(filename);
        }else if(isXml(filename)){
            lUsers = parseXml(filename);
        }else{
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

    private List<User> parseCsv(String filename) throws InexistentFileException{
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
                if(strTok.countTokens()!=X){
                    badDataUser();
                }else{
                    String nif = strTok.nextToken();
                    String name = strTok.nextToken();
                    String mail = strTok.nextToken();
                    createGoodProffesorpas(nif, mail, name, lUsers);
                }
            }
        }catch(FileNotFoundException e){
            LOGGER.log(Level.WARN, e);
        }catch(IOException e){
            LOGGER.log(Level.WARN, e);
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
                
                Element eElement = (Element) nNode;
                
                obtainElementInformation(eElement,lUsers);
                
            }
        }catch(Exception e){
            LOGGER.log(Level.WARN, e);
        }
        return lUsers;
    }

    private void createGoodProffesorpas(String nif, String mail, String name, List<User> lUser) {
        if(nif.replaceAll(" ", "").isEmpty() || 
                mail.replaceAll(" ", "").isEmpty() ||
                name.replaceAll(" ", "").isEmpty()){
            Logger.getLogger(ReservationMgrServiceImpl.class).log(Level.INFO, "Empty data user.");
        }else{
            User user = new ProfessorPas(nif, mail, name);
            lUser.add(user);
        }
    }

    @Override
    public User getUser(String nif) {
        return u.getUserByNif(nif);
    }

    private void badDataUser() {
        LOGGER.log(Level.INFO, "Bad data user.");
    }

    private void obtainElementInformation(Element eElement, List<User> lUsers) {
        try{
            String nif = eElement.getElementsByTagName("nif").item(0).getTextContent();
            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
            String email = eElement.getElementsByTagName("email").item(0).getTextContent();
            createGoodProffesorpas(nif, email, name, lUsers);
        }catch(NullPointerException e){
            LOGGER.log(Level.INFO, "Bad data user in the xml.",e);
        }
    }

    
    
}
