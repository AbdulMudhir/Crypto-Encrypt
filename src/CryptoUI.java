import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import java.security.spec.KeySpec;
import javax.crypto.spec.IvParameterSpec;


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
    private JLabel passwordLabel;
    private  Cipher cipher;
    private SecretKey key;
    byte[] iv;


    private byte[] salt  = new byte[8];;




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

        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        passwordField.putClientProperty("JPasswordField.cutCopyAllowed",true);

        stringToEncryptTextArea.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (decryptRadioButton.isSelected()){

                    try {
                        decrypt();
                    } catch (NoSuchPaddingException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    } catch (InvalidAlgorithmParameterException ex) {
                        ex.printStackTrace();
                    } catch (InvalidKeyException ex) {
                        ex.printStackTrace();
                    } catch (UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                    } catch (BadPaddingException ex) {
                        ex.printStackTrace();
                    } catch (IllegalBlockSizeException ex) {
                        ex.printStackTrace();
                    }

                }
                else{


                    try {
                        encryptString();
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    } catch (InvalidKeySpecException ex) {
                        ex.printStackTrace();
                    } catch (InvalidKeyException ex) {
                        ex.printStackTrace();
                    } catch (UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                    } catch (BadPaddingException ex) {
                        ex.printStackTrace();
                    } catch (IllegalBlockSizeException ex) {
                        ex.printStackTrace();
                    } catch (InvalidParameterSpecException ex) {
                        ex.printStackTrace();
                    }


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

        decryptRadioButton.addActionListener(this::actionPerformed);

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

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

        if (e.getSource() == decryptRadioButton && decryptRadioButton.isSelected()) {

            stringToEncryptLabel.setText("Decode To decrypt");
            encryptedLabel.setText("Decrypted");
            passwordLabel.setText("Secret Key");

        } else {
            stringToEncryptLabel.setText("String To Encrypt");
            encryptedLabel.setText("Encrypted");

            if (passwordLabel.getText().equals("Secret key has been generated") || passwordLabel.getText().equals("Secret Key") ) ;
            else {
                passwordLabel.setText("Password");


            }
        }
        // check if the show password radio was enabled if so, display the password
        if (e.getSource() == showPasswordRadioButton && showPasswordRadioButton.isSelected() || e.getSource() == decryptRadioButton && showPasswordRadioButton.isSelected()) {
            passwordField.setEchoChar((char) 0);
        }


        else {
            passwordField.setEchoChar('*');
        }

        if (e.getSource() == passwordField && passwordField.getPassword().length == 0)
            passwordLabel.setText("Password");
    }
    public void encryptString() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidParameterSpecException{

        //salt = new byte[8];
        int keySize = 256;

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        KeySpec spec = new PBEKeySpec(passwordField.getPassword(), salt, 65536, keySize);

        SecretKey tmp = factory.generateSecret(spec);

        key = new SecretKeySpec(tmp.getEncoded(), "AES");



        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte [] dataByte = stringToEncryptTextArea.getText().getBytes("UTF-8");

        AlgorithmParameters params = cipher.getParameters();

        iv = params.getParameterSpec(IvParameterSpec.class).getIV();

        String viString = Base64.getEncoder().withoutPadding().encodeToString(iv);

        System.out.println(viString);


        System.out.println(iv);


        byte[] ciphertext = cipher.doFinal(dataByte);


        String encodedString = Base64.getEncoder().withoutPadding().encodeToString(key.getEncoded());

        String encodeEncryptedString = Base64.getEncoder().withoutPadding().encodeToString(ciphertext);

        String outputInfo = String.format("Encrypted String: %s \nEncoded IV Key: %s \nSalt: %s \nKey Size: %s", encodeEncryptedString, viString
        ,salt, keySize);

        encrpyedTextArea.setText(outputInfo);

        passwordField.setText(encodedString);


        passwordLabel.setText("Secret key has been generated");

        showPasswordRadioButton.setText("Show secret key");

    }
    public void decrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException{



        System.out.println(salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");


        String oldKey = new String(passwordField.getPassword());

        byte [] decodedKey = Base64.getDecoder().decode(oldKey);

        SecretKey originalKey = new SecretKeySpec(decodedKey, 0 , decodedKey.length, "AES");


        byte[] oldIV = Base64.getDecoder().decode("UZ6M9a0ZtR8x21/FidlQ6w");

        cipher.init(Cipher.DECRYPT_MODE,originalKey, new IvParameterSpec(oldIV));

        String oldencryptedString = stringToEncryptTextArea.getText();


        String plaintext = new String(cipher.doFinal(Base64.getDecoder().decode(oldencryptedString)), "UTF-8");

        encrpyedTextArea.setText(new String(plaintext));





    }



}
