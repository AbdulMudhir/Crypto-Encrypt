import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

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
    private  Cipher cipher;




    public static void main (String [] args) throws NoSuchAlgorithmException, NoSuchPaddingException {

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


    public void setupWindow() throws NoSuchPaddingException, NoSuchAlgorithmException {

        JFrame frame = new JFrame("Crypto UI");

        frame.setFocusable(true);

        frame.getContentPane().add(mainPanel);

        cipher = Cipher.getInstance("AES");


        stringToEncryptTextArea.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (decryptRadioButton.isSelected()){

                }
                else{


                    encryptString();



                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        showPasswordRadioButton.addActionListener(this::actionPerformed);



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
        // check if the show password radio was enabled if so, display the password
        if (e.getSource() == showPasswordRadioButton && showPasswordRadioButton.isSelected()){

            passwordField.setEchoChar((char) 0);
        }
        else{
            passwordField.setEchoChar('*');
        }
    }
    public void encryptString() {


        System.out.println(passwordField.getPassword());

        String data = stringToEncryptTextArea.getText();


    }



}
