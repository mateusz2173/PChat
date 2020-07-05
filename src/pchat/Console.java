package pchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Console
{
    private JPanel panel;
    private JTextField textField;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JFrame frame;

    public Console()
    {
        frame = new JFrame();
        panel.setSize(800, 600);
        frame.setSize(800, 600);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        LinkedBlockingQueue<Integer> sb = new LinkedBlockingQueue<>();
        textField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                {
                    String text = textField.getText();
                    if (text.equals("")) return;
                    text.chars().forEach(sb::offer);
                    sb.offer((int) '\n');
                    sb.offer(-1);
                    textField.setText("");
                }
            }
        });
        OutputStream os = new OutputStream()
        {
            @Override
            public void write(int b)
            {
                textArea.append(String.valueOf((char) b));
            }
        };
        try
        {
            System.setOut(new PrintStream(os, true, "UTF-8"));
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        System.setIn(new BufferedInputStream(new InputStream()
        {
            @Override
            public int read()
            {
                try
                {
                    return sb.take();
                } catch (InterruptedException ignored)
                {
                }

                return -1;
            }
        }));

        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));

    }

    public void close()
    {
        frame.setVisible(false);
        frame.dispose();
    }
}
