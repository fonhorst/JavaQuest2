/*
 * DesktopApplication1View.java
 */

package Android;


import Dict.ProcessingManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import Android.JavaQuest;
import java.awt.Desktop;

/**
 * The application's main frame.
 */
public class JavaQuestView extends FrameView {

    //класс управления вычисления(всем функционалом) - реализуется паттерн фасад
    private ProcessingManager manager;//=new ProcessingManager();
    //текущая директроия для файлового диалога
    private String currentDir=System.getProperty("user.dir");
    
    private void init()
    {
        this.manager=new ProcessingManager();
        
        
    }
   
 //фильтр для выбора файлов в файловом диалоге   
class CustomFileFilter extends FileFilter {

    public CustomFileFilter(String fileExt)
    {
        this.extension=fileExt;
    }
    
  private String extension = ".*";

  //устанавливает расширение, котрое будет доступно
  public void setExtension(String fileExt) {

    extension = fileExt;

  }
  //определяет удовлетворяет ли файл условию
  public boolean accept(File f) {

    return (f.getName().endsWith(extension) || f.isDirectory());

  }
  //строка с расширением фильтра
  public String getDescription() {

    return "(*" + extension + ")";

  }     

}



    public JavaQuestView(SingleFrameApplication app) {
        super(app);

        initComponents();

        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        
        init();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = JavaQuest.getApplication().getMainFrame();
            aboutBox = new JavaQuestAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        JavaQuest.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jtfDictionary = new javax.swing.JTextField();
        jbDictionary = new javax.swing.JButton();
        jlDictionary = new javax.swing.JLabel();
        jlText = new javax.swing.JLabel();
        jtfText = new javax.swing.JTextField();
        jbText = new javax.swing.JButton();
        jbDo = new javax.swing.JButton();
        jtfMaxLines = new javax.swing.JTextField();
        jlMaxLines = new javax.swing.JLabel();
        jtfOutput = new javax.swing.JTextField();
        jbOutput = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jbDictionaryOpen = new javax.swing.JButton();
        jbTextOpen = new javax.swing.JButton();
        jbOutputDirectoryOpen = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(400, 400));

