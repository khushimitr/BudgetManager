package org.example.budgetmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.budgetmanager.models.dto.LoanDto;
import org.example.budgetmanager.repository.LoanRepository;
import org.example.budgetmanager.service.ExpenseService;
import org.example.budgetmanager.service.LoanService;
import org.example.budgetmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserService userService;

    @Autowired
    ExpenseService expenseService;

    @Override
    public void save(LoanDto loanDto) {

    }

//    @Transactional
//    @Override
//    public void save(LoanDto loanDto) {
//        List<Integer> toUserIds = loanDto.getToUserIds();
//        int noOfSharers = toUserIds.size();
//        List<BigDecimal> perUserAmount = loanDto.getSplitType().getSplits(loanDto.getAmount(), loanDto.getShares(), noOfSharers);
//        // user A spent 100rs split equally user a, user b, user c
//        // user A spent 100rs split percentage user a, user b, user c 50 20 30
//        // user A spent 100rs split exactly user a, user b, user c 10 80 10
//
//        User fromUser = userService.getUserById(loanDto.getFromUserId());
//        for (int i = 0; i < toUserIds.size(); i++) {
//            int toUserId = toUserIds.get(i);
//            User toUser = userService.getUserById(toUserId);
//
//            if (toUserId == loanDto.getFromUserId() && !Objects.equals(perUserAmount.getFirst(), BigDecimal.ZERO)) {
//                Expense expense = Expense.builder()
//                        .user(fromUser)
//                        .amount(perUserAmount.getFirst())
//                        .description(loanDto.getDescription())
//                        .expenseType(ExpenseType.EXPEND)
//                        .category(loanDto.getCategory())
//                        .build();
//
//                Expense savedExpense = expenseService.createExpense(expense);
//                log.debug("Expense Recorded for user : {} to his passbook, entry looks like : {}", fromUser, savedExpense);
//                continue;
//            }
//
//            String username = loanDto.getUsernames().size() > i ? loanDto.getUsernames().get(i) : null;
//            if (toUser != null) {
//                username = toUser.getUsername();
//            }
//            username = username.toLowerCase();
//            Loan loan = Loan.builder()
//                    .expenseType(loanDto.getExpenseType())
//                    .fromUser(fromUser)
//                    .toUser(toUser)
//                    .username(username)
//                    .amount(perUserAmount.get(i))
//                    .category(loanDto.getCategory())
//                    .description(loanDto.getDescription())
//                    .build();
//
//            Loan savedLoan = loanRepository.save(loan);
//            log.debug("Expense Recorded from user : {} to user : {} and entry looks like : {}", fromUser, toUser, savedLoan);
//
//            // TODO: Add logic for Balance entries
////            Balance bal = Balance.builder()
////                    .fromUser(loanDto.getFromUser())
////                    .toUser(toUser)
////                    .username(username)
////                    .amount(perUserAmount.get(i))
////                    .build();
//
//
//        }
//    }
}
