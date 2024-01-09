package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
public class Login {
    private final int LOGIN_ATTEMPTS_LIMIT=5;
    private int LOGIN_ATTEMPTED_TRIES=0;
    private final int LOGIN_STARTING_TRIES=0;
    private String LOGIN_ATTEMPTED_USERNAME="";
    private boolean hasReachedLimitOfTries=false;
    private List<Component> defaultDisplayedComponents =new ArrayList<>();
    private List<Component> loginComponents=new ArrayList<>();
    private List<Component> signupComponents=new ArrayList<>();
    public Login(JFrame frame){
        frame.setTitle("registration phase");
        frame.setSize(Constants.frameWidth, Constants.frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        Dimension frameSize= frame.getSize();
        Dimension labelSize;

        JLabel greetingLabel=new JLabel(Utility.getGreeting());
        greetingLabel.setBounds(20,80,200,30);
        frame.add(greetingLabel);
        JLabel formattedTimeLabel=new JLabel(Utility.getFormattedTime());
        formattedTimeLabel.setBounds(Constants.frameWidth-120,5,150,30);

        JLabel zevaxLabel=new JLabel("ZEVAX");
        zevaxLabel.setBounds(20,30,100,60);
        zevaxLabel.setFont(new Font("Ariel",Font.BOLD, 24));

        JButton loginButton=new JButton("Login");
        loginButton.setBounds(20,150,200,30);

        JButton signupButton=new JButton("Sign Up");
        signupButton.setBounds(loginButton.getX(),loginButton.getY()+loginButton.getHeight()+10,loginButton.getWidth(),loginButton.getHeight());

        JButton settingsButton=new JButton("Settings");
        settingsButton.setBounds(signupButton.getX(),signupButton.getY()+signupButton.getHeight()+10,signupButton.getWidth(),signupButton.getHeight());

        JButton continueButton=new JButton("Continue");
        continueButton.setBounds(loginButton.getBounds());
        continueButton.setVisible(false);
        frame.add(continueButton);

        //List<Component> defaultDisplayedComponents=new ArrayList<>();
        defaultDisplayedComponents.add(loginButton);
        defaultDisplayedComponents.add(signupButton);
        defaultDisplayedComponents.add(zevaxLabel);
        defaultDisplayedComponents.add(greetingLabel);
        defaultDisplayedComponents.add(formattedTimeLabel);
        defaultDisplayedComponents.add(settingsButton);
        for (Component component:defaultDisplayedComponents){frame.add(component);}

        JTextArea promptUserName=new JTextArea("username: ");
        promptUserName.setBounds(20,250,80,30);
        promptUserName.setEditable(false);

        JTextArea promptPassword=new JTextArea("password: ");
        promptPassword.setBounds(promptUserName.getX(),promptUserName.getY()+promptUserName.getHeight()+20,promptUserName.getWidth(),promptUserName.getHeight());
        promptPassword.setEditable(false);

        JTextField usernameTextField=new JTextField();// נשים דוגמא לשם משתמש שתיעלם ברגע שהמשתמש יגע בשדה
        usernameTextField.setBounds(promptUserName.getX()+promptUserName.getWidth(),promptUserName.getY(),250,promptUserName.getHeight());
        usernameTextField.setEditable(true);

        JTextField passwordTextField=new JTextField(); // נשים דוגמא לסיסמה שתיעלם ברגע שהמשתמש יגע בשדה
        passwordTextField.setBounds(promptPassword.getX()+promptPassword.getWidth(),promptPassword.getY(),250,promptPassword.getHeight());
        passwordTextField.setEditable(true);

        JLabel loginErrorLabel=new JLabel("either the password or username are incorrect.");
        labelSize=loginErrorLabel.getPreferredSize();
        loginErrorLabel.setBounds((frameSize.width - labelSize.width) / 2, -200+(frameSize.height - labelSize.height) / 2, labelSize.width, labelSize.height);

        JButton loginEnterButton=new JButton("ENTER");
        loginEnterButton.setBounds(100,450,200,60);
        loginEnterButton.setFont(new Font("Ariel",Font.PLAIN,30));

        CustomSwingComponents loginGoBackButton = new CustomSwingComponents("go back");
        loginGoBackButton.setBounds(loginEnterButton.getX()+loginEnterButton.getWidth()/4,loginEnterButton.getY()+loginEnterButton.getHeight()+5,100,20);

        //List<Component> loginComponents=new ArrayList<>();
        loginComponents.add(promptUserName);
        loginComponents.add(promptPassword);
        loginComponents.add(usernameTextField);
        loginComponents.add(passwordTextField);
        loginComponents.add(loginEnterButton);
        loginComponents.add(loginGoBackButton);
        loginComponents.add(loginErrorLabel);
        for(Component component:loginComponents){component.setVisible(false);frame.add(component);}


        loginButton.addActionListener(e -> {
            hideDefaultDisplayComponents();
            showLoginComponents();
            usernameTextField.setText(null); // כנשסיים עם כל הבדיקות עם הכנסיה ויצירת משתמש אפשר להוריד
            passwordTextField.setText(null); //
            loginErrorLabel.setVisible(false);
        });

        loginEnterButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            if(username.isEmpty() && password.isEmpty()){
                loginErrorLabel.setText("both of the fields are empty!");
                loginErrorLabel.setVisible(true);
                return;
            }
            if(username.isEmpty() || password.isEmpty()){
                loginErrorLabel.setText("one of the fields is empty!");
                loginErrorLabel.setVisible(true);
                return;
            }
            boolean isConfirmed=UserManager.getInstance().confirmUser(username,password);

            if(!isConfirmed){
                if(UserManager.getInstance().checkIfUsernameAlreadyExists(username)){
                    if(!LOGIN_ATTEMPTED_USERNAME.matches(username)){
                        LOGIN_ATTEMPTED_USERNAME=username;
                        LOGIN_ATTEMPTED_TRIES=LOGIN_STARTING_TRIES;
                    }
                    LOGIN_ATTEMPTED_TRIES++;
                }

                if(LOGIN_ATTEMPTED_TRIES==LOGIN_ATTEMPTS_LIMIT){
                    loginErrorLabel.setText("you have reached the limit of tries to log in.");
                    hideLoginComponents();
                    Utility.toggleVisibilityOn(loginErrorLabel,Constants.defaultToggleVisibilityTimesSet);
                    defaultDisplayedComponents.remove(loginButton);
                    settingsButton.setBounds(signupButton.getBounds());
                    signupButton.setBounds(loginButton.getBounds());
                    toggleVisibility1(defaultDisplayedComponents);
                    hasReachedLimitOfTries=true;
                }else {
                    loginErrorLabel.setText("either the password or username are incorrect.");
                    loginErrorLabel.setVisible(true);
                    Utility.toggleVisibilityOff(loginErrorLabel,200);

                }

            }else {
                settingsButton.setBounds(signupButton.getBounds());
                hideLoginComponents();
                defaultDisplayedComponents.remove(signupButton);
                defaultDisplayedComponents.remove(loginButton);
                defaultDisplayedComponents.add(continueButton);
                showDefaultDisplayComponents();
                loginErrorLabel.setVisible(false);
            }


        });



