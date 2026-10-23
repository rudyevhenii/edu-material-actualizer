package dev.rudyevhenii.edu_material_actualizer.llm.openai.config;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class OpenAiChatClientConfig {

    @Value("classpath:/string-templates/llm/actualizationSystemPrompt.st")
    private Resource systemPromptResource;

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                                 List<McpSyncClient> mcpSyncClients) {
        List<ToolCallback> toolCallbacks = McpToolUtils.getToolCallbacksFromSyncClients(mcpSyncClients);

        return chatClientBuilder
                .defaultTools(toolCallbacks)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem(systemPromptResource)
                .build();
    }
}
