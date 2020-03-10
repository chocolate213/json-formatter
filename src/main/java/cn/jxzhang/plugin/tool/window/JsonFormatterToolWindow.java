package cn.jxzhang.plugin.tool.window;

import com.intellij.json.JsonFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.ui.EditorTextField;

import javax.swing.*;

/**
 * JsonFormatterToolWindow
 *
 * @author zhangjiaxing
 */
public class JsonFormatterToolWindow {
    private JPanel panel;

    public JsonFormatterToolWindow(Project project) {
        this.panel = new JPanel(new VerticalFlowLayout());
        EditorTextField editorTextField = new EditorTextField(project, JsonFileType.INSTANCE);

        editorTextField.setFileType(JsonFileType.INSTANCE);
        editorTextField.setOneLineMode(false);

        editorTextField.setAutoscrolls(true);

        this.panel.add(editorTextField);
    }

    public JPanel getPanel() {
        return panel;
    }
}
