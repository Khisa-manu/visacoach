-- =============================================================================
-- Migration V2: Seed Visa Types & Subscription Plans for Kenya
-- =============================================================================

INSERT INTO visa_types (id, code, name, description, is_active) VALUES
('vt-b1b2', 'B1_B2', 'B1/B2 Visitor Visa', 'For temporary business (B1), tourism, visiting friends/family, or medical treatment (B2).', TRUE),
('vt-f1', 'F1', 'F1 Academic Student Visa', 'For students enrolled at an accredited U.S. college, university, or English language institute.', TRUE),
('vt-j1', 'J1', 'J1 Exchange Visitor Visa', 'For individuals participating in approved work-and-study-based exchange visitor programs.', TRUE)
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO subscription_plans (id, code, name, price_kes, duration_days, mock_interviews_limit, has_deep_evaluation, has_unlimited_practice, is_active) VALUES
('plan-free', 'FREE', 'Free Starter', 0.00, 365, 1, FALSE, FALSE, TRUE),
('plan-premium', 'PREMIUM', 'Visa Pro Complete (Kenya)', 1499.00, 30, 10, TRUE, TRUE, TRUE)
ON DUPLICATE KEY UPDATE name=VALUES(name);
