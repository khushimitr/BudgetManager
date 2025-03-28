import lombok.*;
import java.util.*;
import java.time.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {

    private Long id;
    private User user;
    private String month;
    private String year;
    private BigDecimal budget;

}