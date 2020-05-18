package org.jojo;

import java.awt.Container;
import org.jojo.search.SearchData;
import org.jojo.search.FileEntry;
import java.awt.Event;
import java.awt.Frame;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import org.jojo.helper.ProjectHelper;
import org.jojo.helper.ResourceBundleHelper;
import org.jojo.search.SearchService;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;

public class JOpenDialog extends JDialog {

    private JOpenDefaultListModel resultListModel = new JOpenDefaultListModel();
    private int MAX_DISPLAY_RESULTS = 40;
    private Frame parent = null;

    /** Creates new form JOpenDialog */
    public JOpenDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        initComponents();
        addCustomListeners();
        setDefaultListCellRenderer(jResultList);
        moveToCenterOfScreen();
        
        selectDefaultProject();
        JTextComponent editor = EditorRegistry.lastFocusedComponent();
        
        Console console = System.console();
        System.out.println("Caret pos: " + editor.getCaretPosition());
        System.out.println("Selection start: " + editor.getSelectionStart());
        System.out.println("Selection end: " + editor.getSelectionEnd());
       

        String selectedText = "";
        try {
            selectedText = editor.getSelectedText();
        } catch (Exception e) {
            selectedText = "";
        }
        if (selectedText == null) {
            selectedText = "";
        }
        
        if (selectedText.length() == 0) {
            String mayQueryString = "";
            int p = editor.getCaretPosition();
            String t = "";

            int l = 1;
            int lpos = -1;
            while (true) {
                try {
                    t = editor.getText(p - l, 1);
                } catch (Exception e) {
                    lpos = -1;
                    break;
                }
                if (t.indexOf("'") >= 0) {
                    lpos = p - l + 1;
                    break;
                }
                if (t.indexOf("\"") >= 0) {
                    lpos = p - l + 1;
                    break;
                }
                l++;
                if (l > 250) {
                    break;
                }
            }
            
            l = 1;
            int rpos = -1;
            while (true) {
                try {
                    t = editor.getText(p + l, 1);
                } catch (Exception e) {
                    rpos = -1;
                    break;
                }
                if (t.indexOf("'") >= 0) {
                    rpos = p + l;
                    break;
                }
                if (t.indexOf("\"") >= 0) {
                    rpos = p + l;
                    break;
                }
                l++;
                if (l > 250) {
                    break;
                }
            }
            
            if ((lpos > 0) && (rpos >= 0) && (lpos < rpos)) {
                try {
                    mayQueryString = editor.getText(lpos, rpos - lpos);
                    
                    
                    System.out.println("lpos: " + lpos);
                    System.out.println("rpos: " + rpos);
                    System.out.println("mayQueryString: " + mayQueryString);
                    
                    
                    jQueryField.setText(mayQueryString);
                    updateResultList();
                } catch (Exception e) {
                   //log
                }
                
            }
         
        }
        
               
        if (selectedText.length() > 0) {
            jQueryField.setText(selectedText);
            updateResultList();
        }
    }
    
    
    
    private void selectDefaultProject() {
        try {
            Project openProjects[] = OpenProjects.getDefault().openProjects().get();
            
//            Console console = System.console();
//            System.out.println(openProjects.length);
            
            if (openProjects.length > 0) {
                Project project = openProjects[0];
                OpenProjects.getDefault().setMainProject(project);
                SearchData.getInstance().setSourceFolders(ProjectHelper.getSourceFolders(project));
            }
        } catch (Exception exception) {
            Exceptions.printStackTrace(exception);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jQueryField = new javax.swing.JTextField();
        jResultPane = new javax.swing.JScrollPane();
        jResultList = new javax.swing.JList();
        jSelectProjectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(JOpenDialog.class, "JOpenDialog.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jQueryField.setText(org.openide.util.NbBundle.getMessage(JOpenDialog.class, "JOpenDialog.jQueryField.text")); // NOI18N
        jQueryField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jQueryFieldKeyPressed(evt);
            }
        });

        jResultList.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jResultList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jResultListValueChanged(evt);
            }
        });
        jResultList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jResultListKeyPressed(evt);
            }
        });
        jResultPane.setViewportView(jResultList);

        jSelectProjectButton.setText(org.openide.util.NbBundle.getMessage(JOpenDialog.class, "JOpenDialog.jSelectProjectButton.text")); // NOI18N
        jSelectProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSelectProjectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jQueryField, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSelectProjectButton))
                    .addComponent(jResultPane, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jQueryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSelectProjectButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jResultPane, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jResultListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jResultListKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (jResultList.getSelectedIndex() == 0) {
                    jQueryField.requestFocus();
                    jResultList.clearSelection();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (jResultList.getSelectedIndex() == resultListModel.getIndexOfLastElement()) {
                    jQueryField.requestFocus();
                    jResultList.clearSelection();
                }
                break;
            case KeyEvent.VK_ENTER:
                openSelectedFiles();
                break;
        }
    }//GEN-LAST:event_jResultListKeyPressed

    private void jQueryFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jQueryFieldKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                jResultList.requestFocus();
                jResultList.setSelectedIndex(0);
                jResultPane.getVerticalScrollBar().setValue(0);
                break;
            case KeyEvent.VK_UP:
                jResultList.requestFocus();
                jResultList.setSelectedIndex(resultListModel.getIndexOfLastElement());
                jResultList.ensureIndexIsVisible(resultListModel.getIndexOfLastElement());
                break;
            case KeyEvent.VK_ENTER:
                if (resultListModel.size() == 1) {
                    jResultList.setSelectedIndex(0);
                    openSelectedFiles();
                }
        }
    }//GEN-LAST:event_jQueryFieldKeyPressed

