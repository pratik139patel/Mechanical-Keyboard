import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JSlider;
import javax.swing.JComboBox;

public class MechanicalKeyboard implements KeyListener 
{
    private static JButton[] keyboardBtns;
    private static JTextField writingSpace;
    private static JFrame mainFrame;
    private static JButton clearBtn;
    private static JButton resetBtn;
    private static JMenuBar mainMenuBar;
    private static JMenuItem colorMenu;
    private static JMenuItem soundProfile;
    private static JMenu settMenu;
    private static Clip keyPressClip;
    private static Clip keyReleaseClip;
    private static AudioInputStream keyPressaudioInputStream;
    private static AudioInputStream keyReleaseaudioInputStream;
    private static GridBagConstraints constr;
    private static Dimension btnSize;
    private boolean flag;
    private int key1;
    private int key2;
    private int code;
    private String typedString;

    private final int[] keyCodes = { KeyEvent.VK_ESCAPE, KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4,
            KeyEvent.VK_F5, KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10,
            KeyEvent.VK_F11, KeyEvent.VK_F12, KeyEvent.VK_PRINTSCREEN, KeyEvent.VK_SCROLL_LOCK, KeyEvent.VK_PAUSE, 192,
            KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7,
            KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0, KeyEvent.VK_MINUS, KeyEvent.VK_EQUALS, KeyEvent.VK_BACK_SPACE,
            KeyEvent.VK_INSERT, KeyEvent.VK_HOME, KeyEvent.VK_PAGE_UP, KeyEvent.VK_NUM_LOCK, KeyEvent.VK_DIVIDE, KeyEvent.VK_MULTIPLY,
            KeyEvent.VK_SUBTRACT, KeyEvent.VK_TAB, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T,
            KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_OPEN_BRACKET,
            KeyEvent.VK_CLOSE_BRACKET, KeyEvent.VK_BACK_SLASH, KeyEvent.VK_DELETE, KeyEvent.VK_END,
            KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9, KeyEvent.VK_ADD,
            KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G,
            KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SEMICOLON, KeyEvent.VK_QUOTE,
            KeyEvent.VK_ENTER, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6, KeyEvent.VK_SHIFT,
            KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M,
            KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD, KeyEvent.VK_SLASH, KeyEvent.VK_SHIFT, KeyEvent.VK_UP,
            KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_ENTER, KeyEvent.VK_CONTROL,
            KeyEvent.VK_WINDOWS, KeyEvent.VK_ALT, KeyEvent.VK_SPACE, KeyEvent.VK_ALT, KeyEvent.VK_ACCEPT,
            KeyEvent.VK_CONTEXT_MENU, KeyEvent.VK_CONTROL, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT,
            KeyEvent.VK_NUMPAD0, KeyEvent.VK_DECIMAL };

    private static final String[] keyboardLetters = { "ESC", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9",
            "F10", "F11", "F12", "PRINT", "SCRLK", "PAUSE", "` ~", "1 !", "2 @", "3 #", "4 $", "5 %", "6 ^", "7 &", "8 *", "9 (",
            "0 )", "- _", "= +", "BACK", "INS", "HOME", "PGUP", "NUMLK", "/", "*", "-", "TAB", "Q", "W", "E", "R", "T",
            "Y", "U", "I", "O", "P", "[ {", "] }", "\\ |", "DEL", "END", "PGDN", "7", "8", "9", "+", "CAPS", "A", "S", "D",
            "F", "G", "H", "J", "K", "L", "; :", "' \"", "ENTER", "4", "5", "6", "SHIFT", "Z", "X", "C", "V", "B", "N",
            "M", ", <", ". >", "/ ?", "SHIFT", "UP", "1", "2", "3", "ENTER", "CTRL", "WIN", "ALT", "SPACE", "ALT", "FN",
            "OPT", "CTRL", "LEFT", "DOWN", "RIGHT", "0", "." };

    // JMenuBar, JMenu, JMenuItem, JFrame, JButton, JTextField, JButton
    private static final Color[] defaultColorScheme = { (new JMenuBar()).getBackground(), (new JMenu()).getForeground(),
            (new JMenuItem()).getBackground(), (new JFrame()).getBackground(), (new JButton()).getBackground(),
            (new JTextField()).getBackground(), (new JButton()).getBackground(), Color.RED, Color.GREEN };

    private static final String[] userColorSchemeDescription = { "Menu Bar Color", "Menu Option Color",
            "Option Item Color", "Frame Color", "Keyboard Button Color", "Writing Space Color",
            "System button Color", "Key Press Color", "Key Release Color", "Key Press Choice", "Key Release Choice" };

    private static Color[] userColorScheme;
    private static final String[] keyPressFilePaths = { "KeySounds/CherryBlueKeyPress.wav", "KeySounds/CherryBrownKeyPress.wav", "KeySounds/CherryBlueKeyRelease.wav", "KeySounds/CherryBrownKeyRelease.wav" };
    private static final String[] keyReleaseFilePaths = { "KeySounds/CherryBlueKeyRelease.wav", "KeySounds/CherryBrownKeyRelease.wav", "KeySounds/CherryBlueKeyPress.wav", "KeySounds/CherryBrownKeyPress.wav" };
    private static String keyPressCustomFilePath;
    private static String keyReleaseCustomFilePath;
    private static int userKeyPressSoundChoice;
    private static int userKeyReleaseSoundChoice;


    public static void main(String[] Args) 
    {
        //Load saved user data and initialize key press sounds with previous user selection
        loadSavedColorScheme();
        
        //Don't produce sound if the user selection is -1
        if(userKeyPressSoundChoice != -1) { initializeKeyPressSound(); }
        if(userKeyReleaseSoundChoice != -1) { initializeKeyReleaseSound(); }

        //Initialize frame and its components
        mainFrame = new JFrame("Mechanical Keyboard");
        if (userColorScheme[3].getRGB() != -1118482) { mainFrame.getContentPane().setBackground(userColorScheme[3]); }
        keyboardBtns = new JButton[104];
        mainFrame.setLayout(new GridBagLayout());
        constr = new GridBagConstraints();
        constr.insets = new Insets(5, 5, 5, 5);
        btnSize = new Dimension(75, 30);

        //Add keyboard buttons, typing space, and other application buttons
        createKeyboard();
        createWritingSpace();
        createResetAndClearBtns();
        createMenuBar();

        //Launch the frame
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }


