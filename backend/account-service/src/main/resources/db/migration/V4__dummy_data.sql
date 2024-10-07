-- linked_account 테이블에 더미 데이터 삽입
INSERT INTO linked_account (account_id, bank_name, account_number)
VALUES
(1, '싸피은행', '9991988005402710'),
(2, '싸피은행', '9991988005402710');
-- recurring_transaction 테이블에 더미 데이터 삽입
INSERT INTO recurring_transaction (account_id, amount, payment_date, next_transaction_date, status)
VALUES
(1, 1000000, 7, '2024-10-07', '활성'),
(2, 10000, 1, '2024-09-26', '활성');

-- pet_to_account 테이블에 더미 데이터 삽입
INSERT INTO pet_to_account (pet_id, account_id)
VALUES
(1, 1),
(1, 2),
(2, 2);