private void jSelectProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSelectProjectButtonActionPerformed
    jQueryField.requestFocus();
    JSelectProjectDialog selectProjectDialog = new JSelectProjectDialog(this.parent, true);
    selectProjectDialog.setVisible(true);
}//GEN-LAST:event_jSelectProjectButtonActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.updateTitle(null);
    }//GEN-LAST:event_formWindowActivated

    private void jResultListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jResultListValueChanged
        this.updateTitle(null);
    }//GEN-LAST:event_jResultListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField jQueryField;
    private javax.swing.JList jResultList;
    private javax.swing.JScrollPane jResultPane;
    private javax.swing.JButton jSelectProjectButton;
    // End of variables declaration//GEN-END:variables

    private void addCustomListeners() {
        jQueryField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateResultList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateResultList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        jResultList.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    openSelectedFiles();
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        Container container = e.getComponent().getParent();
                        while (null != container && !container.getClass().equals(JOpenDialog.class)) {
                            container = container.getParent();
                        }
                        if (container != null) {
                            ((JOpenDialog) container).close();
                        }
                    }
                    if (e.getKeyCode() == KeyEvent.VK_R && e.getModifiers() == Event.CTRL_MASK) {
                        updateTitle(ResourceBundleHelper.getString("JOpenDialog.reloading"));
                        SearchData.getInstance().reload();
                        updateTitle(null);
                    }
                }
                return false;
            }
        });
    }

    private void updateResultList() {
        resultListModel.clear();
        String query = jQueryField.getText();
        ArrayList<FileEntry> fileList = SearchData.getInstance().getFileList();
        ArrayList<FileEntry> searchResults = SearchService.getInstance().search(fileList, query, MAX_DISPLAY_RESULTS);
        for (Iterator<FileEntry> it = searchResults.iterator(); it.hasNext();) {
            FileEntry fileEntry = it.next();
            resultListModel.addElement(fileEntry);
        }
        jResultList.setModel(resultListModel);
    }

    private void openSelectedFiles() {
        try {
            Object[] selectedValues = jResultList.getSelectedValues();
            for (int i = 0; i < selectedValues.length; i++) {
                String selectedPath = ((FileEntry) selectedValues[i]).getAbsolutePath();
                FileObject fileObject = FileUtil.toFileObject(new File(selectedPath).getAbsoluteFile());
                DataObject dataObject = DataObject.find(fileObject);
                Node node = dataObject.getNodeDelegate();
                javax.swing.Action action = node.getPreferredAction();
                if (action instanceof ContextAwareAction) {
                    action = ((ContextAwareAction) action).createContextAwareInstance(node.getLookup());
                }
                if (action != null) {
                    action.actionPerformed(new ActionEvent(node, ActionEvent.ACTION_PERFORMED, ""));
                }
            }
            this.close();
        } catch (Exception exception) {
            Exceptions.printStackTrace(exception);
        }
    }

    private void updateTitle(String text) {
        String title = ResourceBundleHelper.getString("JOpenDialog.title");
        String mainProjectTitle = ProjectHelper.getProjectName(OpenProjects.getDefault().getMainProject());
        if (mainProjectTitle != null) {
            title = mainProjectTitle;
        }
        if (jResultList.getSelectedIndices().length == 1) {
            title += " - " + ((FileEntry)jResultList.getSelectedValue()).getAbsolutePath();
        }
        if (text != null) {
            title = text;
        }
        this.setTitle(title);
    }
}
