package org.example.project1._main;
import org.example.project1._common.Model.Service.getComboBox;
import org.example.project1.mainmenu.UI.AdminMenuFrame;
import org.example.project1.mainmenu.UI.UserMenuFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        //new LoginFrame();
        new UserMenuFrame("giho");
        new AdminMenuFrame("hyeseon");
    }
}