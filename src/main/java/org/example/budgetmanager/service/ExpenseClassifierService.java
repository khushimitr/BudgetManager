package org.example.budgetmanager.service;

import org.example.budgetmanager.mappers.ExpenseMapper;
import org.example.budgetmanager.models.dto.RequestDTOs.ExpenseRequestDto;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseClassifierService {

    @Autowired
    MistralAiChatModel chatModel;

    @Autowired
    ExpenseMapper expenseMapper;


    private final String SYSTEM_PROMPT = """
            You are an expense categorization assistant. Return ONLY 'Type,Amount' using these categories: Housing, Utilities, Groceries, Dining, Transportation, Shopping, Cosmetic, Pets, Healthcare, Entertainment, Travel, Income, Investments, Subscriptions, Miscellaneous. Rules: 1) Extract the largest explicit amount (ignore quantities like '2 shoes'). 2) Convert formats (12K→12000). 3) Respond ONLY with 'Type,Amount' with no other text.
            """;


    public ExpenseRequestDto categorize(String userInput) {
        Prompt prompt = new Prompt(
                List.of(
                        new SystemPromptTemplate(SYSTEM_PROMPT).createMessage(),
                        new UserMessage(userInput)
                )
        );
        String result = chatModel.call(prompt).getResult().getOutput().getText();
        return expenseMapper.toRequestDto(result, userInput);
    }


}
