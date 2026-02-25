package org.example.portfolioanalysisapi.repository;

import org.example.portfolioanalysisapi.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}