        jtfDictionary.setEditable(false);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(Android.JavaQuest.class).getContext().getResourceMap(JavaQuestView.class);
        jtfDictionary.setText(resourceMap.getString("tfDictionary.text")); // NOI18N
        jtfDictionary.setName("tfDictionary"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(Android.JavaQuest.class).getContext().getActionMap(JavaQuestView.class, this);
        jbDictionary.setAction(actionMap.get("jbDictionary_Click")); // NOI18N
        jbDictionary.setForeground(resourceMap.getColor("jbDictionary.foreground")); // NOI18N
        jbDictionary.setText(resourceMap.getString("jbDictionary.text")); // NOI18N
        jbDictionary.setToolTipText(resourceMap.getString("jbDictionary.toolTipText")); // NOI18N
        jbDictionary.setActionCommand(resourceMap.getString("jbDictionary.actionCommand")); // NOI18N
        jbDictionary.setDoubleBuffered(true);
        jbDictionary.setFocusCycleRoot(true);
        jbDictionary.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbDictionary.setInheritsPopupMenu(true);
        jbDictionary.setMaximumSize(new java.awt.Dimension(63, 31));
        jbDictionary.setMinimumSize(new java.awt.Dimension(63, 30));
        jbDictionary.setName("jbDictionary"); // NOI18N
        jbDictionary.setPreferredSize(new java.awt.Dimension(63, 30));
        jbDictionary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbDictionaryMouseClicked(evt);
            }
        });

        jlDictionary.setText(resourceMap.getString("lbDictionary.text")); // NOI18N
        jlDictionary.setName("lbDictionary"); // NOI18N

        jlText.setText(resourceMap.getString("jlText.text")); // NOI18N
        jlText.setName("jlText"); // NOI18N

        jtfText.setEditable(false);
        jtfText.setText(resourceMap.getString("jtfText.text")); // NOI18N
        jtfText.setName("jtfText"); // NOI18N

        jbText.setText(resourceMap.getString("jbText.text")); // NOI18N
        jbText.setName("jbText"); // NOI18N
        jbText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbTextMouseClicked(evt);
            }
        });

        jbDo.setText(resourceMap.getString("jbDo.text")); // NOI18N
        jbDo.setName("jbDo"); // NOI18N
        jbDo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbDoMouseClicked(evt);
            }
        });

        jtfMaxLines.setText(resourceMap.getString("jtfMaxLines.text")); // NOI18N
        jtfMaxLines.setName("jtfMaxLines"); // NOI18N

        jlMaxLines.setText(resourceMap.getString("jlMaxLines.text")); // NOI18N
        jlMaxLines.setName("jlMaxLines"); // NOI18N

        jtfOutput.setEditable(false);
        jtfOutput.setName("jtfOutput"); // NOI18N

        jbOutput.setText(resourceMap.getString("jbOutput.text")); // NOI18N
        jbOutput.setName("jbOutput"); // NOI18N
        jbOutput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbOutputMouseClicked(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jbDictionaryOpen.setText(resourceMap.getString("jbDictionaryOpen.text")); // NOI18N
        jbDictionaryOpen.setName("jbDictionaryOpen"); // NOI18N
        jbDictionaryOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbDictionaryOpenMouseClicked(evt);
            }
        });

        jbTextOpen.setLabel(resourceMap.getString("jbTextOpen.label")); // NOI18N
        jbTextOpen.setName("jbTextOpen"); // NOI18N
        jbTextOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbTextOpenMouseClicked(evt);
            }
        });

        jbOutputDirectoryOpen.setText(resourceMap.getString("jbOutputDirectoryOpen.text")); // NOI18N
        jbOutputDirectoryOpen.setName("jbOutputDirectoryOpen"); // NOI18N
        jbOutputDirectoryOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbOutputDirectoryOpenMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbDo, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlMaxLines)
                    .addComponent(jlDictionary, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jtfOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbOutput))
                    .addComponent(jLabel1)
                    .addComponent(jlText)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jtfText, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbText))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jtfDictionary, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbDictionary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jtfMaxLines, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbOutputDirectoryOpen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jbDictionaryOpen)
                                .addGap(18, 18, 18)
                                .addComponent(jbTextOpen)))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jlDictionary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbDictionary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfDictionary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jlText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbText)
                    .addComponent(jtfText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbOutput))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlMaxLines)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfMaxLines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbTextOpen)
                    .addComponent(jbDictionaryOpen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbOutputDirectoryOpen)
                .addGap(7, 7, 7)
                .addComponent(jbDo)
                .addGap(20, 20, 20))
        );

        jtfDictionary.getAccessibleContext().setAccessibleName(resourceMap.getString("tfDictionary.AccessibleContext.accessibleName")); // NOI18N
        jbDictionary.getAccessibleContext().setAccessibleName(resourceMap.getString("jbDictionary.AccessibleContext.accessibleName")); // NOI18N
        jlDictionary.getAccessibleContext().setAccessibleName(resourceMap.getString("lbDictionary.AccessibleContext.accessibleName")); // NOI18N

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jbDictionaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDictionaryMouseClicked
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(null,this.jbDictionary.getText());
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setCurrentDirectory(new File(currentDir));
        fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.setFileFilter(new CustomFileFilter(".txt"));
        if(fileChooser.showOpenDialog(null)== fileChooser.APPROVE_OPTION)
        {
            String s=System.getProperty("user.dir");
            this.jtfDictionary.setText(s=fileChooser.getSelectedFile().getPath());
           String d= fileChooser.getSelectedFile().getName();
            this.currentDir=s.substring(0,s.length()-d.length());
            
        }
    }//GEN-LAST:event_jbDictionaryMouseClicked

    private void jbTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbTextMouseClicked
        // TODO add your handling code here:
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setCurrentDirectory(new File(currentDir));
        fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.setFileFilter(new CustomFileFilter(".txt"));
        if(fileChooser.showOpenDialog(null)== fileChooser.APPROVE_OPTION)
        {
            String s=System.getProperty("user.dir");
            this.jtfText.setText(s=fileChooser.getSelectedFile().getPath());
            String d= fileChooser.getSelectedFile().getName();
            this.currentDir=s.substring(0,s.length()-d.length());
             
            //JOptionPane.showMessageDialog(null,s);
        }
    }//GEN-LAST:event_jbTextMouseClicked

    private void jbDoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDoMouseClicked
        // TODO add your handling code here:
        try
        {
            int maxLines;
            //получаем количество строк выводимых в файл
            maxLines =  ProcessingManager.MaxLines(Integer.parseInt(this.jtfMaxLines.getText()));               
            this.jtfMaxLines.setText(((Integer)maxLines).toString());
            //обрабатываем данные
            //this.manager.Process(this.jtfDictionary.getText(),this.jtfText.getText(),maxLines );
            //JOptionPane.showMessageDialog(null," Готово");
            jDgOperation operation=new jDgOperation(null,true);
            this.manager.addExceptionOccuredHandler(operation);
            this.manager.addProgressChangedHandler(operation);
            this.manager.Process(this.jtfDictionary.getText(),this.jtfText.getText(), this.jtfOutput.getText(),maxLines);
            operation.setVisible(true);
        }
         catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null," Введено некорректное значение маскимального числа строчек");
            return;
        }
        catch(Exception ex)
        {
           JOptionPane.showMessageDialog(null,ex.getMessage()); 
        }
        
        
             
             
    }//GEN-LAST:event_jbDoMouseClicked

    private void jbOutputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbOutputMouseClicked
        // TODO add your handling code here:
        
        
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setCurrentDirectory(new File(currentDir));
        fileChooser.setFileFilter(new CustomFileFilter(".html"));
        fileChooser.setSelectedFile(new File("Output.html"));
        if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        {
          
            String s=fileChooser.getSelectedFile().getPath();
            this.jtfOutput.setText(s);
            String d= fileChooser.getSelectedFile().getName();
            this.currentDir=s.substring(0,s.length()-d.length());
            
        }
        
    }//GEN-LAST:event_jbOutputMouseClicked

    private void jbDictionaryOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDictionaryOpenMouseClicked
        try {
            // TODO add your handling code here:
            Desktop.getDesktop().open(new File(this.jtfDictionary.getText()));
        }
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, " Файл словаря не существует");
        }
        catch(SecurityException ex)
        {
            JOptionPane.showMessageDialog(null, " Ошибка прав доступа к файлу");
        }
        catch (IOException ex) {
            
            JOptionPane.showMessageDialog(null, " Ошибка открытия файла");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        
    }//GEN-LAST:event_jbDictionaryOpenMouseClicked

    private void jbTextOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbTextOpenMouseClicked
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            Desktop.getDesktop().open(new File(this.jtfText.getText()));
        }
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, " Файл текста не существует");
        }
        catch(SecurityException ex)
        {
            JOptionPane.showMessageDialog(null, " Ошибка прав доступа к файлу");
        }
        catch (IOException ex) {
            
            JOptionPane.showMessageDialog(null, " Ошибка открытия файла");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }//GEN-LAST:event_jbTextOpenMouseClicked

    private void jbOutputDirectoryOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbOutputDirectoryOpenMouseClicked
        // TODO add your handling code here:
        
        try {
           
            Desktop.getDesktop().open(new File(new File(this.jtfOutput.getText()).getParent()));
        }
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, " Файл словаря не существует");
        }
        catch(SecurityException ex)
        {
            JOptionPane.showMessageDialog(null, " Ошибка прав доступа к файлу");
        }
        catch (IOException ex) {
            
            JOptionPane.showMessageDialog(null, " Ошибка открытия файла");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }//GEN-LAST:event_jbOutputDirectoryOpenMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbDictionary;
    private javax.swing.JButton jbDictionaryOpen;
    private javax.swing.JButton jbDo;
    private javax.swing.JButton jbOutput;
    private javax.swing.JButton jbOutputDirectoryOpen;
    private javax.swing.JButton jbText;
    private javax.swing.JButton jbTextOpen;
    private javax.swing.JLabel jlDictionary;
    private javax.swing.JLabel jlMaxLines;
    private javax.swing.JLabel jlText;
    private javax.swing.JTextField jtfDictionary;
    private javax.swing.JTextField jtfMaxLines;
    private javax.swing.JTextField jtfOutput;
    private javax.swing.JTextField jtfText;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
