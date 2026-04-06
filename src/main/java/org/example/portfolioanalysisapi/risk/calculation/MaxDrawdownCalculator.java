package org.example.portfolioanalysisapi.risk.calculation;


import org.example.portfolioanalysisapi.marketdata.PricePoint;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MaxDrawdownCalculator {




    public DrawdownResult calculateMaxDrawdown(List<PricePoint> pricePoints) {

        if (pricePoints.isEmpty()) {
            throw  new IllegalArgumentException("No values in list");
        }

        BigDecimal peak = pricePoints.get(0).getClosePrice();
        LocalDate currentPeakDate = pricePoints.get(0).getDate();

        BigDecimal maxDrawdown = BigDecimal.ZERO;

        BigDecimal maxDrawdownPeakValue = peak;
        BigDecimal maxDrawdownTroughValue = peak;

        LocalDate maxDrawdownPeakDate = currentPeakDate;
        LocalDate maxDrawdownTroughDate = currentPeakDate;

        for (int i = 1; i < pricePoints.size(); i++) {
            BigDecimal current = pricePoints.get(i).getClosePrice();
            LocalDate currentDate = pricePoints.get(i).getDate();

            if (current.compareTo(peak) > 0) {
                peak = current;
                currentPeakDate = currentDate;
            } else {
                BigDecimal newDrawdown = (current.subtract(peak).divide(peak, 6, RoundingMode.HALF_UP));

                if (newDrawdown.compareTo(maxDrawdown) < 0) {
                    maxDrawdown = newDrawdown;
                    maxDrawdownPeakValue = peak;
                    maxDrawdownTroughValue = current;
                    maxDrawdownPeakDate = currentPeakDate;
                    maxDrawdownTroughDate = currentDate;

                }
            }
        }

        return new DrawdownResult(maxDrawdown, maxDrawdownPeakValue, maxDrawdownTroughValue, maxDrawdownPeakDate, maxDrawdownTroughDate);
    }
}
