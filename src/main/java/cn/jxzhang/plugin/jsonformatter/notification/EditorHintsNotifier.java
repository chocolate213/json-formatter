package cn.jxzhang.plugin.jsonformatter.notification;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.ScrollingModel;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * EditorHintsNotifier
 *
 * @author zhangjiaxing created on 2020-04-15
 */
public class EditorHintsNotifier {

    private EditorHintsNotifier() {
    }

    public static void notify(@NotNull Editor editor, @NotNull String message, long position) {
        if (StringUtils.isBlank(message)) {
            return;
        }

        if (position > editor.getDocument().getTextLength() || position < 0) {
            return;
        }

        // move caret to position, and scroll to the caret
        editor.getCaretModel().moveToOffset((int) position);
        ScrollingModel scrollingModel = editor.getScrollingModel();
        scrollingModel.scrollToCaret(ScrollType.MAKE_VISIBLE);

        scrollingModel.runActionOnScrollingFinished(() -> HintManager.getInstance().showErrorHint(Objects.requireNonNull(editor), message));
    }
}
