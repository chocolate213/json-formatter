package cn.jxzhang.plugin.jsonformatter.actions;

import cn.jxzhang.plugin.jsonformatter.panel.EditorTextPanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * UnescapeJsonAction.
 *
 * @author zhangjiaxing created on 2020-12-03
 */
public class EscapeStringAction extends AnAction {

    private EditorTextPanel editorTextPanel;

    public static final String ACTION_TEXT = "Escape String";

    public EscapeStringAction() {}

    public EscapeStringAction(EditorTextPanel editorTextPanel) {
        this.editorTextPanel = editorTextPanel;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void actionPerformed(@NotNull AnActionEvent e) {
        String editorContent = editorTextPanel.getText();

        if (StringUtils.isBlank(editorContent)) {
            return;
        }

        String unescapedContent = StringEscapeUtils.escapeJava(editorContent);
        editorTextPanel.setText(unescapedContent);
    }
}
