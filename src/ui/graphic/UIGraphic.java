package ui.graphic;

import ui.IsGraphical;

public class UIGraphic {
    public static void main(String[] args) {
        System.out.println("Starting graphical interface...\n");
        IsGraphical.isGraphical = true;
        new MainFrame();
    }
}