    //Generates and add menu bar to main frame
    private static void createMenuBar() 
    {
        //Create menu bar and add related components
        mainMenuBar = new JMenuBar();
        if (userColorScheme[0].getRGB() != -1118482) { mainMenuBar.setBackground(userColorScheme[0]); }
        settMenu = new JMenu("Settings");
        settMenu.setForeground(userColorScheme[1]);
        colorMenu = new JMenuItem("Color Change");
        if (userColorScheme[2].getRGB() != -1118482) { colorMenu.setBackground(userColorScheme[2]); }
        colorMenu.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { changeColorMenu(); } });
        soundProfile = new JMenuItem("Switch Sound");
        if (userColorScheme[2].getRGB() != -1118482) { soundProfile.setBackground(userColorScheme[2]); }
        soundProfile.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { changeSoundProfileMenu(); } });

        //Add menu item to menu
        settMenu.add(colorMenu);
        settMenu.add(soundProfile);
        //Add menu to menu bar
        mainMenuBar.add(settMenu);
        mainMenuBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "none");
        mainFrame.setJMenuBar(mainMenuBar);
    }


    //Creates reset and clear button for the main frame
    private static void createResetAndClearBtns() 
    {
        //Initialize clear button and add to main frame
        constr.gridwidth = 1;
        constr.gridx = 19;
        constr.gridy = 7;
        constr.insets = new Insets(25, 5, 5, 5);
        clearBtn = new JButton("CLEAR");
        if (userColorScheme[6].getRGB() != -1118482) { clearBtn.setBackground(userColorScheme[6]); }
        clearBtn.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { writingSpace.setText(""); } });
        clearBtn.addKeyListener(new MechanicalKeyboard());
        clearBtn.setPreferredSize(btnSize);
        clearBtn.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        mainFrame.add(clearBtn, constr);

        //Initialize reset button and add to main frame
        constr.gridx = 20;
        resetBtn = new JButton("RESET");
        if (userColorScheme[6].getRGB() != -1118482) { resetBtn.setBackground(userColorScheme[6]); }
        resetBtn.addActionListener(new ActionListener() 
        { 
            @Override public void actionPerformed(ActionEvent e) 
            {
                clearBtn.doClick();
                for (int i = 0; i < keyboardBtns.length; ++i) { keyboardBtns[i].setBackground(resetBtn.getBackground()); }
            }
        });
        resetBtn.addKeyListener(new MechanicalKeyboard());
        resetBtn.setPreferredSize(btnSize);
        resetBtn.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        mainFrame.add(resetBtn, constr);
    }


    //Creates typing bar for the main frame
    private static void createWritingSpace() 
    {
        //Initialize writing space and add it to main frame
        writingSpace = new JTextField();
        constr.gridheight = 1;
        constr.gridwidth = 20;
        constr.gridx = 0;
        constr.gridy = 7;
        constr.insets = new Insets(25, 5, 5, 5);
        if (userColorScheme[5].getRGB() != -1118482) { writingSpace.setBackground(userColorScheme[5]); }
        writingSpace.setPreferredSize(new Dimension(btnSize.width * constr.gridwidth, btnSize.height));
        writingSpace.addFocusListener(new FocusListener() 
        { 
            @Override public void focusGained(FocusEvent e) { writingSpace.selectAll(); }
            @Override public void focusLost(FocusEvent e) {}
        });
        mainFrame.add(writingSpace, constr);
    }


    //Creates and adds keyboard buttons to main frame
    private static void createKeyboard() 
    {
        boolean colorFlag = (userColorScheme[4].getRGB() != -1118482);
        for (int i = 0; i < keyboardBtns.length; ++i) 
        {
            keyboardBtns[i] = new JButton(keyboardLetters[i]);
            keyboardBtns[i].setPreferredSize(btnSize);
            keyboardBtns[i].addKeyListener(new MechanicalKeyboard());
            keyboardBtns[i].getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
            if (colorFlag) { keyboardBtns[i].setBackground(userColorScheme[4]); }

            //Adds first row of keys
            if (i < 16) 
            {
                constr.gridx = i;

                //If it is not 'ESC' key
                if (i != 0) 
                {
                    constr.gridx += 1;

                    //If it is 'PRINT SCREEN' key
                    if (i == 13) 
                    {
                        constr.insets = new Insets(5, 20, 5, 5);
                        mainFrame.add(keyboardBtns[i], constr);
                        constr.insets = new Insets(5, 5, 5, 5);
                        continue;
                    }
                    //If it is 'PAUSE BREAK' key
                    else if (i == 15) 
                    {
                        constr.insets = new Insets(5, 5, 5, 20);
                        mainFrame.add(keyboardBtns[i], constr);
                        constr.insets = new Insets(5, 5, 5, 5);
                        continue;
                    }
                }
                mainFrame.add(keyboardBtns[i], constr);
            }

            //Adds second row of keys
            else if (i < 37) 
            {
                constr.gridx = i - 16;
                constr.gridy = 1;

                //If it is 'INS' key
                if (i == 30) 
                {
                    constr.insets = new Insets(5, 20, 5, 5);
                    mainFrame.add(keyboardBtns[i], constr);
                    constr.insets = new Insets(5, 5, 5, 5);
                    continue;
                } 
                //If it is 'PGUP' key
                else if (i == 32) 
                {
                    constr.insets = new Insets(5, 5, 5, 20);
                    mainFrame.add(keyboardBtns[i], constr);
                    constr.insets = new Insets(5, 5, 5, 5);
                    continue;
                }
                mainFrame.add(keyboardBtns[i], constr);
            }

            //Adds third row of keys
            else if (i < 58) 
            {
                constr.gridy = 2;
                constr.gridx = i - 37;

                //If it is 'DEL' key
                if (i == 51) 
                {
                    constr.insets = new Insets(5, 20, 5, 5);
                    mainFrame.add(keyboardBtns[i], constr);
                    constr.insets = new Insets(5, 5, 5, 5);
                    continue;
                }
                //If it is 'PGDN' key
                else if (i == 53) 
                {
                    constr.insets = new Insets(5, 5, 5, 20);
                    mainFrame.add(keyboardBtns[i], constr);
                    constr.insets = new Insets(5, 5, 5, 5);
                    continue;
                }
                //If it is numpad 'ENTER' key
                else if (i == 57) 
                {
                    constr.gridheight = 2;
                    keyboardBtns[i].setPreferredSize(new Dimension(btnSize.width, btnSize.height * 2 + 10));
                    mainFrame.add(keyboardBtns[i], constr);
                    constr.gridheight = 1;
                    continue;
                }
                mainFrame.add(keyboardBtns[i], constr);
            }

            //Adds fourth row of keys
            else if (i < 74) 
            {
                constr.gridy = 3;
                constr.gridx = i - 58;

                //If it is 'ENTER' key
                if (i == 70) 
                {
                    constr.gridwidth = 2;
                    keyboardBtns[i].setPreferredSize(new Dimension(btnSize.width * 2 + 10, btnSize.height));
                    mainFrame.add(keyboardBtns[i], constr);
                    constr.gridwidth = 1;
                    continue;
                }
                //If it is numpad keys
                else if (i > 70)
                {
                    constr.gridx += 4;
                }
                mainFrame.add(keyboardBtns[i], constr);
            } 
            
            //Adds fifth row of keys
            else if (i < 91) 
            {
                constr.gridy = 4;
                constr.gridx = i - 73;
                
                //If it is numpad key
                if (i > 86) 
                {
                    constr.gridx += 3;
                    if (i == 90) 
                    {
                        constr.gridheight = 2;
                        keyboardBtns[i].setPreferredSize(new Dimension(btnSize.width, btnSize.height * 2 + 10));
                        mainFrame.add(keyboardBtns[i], constr);
                        constr.gridwidth = 1;
                        continue;
                    }
                }
                //If it is left 'SHIFT' key
                else if (i == 74) 
                {
                    constr.gridx -= 1;
                    constr.gridwidth = 2;
                    keyboardBtns[i].setPreferredSize(new Dimension(btnSize.width * 2 + 10, btnSize.height));
                    mainFrame.add(keyboardBtns[i], constr);
                    constr.gridwidth = 1;
                    continue;
                }
                //If it is right 'SHIFT' key
                else if (i == 85) 
                {
                    constr.gridwidth = 2;
                    keyboardBtns[i].setPreferredSize(new Dimension(btnSize.width * 2 + 10, btnSize.height));
                    mainFrame.add(keyboardBtns[i], constr);
                    constr.gridwidth = 1;
                    continue;
                }
                //If it is 'UP ARROW' key
                else if (i == 86) 
                {
                    constr.gridx += 2;
                }
                mainFrame.add(keyboardBtns[i], constr);
            } 
            
            //Adds sixth row of keys
            else 
            {
                constr.gridy = 5;
                constr.gridx = i - 91;
                
                //If it is 'SPACE' key
                if (i == 94) 
                {
                    constr.gridwidth = 7;
                    keyboardBtns[i].setPreferredSize(new Dimension(btnSize.width * 7 + 60, btnSize.height));
                    mainFrame.add(keyboardBtns[i], constr);
                    constr.gridwidth = 1;
                    continue;
                }
                //If it is a key after 'SPACE'
                else if (i > 94) 
                {
                    constr.gridx += 6;

                    //If it is 'LEFT ARROW' key
                    if (i == 99) 
                    {
                        constr.insets = new Insets(5, 20, 5, 5);
                        mainFrame.add(keyboardBtns[i], constr);
                        constr.insets = new Insets(5, 5, 5, 5);
                        continue;
                    }
                    //If it is 'RIGHT ARROW' key
                    else if (i == 101) 
                    {
                        constr.insets = new Insets(5, 5, 5, 20);
                        mainFrame.add(keyboardBtns[i], constr);
                        constr.insets = new Insets(5, 5, 5, 5);
                        continue;
                    }
                    //If it is numpad '0' key
                    else if (i == 102) 
                    {
                        constr.gridwidth = 2;
                        keyboardBtns[i].setPreferredSize(new Dimension(btnSize.width * 2 + 10, btnSize.height));
                        mainFrame.add(keyboardBtns[i], constr);
                        constr.gridwidth = 1;
                        continue;
                    }
                    //If it is numpad '.' key
                    else if (i == 103) 
                    {
                        constr.gridx += 1;
                    }
                }
                mainFrame.add(keyboardBtns[i], constr);
            }
        }
    }


    //Save current changes to the file
    private static void saveChangesToFile() 
    {
        //Create input file and initialize variables
        File outFile = new File("Settings.txt");
        BufferedWriter bw;
        FileWriter fw;

        try 
        {
            fw = new FileWriter(outFile);
            bw = new BufferedWriter(fw);
            int i;

            //Output color selection to the file
            for (i = 0; i < userColorScheme.length; ++i) 
            {
                bw.write(Integer.toString(userColorScheme[i].getRGB()) + " : " + userColorSchemeDescription[i]);
                bw.newLine();
            }

            //Output the key sound selection of the user to the file
            bw.write(Integer.toString(userKeyPressSoundChoice) + " : " + userColorSchemeDescription[i]);
            bw.newLine();
            if(userKeyPressSoundChoice == -2) 
            { 
                bw.write(keyPressCustomFilePath + " : Key Press Custom File Path"); 
                bw.newLine(); 
            }

            bw.write(Integer.toString(userKeyReleaseSoundChoice) + " : " + userColorSchemeDescription[i + 1]);
            if(userKeyReleaseSoundChoice == -2) 
            {
                bw.newLine(); 
                bw.write(keyReleaseCustomFilePath + " : Key Release Custom File Path"); 
            }


            bw.close();
            fw.close();
        } 
        catch (Exception ex) {}
    }


    //Loads the changes saved to the file
    private static void loadSavedColorScheme() 
    {
        //Create input file and initialize variables
        File inFile = new File("Settings.txt");
        userColorScheme = new Color[defaultColorScheme.length];
        for(int i = 0; i < defaultColorScheme.length; ++i) { userColorScheme[i] = new Color(defaultColorScheme[i].getRGB()); }
        userKeyPressSoundChoice = -1;
        userKeyReleaseSoundChoice = -1;

        try 
        {
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String fileLine;

            //Load all the saved color
            for (int i = 0; i < userColorScheme.length; ++i) 
            {
                fileLine = br.readLine().split(":")[0];
                userColorScheme[i] = new Color(Integer.parseInt(fileLine.substring(0, fileLine.length() - 1)));
            }

            //Load all the saved sound selection
            fileLine = br.readLine().split(":")[0];
            userKeyPressSoundChoice = Integer.parseInt(fileLine.substring(0, fileLine.length() - 1));
            if(userKeyPressSoundChoice == -2) 
            { 
                fileLine = br.readLine().split(":")[0]; 
                keyPressCustomFilePath = fileLine.substring(0, fileLine.length() - 1);
            }

            fileLine = br.readLine().split(":")[0];
            userKeyReleaseSoundChoice = Integer.parseInt(fileLine.substring(0, fileLine.length() - 1));
            if(userKeyReleaseSoundChoice == -2) 
            { 
                fileLine = br.readLine().split(":")[0]; 
                keyReleaseCustomFilePath = fileLine.substring(0, fileLine.length() - 1);
            }

            br.close();
            fr.close();

            //If the user selection of the sound is out of range of array, then throw exception
            if (userKeyPressSoundChoice >= keyPressFilePaths.length || userKeyReleaseSoundChoice >= keyReleaseFilePaths.length || userKeyPressSoundChoice < -2 || userKeyReleaseSoundChoice < -2) { throw new Exception(); }
        }
        //If anything fails, then create a new save file with default values of color and user selection
        catch (Exception ex) { saveChangesToFile(); }
    }


    //Allow the user to change element colors in main frame
    private static void changeColorMenu() 
    {
        //Initialize local variables
        JFrame colorMenuFrame = new JFrame("Change Color");
        JSlider redSlider = new JSlider(0, 255, userColorScheme[0].getRed());
        JSlider greenSlider = new JSlider(redSlider.getMinimum(), redSlider.getMaximum(), userColorScheme[0].getGreen());
        JSlider blueSlider = new JSlider(redSlider.getMinimum(), redSlider.getMaximum(), userColorScheme[0].getBlue());
        JTextField redField = new JTextField(Integer.toString(redSlider.getValue()));
        JTextField greenField = new JTextField(Integer.toString(greenSlider.getValue()));
        JTextField blueField = new JTextField(Integer.toString(blueSlider.getValue()));
        JTextField totalField = new JTextField();
        colorMenuFrame.setLayout(new GridBagLayout());

        //Initialize all 3 sliders's attributes
        redSlider.setOrientation(JSlider.VERTICAL);
        redSlider.setMajorTickSpacing(50);
        redSlider.setMinorTickSpacing(10);
        redSlider.setPaintTicks(true);
        redSlider.setPaintLabels(true);
        redSlider.setPreferredSize(new Dimension(redSlider.getPreferredSize().width + 20, redSlider.getPreferredSize().height+90));
        greenSlider.setOrientation(redSlider.getOrientation());
        greenSlider.setMajorTickSpacing(redSlider.getMajorTickSpacing());
        greenSlider.setMinorTickSpacing(redSlider.getMinorTickSpacing());
        greenSlider.setPaintTicks(redSlider.getPaintTicks());
        greenSlider.setPaintLabels(redSlider.getPaintLabels());
        greenSlider.setPreferredSize(redSlider.getPreferredSize());
        blueSlider.setOrientation(redSlider.getOrientation());
        blueSlider.setMajorTickSpacing(redSlider.getMajorTickSpacing());
        blueSlider.setMinorTickSpacing(redSlider.getMinorTickSpacing());
        blueSlider.setPaintTicks(redSlider.getPaintTicks());
        blueSlider.setPaintLabels(redSlider.getPaintLabels());
        blueSlider.setPreferredSize(redSlider.getPreferredSize());

        //Change text and color of redField when there is a change to redSlider
        redSlider.addChangeListener(new ChangeListener() 
        {
            @Override public void stateChanged(ChangeEvent e) 
            {
                redField.setText(Integer.toString(((JSlider)e.getSource()).getValue()));
                redField.setBackground(new Color(((JSlider)e.getSource()).getValue(),0,0));
                totalField.setBackground(new Color(redField.getBackground().getRed(), greenField.getBackground().getGreen(), blueField.getBackground().getBlue()));
            }
        });
        //Change text and color of greenField when there is a change to greenSlider
        greenSlider.addChangeListener(new ChangeListener() 
        {
            @Override public void stateChanged(ChangeEvent e) 
            {
                greenField.setText(Integer.toString(((JSlider)e.getSource()).getValue()));
                greenField.setBackground(new Color(0,((JSlider)e.getSource()).getValue(),0));
                totalField.setBackground(new Color(redField.getBackground().getRed(), greenField.getBackground().getGreen(), blueField.getBackground().getBlue()));
                
                //If the green component is more than 144, then set editable to false
                if(((JSlider)e.getSource()).getValue() > 144) { greenField.setEditable(false); greenField.setEnabled(true); }
                //If the green component is more than 144, then set enable to false
                else { greenField.setEditable(true); greenField.setEnabled(false); }
            }
        });
        //Change text and color of blueField when there is a change to greenSlider
        blueSlider.addChangeListener(new ChangeListener() 
        {
            @Override public void stateChanged(ChangeEvent e) 
            {
                blueField.setText(Integer.toString(((JSlider)e.getSource()).getValue()));
                blueField.setBackground(new Color(0,0,((JSlider)e.getSource()).getValue()));
                totalField.setBackground(new Color(redField.getBackground().getRed(), greenField.getBackground().getGreen(), blueField.getBackground().getBlue()));
            }
        });
        
        constr.insets = new Insets(5,5,5,5);
        constr.gridheight = 12;
        constr.gridwidth = 1;
        constr.gridx = 0;
        constr.gridy = 0;

        //Add sliders to the color frame
        colorMenuFrame.add(redSlider, constr);
        constr.gridx = 1;
        colorMenuFrame.add(greenSlider, constr);
        constr.gridx = 2;
        colorMenuFrame.add(blueSlider, constr);

        //Set attributes of the red, blue, and green field
        redField.setBackground(new Color(redSlider.getValue(),0,0));
        greenField.setBackground(new Color(0,greenSlider.getValue(),0));
        blueField.setBackground(new Color(0,0,blueSlider.getValue()));
        totalField.setBackground(userColorScheme[0]);
        redField.setPreferredSize(new Dimension(redSlider.getPreferredSize().width, redField.getPreferredSize().height+5));
        greenField.setPreferredSize(redField.getPreferredSize());
        blueField.setPreferredSize(redField.getPreferredSize());
        totalField.setPreferredSize(redField.getPreferredSize());
        redField.setHorizontalAlignment(JTextField.CENTER);
        greenField.setHorizontalAlignment(JTextField.CENTER);
        blueField.setHorizontalAlignment(JTextField.CENTER);
        totalField.setHorizontalAlignment(JTextField.CENTER);
        redField.setEnabled(false);
        if(greenSlider.getValue() > 144) { greenField.setEditable(false); }
        else { greenField.setEnabled(false); }
        blueField.setEnabled(false);
        totalField.setEditable(false);

        //Add all the 3 fields to color frame
        constr.gridheight = 1;
        constr.gridx = 0;
        constr.gridy = 12;
        colorMenuFrame.add(redField,constr);
        constr.gridx = 1;
        colorMenuFrame.add(greenField,constr);
        constr.gridx = 2;
        colorMenuFrame.add(blueField,constr);
        constr.gridx = 3;
        colorMenuFrame.add(totalField,constr);

        //Create buttons to change color of the components in the main frame
        JButton[] colorMenuChangeBtns = new JButton[userColorSchemeDescription.length-2];
        constr.insets = new Insets(5,1,1,5);
        constr.gridy = 0;
        
        //Add first colorMenuChangeBtns buttons to color frame
        colorMenuChangeBtns[0] = new JButton(userColorSchemeDescription[0]);
        colorMenuChangeBtns[0].setBackground(userColorScheme[0]);
        if(userColorScheme[0].getRed()<125 && userColorScheme[0].getGreen()<125 && userColorScheme[0].getBlue()<125) { colorMenuChangeBtns[0].setForeground(Color.WHITE); }
        colorMenuFrame.add(colorMenuChangeBtns[0], constr);
        constr.insets = new Insets(1,1,1,5);

        //Add remaining the colorMenuChangeBtns buttons to color frame
        for(int i = 1; i < colorMenuChangeBtns.length; ++i)
        {
            constr.gridy = i;
            colorMenuChangeBtns[i] = new JButton(userColorSchemeDescription[i]);
            colorMenuChangeBtns[i].setBackground(userColorScheme[i]);
            if(userColorScheme[i].getRed()<125 && userColorScheme[i].getGreen()<125 && userColorScheme[i].getBlue()<125) { colorMenuChangeBtns[i].setForeground(Color.WHITE); }
            colorMenuFrame.add(colorMenuChangeBtns[i], constr);
        }

        //Create save, reset, and exit buttons. Add it to color frame
        JButton colorMenuSaveBtn = new JButton("SAVE");
        colorMenuSaveBtn.setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuSaveBtn.addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { saveChangesToFile(); }});
        constr.gridy += 1;
        colorMenuFrame.add(colorMenuSaveBtn, constr);
        JButton colorMenuResetBtn = new JButton("RESET");
        constr.gridy += 1;
        colorMenuResetBtn.setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuResetBtn.addActionListener(new ActionListener()
        {
            @Override public void actionPerformed(ActionEvent e) 
            {
                //Revert the settings
                colorMenuChangeBtns[0].setBackground(defaultColorScheme[0]); mainMenuBar.setBackground(defaultColorScheme[0]);
                colorMenuChangeBtns[1].setBackground(defaultColorScheme[1]); settMenu.setForeground(defaultColorScheme[1]);
                colorMenuChangeBtns[2].setBackground(defaultColorScheme[2]); colorMenu.setBackground(defaultColorScheme[2]); soundProfile.setBackground(defaultColorScheme[2]);
                colorMenuChangeBtns[3].setBackground(defaultColorScheme[3]); mainFrame.getContentPane().setBackground(defaultColorScheme[3]);
                colorMenuChangeBtns[4].setBackground(defaultColorScheme[4]); for(int i = 0; i < keyboardBtns.length; ++i) { if(keyboardBtns[i].getBackground() != userColorScheme[7] && keyboardBtns[i].getBackground() != userColorScheme[8]) { keyboardBtns[i].setBackground(defaultColorScheme[4]); } }
                colorMenuChangeBtns[5].setBackground(defaultColorScheme[5]); writingSpace.setBackground(defaultColorScheme[5]);
                colorMenuChangeBtns[6].setBackground(defaultColorScheme[6]); clearBtn.setBackground(defaultColorScheme[6]); resetBtn.setBackground(defaultColorScheme[6]);
                colorMenuChangeBtns[7].setBackground(defaultColorScheme[7]); for(int i = 0; i < keyboardBtns.length; ++i) { if(keyboardBtns[i].getBackground() == userColorScheme[7]) { keyboardBtns[i].setBackground(defaultColorScheme[7]); } }
                colorMenuChangeBtns[8].setBackground(defaultColorScheme[8]); for(int i = 0; i < keyboardBtns.length; ++i) { if(keyboardBtns[i].getBackground() == userColorScheme[8]) { keyboardBtns[i].setBackground(defaultColorScheme[8]); } }

                //Set the user color selection to default and save the the changes to the file
                userColorScheme = new Color[defaultColorScheme.length];
                for(int i = 0; i < defaultColorScheme.length; ++i) { userColorScheme[i] = defaultColorScheme[i]; }
                saveChangesToFile();
            }
        });
        colorMenuFrame.add(colorMenuResetBtn, constr);
        JButton colorMenuExitBtn = new JButton("EXIT");
        constr.gridy += 1;
        colorMenuExitBtn.setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuExitBtn.addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuFrame.dispose(); }});
        colorMenuFrame.add(colorMenuExitBtn, constr);
        
        //Set the button size for all the buttons in color frame
        colorMenuChangeBtns[0].setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuChangeBtns[1].setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuChangeBtns[2].setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuChangeBtns[3].setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuChangeBtns[5].setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuChangeBtns[6].setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuChangeBtns[7].setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuChangeBtns[8].setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuSaveBtn.setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuResetBtn.setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        colorMenuExitBtn.setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());
        totalField.setPreferredSize(colorMenuChangeBtns[4].getPreferredSize());

        //Add action listener to each button
        colorMenuChangeBtns[0].addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuChangeBtns[0].setBackground(totalField.getBackground()); mainMenuBar.setBackground(totalField.getBackground()); userColorScheme[0] = mainMenuBar.getBackground(); }});
        colorMenuChangeBtns[1].addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuChangeBtns[1].setBackground(totalField.getBackground()); settMenu.setForeground(totalField.getBackground()); userColorScheme[1] = settMenu.getForeground(); }});
        colorMenuChangeBtns[2].addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuChangeBtns[2].setBackground(totalField.getBackground()); colorMenu.setBackground(totalField.getBackground()); soundProfile.setBackground(colorMenu.getBackground()); userColorScheme[2] = colorMenu.getBackground(); }});
        colorMenuChangeBtns[3].addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuChangeBtns[3].setBackground(totalField.getBackground()); mainFrame.getContentPane().setBackground(totalField.getBackground()); userColorScheme[3] = mainFrame.getBackground(); }});
        colorMenuChangeBtns[4].addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuChangeBtns[4].setBackground(totalField.getBackground()); for(int i = 0; i < keyboardBtns.length; ++i) { if(keyboardBtns[i].getBackground() != userColorScheme[7] && keyboardBtns[i].getBackground() != userColorScheme[8]) { keyboardBtns[i].setBackground(totalField.getBackground()); } } userColorScheme[4] = totalField.getBackground(); }});
        colorMenuChangeBtns[5].addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuChangeBtns[5].setBackground(totalField.getBackground()); writingSpace.setBackground(totalField.getBackground()); userColorScheme[5] = writingSpace.getBackground(); }});
        colorMenuChangeBtns[6].addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuChangeBtns[6].setBackground(totalField.getBackground()); clearBtn.setBackground(totalField.getBackground()); resetBtn.setBackground(clearBtn.getBackground()); userColorScheme[6] = clearBtn.getBackground(); }});
        colorMenuChangeBtns[7].addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuChangeBtns[7].setBackground(totalField.getBackground()); for(int i = 0; i < keyboardBtns.length; ++i) { if(keyboardBtns[i].getBackground() == userColorScheme[7]) { keyboardBtns[i].setBackground(totalField.getBackground()); } } userColorScheme[7] = totalField.getBackground(); }});
        colorMenuChangeBtns[8].addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { colorMenuChangeBtns[8].setBackground(totalField.getBackground()); for(int i = 0; i < keyboardBtns.length; ++i) { if(keyboardBtns[i].getBackground() == userColorScheme[8]) { keyboardBtns[i].setBackground(totalField.getBackground()); } } userColorScheme[8] = totalField.getBackground(); }});

        //Deploy the frame to the user
        colorMenuFrame.pack();
        colorMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        colorMenuFrame.setLocationRelativeTo(null);
        colorMenuFrame.setVisible(true);
    }
    
    
    //Allows user to change the click sounds
    private static void changeSoundProfileMenu()
    {
        JFrame colorMenuFrame = new JFrame("Change Sound");
        colorMenuFrame.setLayout(new GridBagLayout());

        String[] changeSoundMenuKeyPressFilePaths = new String[keyPressFilePaths.length+2];
        String[] changeSoundMenuKeyReleaseFilePaths = new String[keyReleaseFilePaths.length+2];
        JTextField keyPressCustomField = new JTextField();
        JTextField keyReleaseCustomField = new JTextField();

        //Add miscellaneous options
        changeSoundMenuKeyPressFilePaths[0] = "--NONE--";
        changeSoundMenuKeyPressFilePaths[changeSoundMenuKeyPressFilePaths.length-1] = "--CUSTOM--";
        changeSoundMenuKeyReleaseFilePaths[0] = "--NONE--";
        changeSoundMenuKeyReleaseFilePaths[changeSoundMenuKeyReleaseFilePaths.length-1] = "--CUSTOM--";

        //Copy existing key sound file paths
        for(int i = 0; i < keyPressFilePaths.length; ++i) { changeSoundMenuKeyPressFilePaths[i+1] = keyPressFilePaths[i]; }
        for(int i = 0; i < keyReleaseFilePaths.length; ++i) { changeSoundMenuKeyReleaseFilePaths[i+1] = keyReleaseFilePaths[i]; }

        //Create drop down menu to select key sounds
        JComboBox<String> keyPressOptionBox = new JComboBox<String>(changeSoundMenuKeyPressFilePaths);
        JComboBox<String> keyReleaseOptionBox = new JComboBox<String>(changeSoundMenuKeyReleaseFilePaths);
        keyPressOptionBox.setSelectedIndex((userKeyPressSoundChoice == -2) ? (changeSoundMenuKeyPressFilePaths.length-1) : (userKeyPressSoundChoice+1));
        keyReleaseOptionBox.setSelectedIndex((userKeyReleaseSoundChoice == -2) ? (changeSoundMenuKeyReleaseFilePaths.length-1) : (userKeyReleaseSoundChoice+1));
        keyPressOptionBox.setEditable(true);
        keyReleaseOptionBox.setEditable(true);

        keyPressOptionBox.addActionListener(new ActionListener()
        {
            @SuppressWarnings("unchecked")
			@Override public void actionPerformed(ActionEvent e) 
            {
                userKeyPressSoundChoice = ((JComboBox<String>)e.getSource()).getSelectedIndex();
                userKeyPressSoundChoice = (userKeyPressSoundChoice == changeSoundMenuKeyPressFilePaths.length-1) ? (-2) : (userKeyPressSoundChoice-1);
                if(userKeyPressSoundChoice == -2) { keyPressCustomField.setEditable(true); }
                else { keyPressCustomField.setEditable(false); }
			}
        });

        keyReleaseOptionBox.addActionListener(new ActionListener()
        {
            @SuppressWarnings("unchecked")
			@Override public void actionPerformed(ActionEvent e) 
            {
                userKeyReleaseSoundChoice = ((JComboBox<String>)e.getSource()).getSelectedIndex();
                userKeyReleaseSoundChoice = (userKeyReleaseSoundChoice == changeSoundMenuKeyReleaseFilePaths.length-1) ? (-2) : (userKeyReleaseSoundChoice-1);
                if(userKeyReleaseSoundChoice == -2) { keyReleaseCustomField.setEditable(true); }
                else { keyReleaseCustomField.setEditable(false); }
			}
        });


        //Reset constrain
        constr.gridheight = 1;
        constr.gridwidth = 1;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.insets = new Insets(5,5,5,5);

        //Adding lables to the frame
        JLabel keyPressLabel = new JLabel("Key Press Sound: ");
        JLabel keyReleaseLabel = new JLabel("Key Release Sound: ");
        colorMenuFrame.add(keyPressLabel, constr);
        constr.gridy = 1;
        colorMenuFrame.add(keyReleaseLabel, constr);

        //Add custom file location field
        JLabel keyPressCustomLabel = new JLabel("Key Press Custom Relative Path: ");
        JLabel keyReleaseCustomLabel = new JLabel("Key Release Custom Relative Path: ");
        constr.gridy = 2;
        colorMenuFrame.add(keyPressCustomLabel, constr);
        constr.gridy = 3;
        colorMenuFrame.add(keyReleaseCustomLabel, constr);
        keyPressCustomField.setEditable(false);
        keyReleaseCustomField.setEditable(false);
        keyPressCustomField.setPreferredSize(new Dimension(keyPressOptionBox.getPreferredSize().width+13, keyPressOptionBox.getPreferredSize().height));
        keyReleaseCustomField.setPreferredSize(keyPressCustomField.getPreferredSize());

        //Enable the input file path field if the user selection is custom
        if(userKeyPressSoundChoice == -2) 
        { 
            keyPressCustomField.setText(keyPressCustomFilePath); 
            keyPressCustomField.setEditable(true);
        }
        if(userKeyReleaseSoundChoice == -2) 
        { 
            keyReleaseCustomField.setText(keyReleaseCustomFilePath); 
            keyReleaseCustomField.setEditable(true);
        }

        constr.gridx = 1;
        colorMenuFrame.add(keyReleaseCustomField, constr);
        constr.gridy = 2;
        colorMenuFrame.add(keyPressCustomField, constr);

        //Add buttons to the frame
        JButton changeSoundResetBtn = new JButton("RESET");
        JButton changeSoundSaveBtn = new JButton("SAVE");
        constr.gridy = 4;
        colorMenuFrame.add(changeSoundSaveBtn, constr);
        constr.gridx = 0;
        colorMenuFrame.add(changeSoundResetBtn, constr);

        //Reset changes
        changeSoundResetBtn.addActionListener(new ActionListener()
        {
			@Override public void actionPerformed(ActionEvent e) 
            {
                userKeyPressSoundChoice = -1;
                userKeyReleaseSoundChoice = -1;
                keyPressOptionBox.setSelectedIndex(0);
                keyReleaseOptionBox.setSelectedIndex(0);
                saveChangesToFile();
			}
        });

        //Save current changes
        changeSoundSaveBtn.addActionListener(new ActionListener() 
        { 
            @Override public void actionPerformed(ActionEvent e) 
            { 
                keyPressCustomFilePath = keyPressCustomField.getText(); 
                keyReleaseCustomFilePath = keyReleaseCustomField.getText(); 
                saveChangesToFile(); 
            } 
        });

        //Add components to the frame
        constr.gridx = 1;
        constr.gridy = 0;
        colorMenuFrame.add(keyPressOptionBox, constr);
        constr.gridy = 1;
        colorMenuFrame.add(keyReleaseOptionBox, constr);

        //Set appropriate size for the drop down boxes
        if(keyPressOptionBox.getPreferredSize().width > keyReleaseOptionBox.getPreferredSize().width) { keyReleaseOptionBox.setPreferredSize(keyPressOptionBox.getPreferredSize()); }
        else { keyPressOptionBox.setPreferredSize(keyReleaseOptionBox.getPreferredSize()); }

        //Deploy the frame to the user
        colorMenuFrame.pack();
        colorMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        colorMenuFrame.setLocationRelativeTo(null);
        colorMenuFrame.setVisible(true);
    }


    //Initialize the sound clip for a key down press
    private static void initializeKeyPressSound()
    {
        try
        {
            keyPressaudioInputStream = (userKeyPressSoundChoice != -2) ? (AudioSystem.getAudioInputStream(new File(keyPressFilePaths[userKeyPressSoundChoice]).getAbsoluteFile())) : (AudioSystem.getAudioInputStream(new File(keyPressCustomFilePath).getAbsoluteFile()));
            keyPressClip = AudioSystem.getClip();
            keyPressClip.open(keyPressaudioInputStream);
        }
        catch(Exception ex) 
        {
            userKeyPressSoundChoice = -1;
        }
    }


    //Initialize the sound clip for a key release
    private static void initializeKeyReleaseSound()
    {
        try
        {
            keyReleaseaudioInputStream = (userKeyReleaseSoundChoice != -2) ? (AudioSystem.getAudioInputStream(new File(keyReleaseFilePaths[userKeyReleaseSoundChoice]).getAbsoluteFile())) : (AudioSystem.getAudioInputStream(new File(keyReleaseCustomFilePath).getAbsoluteFile()));
            keyReleaseClip = AudioSystem.getClip();
            keyReleaseClip.open(keyReleaseaudioInputStream);
        }
        catch(Exception ex) 
        {
            userKeyReleaseSoundChoice = -1;
        }
    }


    //Append the key typed to writing space
    @Override public void keyTyped(KeyEvent e) 
    {
        if((int) e.getKeyChar() != KeyEvent.VK_BACK_SPACE && (int) e.getKeyChar() != KeyEvent.VK_ESCAPE) { writingSpace.setText(writingSpace.getText() + e.getKeyChar()); }
        else if((int) e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
        {
            typedString = writingSpace.getText();
            if(typedString.length() != 0) { writingSpace.setText(typedString.substring(0, typedString.length()-1)); }
        }
    }


    //Identify key press, change color of the button, and make sound
    @Override public void keyPressed(KeyEvent e) 
    {
        key1 = 0;
        code = e.getKeyCode();

        //If the key press is not 'SHIFT', 'ENTER', 'ALT', and 'CTRL'
        if(code!=KeyEvent.VK_ALT && code!=KeyEvent.VK_ENTER && code!=KeyEvent.VK_CONTROL && code!=KeyEvent.VK_SHIFT)
        {
            flag = false;

            //Find the key that was pressed
            for(; key1 < keyCodes.length; ++key1) { if(code == keyCodes[key1]) { flag = true; break; } }

            //If key was found and the key press is new, then make sound and change the background color of the key
            if(flag && keyboardBtns[key1].getBackground() != userColorScheme[7])
            {
                //Don't produce sound if the user selection is -1
                if(userKeyPressSoundChoice != -1) { initializeKeyPressSound(); } 
                if(userKeyReleaseSoundChoice != -1) { try { keyReleaseClip.stop(); } catch(Exception Ex) { initializeKeyReleaseSound(); } }
                if(userKeyPressSoundChoice != -1) { keyPressClip.start(); }

                keyboardBtns[key1].setBackground(userColorScheme[7]);
            }
        }
        else
        {
            key2 = 0;

            //Identify the key press combo
            if(code == KeyEvent.VK_ALT) { key1 = 93; key2 = 95; }
            else if(code == KeyEvent.VK_ENTER) { key1 = 70; key2 = 90; }
            else if(code == KeyEvent.VK_SHIFT) { key1 = 74; key2 = 85;}
            else { key1 = 91; key2 = 98; }

            //If the key is newly pressed, then make sound and change the color of the button combo
            if(keyboardBtns[key1].getBackground() != userColorScheme[7])
            {
                //Don't produce sound if the user selection is -1
                if(userKeyPressSoundChoice != -1) { initializeKeyPressSound(); }
                if(userKeyReleaseSoundChoice != -1) { try { keyReleaseClip.stop(); } catch(Exception Ex) { initializeKeyReleaseSound(); } }
                if(userKeyPressSoundChoice != -1) { keyPressClip.start(); }

                keyboardBtns[key1].setBackground(userColorScheme[7]);
                keyboardBtns[key2].setBackground(userColorScheme[7]);
            }
        }
    }


    //Identify key release, change color of the button, and make sound
    @Override public void keyReleased(KeyEvent e) 
    {
        key1 = 0;
        code = e.getKeyCode();

        //If the key press is not 'SHIFT', 'ENTER', 'ALT', and 'CTRL'
        if(code!=KeyEvent.VK_ALT && code!=KeyEvent.VK_ENTER && code!=KeyEvent.VK_CONTROL && code!=KeyEvent.VK_SHIFT)
        {
            flag = false;

            //Find the key pressed
            for(; key1 < keyCodes.length; ++key1) { if(code == keyCodes[key1]) { flag = true; break; } }

            //If key found, then make release sound and change color
            if(flag)
            {
                if(userKeyReleaseSoundChoice != -1) { initializeKeyReleaseSound(); } 
                if(userKeyPressSoundChoice != -1) { try { keyPressClip.stop(); } catch(Exception Ex) { initializeKeyPressSound(); } }
                if(userKeyReleaseSoundChoice != -1) { keyReleaseClip.start(); }
                keyboardBtns[key1].setBackground(userColorScheme[8]);
            }
        }
        else
        {
            key2 = 0;

            //Identify the key combo
            if(code == KeyEvent.VK_ALT) { key1 = 93; key2 = 95; }
            else if(code == KeyEvent.VK_ENTER) { key1 = 70; key2 = 90; }
            else if(code == KeyEvent.VK_SHIFT) { key1 = 74; key2 = 85; }
            else { key1 = 91; key2 = 98; }

            //Make release sound and change color of the button combo
            if(userKeyReleaseSoundChoice != -1) { initializeKeyReleaseSound(); }
            if(userKeyPressSoundChoice != -1) { try { keyPressClip.stop(); } catch(Exception Ex) { initializeKeyPressSound(); } }
            if(userKeyReleaseSoundChoice != -1) { keyReleaseClip.start(); }
            keyboardBtns[key1].setBackground(userColorScheme[8]);
            keyboardBtns[key2].setBackground(userColorScheme[8]);
        }
    }
}
