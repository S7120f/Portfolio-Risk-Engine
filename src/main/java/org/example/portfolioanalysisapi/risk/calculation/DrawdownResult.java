package org.example.portfolioanalysisapi.risk.calculation;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DrawdownResult(
        BigDecimal maxDrawdown,
        BigDecimal peakValue,
        BigDecimal troughValue,
        LocalDate peakDate,
        LocalDate troughDate

) {
}
