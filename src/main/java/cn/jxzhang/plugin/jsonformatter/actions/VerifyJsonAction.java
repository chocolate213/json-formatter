package cn.jxzhang.plugin.jsonformatter.actions;

import cn.jxzhang.plugin.jsonformatter.notification.EditorHintsNotifier;
import cn.jxzhang.plugin.jsonformatter.panel.EditorTextPanel;
import cn.jxzhang.plugin.jsonformatter.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * VerifyJsonAction
 *
 * @author zhangjiaxing created on 2020-04-15
 */
public class VerifyJsonAction extends AnAction {

    public static final String VALID_JSON_MSG = "Valid Json";

    public static final String INVALID_JSON_MSG = "Invalid Json";

    public static final String ACTION_TEXT = "Verify Json";

    private EditorTextPanel editorTextPanel;

    public VerifyJsonAction() {
    }

    public VerifyJsonAction(final EditorTextPanel editorTextPanel) {
        this.editorTextPanel = editorTextPanel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String editorContent = editorTextPanel.getText();

        if (StringUtils.isBlank(editorContent)) {
            return;
        }

        try {
            JsonUtils.verifyJson(editorContent);
        } catch (JsonProcessingException jsonProcessingException) {
            String originalMessage = jsonProcessingException.getOriginalMessage();
            originalMessage = INVALID_JSON_MSG.concat(": ").concat(originalMessage);
            long charOffset = jsonProcessingException.getLocation().getCharOffset();

            EditorHintsNotifier.notifyError(Objects.requireNonNull(editorTextPanel.getEditor()), originalMessage, charOffset);
            return;
        }

        EditorHintsNotifier.notifyInfo(Objects.requireNonNull(editorTextPanel.getEditor()), VALID_JSON_MSG);
    }
}