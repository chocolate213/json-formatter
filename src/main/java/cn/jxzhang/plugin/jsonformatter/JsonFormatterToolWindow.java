package cn.jxzhang.plugin.jsonformatter;

import cn.jxzhang.plugin.jsonformatter.panel.EditorTextPanel;
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

    private final JPanel panel;

    private final EditorTextPanel editorTextPanel;

    public JsonFormatterToolWindow(Project project) {
        this.panel = new JPanel(new GridLayout(0, 1));
        this.editorTextPanel = createEditorTextPanel(project);

        init();
    }

    private void init() {
        this.panel.add(editorTextPanel);
    }

    private EditorTextPanel createEditorTextPanel(Project project) {
        return new EditorTextPanel(project, JsonFileType.INSTANCE);
    }

    public EditorTextPanel getEditorTextPanel() {
        return editorTextPanel;
    }

    public JPanel getPanel() {
        return panel;
    }
}
