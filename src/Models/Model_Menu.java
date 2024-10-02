/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Dang
 */
public class Model_Menu {
   

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menutype getType() {
        return type;
    }

    public void setType(Menutype type) {
        this.type = type;
    }
    
     private String icon;
    private String name;
    private Menutype type;
    
   
    public Model_Menu() {
    }

    public Model_Menu(String icon, String name, Menutype type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }
    
     public Icon toIcon(){
        return new ImageIcon(getClass().getResource("/Icon/" + icon +".png"));
}
    public static enum Menutype{
        TITLE,MENU,EMPTY
    }

    
}
