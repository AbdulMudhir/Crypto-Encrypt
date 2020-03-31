import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
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
    private JScrollPane stringToEncryptScrollPane;
    private JScrollPane encryptedScrollPane;
    private  Cipher cipher;
    private SecretKey key;
    byte[] iv;


    private byte[] salt;





    public void setupWindow() throws NoSuchPaddingException, NoSuchAlgorithmException {

        JFrame frame = new JFrame("Crypto UI");

        frame.setFocusable(true);

        frame.getContentPane().add(mainPanel);

        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        passwordField.putClientProperty("JPasswordField.cutCopyAllowed",true);


        stringToEncryptTextArea.addKeyListener(new KeyListener() {

                                                   @Override
                                                   public void keyTyped(KeyEvent e) {

                                                   }

                                                   @Override
                                                   public void keyPressed(KeyEvent e) {

                                                   }

                                                   @Override
                                                   public void keyReleased(KeyEvent e) {
                                                       if (decryptRadioButton.isSelected()) {

                                                           try {
                                                               decrypt();
                                                           }  catch (NoSuchAlgorithmException ex) {
                                                               ex.printStackTrace();
                                                           } catch (InvalidAlgorithmParameterException ex) {
                                                               ex.printStackTrace();
                                                           } catch (InvalidKeyException ex) {
                                                               ex.printStackTrace();
                                                           } catch (UnsupportedEncodingException ex) {
                                                               ex.printStackTrace();
                                                           } catch (BadPaddingException ex) {
                                                               encrpyedTextArea.setText("Invalid Password");
                                                           } catch (IllegalBlockSizeException ex) {
                                                               ex.printStackTrace();
                                                           } catch (InvalidKeySpecException ex) {
                                                               ex.printStackTrace();
                                                           }

                                                       } else {


                                                           try {

                                                               if (stringToEncryptTextArea.getText().length() > 0)
                                                                   encryptString();

                                                           } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException | InvalidParameterSpecException ex) {
                                                               ex.printStackTrace();
                                                           }


                                                       }

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

                                             if (!decryptRadioButton.isSelected() && stringToEncryptTextArea.getText().length() > 0) {

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


                                                 else{
                                                     if (stringToEncryptTextArea.getText().length() > 0){

                                                         System.out.println("i am here");
                                                         try {
                                                             decrypt();
                                                         }  catch (NoSuchAlgorithmException ex) {
                                                             ex.printStackTrace();
                                                         } catch (InvalidAlgorithmParameterException ex) {
                                                             ex.printStackTrace();
                                                         } catch (InvalidKeyException ex) {
                                                             ex.printStackTrace();
                                                         } catch (UnsupportedEncodingException ex) {
                                                             ex.printStackTrace();
                                                         } catch (BadPaddingException ex) {
                                                             encrpyedTextArea.setText("Invalid Password");
                                                         } catch (IllegalBlockSizeException ex) {
                                                             ex.printStackTrace();
                                                         } catch (InvalidKeySpecException ex) {
                                                             ex.printStackTrace();
                                                         }

                                                     }


                                                 }



                                     }});





        frame.setSize(600,600);

        frame.setVisible(true);


    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == decryptRadioButton && decryptRadioButton.isSelected()) {

            stringToEncryptLabel.setText("Encrypted Message To Decrypt");
            encryptedLabel.setText("Decrypted");

        } else {
            stringToEncryptLabel.setText("String To Encrypt");
            encryptedLabel.setText("Encrypted");
            passwordLabel.setText("Password");

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
        salt = new byte[8];

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        KeySpec spec = new PBEKeySpec(passwordField.getPassword(), salt, 65536, keySize);

        SecretKey tmp = factory.generateSecret(spec);

        key = new SecretKeySpec(tmp.getEncoded(), "AES");



        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte [] dataByte = stringToEncryptTextArea.getText().getBytes("UTF-8");

        AlgorithmParameters params = cipher.getParameters();

        iv = params.getParameterSpec(IvParameterSpec.class).getIV();

        String viString = Base64.getEncoder().withoutPadding().encodeToString(iv);


        byte[] ciphertext = cipher.doFinal(dataByte);


        String encodedKey = Base64.getEncoder().withoutPadding().encodeToString(key.getEncoded());

        String encodedEncryptedString = Base64.getEncoder().withoutPadding().encodeToString(ciphertext);

        //String encodedSalt = Base64.getEncoder().withoutPadding().encodeToString(salt);
        String outputInfo = String.format("Encrypted Key: %s \nEncrypted String: %s#%s \nEncoded IV : %s \nSalt: %s \nKey Size: %s",encodedKey,encodedEncryptedString,viString , viString
        , salt, keySize);

        encrpyedTextArea.setText(outputInfo);

    }
    public void decrypt() throws  NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {


        int keySize = 256;
        salt = new byte[8];

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        KeySpec spec = new PBEKeySpec(passwordField.getPassword(), salt, 65536, keySize);

        SecretKey tmp = factory.generateSecret(spec);

        key = new SecretKeySpec(tmp.getEncoded(), "AES");


        String [] encryptedString = stringToEncryptTextArea.getText().strip().split("#");


        //System.out.println(encryptedString[0]+ encryptedString[1]);



        // for debugging
//        String encodedKey = Base64.getEncoder().withoutPadding().encodeToString(key.getEncoded());
//        System.out.println(encodedKey);


//        String originalKeyBase64String = new String(secretKey[0]);
//
//        byte [] decodedKey = Base64.getDecoder().decode(originalKeyBase64String);
//
//        SecretKey originalKey = new SecretKeySpec(decodedKey, 0 , decodedKey.length, "AES");

        try{

            byte[] iv = Base64.getDecoder().decode(encryptedString[1]);



            cipher.init(Cipher.DECRYPT_MODE,key, new IvParameterSpec(iv));


            String plaintext = new String(cipher.doFinal(Base64.getDecoder().decode(encryptedString[0])), "UTF-8");

            encrpyedTextArea.setText(new String(plaintext));}
            catch(ArrayIndexOutOfBoundsException e){

                encrpyedTextArea.setText("Invalid Encryption");

            }





    }



}
