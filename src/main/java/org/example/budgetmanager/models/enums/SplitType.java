package org.example.budgetmanager.models.enums;

import org.example.budgetmanager.utils.Utils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public enum SplitType {
    EQUAL {
        @Override
        public List<BigDecimal> getSplits(BigDecimal amount, List<BigDecimal> splits, int n) {
            BigDecimal roundedAmount = Utils.processAmount(amount);
            BigDecimal dividedAmount = roundedAmount.divide(BigDecimal.valueOf(n));
            return Collections.nCopies(n, dividedAmount);
        }
    },
    EXACT {
        @Override
        public List<BigDecimal> getSplits(BigDecimal amount, List<BigDecimal> splits, int n) {
            splits = splits.stream().map(Utils::processAmount).toList();
            return splits;
        }
    },
    PERCENT {
        @Override
        public List<BigDecimal> getSplits(BigDecimal amount, List<BigDecimal> splits, int n) {
            splits = splits.stream().map(x -> {
                BigDecimal amt = (amount.multiply(x)).divide(BigDecimal.valueOf(100));
                amt = Utils.processAmount(amt);
                return amt;
            }).toList();
            return splits;
        }
    };

    public abstract List<BigDecimal> getSplits(BigDecimal amount, List<BigDecimal> splits, int n);
}