        loginGoBackButton.addActionListener(e -> {
            hideLoginComponents();
            showDefaultDisplayComponents();
        });


        //JLabel signupUsernameGuideLabel=new JLabel("Username Guide: ");
        JLabel signupUsernameGuideLabel1=new JLabel("<html>-the user name cannot consist of less than 3 or more<br> than 10 letters.<html>");
        JLabel signupUsernameGuideLabel2=new JLabel("-the user name cannot contain any 'space' bars.");

        //JLabel signupPasswordGuideLabel=new JLabel("Password Guide: ");
        JLabel signupPasswordGuideLabel1=new JLabel("<html>-the password must contain at least one of both<br> Capital and lower letter.<html>");
        JLabel signupPasswordGuideLabel2=new JLabel("<html>-the password cannot consist of less than 8 or more<br> than 20 characters.<html>");

        JButton signupEnterButton=new JButton("ENTER");
        signupEnterButton.setBounds(loginEnterButton.getX(),loginEnterButton.getY(),loginEnterButton.getWidth(),loginEnterButton.getHeight());

        CustomSwingComponents signupGoBackButton=new CustomSwingComponents(loginGoBackButton.getText());
        signupGoBackButton.setBounds(loginGoBackButton.getX(),loginGoBackButton.getY(),loginGoBackButton.getWidth(),loginGoBackButton.getHeight());

        //List<Component> signupComponents=new ArrayList<>();
        signupComponents.add(signupUsernameGuideLabel1);signupComponents.add(signupUsernameGuideLabel2);
        signupComponents.add(signupPasswordGuideLabel1);signupComponents.add(signupPasswordGuideLabel2);
        signupComponents.add(signupEnterButton);signupComponents.add(signupGoBackButton);
        for (Component component:signupComponents){component.setVisible(false);frame.add(component);}
        signupComponents.add(usernameTextField);signupComponents.add(passwordTextField);
        signupComponents.add(promptUserName);signupComponents.add(promptPassword);

