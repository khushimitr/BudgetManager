package org.example.budgetmanager.service;

//import org.example.budgetmanager.mappers.ExpenseMapper;
//import org.example.budgetmanager.models.dto.ExpenseClassifierResponse;
//import org.example.budgetmanager.models.enums.Category;
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.prompt.Prompt;
//import org.springframework.ai.chat.prompt.SystemPromptTemplate;
//import org.springframework.ai.mistralai.MistralAiChatModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Service
//public class ExpenseClassifierService {
//
//    @Autowired
//    MistralAiChatModel chatModel;
//
//    @Autowired
//    ExpenseMapper expenseMapper;
//
//
//    private final String SYSTEM_PROMPT = """
//            You are an expense categorization assistant. Return ONLY 'Type,Amount' using these categories: Housing, Utilities, Groceries, Dining, Transportation, Shopping, Cosmetic, Pets, Healthcare, Entertainment, Travel, Income, Investments, Subscriptions, Miscellaneous. Rules: 1) Extract the largest explicit amount (ignore quantities like '2 shoes'). 2) Convert formats (12K→12000). 3) Respond ONLY with 'Type,Amount' with no other text.
//            """;
//
//
//    public ExpenseClassifierResponse categorize(String userInput) {
//        Prompt prompt = new Prompt(
//                List.of(
//                        new SystemPromptTemplate(SYSTEM_PROMPT).createMessage(),
//                        new UserMessage(userInput)
//                )
//        );
//        String result = chatModel.call(prompt).getResult().getOutput().getText();
//        System.out.println("RESULT : " + result);
//        String[] split = result.split("\n");
//        split = split[0].split(",");
//        Category category = Category.valueOf(split[0].trim());
//        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(split[1]));
//
//        return ExpenseClassifierResponse.builder()
//                .amount(amount)
//                .category(category)
//                .build();
//    }
//
//
//}


import org.example.budgetmanager.models.dto.ExpenseClassifierResponse;
import org.example.budgetmanager.models.enums.Category;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ExpenseClassifierService {

    @Autowired
    ChatModel chatModel; // Use ChatModel or MistralAiChatModel as appropriate

    // @Autowired
    // ExpenseMapper expenseMapper; // Assuming you need this later

    // Use the revised prompt
    private final String SYSTEM_PROMPT = """
            Role: Expense categorizer.
            Output: Respond ONLY with 'Type,Amount'. No extra text.
            Categories: Housing, Utilities, Groceries, Dining, Transportation, Shopping, Cosmetic, Pets, Healthcare, Entertainment, Travel, Income, Investments, Subscriptions, Miscellaneous.
            Rules:
            1. Pick best category from list. Default: Miscellaneous.
            2. Calculate total amount. If qty & price given (e.g., '2x50'), multiply. If single number ('50 milk'), use it. Handle 'k'=1000. If no amount, use 0.
            Examples: '2 milk of 50' -> Groceries,100 | 'rent 1k' -> Housing,1000 | 'shoes' -> Shopping,0
            Process Input:
            """;

    // Define the expected output pattern using Regex
    // Allows for optional whitespace around comma and captures category and amount
    private static final Pattern OUTPUT_PATTERN = Pattern.compile(
            "([A-Za-z]+)\\s*,\\s*([0-9]+(?:\\.[0-9]+)?)");

    public ExpenseClassifierResponse categorize(String userInput) {
        Prompt prompt = new Prompt(
                List.of(
                        // Use SystemMessage or specific implementation if needed
                        new SystemMessage(SYSTEM_PROMPT),
                        new UserMessage(userInput)
                )
        );

        // It's good practice to specify model options if possible,
        // like temperature=0 for more deterministic output
        // ChatOptions options = MistralAiChatOptions.builder().withTemperature(0.0f).build();
        // String result = chatModel.call(new Prompt(messages, options))...

        String rawResult = chatModel.call(prompt).getResult().getOutput().getText(); // Use getContent()
        System.out.println("Raw AI Result: " + rawResult);

        // Use regex to find the pattern in the result
        Matcher matcher = OUTPUT_PATTERN.matcher(rawResult);

        if (matcher.find()) {
            try {
                String categoryStr = matcher.group(1).trim();
                String amountStr = matcher.group(2).trim();

                Category category = Category.valueOf(categoryStr); // Assumes Category enum names match exactly (case-sensitive)
                BigDecimal amount = new BigDecimal(amountStr);

                return ExpenseClassifierResponse.builder()
                        .amount(amount)
                        .category(category)
                        .build();

            } catch (IllegalArgumentException e) {
                // Handle cases where category string is invalid or amount is not a number
                System.err.println("Error parsing AI response: " + rawResult + " - " + e.getMessage());
                // Return a default/error response or throw a custom exception
                return ExpenseClassifierResponse.builder()
                        .category(Category.Miscellaneous) // Default category
                        .amount(BigDecimal.ZERO)          // Default amount
                        .build();
            }
        } else {
            // Handle cases where the pattern was not found in the AI response
            System.err.println("Could not find 'Type,Amount' pattern in AI response: " + rawResult);
            return ExpenseClassifierResponse.builder()
                    .category(Category.Miscellaneous)
                    .amount(BigDecimal.ZERO)
                    .build();
        }
    }
}