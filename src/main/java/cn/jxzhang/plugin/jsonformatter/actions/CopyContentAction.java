package cn.jxzhang.plugin.jsonformatter.actions;

import cn.jxzhang.plugin.jsonformatter.notification.EditorHintsNotifier;
import cn.jxzhang.plugin.jsonformatter.panel.EditorTextPanel;
import cn.jxzhang.plugin.jsonformatter.utils.SystemUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * CopyContentAction
 *
 * @author zhangjiaxing created on 2020-04-14
 */
public class CopyContentAction extends AnAction {

    public static final String ACTION_TEXT = "Copy Editor Content to Clipboard";

    private static final String COPY_CONTENT_SUCCESS_MSG = "Copy content to Clipboard success.";

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
            EditorHintsNotifier.notifyInfo(Objects.requireNonNull(editorTextPanel.getEditor()), COPY_CONTENT_SUCCESS_MSG);
        }
    }
}
