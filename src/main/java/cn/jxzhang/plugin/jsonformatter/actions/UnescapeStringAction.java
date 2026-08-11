package cn.jxzhang.plugin.jsonformatter.actions;

import cn.jxzhang.plugin.jsonformatter.panel.EditorTextPanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.text.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * UnescapeJsonAction.
 *
 * @author zhangjiaxing created on 2020-12-03
 */
public class UnescapeStringAction extends AnAction {

    private EditorTextPanel editorTextPanel;

    public static final String ACTION_TEXT = "Unescape String";

    public UnescapeStringAction() {}

    public UnescapeStringAction(EditorTextPanel editorTextPanel) {
        this.editorTextPanel = editorTextPanel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String editorContent = editorTextPanel.getText();

        if (StringUtils.isBlank(editorContent)) {
            return;
        }

        String unescapedContent = StringUtil.unescapeStringCharacters(editorContent);
        editorTextPanel.setText(unescapedContent);
    }
}