        signupUsernameGuideLabel1.setBounds(30,30,300,40);
        signupUsernameGuideLabel2.setBounds(signupUsernameGuideLabel1.getX(),signupUsernameGuideLabel1.getY()+signupUsernameGuideLabel1.getHeight(),signupUsernameGuideLabel1.getWidth(),signupUsernameGuideLabel1.getHeight());

        signupPasswordGuideLabel1.setBounds(signupUsernameGuideLabel2.getX(),signupUsernameGuideLabel2.getY()+signupUsernameGuideLabel2.getHeight()+20,signupUsernameGuideLabel2.getWidth(),signupUsernameGuideLabel2.getHeight());
        signupPasswordGuideLabel2.setBounds(signupPasswordGuideLabel1.getX(),signupPasswordGuideLabel1.getY()+signupPasswordGuideLabel1.getHeight()+5,signupPasswordGuideLabel1.getWidth(),signupPasswordGuideLabel1.getHeight());

        JLabel signupSuccessfulLabel=new JLabel("sign up successful!");
        signupSuccessfulLabel.setFont(new Font("Ariel",Font.BOLD,24));
        signupSuccessfulLabel.setVisible(false);
        frame.add(signupSuccessfulLabel);

        labelSize = signupSuccessfulLabel.getPreferredSize();
        signupSuccessfulLabel.setBounds((frameSize.width - labelSize.width) / 2, -200+(frameSize.height - labelSize.height) / 2, labelSize.width, labelSize.height);



        signupButton.addActionListener(e -> {
            hideDefaultDisplayComponents();
            showSignupComponents();
            usernameTextField.setText(null); // כנשסיים עם כל הבדיקות עם הכנסיה ויצירת משתמש אפשר להוריד
            passwordTextField.setText(null); //
        });

        signupEnterButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            int usernameErrorCode = validateUsername(username);
            int passwordErrorCode = validatePassword(password,username);

            if(usernameErrorCode==1 || usernameErrorCode==2){
                String originalText1=signupUsernameGuideLabel1.getText();
                String originalText2=signupUsernameGuideLabel2.getText();
                signupUsernameGuideLabel2.setText(null);
                if (usernameErrorCode == 1) {
                    signupUsernameGuideLabel1.setText("<html>*make sure you followed the guide<br> regarding the user name.<html>");
                } else {
                    signupUsernameGuideLabel1.setText("<html>*it appears this user name is already taken.<br> please choose another.<html>");
                }
                toggleVisibility(signupUsernameGuideLabel1,signupUsernameGuideLabel2,originalText1,originalText2);
            }

            if(passwordErrorCode!=0){
                String originalText1=signupPasswordGuideLabel1.getText();
                String originalText2=signupPasswordGuideLabel2.getText();
                signupPasswordGuideLabel2.setText(null);
                if(passwordErrorCode==1){signupPasswordGuideLabel1.setText("<html>*the password is not standing up to the capacity<br> of characters mentioned in the guide.<html>");
                }else if (passwordErrorCode==2){signupPasswordGuideLabel1.setText("<html>*the password doesnt contain one of<br> both Capital and lower letter.<html>");
                } else if (passwordErrorCode==3) {signupPasswordGuideLabel1.setText("<html>*the password must contain at least 3 digits.");
                }else if (passwordErrorCode==4){signupPasswordGuideLabel1.setText("<html>*the password cant contain the same character<br> 3 times in a row.");
                }else if (passwordErrorCode==5){signupPasswordGuideLabel1.setText("*the password cannot contain your username!");
                }else {signupPasswordGuideLabel1.setText("<html>*the password cannot contain more than 2<br> following numbers in ascending or descending order.<html>");
                }
                toggleVisibility(signupPasswordGuideLabel1,signupPasswordGuideLabel2,originalText1,originalText2);
            }

