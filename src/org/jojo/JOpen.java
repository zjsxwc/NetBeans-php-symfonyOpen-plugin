package org.jojo;

import org.jojo.search.SearchData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import org.jojo.helper.ProjectHelper;
import org.jojo.search.FileEntry;
import org.netbeans.api.project.ui.OpenProjects;

public final class JOpen implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        SearchData.getInstance().setSourceFolders(ProjectHelper.getSourceFolders(OpenProjects.getDefault().getMainProject()));
        
        ArrayList<FileEntry> rl = JOpenDialog.findResult();
        if (rl.size() == 1) {
            String ap = rl.get(0).getAbsolutePath();
            JOpenDialog.openFileByAbsoluteFilePath(ap);
        } else {
            JOpenDialog dialog = new JOpenDialog(null, true);
            dialog.setVisible(true);
        }
    }
}
