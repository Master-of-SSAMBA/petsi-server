-- account 테이블에 더미 데이터 삽입
INSERT INTO account (account_id, account_no, balance, created_at, interest_rate, maturity_date, name, password, status, updated_at, user_id, account_product_id, user_key)
VALUES
(998, '9990577647454346', 0, '2024-09-12 16:57:03.000000', 1.0, '2044-09-12', '단지 생일선물 통장', '1234', '활성', '2024-09-12 16:57:03.000000', 1, 0, 'f945d69e-354b-4623-8ce1-48d8f6ef6240'),
(999, '9999996327714330', 0, '2024-09-24 00:57:03.000000', 1.0, '2044-09-12', '의료비 통장', '1234', '활성', '2024-09-25 16:57:03.000000', 1, 0, 'f945d69e-354b-4623-8ce1-48d8f6ef6240');
-- linked_account 테이블에 더미 데이터 삽입
INSERT INTO linked_account (linked_account_id, account_id, bank_name, account_number)
VALUES
(998, 998, '싸피은행', '9991988005402710'),
(999, 999, '싸피은행', '9991988005402710');
-- recurring_transaction 테이블에 더미 데이터 삽입
INSERT INTO recurring_transaction (recurring_transaction_id, account_id, amount, payment_date, next_transaction_date, status)
VALUES
(998, 998, 1000000, 7, '2024-10-07', '활성'),
(999, 999, 10000, 1, '2024-09-26', '활성');

-- pet_to_account 테이블에 더미 데이터 삽입
INSERT INTO pet_to_account (pet_to_account_id, pet_id, account_id)
VALUES
(997, 998, 998),
(998, 998, 999),
(999, 999, 999);

