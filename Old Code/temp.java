/*import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class temp
{
    public static void main(String[] Args) 
    {
        JFrame mainFrame = new JFrame("Mechanical Keyboard");
        JButton[] keyboard_btns = new JButton[104];
        String[] keyboardLetters = { "ESC", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "PRINT", "SCRLK", "PAUSE", 
            "`~", "1!", "2@", "3#", "4$", "5%", "6^", "7&", "8*", "9(", "0)", "-_", "=+", "BACK", "INS", "HOME", "PGUP", "NUMLK", "/", "*", "-", 
            "TAB", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[{", "]}", "\\|", "DEL", "END", "PGDN", "7", "8", "9", "+", 
            "CAPS", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";:", "'\"", "ENTER", "4", "5", "6", 
            "SHIFT", "Z", "X", "C", "V", "B", "N", "M", ",<", ".>", "/?", "SHIFT", "UP", "1", "2", "3", "ENTER", 
            "CTRL", "WIN", "ALT", "SPACE", "ALT", "FN", "OPT", "CTRL", "LEFT", "DOWN", "RIGHT", "0", "." };        
        int[] keyCodes = {KeyEvent.VK_ESCAPE, KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11, KeyEvent.VK_F12, KeyEvent.VK_PRINTSCREEN, KeyEvent.VK_SCROLL_LOCK, KeyEvent.VK_PAUSE, 
            KeyEvent.VK_DEAD_TILDE, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0, KeyEvent.VK_UNDERSCORE, KeyEvent.VK_EQUALS, KeyEvent.VK_BACK_SPACE, KeyEvent.VK_INSERT, KeyEvent.VK_HOME, KeyEvent.VK_PAGE_UP, KeyEvent.VK_NUM_LOCK, KeyEvent.VK_SLASH, KeyEvent.VK_MULTIPLY, KeyEvent.VK_MINUS, 
            KeyEvent.VK_TAB, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_OPEN_BRACKET, KeyEvent.VK_CLOSE_BRACKET, KeyEvent.VK_BACK_SLASH, KeyEvent.VK_DELETE, KeyEvent.VK_END, KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9, KeyEvent.VK_PLUS, 
            KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SEMICOLON, KeyEvent.VK_QUOTE, KeyEvent.VK_ENTER, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6, 
            KeyEvent.VK_SHIFT, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD, KeyEvent.VK_SLASH, KeyEvent.VK_SHIFT, KeyEvent.VK_UP, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_ENTER, 
            KeyEvent.VK_CONTROL, KeyEvent.VK_WINDOWS, KeyEvent.VK_ALT, KeyEvent.VK_SPACE, KeyEvent.VK_ALT, KeyEvent.VK_ACCEPT, KeyEvent.VK_CONTEXT_MENU, KeyEvent.VK_CONTROL, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_NUMPAD0, KeyEvent.VK_PERIOD};
        
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();
        constr.insets = new Insets(5, 5, 5, 5);
        Dimension btnSize = new Dimension(75, 30);

        for (int i = 0; i < keyboard_btns.length; ++i) 
        {
            JButton tempBtn = new JButton(keyboardLetters[i]);
            int Code = keyCodes[i];
            tempBtn.addKeyListener(new KeyListener() 
            {
                @Override public void keyTyped(KeyEvent e) {}
                @Override public void keyPressed(KeyEvent e) { if(e.getKeyCode() == Code && tempBtn.getBackground() != Color.RED) { initializeKeyPressSound(); keyReleaseClip.stop(); keyPressClip.start(); tempBtn.setBackground(Color.RED); } }
                @Override public void keyReleased(KeyEvent e) { if(e.getKeyCode() == Code) { initializeKeyReleaseSound(); keyPressClip.stop(); keyReleaseClip.start(); tempBtn.setBackground(Color.GREEN); } }
            });

            tempBtn.setPreferredSize(btnSize);
            keyboard_btns[i] = tempBtn;
            if(i < 16)
            {
                constr.gridx = i;
                if(i!=0) 
                {
                    constr.gridx += 1; 
                    if(i==13) { constr.insets = new Insets(5,20,5,5); mainFrame.add(keyboard_btns[i], constr); constr.insets = new Insets(5,5,5,5); continue; }
                    if(i==15) { constr.insets = new Insets(5,5,5,20); mainFrame.add(keyboard_btns[i], constr); constr.insets = new Insets(5,5,5,5); continue; }
                }
                mainFrame.add(keyboard_btns[i], constr);
            }
            else if(i < 37)
            {
                constr.gridx = i-16;
                constr.gridy = 1;
                if(i==30) { constr.insets = new Insets(5,20,5,5); mainFrame.add(keyboard_btns[i], constr); constr.insets = new Insets(5,5,5,5); continue; }
                if(i==32) { constr.insets = new Insets(5,5,5,20); mainFrame.add(keyboard_btns[i], constr); constr.insets = new Insets(5,5,5,5); continue; }
                mainFrame.add(keyboard_btns[i], constr);
            }
            else if(i < 58)
            {
                constr.gridy = 2;
                constr.gridx = i-37;
                if(i==51) { constr.insets = new Insets(5,20,5,5); mainFrame.add(keyboard_btns[i], constr); constr.insets = new Insets(5,5,5,5); continue; }
                if(i==53) { constr.insets = new Insets(5,5,5,20); mainFrame.add(keyboard_btns[i], constr); constr.insets = new Insets(5,5,5,5); continue; }
                if(i==57) { constr.gridheight = 2; keyboard_btns[i].setPreferredSize(new Dimension(btnSize.width, btnSize.height*2+10)); mainFrame.add(keyboard_btns[i], constr); constr.gridheight = 1; continue; }
                mainFrame.add(keyboard_btns[i], constr);
            }
            else if(i < 74)
            {
                constr.gridy = 3;
                constr.gridx = i-58;
                if(i==70) { constr.gridwidth = 2; keyboard_btns[i].setPreferredSize(new Dimension(btnSize.width*2+10, btnSize.height)); mainFrame.add(keyboard_btns[i], constr); constr.gridwidth = 1; continue; }
                if(i>70) { constr.gridx += 4; }
                mainFrame.add(keyboard_btns[i], constr);
            }
            else if(i < 91)
            {
                constr.gridy = 4;
                constr.gridx = i-73;
                if(i>86) 
                { 
                    constr.gridx += 3;
                    if(i==90) { constr.gridheight = 2; keyboard_btns[i].setPreferredSize(new Dimension(btnSize.width, btnSize.height*2+10)); mainFrame.add(keyboard_btns[i], constr); constr.gridwidth = 1; continue; }
                }
                if(i==74) { constr.gridx -= 1; constr.gridwidth = 2; keyboard_btns[i].setPreferredSize(new Dimension(btnSize.width*2+10, btnSize.height)); mainFrame.add(keyboard_btns[i], constr); constr.gridwidth = 1; continue; }
                if(i==85) { constr.gridwidth = 2; keyboard_btns[i].setPreferredSize(new Dimension(btnSize.width*2+10, btnSize.height)); mainFrame.add(keyboard_btns[i], constr); constr.gridwidth = 1; continue; }
                if(i==86) { constr.gridx += 2; }
                mainFrame.add(keyboard_btns[i], constr);
            }
            else
            {
                constr.gridy = 5;
                constr.gridx = i-91;
                if(i==94) { constr.gridwidth = 7; keyboard_btns[i].setPreferredSize(new Dimension(btnSize.width*7+60, btnSize.height)); mainFrame.add(keyboard_btns[i], constr); constr.gridwidth = 1; continue; }
                if(i>94) 
                { 
                    constr.gridx += 6; 
                    if(i==99) { constr.insets = new Insets(5,20,5,5); mainFrame.add(keyboard_btns[i], constr); constr.insets = new Insets(5,5,5,5); continue; }
                    if(i==101) { constr.insets = new Insets(5,5,5,20); mainFrame.add(keyboard_btns[i], constr); constr.insets = new Insets(5,5,5,5); continue; }
                    if(i==102) { constr.gridwidth = 2; keyboard_btns[i].setPreferredSize(new Dimension(btnSize.width*2+10, btnSize.height)); mainFrame.add(keyboard_btns[i], constr); constr.gridwidth = 1; continue; }
                    if(i==103) { constr.gridx += 1; }
                }
                mainFrame.add(keyboard_btns[i], constr);
            }
        }

        
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    static String keyPressFilePath = "CherryBlueKeyPress.wav";
    static String keyReleaseFilePath = "CherryBlueKeyRelease.wav";
    static Clip keyPressClip;
    static Clip keyReleaseClip;
    static AudioInputStream keyPressaudioInputStream;
    static AudioInputStream keyReleaseaudioInputStream;

    private static void initializeKeyPressSound()
    {
        try
        {
            keyPressaudioInputStream = AudioSystem.getAudioInputStream(new File(keyPressFilePath).getAbsoluteFile());
            keyPressClip = AudioSystem.getClip();
            keyPressClip.open(keyPressaudioInputStream);
        }
        catch(Exception ex) {}
    }

    private static void initializeKeyReleaseSound()
    {
        try
        {
            keyReleaseaudioInputStream = AudioSystem.getAudioInputStream(new File(keyReleaseFilePath).getAbsoluteFile());
            keyReleaseClip = AudioSystem.getClip();
            keyReleaseClip.open(keyReleaseaudioInputStream);
        }
        catch(Exception ex) {}
    }
}
*/