            if (usernameErrorCode == 0 && passwordErrorCode == 0) {
                hideSignupComponents();
                UserManager.getInstance().addUser(username,password);
                Utility.toggleVisibilityOn(signupSuccessfulLabel,Constants.defaultToggleVisibilityTimesSet);
                defaultDisplayedComponents.remove(signupButton);
                defaultDisplayedComponents.remove(loginButton);
                defaultDisplayedComponents.add(continueButton);
                if(!hasReachedLimitOfTries){settingsButton.setBounds(signupButton.getBounds());}
                continueButton.setBounds(loginButton.getBounds());
                toggleVisibility1(defaultDisplayedComponents);
                //hasReachedLimitOfTries=false;
            }

        });

        signupGoBackButton.addActionListener(e -> {hideSignupComponents();showDefaultDisplayComponents();});

        continueButton.addActionListener(e -> {new ConnectionTest(frame);});

        KeyAdapter usernameAndPasswordKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (loginEnterButton.isVisible()) {loginEnterButton.doClick();}
                    if (signupEnterButton.isVisible()) {signupEnterButton.doClick();}
                }
            }
        };

        usernameTextField.addKeyListener(usernameAndPasswordKeyAdapter);
        passwordTextField.addKeyListener(usernameAndPasswordKeyAdapter);

        KeyAdapter goBackButtonsKeyAdapter=new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (loginGoBackButton.isVisible()) {loginGoBackButton.doClick();}
                    if (signupGoBackButton.isVisible()) {signupGoBackButton.doClick();}
                }
            }
        };
        frame.setFocusable(true);
        usernameTextField.addKeyListener(goBackButtonsKeyAdapter);
        passwordTextField.addKeyListener(goBackButtonsKeyAdapter);
        frame.addKeyListener(goBackButtonsKeyAdapter);

        frame.setVisible(true);
    }
    private int validateUsername(String username) {
        if (username.isEmpty()) return 1;
        if (username.length() < 3 || username.length() > 10) return 1;
        if (username.contains(" ")) return 1;
        if(UserManager.getInstance().checkIfUsernameAlreadyExists(username)) return 2;
        return 0;
    }

    private int validatePassword(String password,String username) {
        if (password.isEmpty()) return 1;
        if (password.length() < 8 || password.length() > 20) return 1;
        if (!password.chars().anyMatch(Character::isUpperCase) || !password.chars().anyMatch(Character::isLowerCase)) return 2;
        long countDigits = password.chars().filter(Character::isDigit).count();
        if (countDigits < 3) return 3;
        char lastChar = password.charAt(0);
        int repeatCount = 1;
        for (int i = 1; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            if (currentChar == lastChar) {
                repeatCount++;
                if (repeatCount > 3) return 4;
            } else {
                repeatCount = 1;
                lastChar = currentChar;
            }
        }
        if (!username.isEmpty() && password.toLowerCase().contains(username.toLowerCase())){
            return 5;
        }
        for (int i = 0; i < password.length() - 2; i++) {
            if (Character.isDigit(password.charAt(i)) &&
                    Character.isDigit(password.charAt(i + 1)) &&
                    Character.isDigit(password.charAt(i + 2))) {
                int num1 = Character.getNumericValue(password.charAt(i));
                int num2 = Character.getNumericValue(password.charAt(i + 1));
                int num3 = Character.getNumericValue(password.charAt(i + 2));
                if (num2 == num1 + 1 && num3 == num2 + 1) {
                    return 6;  // Ascending sequence
                } else if (num1 == num2 + 1 && num2 == num3 + 1) {
                    return 6;  // Descending sequence
                }
            }
        }
        return 0;
    }
    private void hideDefaultDisplayComponents(){defaultDisplayedComponents.forEach(component -> component.setVisible(false));}
    private void hideLoginComponents(){loginComponents.forEach(component -> component.setVisible(false));}
    private void hideSignupComponents(){signupComponents.forEach(component -> component.setVisible(false));}
    private void showDefaultDisplayComponents(){defaultDisplayedComponents.forEach(component -> component.setVisible(true));}
    private void showLoginComponents(){loginComponents.forEach(component -> component.setVisible(true));}
    private void showSignupComponents(){signupComponents.forEach(component -> component.setVisible(true));}
    private void toggleVisibility(JLabel label1,JLabel label2,String originalText1, String originalText2){
        Timer usernameTimer = new Timer(8000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label1.setText(originalText1);
                label2.setText(originalText2);
            }
        });
        usernameTimer.setRepeats(false);
        usernameTimer.start();
    }
    private void toggleVisibility1(List<Component> components){

        Timer timer = new Timer(8000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Component component : components) {
                    component.setVisible(true);
                }
            }
        });
        timer.setRepeats(false);
        timer.start();

        for (Component component : components) {
            component.setVisible(false);
        }
    }



}