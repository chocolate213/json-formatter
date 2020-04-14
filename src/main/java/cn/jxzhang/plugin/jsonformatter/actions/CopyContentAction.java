package cn.jxzhang.plugin.jsonformatter.actions;

import cn.jxzhang.plugin.jsonformatter.notification.JsonFormatterNotifier;
import cn.jxzhang.plugin.jsonformatter.panel.EditorTextPanel;
import cn.jxzhang.plugin.jsonformatter.utils.SystemUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * CopyContentAction
 *
 * @author zhangjiaxing created on 2020-04-14
 */
public class CopyContentAction extends AnAction {

    public static final String ACTION_TEXT = "Copy Editor Content to Clipboard";

    private EditorTextPanel editorTextPanel;

    public CopyContentAction() {}

    public CopyContentAction(EditorTextPanel editorTextPanel) {
        this.editorTextPanel = editorTextPanel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String editorContent = editorTextPanel.getText();

        if (StringUtils.isNotBlank(editorContent)) {
            SystemUtils.copyToClipboard(editorContent);
            JsonFormatterNotifier.notify("Copy content to Clipboard success.");
        }
    }
}
