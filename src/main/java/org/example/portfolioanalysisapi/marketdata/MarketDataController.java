
package org.example.portfolioanalysisapi.marketdata;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/marketdata")
public class MarketDataController {

    final private MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }



    @GetMapping("/{ticker}")
    public ResponseEntity<List<PricePoint>> getPriceHistory(@PathVariable String ticker) {
        List<PricePoint> list = marketDataService.getPriceHistory(ticker);
        return ResponseEntity.ok(list);
    }

}
