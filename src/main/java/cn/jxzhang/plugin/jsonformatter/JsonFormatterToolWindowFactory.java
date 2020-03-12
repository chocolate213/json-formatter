package cn.jxzhang.plugin.jsonformatter;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * JsonFormatterToolWindowFactory
 *
 * @author zhangjiaxing
 */
public class JsonFormatterToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JsonFormatterToolWindow window = new JsonFormatterToolWindow(project);
        ContentFactory factory = ContentFactory.SERVICE.getInstance();
        Content jsonFormatter = factory.createContent(window.getPanel(), "", true);
        toolWindow.getContentManager().addContent(jsonFormatter);
    }
}
