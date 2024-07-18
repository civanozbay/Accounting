package com.accounting.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DashboardService {

    Map<String, BigDecimal> getAllSummaryNumbers();
}
