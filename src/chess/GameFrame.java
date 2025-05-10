package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {

    private Board board;
    private JLabel turnLabel;

    public GameFrame() {
        this.setTitle("Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        // Top panel with turn label and restart button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));
        turnLabel = new JLabel("Turn: WHITE");
        JButton restartButton = new JButton("Restart");

        topPanel.add(turnLabel);
        topPanel.add(restartButton);
        this.add(topPanel, BorderLayout.NORTH);

        // Game board
        board = new Board();
        board.setTurnLabel(turnLabel);  // <— pass label to board
        this.add(board, BorderLayout.CENTER);

        // Restart logic
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.resetGame();
            }
        });

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
