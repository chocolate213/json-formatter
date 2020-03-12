package cn.jxzhang.plugin.jsonformatter;

import com.intellij.json.JsonFileType;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;

/**
 * JsonFormatterToolWindow
 *
 * @author zhangjiaxing
 */
public class JsonFormatterToolWindow {

    private JPanel panel;

    public JsonFormatterToolWindow(Project project) {
        this.panel = new JPanel(new GridLayout(0, 1));
        this.panel.add(getEditorTextPanel(project));
    }

    private Component getEditorTextPanel(Project project) {
        return new EditorTextPanel(project, JsonFileType.INSTANCE);
    }

    public JPanel getPanel() {
        return panel;
    }
}
