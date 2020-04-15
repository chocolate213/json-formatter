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
 * MinifyJsonAction
 *
 * @author zhangjiaxing created on 2020-04-14
 */
public class MinifyJsonAction extends AnAction {

    private EditorTextPanel editorTextPanel;

    public static final String ACTION_TEXT = "Minify Json";

    public MinifyJsonAction() {
    }

    public MinifyJsonAction(EditorTextPanel editorTextPanel) {
        this.editorTextPanel = editorTextPanel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String text = editorTextPanel.getText();

        if (StringUtils.isBlank(text)) {
            return;
        }

        try {
            String minifiedJson = JsonUtils.minifyJson(text);
            editorTextPanel.setText(minifiedJson);
        } catch (JsonProcessingException jsonProcessingException) {
            String originalMessage = jsonProcessingException.getOriginalMessage();
            long charOffset = jsonProcessingException.getLocation().getCharOffset();

            EditorHintsNotifier.notifyError(Objects.requireNonNull(editorTextPanel.getEditor()), originalMessage, charOffset);
        }
    }
}
