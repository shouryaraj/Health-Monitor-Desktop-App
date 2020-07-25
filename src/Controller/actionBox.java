package Controller;

import javax.swing.*;

/***
 * It is a class to share the data to render the Jcombox in the TableRender
 */
public class actionBox{
    private JComboBox combox;
    public actionBox(JComboBox combox){
        this.combox = combox;
    }
    public void setCombox(JComboBox combox){
        this.combox = combox;

    }
    public  JComboBox getCombox(){
        return this.combox;
    }
}