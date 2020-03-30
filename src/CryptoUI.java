import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.CryptoPrimitive;

public class CryptoUI implements ActionListener{


    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPasswordField passwordField;
    private JPanel passwordPanel;
    private JRadioButton showPasswordRadioButton;
    private JRadioButton decryptRadioButton;
    private JTextArea encrpyedTextArea;
    private JPanel encryptedPanel;
    private JLabel encryptedLabel;
    private JTextArea stringToEncryptTextArea;
    private JPanel stringToEncryptPanel;
    private JLabel stringToEncryptLabel;
    private JPanel bottomPanel;

    public static void main (String [] args){

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }

        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        CryptoUI cryptoUI = new CryptoUI();
        cryptoUI.setupWindow();
    }


    public void setupWindow(){

        JFrame frame = new JFrame("Crypto UI");

        frame.setFocusable(true);

        frame.getContentPane().add(mainPanel);


        encrpyedTextArea.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(encrpyedTextArea.getText());
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        frame.setSize(600,600);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == decryptRadioButton){
            if (decryptRadioButton.isSelected()){
            System.out.println("Radio is on");
            System.out.println(encrpyedTextArea.getText());
            }

            else
                System.out.println("Radio is off");



        }
    }


}
