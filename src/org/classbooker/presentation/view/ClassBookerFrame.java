package org.classbooker.presentation.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;

import org.classbooker.service.*;
import org.classbooker.presentation.controller.*;

/**
 *
 * @author josepma
 *
 *
 *
 */
public class ClassBookerFrame extends JFrame {

    private JMenuBar menubar;
    private JPanel content;

    private ReservationMgrService resService;
    private SpaceMgrService spaceService;
    private StaffMgrService staffService;
    private AuthenticationMgr authMgr;

    public ClassBookerFrame(ReservationMgrService resService, SpaceMgrService spaceService, StaffMgrService staffService, AuthenticationMgr authMgr) {

        super("ClassBooker space reservation application");
        this.resService = resService;
        this.spaceService = spaceService;
        this.staffService = staffService;
        this.authMgr = authMgr;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(800, 800);

        createMenuBar();
        createContents();

        this.setVisible(true);

    }

    private void createContents() {

        content = new JPanel();

        content.add(new JLabel(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("WELCOME TO CLASSBOOKER")));

        this.getContentPane().add(content, BorderLayout.CENTER);

    }

    private void createMenuBar() {
        menubar = new JMenuBar();

        createSpacesMenu();
        createStaffMenu();
        createReservationsMenu();
        createAuthenticatorMenu();

        this.setJMenuBar(menubar);
    }

    private void createSpacesMenu() {
        JMenu spacemenu = new JMenu(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("SPACES"));
        spacemenu.add(new JSeparator());

        JMenuItem spaceItem1 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("NEW BUILDING"));
        spaceItem1.addActionListener(new BuildingInsertionAction(this, spaceService));
        //We associate the "New building" button of the menu to the 
        // BuildingInsertionAction controller action.
        //This is not ideal. It would be better to decouple completely the 
        //view (e.g., this ClassBookerFrame class) and the controller (BuildingInsertionAction).
        //However, the overall design would be a bit more complicated (but more reusable).

        JMenuItem spaceItem2 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("NEW ROOM"));
        spaceItem2.addActionListener(new RoomInsertionAction(this, spaceService));

        JMenuItem spaceItem3 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("LIST BUILDINGS"));
        //spaceItem3.addActionListener(new BuildingListAction(this, spaceService));

        spacemenu.add(spaceItem1);
        spacemenu.add(spaceItem2);
        spacemenu.add(spaceItem3);
        menubar.add(spacemenu);

    }

    private void createStaffMenu() {
        JMenu staffmenu = new JMenu(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("STAFF"));
        staffmenu.add(new JSeparator());

        JMenuItem staffItem1 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("ADD USER"));
        staffItem1.addActionListener(new UserInsertionAction(this, staffService));

        JMenuItem staffItem2 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("ADD MASIVE USERS"));
        staffItem2.addActionListener(new MassiveUserInsertionAction(this, staffService));

        staffmenu.add(staffItem1);
        staffmenu.add(staffItem2);
        menubar.add(staffmenu);
    }

    private void createReservationsMenu() {
        JMenu reservationsmenu = new JMenu(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("RESERVATIONS"));
        reservationsmenu.add(new JSeparator());

        
        JMenuItem reservationItem1 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("FIND USER RESERVATIONS"));
        reservationItem1.addActionListener(new UserReservationFindAction(this, resService));
        JMenuItem reservationItem2 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("MAKE RESERVATION BY SPACE"));
        reservationItem2.addActionListener(new MakeReservationBySpaceAction(this,resService));
        
        JMenuItem reservationItem3 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("MAKE RESERVATION BY TYPE"));
        reservationItem3.addActionListener(new MakeReservationByTypeAction(this, resService));
        
        reservationsmenu.add(reservationItem1);
        reservationsmenu.add(reservationItem2);
        reservationsmenu.add(reservationItem3);
        menubar.add(reservationsmenu);
    }

     private void createAuthenticatorMenu() {
        JMenu staffmenu = new JMenu(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("AUTHENTICATION"));
        staffmenu.add(new JSeparator());

        JMenuItem logItem1 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("LOG IN"));
        logItem1.addActionListener(new MakeLogInAction(this, authMgr));

        JMenuItem logItem2 = new JMenuItem(java.util.ResourceBundle.getBundle("org/classbooker/util/Bundle").getString("LOG OUT"));
        logItem2.addActionListener(new MakeLogOutAction(this, authMgr));
        
        staffmenu.add(logItem1);
        staffmenu.add(logItem2);
        
        menubar.add(staffmenu);
    }   
    
    
    
}
