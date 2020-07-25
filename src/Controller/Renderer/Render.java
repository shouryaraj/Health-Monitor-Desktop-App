package Controller.Renderer;

import javax.swing.*;
import java.awt.*;

public interface Render {
    Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column);
}
