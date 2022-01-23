package views.chess;

import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import config.UserInterface;
import views.components.ContentPanel;

public class MovesPanel extends ContentPanel {
    
    String[] columnNames = {"#", "White", "Black"};

    JTable table;
    JScrollPane pane;

    DefaultTableModel movesList;
    int numMoves = 0;

    public MovesPanel() {

        movesList = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };


        for (String column : columnNames) {
            movesList.addColumn(column);
        }

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        table = new JTable(movesList);

        table.setRowHeight(15);

        // Table function removal
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setFillsViewportHeight(true);

        // Table styling
        table.setBackground(UserInterface.NAVBAR_COLOUR);
        table.setBorder(UserInterface.GAME_CHAT_BORDER);
        table.setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        table.setSelectionForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        table.setSelectionBackground(UserInterface.GAME_SIDE_HIGHLIGHT_COLOR);

        // Table header styling
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBorder(UserInterface.EMPTY_BORDER);
        table.getTableHeader().setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        table.getTableHeader().setBackground(UserInterface.GAME_MOVES_HEADER_BACKGROUND);

        pane = new JScrollPane(table);           
        pane.setBounds(0, 0, 240, 120);
        pane.setBorder(UserInterface.EMPTY_BORDER);
        
        this.add(pane);
    }

    public void addMove(String move) {
        if (numMoves % 2 == 0) {
            this.movesList.addRow(new Object[]{(numMoves/2 + 1) + ".", move, ""});
        } else {
            this.movesList.setValueAt(move, numMoves/2, 2);
        }
        
        table.getSelectionModel().setSelectionInterval(numMoves/2, numMoves/2);
        table.scrollRectToVisible(new Rectangle(table.getCellRect(numMoves/2, 0, true)));

        numMoves++;
    }

    public void removeMove(){
        if (numMoves == 0) return;
        else if (numMoves % 2 == 0){
            this.movesList.setValueAt("", numMoves/2-1, 2);
        } else {
            this.movesList.removeRow(numMoves/2);
        }

        numMoves--;
    }

    public void clearMoves() {
        numMoves = 0;
        movesList.setRowCount(0);
    }

    public int getNumMoves() {
        return this.numMoves;
    }
}